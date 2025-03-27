package com.example.tmnewa.service;

import com.example.tmnewa.repository.UserInfoRepository;
import com.example.tmnewa.entity.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.tmnewa.vo.RequestQueryVo;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserInfoService extends LoginService{




    UserInfoRepository userInfoRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Page<UserInfo> findByQueryParameter(RequestQueryVo requestQueryVo, PageRequest pageRequest) {
        return userInfoRepository.findByQueryParameter(requestQueryVo, pageRequest);
    }

    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }


    public UserInfo addUserInfo(UserInfo userInfo) throws JsonProcessingException {
        userInfo.setPassword(passwordEncoder.encode(DefaultPassWord));
        userInfo.setCreator(getLoginId());
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdater(getLoginId());
        userInfo.setUpdatedAt(LocalDateTime.now());
        return this.save(userInfo);
    }

    public UserInfo updateUserInfo(UserInfo userInfo) throws JsonProcessingException {
        if(Strings.isEmpty(userInfo.getPassword())){
            userInfo.setPassword(passwordEncoder.encode(DefaultPassWord));
        }
        else{
            String password = userInfo.getPassword();
            if(password.startsWith("$2a$")){
                userInfo.setPassword(password);
            }else{
                userInfo.setPassword(passwordEncoder.encode(password));
            }

        }
        userInfo.setUpdater(getLoginId());
        userInfo.setUpdatedAt(LocalDateTime.now());
        return this.save(userInfo);
    }

    public UserInfo deleteUserInfo(UserInfo userInfo) {
        userInfoRepository.delete(userInfo);
        return userInfo;
    }

    public Optional<UserInfo> findByAccount(String account){
        return userInfoRepository.findByAccount(account);
    }
}
