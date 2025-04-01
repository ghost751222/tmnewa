package com.example.tmnewa.repository.qa;

import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.vo.RequestQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QATaskJobRepository extends JpaRepository<QaTaskJob, String> {


    @Query(value = "select * from qa_task_job " +
            "where date(call_start_time) >= :#{#requestQueryVo.startDate} and date(call_stop_time) <=:#{#requestQueryVo.endDate} " +
            " and  (:#{#requestQueryVo.productName} is null or product_name = :#{#requestQueryVo.productName})", nativeQuery = true)
    Page<QaTaskJob> findByQueryParameter(@Param("requestQueryVo") RequestQueryVo requestQueryVo, Pageable pageable);

}