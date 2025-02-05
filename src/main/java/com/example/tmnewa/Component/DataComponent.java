package com.example.tmnewa.Component;

import com.example.tmnewa.Respository.UserInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.service.RoleInfoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class DataComponent {

    public final static String RoleAdminCode = "0";
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleInfoService roleInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void AddAdminUser() {
        String[] roleCodes = {"0","1","2"};
        RoleInfo roleInfo;
        for(String roleCode :roleCodes){
            roleInfo = roleInfoService.findByRoleCode(roleCode);
            if (roleInfo == null) {
                roleInfo = new RoleInfo();
                switch (roleCode) {
                    case "0" -> {
                        roleInfo.setName("管理者");
                        roleInfo.setRoleName("ROLE_ADMIN");
                        roleInfo.setRoleCode("0");
                    }
                    case "1" -> {
                        roleInfo.setName("主管");
                        roleInfo.setRoleName("ROLE_MANAGER");
                        roleInfo.setRoleCode("1");
                    }
                    case "2" -> {
                        roleInfo.setName("質檢員");
                        roleInfo.setRoleName("ROLE_USER");
                        roleInfo.setRoleCode("2");
                    }
                }
                roleInfo.setCreatedAt(LocalDateTime.now());
                roleInfo.setUpdatedAt(LocalDateTime.now());
                roleInfoService.save(roleInfo);
            }
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
            userInfo.setRoleInfos(new ArrayList<>());

            roleInfo = roleInfoService.findByRoleCode(RoleAdminCode);
            userInfo.getRoleInfos().add(roleInfo);

            userInfoRepository.save(userInfo);
        }

    }


}
