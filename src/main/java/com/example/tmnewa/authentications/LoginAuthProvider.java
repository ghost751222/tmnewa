package com.example.tmnewa.authentications;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class LoginAuthProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(LoginAuthProvider.class);


    private PasswordEncoder passwordEncoder;


    @Autowired
    public LoginAuthProvider() {
        this.passwordEncoder =  new BCryptPasswordEncoder();
    }


    @Override
    public Authentication authenticate(Authentication auth) {

        String account = auth.getName().toLowerCase();
        String password = auth.getCredentials().toString();

        return new UsernamePasswordAuthenticationToken
                (account, password, Collections.emptyList());

    }


    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
