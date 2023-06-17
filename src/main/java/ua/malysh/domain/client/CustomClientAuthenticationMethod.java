package ua.malysh.domain.client;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Embeddable
@NoArgsConstructor
public class CustomClientAuthenticationMethod {
    public static final CustomClientAuthenticationMethod CLIENT_SECRET_BASIC =
            new CustomClientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
    public CustomClientAuthenticationMethod(ClientAuthenticationMethod authenticationMethod) {
        this.value = authenticationMethod.getValue();
    }

    private String value;

    public ClientAuthenticationMethod getClientAuthenticationMethod() {
        return new ClientAuthenticationMethod(this.value);
    }

    public String getValue() {
        return value;
    }
}
