package com.biscoito.security.gateways;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.util.Assert;

import java.util.Map;

public class UserDetailsByJwtToken<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    private UserDetailsService userDetailsService = null;

    private JsonParser jsonParser = JsonParserFactory.create();

    public UserDetailsByJwtToken(final UserDetailsService userDetailsService) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null.");
        this.userDetailsService = userDetailsService;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "UserDetailsService must be set");
    }


    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        final String token = authentication.getPrincipal().toString();
        final Map<String, Object> jwt = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
        return this.userDetailsService.loadUserByUsername(jwt.get("client_id").toString());
    }


    public void setUserDetailsService(UserDetailsService aUserDetailsService) {
        this.userDetailsService = aUserDetailsService;
    }

}
