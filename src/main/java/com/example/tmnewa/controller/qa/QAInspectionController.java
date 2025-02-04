package com.example.tmnewa.controller.qa;


import com.example.tmnewa.entity.qa.QAInspection;
import com.example.tmnewa.service.qa.QAInspectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/qa")
public class QAInspectionController {


    @Autowired
    QAInspectionService qaInspectionService;

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
    public String update(@RequestBody List<QAInspection> qaInspections) throws JsonProcessingException {
        qaInspectionService.update(qaInspections);
        return "ok";
    }

}
