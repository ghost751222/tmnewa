package com.example.tmnewa.component;

import com.example.tmnewa.config.TWNEWAConfigProperties;
import com.example.tmnewa.entity.DNProduct;
import com.example.tmnewa.repository.UserInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.service.DNProductService;
import com.example.tmnewa.service.RoleInfoService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@Slf4j
public class DataComponent {

    public final static String RoleAdminCode = "0";
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleInfoService roleInfoService;

    @Autowired
    DNProductService dnProductService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TWNEWAConfigProperties twnewaConfigProperties;

    @PostConstruct
    public void AddAdminUser() {

        String[] roleCodes = {RoleAdminCode, "1", "2", "3"};
        RoleInfo roleInfo;
        for (String roleCode : roleCodes) {
            roleInfo = roleInfoService.findByRoleCode(roleCode);
            if (roleInfo == null) {
                roleInfo = new RoleInfo();
                switch (roleCode) {
                    case "0" -> {
                        roleInfo.setName("管理者");
                        roleInfo.setRoleName("ROLE_ADMIN");
                        roleInfo.setRoleCode(RoleAdminCode);
                    }
                    case "1" -> {
                        roleInfo.setName("主管");
                        roleInfo.setRoleName("ROLE_MANAGER");
                        roleInfo.setRoleCode("1");
                    }
                    case "2" -> {
                        roleInfo.setName("質檢員");
                        roleInfo.setRoleName("ROLE_QA");
                        roleInfo.setRoleCode("2");
                    }
                    case "3" -> {
                        roleInfo.setName("一般使用者");
                        roleInfo.setRoleName("ROLE_USER");
                        roleInfo.setRoleCode("3");
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


    @PostConstruct
    public void addDNProduct() {

        var dnProducts = twnewaConfigProperties.getDnProducts();

        for (String product : dnProducts) {
            if (dnProductService.findByProductName(product).isEmpty()) {
                var dnProduct = new DNProduct();
                dnProduct.setProductName(product);
                dnProductService.save(dnProduct);
            }
        }
    }
}
