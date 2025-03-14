package com.example.tmnewa.controller.qa;


import com.example.tmnewa.entity.qa.QAInspection;
import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.service.qa.QAInspectionService;
import com.example.tmnewa.service.qa.QATaskJobService;
import com.example.tmnewa.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/qaInspection")
public class QAInspectionController {


    @Autowired
    QAInspectionService qaInspectionService;

    @Autowired
    QATaskJobService qaTaskJobService;
    @RequestMapping(value = {"/",""} ,method = RequestMethod.GET)
    public String page() {
        return "qaInspection";
    }



    @RequestMapping( value = "/{jobId}" ,method = RequestMethod.GET)
    @ResponseBody
    public List<QAInspection> page(@PathVariable(value = "jobId") Long jobId, @RequestParam Map<String, Object> params) throws Exception {
        Long templateId = Long.parseLong ((String)params.get("templateId"));
        return qaInspectionService.findQAInspection(jobId,templateId);
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public String update(@RequestBody  List<QAInspection> qaInspections) throws JsonProcessingException {
        Long jobId = qaInspections.isEmpty() ? 0L: qaInspections.get(0).getJobId();
        Optional<QaTaskJob> qaTaskJobOptional = qaTaskJobService.findById(jobId);
        if(qaTaskJobOptional.isPresent()){
            QaTaskJob qaTaskJob = qaTaskJobOptional.get();
            qaTaskJob.setStatus("1");
            qaTaskJob.setQa_time(LocalDateTime.now());
            qaTaskJobService.update(qaTaskJob);
            qaInspectionService.update(qaInspections);
        }
        return "ok";
    }

}
