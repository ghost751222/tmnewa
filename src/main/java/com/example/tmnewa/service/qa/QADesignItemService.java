package com.example.tmnewa.service.qa;


import com.example.tmnewa.repository.qa.QADesignItemRepository;
import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QADesignItemService extends LoginService {

    @Autowired
    QADesignItemRepository qaDesignItemRepository;

    public List<QADesignItem> findByQaTemplateId(Long templateID) {
        return qaDesignItemRepository.findByQaTemplateId(templateID);
    }


    private List<QADesignItem> findByTemplateIDAndPidOrderBySeqAsc(Long templateID, Long pid) {
        return qaDesignItemRepository.findByQaTemplateIdAndParentIdOrderBySeqAsc(templateID, pid);
    }

    private List<QADesignItem> findByTemplateIDAndPidOrderBySeqDesc(Long templateID, Long pid) {
        return qaDesignItemRepository.findByQaTemplateIdAndParentIdOrderBySeqDesc(templateID, pid);
    }

    public List<QADesignItem> findAllChildrenByTemplateIDAndPid(Long templateID, Long parentId) {
        List<QADesignItem> parent = findByTemplateIDAndPidOrderBySeqDesc(templateID, parentId);
        if (parent != null) {
            for (QADesignItem qaDesignItem : parent) {
                qaDesignItem.setChildren(findAllChildrenByTemplateIDAndPid(templateID, qaDesignItem.getId()));
            }
        }
        return parent;
    }

    public Page<QADesignItem> findPageByQaTemplateIdAndParentId(Long qaTemplateId,Long parentId,PageRequest pageRequest) {
        return qaDesignItemRepository.findPageByQaTemplateIdAndParentId(qaTemplateId,parentId,pageRequest);
    }


    public QADesignItem deleteQADesignItem(QADesignItem qaDesignItem) {
        qaDesignItemRepository.delete(qaDesignItem);
        return qaDesignItem;
    }

    public QADesignItem updateQADesignItem(QADesignItem qaDesignItem) throws JsonProcessingException {
        qaDesignItem.setUpdater(getLoginId());
        qaDesignItem.setUpdatedAt(LocalDateTime.now());
        return qaDesignItemRepository.save(qaDesignItem);
    }

    public QADesignItem addQADesignItem(QADesignItem qaDesignItem) throws JsonProcessingException {
        qaDesignItem.setCreator(getLoginId());
        qaDesignItem.setCreatedAt(LocalDateTime.now());
        qaDesignItem.setUpdater(getLoginId());
        qaDesignItem.setUpdatedAt(LocalDateTime.now());
        return qaDesignItemRepository.save(qaDesignItem);
    }
}
