package com.example.tmnewa.servcive;

import com.example.tmnewa.Component.DataComponent;
import com.example.tmnewa.Respository.RoleInfoRepository;
import com.example.tmnewa.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleInfoService {


    RoleInfoRepository roleInfoRepository;


    @Autowired
    public RoleInfoService(RoleInfoRepository roleInfoRepository) {
        this.roleInfoRepository = roleInfoRepository;
    }


    public List<RoleInfo> findAll() {
        return this.roleInfoRepository.findAll();
    }

    public RoleInfo save(RoleInfo roleInfo) {
        return roleInfoRepository.save(roleInfo);
    }

    public RoleInfo findByName(String roleName) {
        return roleInfoRepository.findByName(roleName);
    }

    public boolean isSuperUser(String roleName) {
        boolean _isSuperUser = false;
        RoleInfo roleInfo = this.findByName(roleName);
        if (roleInfo != null && roleInfo.getName().equals(DataComponent.roleAdminName)) {
            _isSuperUser = true;
        }
        return _isSuperUser;
    }

}
