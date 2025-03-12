package com.example.tmnewa.service.qa;


import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.repository.qa.QATaskJobRepository;
import com.example.tmnewa.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QATaskJobService extends LoginService {

    @Autowired
    QATaskJobRepository qaTaskJobRepository;


    public QaTaskJob add(QaTaskJob qaTaskJob) throws JsonProcessingException {
        qaTaskJob.setCreator(0L);
        qaTaskJob.setCreatedAt(LocalDateTime.now());
        return qaTaskJobRepository.save(qaTaskJob);
    }

    public List<QaTaskJob> saveAll(List<QaTaskJob> qaTaskJobs) {
        return qaTaskJobRepository.saveAll(qaTaskJobs);
    }

}
