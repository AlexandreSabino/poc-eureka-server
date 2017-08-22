package com.biscoito.security.gateways;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

@Component
public class ClientDetailsImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        final BaseClientDetails clientDetails = new BaseClientDetails(
                "biscoito", "", "read,write", "client_credentials,refresh_token", ""
        );

        clientDetails.setClientSecret("teste123");
        return clientDetails;
    }
}
