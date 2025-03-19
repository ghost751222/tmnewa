package com.example.tmnewa.repository;

import com.example.tmnewa.entity.DNRoutine;
import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.vo.RequestQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface DNRoutineRepository extends JpaRepository<DNRoutine, String> {

    @Query(value = "select * from dn_routine where 1=1" +
            " and ( start_date >= date(:#{#requestQueryVo.startDate}) || :#{#requestQueryVo.startDate} is null )" +
            " and ( end_date <= date(:#{#requestQueryVo.endDate})     || :#{#requestQueryVo.endDate} is null  )" +
            " and (dn = :#{#requestQueryVo.dn} || :#{#requestQueryVo.dn} is null)" +
            " and (day_of_week = :#{#requestQueryVo.dayOfWeek} || :#{#requestQueryVo.dayOfWeek} is null)",
            nativeQuery = true)
    Page<DNRoutine> findByQueryParameter(@Param("requestQueryVo") RequestQueryVo requestQueryVo, Pageable pageable);

}
