package ua.malysh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.malysh.domain.client.Client;
import ua.malysh.domain.client.CustomClientAuthenticationMethod;
import ua.malysh.domain.client.GrantType;
import ua.malysh.repository.ClientRepository;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService implements RegisteredClientRepository {
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        var client = new Client();

        var customClientAuthenticationMethods = registeredClient.getClientAuthenticationMethods()
                .stream()
                .map(CustomClientAuthenticationMethod::new)
                .collect(Collectors.toSet());
        client.setCustomClientAuthenticationMethods(customClientAuthenticationMethods);

        var grantTypes = registeredClient.getAuthorizationGrantTypes()
                .stream()
                .map(GrantType::new)
                .collect(Collectors.toSet());
        client.setGrantTypes(grantTypes);

        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setScopes(registeredClient.getScopes());
        client.setRedirectUrls(registeredClient.getRedirectUris());

        clientRepository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        var optionClient = clientRepository.findById(Long.parseLong(id));

        return optionClient.map(Client::toRegisteredClient)
                .orElseThrow();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var optionClient = clientRepository.findByClientId(clientId);

        return optionClient.map(Client::toRegisteredClient)
                .orElseThrow();
    }
}
