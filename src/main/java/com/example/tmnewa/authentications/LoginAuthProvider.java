package com.example.tmnewa.authentications;


import com.example.tmnewa.config.TWNEWAConfigProperties;
import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.repository.UserInfoRepository;
import com.example.tmnewa.service.ADLoginService;
import com.example.tmnewa.service.RoleInfoService;
import com.example.tmnewa.utils.JacksonUtils;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class LoginAuthProvider implements AuthenticationProvider {

    PasswordEncoder passwordEncoder;

    UserInfoRepository userInfoRepository;

    RoleInfoService roleInfoService;

    HttpSession httpSession;

    ADLoginService adLoginService;

    TWNEWAConfigProperties twnewaConfigProperties;

    @Autowired
    public LoginAuthProvider(PasswordEncoder encoder, UserInfoRepository userInfoRepository,
                             RoleInfoService roleInfoService, HttpSession httpSession,
                             TWNEWAConfigProperties twnewaConfigProperties, ADLoginService adLoginService) {
        this.passwordEncoder = encoder;
        this.userInfoRepository = userInfoRepository;
        this.roleInfoService = roleInfoService;
        this.httpSession = httpSession;
        this.adLoginService = adLoginService;
        this.twnewaConfigProperties = twnewaConfigProperties;
    }


    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication auth) throws BadCredentialsException {

        String account = auth.getName().toLowerCase();
        String password = auth.getCredentials().toString();

        boolean isAdLoginSuccessful = false;
        if (twnewaConfigProperties.isAD() && !"admin".toLowerCase().equals(account)) {
            isAdLoginSuccessful = adLoginService.activeDirectoryCheck(account, password);
            if (!isAdLoginSuccessful) {
                throw new BadCredentialsException(String.format("AD User %S not exist", account));
            }
        }


        UserInfo userInfo = userInfoRepository.findByAccount(account).orElseThrow(() -> new IllegalArgumentException("User with username " + account + " does not exist"));


        boolean isSuperUser = false;
        if ((twnewaConfigProperties.isAD() && isAdLoginSuccessful) || passwordEncoder.matches(password, userInfo.getPassword())) {

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (RoleInfo roleInfo : userInfo.getRoleInfos()) {
                authorities.add(new SimpleGrantedAuthority(roleInfo.getRoleName()));
//                if (roleInfoService.isSuperUser(roleInfo.getRoleCode())) {
//                    isSuperUser = true;
//                }
            }
            //httpSession.setAttribute("isAdmin", isSuperUser);
            httpSession.setAttribute("name", userInfo.getName());
            httpSession.setAttribute("userInfo", JacksonUtils.writeValueAsString(userInfo));

            return new UsernamePasswordAuthenticationToken
                    (account, password, authorities);
        } else {
            throw new RuntimeException("User not exist");
        }
    }


    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
