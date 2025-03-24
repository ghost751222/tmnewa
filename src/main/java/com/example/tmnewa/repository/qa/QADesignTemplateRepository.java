package com.example.tmnewa.repository.qa;

import com.example.tmnewa.entity.qa.QADesignTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.tmnewa.vo.RequestQueryVo;

import java.util.Optional;

@Repository
public interface QADesignTemplateRepository extends JpaRepository<QADesignTemplate, Long> {

    @Query(value = "select * from qa_design_template", nativeQuery = true)
    Page<QADesignTemplate> findByQueryParameter(@Param("requestQueryVo") RequestQueryVo requestQueryVo, Pageable pageable);

    Optional<QADesignTemplate> findByProduct(String product);

}