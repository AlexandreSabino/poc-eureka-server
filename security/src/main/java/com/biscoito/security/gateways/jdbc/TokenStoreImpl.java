package com.biscoito.security.gateways.jdbc;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;

@Component
public class TokenStoreImpl extends JwtTokenStore {

    private final JdbcTokenStore jdbcTokenStore;

    private final JwtAccessTokenConverter jwtTokenEnhancer;

    public TokenStoreImpl(final JwtAccessTokenConverter jwtTokenEnhancer, final DataSource dataSource) {
        super(jwtTokenEnhancer);
        this.jwtTokenEnhancer = jwtTokenEnhancer;
        this.jdbcTokenStore = new JdbcTokenStore(dataSource);
    }

    @Override
    public void storeAccessToken(final OAuth2AccessToken token, final OAuth2Authentication authentication) {
        final Collection<OAuth2AccessToken> tokens = this.findTokensByClientId(authentication.getOAuth2Request().getClientId());
        tokens.forEach(this::removeAccessToken);
        this.jdbcTokenStore.storeAccessToken(token, authentication);
    }

    @Override
    public void removeAccessToken(final OAuth2AccessToken token) {
        this.jdbcTokenStore.removeAccessToken(token);
    }

    @Override
    public void storeRefreshToken(final OAuth2RefreshToken refreshToken, final OAuth2Authentication authentication) {
        this.jdbcTokenStore.storeRefreshToken(refreshToken, authentication);
    }

    @Override
    public void removeRefreshToken(final OAuth2RefreshToken token) {
        this.jdbcTokenStore.removeRefreshToken(token);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(final OAuth2RefreshToken refreshToken) {
        this.jdbcTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(final String clientId) {
        return this.jdbcTokenStore.findTokensByClientId(clientId);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        final OAuth2AccessToken oAuth2AccessTokenDataBase = this.jdbcTokenStore.readAccessToken(tokenValue);
        OAuth2AccessToken accessTokenMemory = super.readAccessToken(tokenValue);
        if ((oAuth2AccessTokenDataBase != null) && (oAuth2AccessTokenDataBase.equals(accessTokenMemory))) {
            return accessTokenMemory;
        }
        return null;
    }
}