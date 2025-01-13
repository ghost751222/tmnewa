package com.example.tmnewa.Respository;

import com.example.tmnewa.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vo.RequestQueryVo;

@Repository

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    //    @Query(
//            value = "select * from VMS_Recording where try_cast(startTime as date) between :#{#requestQueryVo.startTime} and :#{#requestQueryVo.endTime} " +
//                    "and (transExt = :#{#requestQueryVo.transExt} or :#{#requestQueryVo.transExt} is null) " +
//                    "and (ani = :#{#requestQueryVo.ani} or :#{#requestQueryVo.ani} is null) " +
//                    "and (dnis = :#{#requestQueryVo.dnis} or :#{#requestQueryVo.dnis} is null)" +
//                    "and (comment like %:#{#requestQueryVo.comment}% or :#{#requestQueryVo.comment} is null)" ,
//
//            nativeQuery = true)

    @Query(value = "select * from user_info where (account = :#{#requestQueryVo.account} or :#{#requestQueryVo.account} is null)", nativeQuery = true)
    Page<UserInfo> findByQueryParameter(@Param("requestQueryVo") RequestQueryVo requestQueryVo, Pageable pageable);

    UserInfo findByName(String name);

    UserInfo findByAccount(String account);
}
