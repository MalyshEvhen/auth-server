package ua.malysh;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ua.malysh.domain.user.User;
import ua.malysh.repository.UserRepository;
import ua.malysh.util.constants.Roles;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServerApplication {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

     @PostConstruct
     public void init() {

         var user = new User();
         user.setIdentifier(UUID.randomUUID());
         user.setEmail("email@gmail.com");
         user.setUsername("user");
         user.setPassword("password");
         user.addAuthorities(Roles.ADMIN, Roles.USER);

         userRepository.save(user);
     }

}
