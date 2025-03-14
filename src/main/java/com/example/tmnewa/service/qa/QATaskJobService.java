package com.example.tmnewa.service.qa;


import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.repository.qa.QATaskJobRepository;
import com.example.tmnewa.service.LoginService;
import com.example.tmnewa.vo.RequestQueryVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QATaskJobService extends LoginService {

    @Autowired
    QATaskJobRepository qaTaskJobRepository;

    public Page<QaTaskJob> findByQueryParameter(RequestQueryVo requestQueryVo, PageRequest pageRequest) {
        return qaTaskJobRepository.findByQueryParameter(requestQueryVo,pageRequest);
    }

    public Optional<QaTaskJob> findById(Long id){
        return  qaTaskJobRepository.findById(id.toString());
    }
    public QaTaskJob add(QaTaskJob qaTaskJob) throws JsonProcessingException {
        qaTaskJob.setCreator(0L);
        qaTaskJob.setCreatedAt(LocalDateTime.now());
        return qaTaskJobRepository.save(qaTaskJob);
    }

    public List<QaTaskJob> saveAll(List<QaTaskJob> qaTaskJobs) {
        return qaTaskJobRepository.saveAll(qaTaskJobs);
    }

    public QaTaskJob update(QaTaskJob qaTaskJob) throws JsonProcessingException {
        qaTaskJob.setUpdater(getLoginId());
        qaTaskJob.setUpdatedAt(LocalDateTime.now());
        return qaTaskJobRepository.save(qaTaskJob);
    }
}
