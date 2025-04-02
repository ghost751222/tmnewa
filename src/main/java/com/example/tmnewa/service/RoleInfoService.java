package com.example.tmnewa.service;

import com.example.tmnewa.component.DataComponent;
import com.example.tmnewa.repository.RoleInfoRepository;
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

    public RoleInfo findByRoleCode(String roleCode) {
        return roleInfoRepository.findByRoleCode(roleCode);
    }

    public boolean isSuperUser(String roleCode) {
        boolean _isSuperUser = false;
        if (DataComponent.RoleAdminCode.equals(roleCode)) _isSuperUser = true;
        return _isSuperUser;
    }

    public boolean isQaUser(String roleCode) {
        boolean _isQaUser = false;
        if (roleCode.equals("2")) _isQaUser = true;
        return _isQaUser;
    }

}
