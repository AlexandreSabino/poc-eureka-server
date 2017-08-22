package com.biscoito.security.config;

import com.biscoito.security.gateways.TokenStoreImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class TokenStoreConfig {

    @Bean
    public TokenStore tokenStore() {
        return new TokenStoreImpl(new JwtAccessTokenConverter());
    }
}
