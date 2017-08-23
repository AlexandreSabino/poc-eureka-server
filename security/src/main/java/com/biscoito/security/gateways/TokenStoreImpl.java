package com.biscoito.security.gateways;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
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

    public TokenStoreImpl(final JwtAccessTokenConverter jwtTokenEnhancer, final DataSource dataSource) {
        super(jwtTokenEnhancer);
        this.jdbcTokenStore = new JdbcTokenStore(dataSource);
    }

    @Override
    public void storeAccessToken(final OAuth2AccessToken token, final OAuth2Authentication authentication) {
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
}