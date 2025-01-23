package com.example.tmnewa.servcive.qa;


import com.example.tmnewa.Respository.qa.QADesignItemRepository;
import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.servcive.LoginService;
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

    private List<QADesignItem> findByTemplateIDAndPidOrderBySeqAsc(Long templateID, Long pid) {
        return qaDesignItemRepository.findByQaTemplateIdAndParentIdOrderBySeqAsc(templateID, pid);
    }

    public List<QADesignItem> findAllChildrenByTemplateIDAndPid(Long templateID, Long parentId) {
        List<QADesignItem> parent = findByTemplateIDAndPidOrderBySeqAsc(templateID, parentId);
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
