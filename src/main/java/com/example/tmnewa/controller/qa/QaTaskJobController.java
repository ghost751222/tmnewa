package com.example.tmnewa.controller.qa;

import com.example.tmnewa.component.TbCallLogSchedule;
import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.service.qa.QATaskJobService;
import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.RequestQueryVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping(value = "/qaTaskJob")
public class QaTaskJobController {

    @Autowired
    TbCallLogSchedule tbCallLogSchedule;

    @Autowired
    QATaskJobService qaTaskJobService;

    @RequestMapping(value = {"/",""} ,method = RequestMethod.GET)
    public String page(){
        return "qaTaskJob";
    }

    @RequestMapping(value = {"/qaTaskJobSupply"} ,method = RequestMethod.GET)
    public String qaTaskJobSupplyPage(){
        return "qaTaskJobSupply";
    }

    @RequestMapping(value = {"/data"} ,method = RequestMethod.GET)
    @ResponseBody
    public Page<QaTaskJob> data(@RequestParam Map<String, Object> params){
        RequestQueryVo requestQueryVo = JacksonUtils.mapToObject(params, RequestQueryVo.class);


        String sortSource = requestQueryVo.getSort();
        String sortField = null;
        String sortDirection = null;
        int page = requestQueryVo.getPageIndex() - 1;
        int size = requestQueryVo.getPageSize();
        PageRequest pageRequest = null;
        if (!Strings.isEmpty(sortSource)) {
            sortField = sortSource.split("\\|")[0];
            sortDirection = sortSource.split("\\|")[1];
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortField).ascending()
                    : Sort.by(sortField).descending();
            pageRequest = PageRequest.of(page, size, sort);
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        return  qaTaskJobService.findByQueryParameter(requestQueryVo, pageRequest);
    }

    @RequestMapping(value = {"/supply"} ,method = RequestMethod.GET)
    @ResponseBody
    public String supply(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime , @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam LocalDate endTime){
        tbCallLogSchedule.transferTbCallLogToQaTaskJob(startTime,endTime);
        return "ok";
    }
}
