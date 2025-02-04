package com.example.tmnewa.Respository.qa;

import com.example.tmnewa.entity.qa.QADesignItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QADesignItemRepository extends JpaRepository<QADesignItem, Long> {


    void deleteByQaTemplateId(Long templateId);
    List<QADesignItem> findByQaTemplateId(Long templateId);
    List<QADesignItem> findByQaTemplateIdAndParentIdOrderBySeqAsc(Long templateId, Long parentId);
    List<QADesignItem> findByQaTemplateIdAndParentIdOrderBySeqDesc(Long templateId, Long parentId);
    Page<QADesignItem> findPageByQaTemplateIdAndParentId(Long qaTemplateId,Long parentId,Pageable pageable);

}