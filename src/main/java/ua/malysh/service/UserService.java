package ua.malysh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.malysh.dto.UserRegistrationForm;
import ua.malysh.mapper.UserMapper;
import ua.malysh.model.SecuredUser;
import ua.malysh.repository.UserRepository;
import ua.malysh.service.exceptions.EmailAlreadyExistsException;
import ua.malysh.service.exceptions.UserNotFoundException;
import ua.malysh.service.exceptions.UsernameAlreadyExistsException;
import ua.malysh.util.constants.Roles;
import ua.malysh.util.constants.Permissions;

import java.util.function.Supplier;

import static ua.malysh.util.constants.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(SecuredUser::new)
                .orElseThrow(userNotFoundExceptionSupplier());
    }

    private static Supplier<UserNotFoundException> userNotFoundExceptionSupplier() {
        return () -> new UserNotFoundException(USER_NOT_FOUND);
    }

    @Transactional
    public Long saveUser(UserRegistrationForm userRegistrationForm) {
        checkUniqueFields(userRegistrationForm);

        var user = UserMapper.toUser(userRegistrationForm);
        user.addAuthorities(Roles.USER, Permissions.READ, Permissions.WRITE);

        var savedUser = repository.save(user);
        return savedUser.getId();
    }

    @PreAuthorize("hasAuthority('permission:write') && hasRole('ADMIN')")
    @Transactional
    public Long saveAdminUser(UserRegistrationForm userRegistrationForm) {
        checkUniqueFields(userRegistrationForm);

        var user = UserMapper.toUser(userRegistrationForm);
        user.addAuthorities(Roles.ADMIN, Permissions.READ, Permissions.WRITE);

        var savedUser = repository.save(user);
        return savedUser.getId();
    }

    private void checkUniqueFields(UserRegistrationForm userRegistrationForm) {
        if (isEmailExists(userRegistrationForm.email()))
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        if (isUsernameExists(userRegistrationForm.username()))
            throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
    }

    private boolean isEmailExists(String email) {
        return repository.existsByEmail(email);
    }

    private boolean isUsernameExists(String username) {
        return repository.existsByUsername(username);
    }
}
