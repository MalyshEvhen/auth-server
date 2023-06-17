package ua.malysh.domain.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Entity
@Table(name = "clients")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_id",
            nullable = false,
            unique = true)
    private String clientId;

    @Column(name = "client_secret",
            nullable = false,
            unique = true)
    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_authentication_methods",
            joinColumns = @JoinColumn(name = "id"))
    @Column(name = "client_authentication_methods")
    @Enumerated(EnumType.STRING)
    private Set<CustomClientAuthenticationMethod> customClientAuthenticationMethods = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "grant_types",
            joinColumns = @JoinColumn(name = "id"))
    @Column(name = "grant_types")
    @Enumerated(EnumType.STRING)
    private Set<GrantType> grantTypes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "redirect_urls",
            joinColumns = @JoinColumn(name = "id"))
    @Column(name = "redirect_urls")
    private Set<String> redirectUrls = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "scopes",
            joinColumns = @JoinColumn(name = "id"))
    @Column(name = "scopes")
    private Set<String> scopes = new HashSet<>();

    @Embedded
    @Column(name = "client_token_settings")
    private ClientTokenSettings clientTokenSettings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return getId() != null && Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public RegisteredClient toRegisteredClient() {
        return RegisteredClient
                .withId(String.valueOf(this.id))
                .clientSecret(this.clientSecret)
                .clientAuthenticationMethods(clientAuthenticationMethods(this.customClientAuthenticationMethods))
                .authorizationGrantTypes(authorizationGrantTypes(this.grantTypes))
                .redirectUris(uris -> uris.addAll(this.redirectUrls))
                .scopes(sc -> sc.addAll(this.scopes))
                .tokenSettings(TokenSettings
                        .builder()
                        .accessTokenTimeToLive(Duration.ofHours(this.clientTokenSettings.getAccessTokenTTL()))
                        .accessTokenFormat(new OAuth2TokenFormat(this.clientTokenSettings.getType()))
                        .build())
                .build();
    }

    private Consumer<Set<AuthorizationGrantType>> authorizationGrantTypes(Set<GrantType> grantTypes) {
        return types -> {
            for (var grantType : grantTypes) {
                var authorizationGrantType = grantType.getAuthorizationGrantType();
                types.add(authorizationGrantType);
            }
        };
    }

    private Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethods(
            Set<CustomClientAuthenticationMethod> customClientAuthenticationMethods) {
        return methods -> {
            for (var customAuthenticationMethod : customClientAuthenticationMethods) {
                var clientAuthenticationMethod = customAuthenticationMethod.getClientAuthenticationMethod();
                methods.add(clientAuthenticationMethod);
            }
        };
    }
}
