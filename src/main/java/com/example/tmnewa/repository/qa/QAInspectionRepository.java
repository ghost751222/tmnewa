package com.example.tmnewa.repository.qa;

import com.example.tmnewa.entity.qa.QAInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QAInspectionRepository extends JpaRepository<QAInspection, Long> {



    Long countByJobId(Long jobId);
    List<QAInspection> findByJobIdAndQaDesignItemParentIdOrderBySeqDesc(Long jobId,Long qaDesignItemParentId);

}