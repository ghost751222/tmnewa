package com.example.tmnewa.repository;

import com.example.tmnewa.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {

    RoleInfo findByName(String roleName);

    RoleInfo findByRoleCode(String roleCode);
}
