package com.biscoito.security.config;

import com.biscoito.security.converters.CustomJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.provider.ClientDetailsService;

@Configuration
public class JwtAccessTokenConverterConfig {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public CustomJwtAccessTokenConverter jwtAccessTokenConverter() {
        CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter(clientDetailsService);
        converter.setSigningKey("12345");
        converter.setVerifier(new MacSigner("12345"));
        return converter;
    }
}
