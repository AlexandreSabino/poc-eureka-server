package com.biscoito.serviceone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDetailsByJwtToken<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    private JsonParser jsonParser = JsonParserFactory.create();

    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        final String token = authentication.getPrincipal().toString();
        final Map<String, Object> jwt = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
        return convert(jwt);
    }

    private UserDetails convert(final Map<String, Object> jwt) {
        return new User(
                jwt.get("client_id").toString(),
                "empty_value",
                buildGrantedAuthority((ArrayList<String>)jwt.get("authorities"))
        );
    }

    private Collection<? extends GrantedAuthority> buildGrantedAuthority(final ArrayList<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
