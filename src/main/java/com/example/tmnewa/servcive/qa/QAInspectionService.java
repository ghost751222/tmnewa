package com.example.tmnewa.servcive.qa;


import com.example.tmnewa.Respository.qa.QAInspectionRepository;
import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.entity.qa.QAInspection;
import com.example.tmnewa.servcive.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QAInspectionService extends LoginService {

    @Autowired
    QAInspectionRepository qaInspectionRepository;

    @Autowired
    QADesignItemService qaDesignItemService;

    @Transactional
    public List<QAInspection> findQAInspection(Long jobId, Long templateId) throws Exception {

        if (countByJobId(jobId) == 0) {
            List<QADesignItem> qaDesignItems = qaDesignItemService.findByQaTemplateId(templateId);
            if(qaDesignItems.isEmpty()){
                throw  new Exception("找不到模版");
            }
            for (QADesignItem qaDesignItem : qaDesignItems) {
                QAInspection qaInspection = new QAInspection();
                qaInspection.setName(qaDesignItem.getName());
                qaInspection.setQaDesignItemId(qaDesignItem.getId());
                qaInspection.setQaDesignItemParentId(qaDesignItem.getParentId());
                qaInspection.setSeq(qaDesignItem.getSeq());
                qaInspection.setJobId(jobId);
                add(qaInspection);
            }

        }
        return findByJobIdAndQADesignItemParentId(jobId, null);

    }


    public Long countByJobId(Long jobId) {
        return qaInspectionRepository.countByJobId(jobId);
    }


    private List<QAInspection> findByJobIdAndQADesignItemParentIdOrderBySeqDesc(Long jobId, Long qaDesignItemParentId) {
        return qaInspectionRepository.findByJobIdAndQaDesignItemParentIdOrderBySeqDesc(jobId, qaDesignItemParentId);
    }

    public List<QAInspection> findByJobIdAndQADesignItemParentId(Long jobId, Long qaDesignItemParentId) {
        List<QAInspection> parent = findByJobIdAndQADesignItemParentIdOrderBySeqDesc(jobId, qaDesignItemParentId);
        if (parent != null) {
            for (QAInspection qaInspection : parent) {
                qaInspection.setChildren(findByJobIdAndQADesignItemParentId(jobId, qaInspection.getQaDesignItemId()));
            }
        }
        return parent;
    }

    public QAInspection delete(QAInspection qaInspection) {
        qaInspectionRepository.delete(qaInspection);
        return qaInspection;
    }


    public QAInspection add(QAInspection qaInspection) throws JsonProcessingException {
        qaInspection.setCreator(getLoginId());
        qaInspection.setCreatedAt(LocalDateTime.now());
        qaInspection.setUpdater(getLoginId());
        qaInspection.setUpdatedAt(LocalDateTime.now());
        return qaInspectionRepository.save(qaInspection);
    }

    public QAInspection update(QAInspection qaInspection) throws JsonProcessingException {
        qaInspection.setUpdater(getLoginId());
        qaInspection.setUpdatedAt(LocalDateTime.now());
        return qaInspectionRepository.save(qaInspection);
    }


    @Transactional
    public List<QAInspection> update(List<QAInspection> qaInspections) throws JsonProcessingException {
        for (QAInspection qaInspection : qaInspections) {
            if(!qaInspection.getChildren().isEmpty()){
                update(qaInspection.getChildren());
            }else {
                update(qaInspection);
            }
        }
        return qaInspections;
    }
}
