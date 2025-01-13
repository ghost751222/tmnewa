package com.example.tmnewa.authentications;


import com.example.tmnewa.Respository.UserInfoRepository;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class LoginAuthProvider implements AuthenticationProvider {

    PasswordEncoder passwordEncoder;

    UserInfoRepository userInfoRepository;

    HttpSession httpSession;
    @Autowired
    public LoginAuthProvider(PasswordEncoder encoder, UserInfoRepository userInfoRepository,HttpSession httpSession) {
        this.passwordEncoder = encoder;
        this.userInfoRepository = userInfoRepository;
        this.httpSession = httpSession;
    }


    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication auth) throws RuntimeException {

        String account = auth.getName().toLowerCase();
        String rawPassword = auth.getCredentials().toString();

        UserInfo userInfo = userInfoRepository.findByAccount(account);
        if (userInfo == null) {
            log.error("{} is not exist ", account);

            throw new RuntimeException("User not exist");

        } else {
            String password = userInfo.getPassword();
            if (passwordEncoder.matches(rawPassword, password)) {
                httpSession.setAttribute("isAdmin",true);
                httpSession.setAttribute("name",userInfo.getName());
                httpSession.setAttribute("userInfo", JacksonUtils.writeValueAsString(userInfo));
                return new UsernamePasswordAuthenticationToken
                        (account, password, Collections.emptyList());
            } else {
                throw new RuntimeException("User not exist");
            }
        }
    }


    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
