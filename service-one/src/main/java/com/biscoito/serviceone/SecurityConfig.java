package com.biscoito.serviceone;

//import br.com.accesstage.securityserver.gateways.impl.UserDetailsByJwtToken;
//import br.com.accesstage.securityserver.gateways.impl.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        final List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(getPreAuthenticatedAuthenticationProvider());
        return new ProviderManager(providers);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter = new OAuth2AuthenticationProcessingFilter();
        oAuth2AuthenticationProcessingFilter.setAuthenticationManager(authenticationManager());

        http.addFilterAfter(oAuth2AuthenticationProcessingFilter, SecurityContextPersistenceFilter.class);
        http.authenticationProvider(getPreAuthenticatedAuthenticationProvider());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = getPreAuthenticatedAuthenticationProvider();
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider);
    }

    private PreAuthenticatedAuthenticationProvider getPreAuthenticatedAuthenticationProvider() {
        final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByJwtToken());
        return preAuthenticatedAuthenticationProvider;
    }
}
