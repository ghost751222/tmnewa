package com.example.tmnewa.service;


import com.example.tmnewa.entity.DNRoutine;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.repository.DNRoutineRepository;
import com.example.tmnewa.vo.RequestQueryVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DNRoutineService extends LoginService {

    @Autowired
    DNRoutineRepository dnRoutineRepository;

    public Page<DNRoutine> findByQueryParameter(RequestQueryVo requestQueryVo, PageRequest pageRequest) {
        return dnRoutineRepository.findByQueryParameter(requestQueryVo, pageRequest);
    }

    public DNRoutine save(DNRoutine dnRoutine) {
        return dnRoutineRepository.save(dnRoutine);
    }

    public DNRoutine add(DNRoutine dnRoutine) throws JsonProcessingException {
        dnRoutine.setCreator(getLoginId());
        dnRoutine.setCreatedAt(LocalDateTime.now());
        return save(dnRoutine);
    }

    public DNRoutine update(DNRoutine dnRoutine) throws JsonProcessingException {
        dnRoutine.setUpdater(getLoginId());
        dnRoutine.setUpdatedAt(LocalDateTime.now());
        return save(dnRoutine);
    }

    public DNRoutine delete(DNRoutine dnRoutine) throws JsonProcessingException {
        dnRoutineRepository.delete(dnRoutine);
        return dnRoutine;
    }

}
