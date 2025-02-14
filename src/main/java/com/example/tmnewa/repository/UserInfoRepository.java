package com.example.tmnewa.repository;

import com.example.tmnewa.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.tmnewa.vo.RequestQueryVo;

import java.util.Optional;
import java.util.Set;

@Repository

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {


    @Query(value = "select * from user_info where account != 'admin' and (account = :#{#requestQueryVo.account} or :#{#requestQueryVo.account} is null)", nativeQuery = true)
    Page<UserInfo> findByQueryParameter(@Param("requestQueryVo") RequestQueryVo requestQueryVo, Pageable pageable);

    UserInfo findByName(String name);

    Optional<UserInfo> findByAccount(String account);
}
