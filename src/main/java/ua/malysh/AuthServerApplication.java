package ua.malysh;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.malysh.domain.client.Client;
import ua.malysh.domain.client.ClientTokenSettings;
import ua.malysh.domain.client.CustomClientAuthenticationMethod;
import ua.malysh.domain.client.GrantType;
import ua.malysh.domain.user.Role;
import ua.malysh.domain.user.User;
import ua.malysh.repository.ClientRepository;
import ua.malysh.repository.UserRepository;

import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServerApplication {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        var client = new Client();
        client.setClientId("client");
        client.setClientSecret("secret");
        client.setScopes(Set.of("openid"));
        client.setRedirectUrls(Set.of("http://example.com/auth"));
        client.setGrantTypes(
                Set.of(GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS, GrantType.REFRESH_TOKEN));
        client.setCustomClientAuthenticationMethods(
                Set.of(CustomClientAuthenticationMethod.CLIENT_SECRET_BASIC));
        client.setClientTokenSettings(new ClientTokenSettings(5L, "REFERENCE"));

        clientRepository.save(client);

        var user = new User();
        user.setIdentifier(UUID.randomUUID());
        user.setEmail("email@gmail.com");
        user.setUsername("foo");
        user.setPassword("bar");
        user.setRoles(Set.of(Role.ADMIN, Role.USER));

        userRepository.save(user);
    }

}
