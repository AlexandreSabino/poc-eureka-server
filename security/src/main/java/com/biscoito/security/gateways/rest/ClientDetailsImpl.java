package com.biscoito.security.gateways.rest;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClientDetailsImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //Tenta pegar do cache
        //Se nao existe chama o endpoint cria o objeto e coloca no cache
        final BaseClientDetails clientDetails = new BaseClientDetails(
                "biscoito", "", "read,write", "client_credentials,refresh_token", ""
        );

        clientDetails.setClientSecret("teste123");
        clientDetails.setAccessTokenValiditySeconds(30);
        clientDetails.setRefreshTokenValiditySeconds(60);
        clientDetails.addAdditionalInformation("casObject", createMap());
        return clientDetails;
    }

    private Object createMap() {
        final Map<String, Object> json = new HashMap<>();
        json.put("usuario", "biscoito");
        json.put("empresa", createEmpresa());
        json.put("perfil", "dev");
        return json;
    }

    private Object createEmpresa() {
        final Map<String, Object> json = new HashMap<>();
        json.put("cnpj", "11111111");
        json.put("nome", "teste");
        return json;
    }
}
