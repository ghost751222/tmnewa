package com.example.tmnewa.authentications;

import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.service.LoginService;
import com.example.tmnewa.service.RoleInfoService;
import com.example.tmnewa.service.UserInfoService;
import com.example.tmnewa.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RoleInfoService roleInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 根據登錄方式處理重定向
        if (authentication.getPrincipal() instanceof OAuth2User auth2User) {
            Map<String, Object> attributes = auth2User.getAttributes();
            String account = attributes.get("preferred_username").toString();
            String name = attributes.get("name").toString();
            Optional<UserInfo> userInfoOptional = userInfoService.findByAccount(account);
            UserInfo userInfo = null;
            if (userInfoOptional.isEmpty()) {
                userInfo = new UserInfo();
                userInfo.setAccount(account);
                userInfo.setPassword(passwordEncoder.encode(LoginService.DefaultPassWord));
                userInfo.setName(name);
                userInfo.setCreatedAt(LocalDateTime.now());
                userInfo.setUpdatedAt(LocalDateTime.now());
                userInfo.setRoleInfos(new ArrayList<>());
                var roleInfo = roleInfoService.findByRoleCode("3");
                userInfo.getRoleInfos().add(roleInfo);
                userInfo = userInfoService.save(userInfo);

            } else {
                userInfo = userInfoOptional.get();
            }

            boolean isSuperUser = false;
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (RoleInfo roleInfo : userInfo.getRoleInfos()) {
                authorities.add(new SimpleGrantedAuthority(roleInfo.getRoleName()));
//                if (roleInfoService.isSuperUser(roleInfo.getRoleCode())) {
//                    isSuperUser = true;
//                }
            }

            //request.getSession().setAttribute("isAdmin", isSuperUser);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("userInfo", JacksonUtils.writeValueAsString(userInfo));
            request.getSession().setAttribute("loginType","oauth2Login");

            // 建立 UsernamePasswordAuthenticationToken，這是你需要返回的類型
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    account, // Principal (這裡是 OAuth2User)
                    null, // Credentials (這裡為 null，因為 OAuth2 登錄不需要密碼)
                    authorities // 用戶的權限
            );
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
