package com.biscoito.security.config;

import com.biscoito.security.gateways.UserDetailsByJwtToken;
import com.biscoito.security.gateways.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        final List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(getPreAuthenticatedAuthenticationProvider());
        return new ProviderManager(providers);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(getPreAuthenticatedAuthenticationProvider());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = getPreAuthenticatedAuthenticationProvider();
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider);
    }

    private PreAuthenticatedAuthenticationProvider getPreAuthenticatedAuthenticationProvider() {
        final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        UserDetailsByJwtToken userDetailsByJwtToken = new UserDetailsByJwtToken(userDetailsService);
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(userDetailsByJwtToken);
        return preAuthenticatedAuthenticationProvider;
    }
}
