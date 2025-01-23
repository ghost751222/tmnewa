package com.example.tmnewa.Respository;

import com.example.tmnewa.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {

    RoleInfo findByName(String roleName);
}
