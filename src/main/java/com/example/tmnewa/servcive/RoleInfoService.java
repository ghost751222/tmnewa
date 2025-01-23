package com.example.tmnewa.servcive;

import com.example.tmnewa.Respository.RoleInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
