package com.example.tmnewa.Respository.qa;

import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.entity.qa.QADesignTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vo.RequestQueryVo;

import java.util.List;

@Repository
public interface QADesignItemRepository extends JpaRepository<QADesignItem, Long> {


    List<QADesignItem> findByQaTemplateIdAndParentIdOrderBySeqAsc(Long templateId, Long parentId);
    Page<QADesignItem> findPageByQaTemplateIdAndParentId(Long qaTemplateId,Long parentId,Pageable pageable);

}