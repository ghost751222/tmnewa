package com.example.tmnewa.servcive;

import com.example.tmnewa.Respository.UserInfoRepository;
import com.example.tmnewa.entity.UserInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vo.RequestQueryVo;

import java.time.LocalDateTime;

@Service
public class UserInfoService {


    private final String defaultPassWord = "123456";

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

    private UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }


    public UserInfo addUserInfo(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(defaultPassWord));
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());
        return this.save(userInfo);
    }

    public UserInfo updateUserInfo(UserInfo userInfo) {
        if(Strings.isEmpty(userInfo.getPassword())){
            userInfo.setPassword(passwordEncoder.encode(defaultPassWord));
        }else{
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }
        userInfo.setUpdatedAt(LocalDateTime.now());
        return this.save(userInfo);
    }

    public void deleteUserInfo(UserInfo userInfo) {
        userInfoRepository.delete(userInfo);
    }
}
