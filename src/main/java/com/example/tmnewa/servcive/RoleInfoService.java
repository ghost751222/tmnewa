package com.example.tmnewa.servcive;

import com.example.tmnewa.Respository.RoleInfoRepository;
import com.example.tmnewa.Respository.UserInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
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
public class RoleInfoService {




    RoleInfoRepository roleInfoRepository;



    @Autowired
    public RoleInfoService(RoleInfoRepository roleInfoRepository) {
        this.roleInfoRepository = roleInfoRepository;

    }


    public RoleInfo save(RoleInfo roleInfo) {
        return roleInfoRepository.save(roleInfo);
    }

    public RoleInfo findByName(String roleName) {
        return roleInfoRepository.findByName(roleName);
    }


}
