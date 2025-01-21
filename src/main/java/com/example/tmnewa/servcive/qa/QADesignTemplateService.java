package com.example.tmnewa.servcive.qa;


import com.example.tmnewa.Respository.qa.QADesignItemRepository;
import com.example.tmnewa.Respository.qa.QADesignTemplateRepository;
import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.entity.qa.QADesignTemplate;
import com.example.tmnewa.servcive.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vo.RequestQueryVo;

import java.time.LocalDateTime;

@Service
public class QADesignTemplateService extends LoginService {

    @Autowired
    QADesignTemplateRepository qaDesignTemplateRepository;

    @Autowired
    QADesignItemRepository qaDesignItemRepository;

    public Page<QADesignTemplate> findByQueryParameter(RequestQueryVo requestQueryVo, PageRequest pageRequest) {
        return qaDesignTemplateRepository.findByQueryParameter(requestQueryVo, pageRequest);
    }


    public QADesignTemplate deleteQADesignTemplate(QADesignTemplate qaDesignTemplate) {
        qaDesignTemplateRepository.delete(qaDesignTemplate);
        return qaDesignTemplate;
    }

    public QADesignTemplate updateQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        qaDesignTemplate.setUpdater(getLoginId());
        qaDesignTemplate.setUpdatedAt(LocalDateTime.now());
        return qaDesignTemplateRepository.save(qaDesignTemplate);
    }

    @Transactional
    public QADesignTemplate addQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        qaDesignTemplate.setCreator(getLoginId());
        LocalDateTime now = LocalDateTime.now();
        Long createId = getLoginId();
        qaDesignTemplate.setCreator(createId);
        qaDesignTemplate.setCreatedAt(now);
        qaDesignTemplate.setUpdater(createId);
        qaDesignTemplate.setUpdatedAt(now);
        qaDesignTemplate= qaDesignTemplateRepository.save(qaDesignTemplate);

        QADesignItem qaDesignItem = new QADesignItem();
        qaDesignItem.setName(qaDesignTemplate.getName());
        qaDesignItem.setQaTemplateId(qaDesignTemplate.getId());
        qaDesignItem.setCreator(createId);
        qaDesignItem.setCreatedAt(now);
        qaDesignItem.setUpdater(createId);
        qaDesignItem.setUpdatedAt(now);
        qaDesignItemRepository.save(qaDesignItem);
        return qaDesignTemplate;
    }
}
