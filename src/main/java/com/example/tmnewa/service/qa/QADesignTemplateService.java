package com.example.tmnewa.service.qa;


import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.entity.qa.QADesignTemplate;
import com.example.tmnewa.repository.qa.QADesignItemRepository;
import com.example.tmnewa.repository.qa.QADesignTemplateRepository;
import com.example.tmnewa.service.LoginService;
import com.example.tmnewa.vo.RequestQueryVo;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public QADesignTemplate addQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
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
        return qaDesignTemplate;
    }

    @Transactional
    public QADesignTemplate copyQADesignTemplate(QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        var qaDesignItems = qaDesignItemService.findAllChildrenByTemplateIDAndPid(qaDesignTemplate.getId(), null);
        qaDesignTemplate.setId(null);
        qaDesignTemplate = addQADesignTemplate(qaDesignTemplate);
        copyQADesignItem(qaDesignTemplate, qaDesignItemService.findAllChildrenByTemplateIDAndPid(qaDesignTemplate.getId(),null).get(0), qaDesignItems.get(0).getChildren());
        return qaDesignTemplate;
    }

    @Transactional
    private void copyQADesignItem(QADesignTemplate qaDesignTemplate, QADesignItem parent, List<QADesignItem> children) throws JsonProcessingException {
        for (QADesignItem _qaDesignItem : children) {

            var qaDesignItem = new QADesignItem();
            qaDesignItem.setQaTemplateId(qaDesignTemplate.getId());
            qaDesignItem.setParentId(parent.getId());
            qaDesignItem.setName(_qaDesignItem.getName());
            qaDesignItem.setScore(_qaDesignItem.getScore());
            qaDesignItem.setSeq(_qaDesignItem.getSeq());
            qaDesignItem = qaDesignItemService.addQADesignItem(qaDesignItem);
            if (_qaDesignItem.getChildren().size() > 0) {
                copyQADesignItem(qaDesignTemplate, qaDesignItem, _qaDesignItem.getChildren());
            }

        }
    }
}
