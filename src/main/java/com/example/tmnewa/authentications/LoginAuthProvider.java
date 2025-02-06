package com.example.tmnewa.authentications;


import com.example.tmnewa.repository.UserInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.service.RoleInfoService;
import com.example.tmnewa.utils.JacksonUtils;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
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


    @Autowired
    public LoginAuthProvider(PasswordEncoder encoder, UserInfoRepository userInfoRepository, RoleInfoService roleInfoService, HttpSession httpSession) {
        this.passwordEncoder = encoder;
        this.userInfoRepository = userInfoRepository;
        this.roleInfoService = roleInfoService;
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
            boolean isSuperUser = false;
            if (passwordEncoder.matches(rawPassword, password)) {

                List<SimpleGrantedAuthority> authorities =  new ArrayList<>();;
                for(RoleInfo roleInfo:userInfo.getRoleInfos()){
                    authorities.add(new SimpleGrantedAuthority(roleInfo.getRoleName()));
                    if(roleInfoService.isSuperUser(roleInfo.getRoleCode())){
                        isSuperUser =true;
                    }
                }

                httpSession.setAttribute("isAdmin",isSuperUser);
                httpSession.setAttribute("name",userInfo.getName());
                httpSession.setAttribute("userInfo", JacksonUtils.writeValueAsString(userInfo));
                return new UsernamePasswordAuthenticationToken
                        (account, password, authorities);
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
