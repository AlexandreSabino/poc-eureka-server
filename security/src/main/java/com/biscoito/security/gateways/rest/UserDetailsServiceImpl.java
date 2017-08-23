package com.biscoito.security.gateways.rest;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Tenta pegar do cache
        //Se nao existe chama o endpoint cria o objeto e coloca no cache
        return new User("biscoito", "teste123", true, true, true, true, new ArrayList<>());
    }
}
