package com.example.tmnewa.Respository;

import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vo.RequestQueryVo;

@Repository

public interface RoleInfoRepository extends JpaRepository<RoleInfo, Long> {


    RoleInfo findByName(String roleName);
}
