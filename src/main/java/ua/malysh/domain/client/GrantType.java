package ua.malysh.domain.client;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Embeddable
@NoArgsConstructor
public class GrantType {
    public static final GrantType AUTHORIZATION_CODE = new GrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
    public static final GrantType CLIENT_CREDENTIALS = new GrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
    public static final GrantType REFRESH_TOKEN = new GrantType(AuthorizationGrantType.REFRESH_TOKEN);

    private String value;

    public GrantType(AuthorizationGrantType grantType) {
        this.value = grantType.getValue();
    }

    public AuthorizationGrantType getAuthorizationGrantType() {
        return new AuthorizationGrantType(this.value);
    }

    public String getValue() {
        return value;
    }
}
