package com.biscoito.security.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@RequiredArgsConstructor
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private final ClientDetailsService clientDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final OAuth2AccessToken oAuth2AccessToken = super.enhance(accessToken, authentication);
        final ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId());
        oAuth2AccessToken.getAdditionalInformation().putAll(clientDetails.getAdditionalInformation());
        return oAuth2AccessToken;
    }
}
