package ua.malysh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.malysh.model.SecuredUser;
import ua.malysh.repository.UserRepository;
import ua.malysh.service.exceptions.UserNotFoundException;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    public static final String USER_NOT_FOUND_MESSAGE = "User '%s' not found";

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(SecuredUser::new)
                .orElseThrow(userNotFoundExceptionSupplier(username));
    }

    private static Supplier<UserNotFoundException> userNotFoundExceptionSupplier(String username) {
        return () -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username));
    }
}
