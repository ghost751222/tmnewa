package com.example.tmnewa.service.qa;


import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.entity.qa.QADesignTemplate;
import com.example.tmnewa.repository.qa.QADesignItemRepository;
import com.example.tmnewa.repository.qa.QADesignTemplateRepository;
import com.example.tmnewa.service.LoginService;
import com.example.tmnewa.vo.RequestQueryVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import groovy.lang.Tuple2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QADesignTemplateService extends LoginService {

    @Autowired
    QADesignTemplateRepository qaDesignTemplateRepository;

    @Autowired
    QADesignItemService qaDesignItemService;

    @Autowired
    QADesignItemRepository qaDesignItemRepository;

    public Page<QADesignTemplate> findByQueryParameter(RequestQueryVo requestQueryVo, PageRequest pageRequest) {
        return qaDesignTemplateRepository.findByQueryParameter(requestQueryVo, pageRequest);
    }

    public Optional<QADesignTemplate> findByProduct(String product) {
        return qaDesignTemplateRepository.findByProduct(product);
    }

    public List<QADesignTemplate> findListByProduct(String product) {
        return qaDesignTemplateRepository.findListByProduct(product);
    }

    @Transactional
    public QADesignTemplate deleteQADesignTemplate(QADesignTemplate qaDesignTemplate) {
        qaDesignTemplateRepository.delete(qaDesignTemplate);
        qaDesignItemRepository.deleteByQaTemplateId(qaDesignTemplate.getId());
        return qaDesignTemplate;
    }

    @Transactional
    public QADesignTemplate updateQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        qaDesignTemplate.setUpdater(getLoginId());
        qaDesignTemplate.setUpdatedAt(LocalDateTime.now());

        List<QADesignItem> qaDesignItems = qaDesignItemRepository.findByQaTemplateIdAndParentIdOrderBySeqAsc(qaDesignTemplate.getId(), null);
        for (QADesignItem qaDesignItem : qaDesignItems) {
            qaDesignItem.setName(qaDesignTemplate.getName());
        }
        qaDesignItemRepository.saveAll(qaDesignItems);

        return qaDesignTemplateRepository.save(qaDesignTemplate);
    }

    @Transactional
    public Tuple2<QADesignTemplate, QADesignItem> addQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        qaDesignTemplate.setCreator(getLoginId());
        LocalDateTime now = LocalDateTime.now();
        Long createId = getLoginId();
        qaDesignTemplate.setCreator(createId);
        qaDesignTemplate.setCreatedAt(now);
        qaDesignTemplate.setUpdater(createId);
        qaDesignTemplate.setUpdatedAt(now);
        qaDesignTemplate = qaDesignTemplateRepository.save(qaDesignTemplate);

        QADesignItem qaDesignItem = new QADesignItem();
        qaDesignItem.setName(qaDesignTemplate.getName());
        qaDesignItem.setQaTemplateId(qaDesignTemplate.getId());
        qaDesignItem.setCreator(createId);
        qaDesignItem.setCreatedAt(now);
        qaDesignItem.setUpdater(createId);
        qaDesignItem.setUpdatedAt(now);
        qaDesignItemRepository.save(qaDesignItem);
        return new Tuple2<>(qaDesignTemplate, qaDesignItem);
    }

    @Transactional
    public QADesignTemplate copyQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        var qaDesignItems = qaDesignItemService.findAllChildrenByTemplateIDAndPid(qaDesignTemplate.getId(), null);
        qaDesignTemplate.setId(null);
        qaDesignTemplate.setName(String.format("%s-copy", qaDesignTemplate.getName()));
        qaDesignTemplate.setProduct(Strings.EMPTY);
        var tuple2 = addQADesignTemplate(qaDesignTemplate);
        qaDesignItemService.copyQADesignItem(tuple2.getV1(), tuple2.getV2(), qaDesignItems.get(0).getChildren());
        return qaDesignTemplate;
    }


}
