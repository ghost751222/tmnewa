package com.example.tmnewa.Component;

import com.example.tmnewa.Respository.UserInfoRepository;
import com.example.tmnewa.entity.UserInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataComponent {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void AddAdminUser()
    {

        String account = "admin";
        String password = "admin";
        String name = "admin";

        UserInfo userInfo = userInfoRepository.findByName(name);
        if(userInfo ==null){
            userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setPassword(passwordEncoder.encode(password));
            userInfo.setName(name);
            userInfo.setCreatedAt(LocalDateTime.now());
            userInfo.setUpdatedAt(LocalDateTime.now());
            userInfoRepository.save(userInfo);
        }

    }

}
