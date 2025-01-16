package com.example.tmnewa.Component;

import com.example.tmnewa.Respository.UserInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.servcive.RoleInfoService;
import jakarta.annotation.PostConstruct;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Component
public class DataComponent {

    String roleAdminName = "管理者";
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleInfoService roleInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void AddAdminUser() {

        String roleName = roleAdminName;
        RoleInfo roleInfo = roleInfoService.findByName(roleName);
        if (roleInfo == null) {
            roleInfo = new RoleInfo();
            roleInfo.setName(roleName);
            roleInfo.setCreatedAt(LocalDateTime.now());
            roleInfo.setUpdatedAt(LocalDateTime.now());
            roleInfoService.save(roleInfo);
        }

        roleName = "一般使用者";
        roleInfo = roleInfoService.findByName(roleName);
        if (roleInfo == null) {
            roleInfo = new RoleInfo();
            roleInfo.setName(roleName);
            roleInfo.setCreatedAt(LocalDateTime.now());
            roleInfo.setUpdatedAt(LocalDateTime.now());
            roleInfoService.save(roleInfo);
        }

        String account = "admin";
        String password = "admin";
        String name = "admin";

        UserInfo userInfo = userInfoRepository.findByName(name);


        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setPassword(passwordEncoder.encode(password));
            userInfo.setName(name);
            userInfo.setCreatedAt(LocalDateTime.now());
            userInfo.setUpdatedAt(LocalDateTime.now());
            userInfo.setRoles(new HashSet<>());

            roleInfo = roleInfoService.findByName(roleAdminName);
            userInfo.getRoles().add(roleInfo);

            userInfoRepository.save(userInfo);
        }

    }


}
