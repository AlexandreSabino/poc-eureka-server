package com.biscoito.security.config;

import com.biscoito.security.converters.CustomJwtAccessTokenConverter;
import com.biscoito.security.gateways.rest.ClientDetailsImpl;
import com.biscoito.security.gateways.rest.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ClientDetailsImpl clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomJwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
        final OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter = new OAuth2AuthenticationProcessingFilter();
        oAuth2AuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        security.addTokenEndpointAuthenticationFilter(oAuth2AuthenticationProcessingFilter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore);
        endpoints.setClientDetailsService(clientDetailsService);
        endpoints.userDetailsService(userDetailsService);
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
        endpoints.tokenGranter(createCustomTokenGranter(endpoints));
        endpoints.reuseRefreshTokens(false);
    }

    private TokenGranter createCustomTokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        return new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters(endpoints));
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
    }

    private List<TokenGranter> getDefaultTokenGranters(final AuthorizationServerEndpointsConfigurer endpoints) {
        final List<TokenGranter> tokenGranters = new ArrayList<>();

        final ClientCredentialsTokenGranter clientCredentialsTokenGranter = new ClientCredentialsTokenGranter(
                endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()
        );
        clientCredentialsTokenGranter.setAllowRefresh(true);
        tokenGranters.add(clientCredentialsTokenGranter);

        final RefreshTokenGranter refreshTokenGranter = new RefreshTokenGranter(
                endpoints.getTokenServices(), clientDetailsService, endpoints.getOAuth2RequestFactory()
        );
        tokenGranters.add(refreshTokenGranter);

        return tokenGranters;
    }
}
