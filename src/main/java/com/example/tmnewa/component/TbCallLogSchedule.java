package com.example.tmnewa.component;


import com.example.tmnewa.entity.TbCallLog;
import com.example.tmnewa.entity.qa.QADesignTemplate;
import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.service.TbCallLogService;
import com.example.tmnewa.service.firstline.FirstLineApiService;
import com.example.tmnewa.service.qa.QADesignTemplateService;
import com.example.tmnewa.service.qa.QATaskJobService;
import com.example.tmnewa.vo.firstline.FirstLineApiResponseVo;
import com.example.tmnewa.vo.firstline.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@EnableScheduling
@Slf4j
public class TbCallLogSchedule {

    @Autowired
    TbCallLogService tbCallLogService;

    @Autowired
    FirstLineApiService firstLineApiService;

    @Autowired
    QADesignTemplateService qaDesignTemplateService;

    @Autowired
    QATaskJobService qaTaskJobService;

    //@PostConstruct
    public void transferTbCallLogToQaTaskJob() throws Exception {
        LocalDate start = LocalDate.now().minusYears(3);
        LocalDate end = start.plusDays(60);
        transferTbCallLogToQaTaskJob(start, end);
    }

    @Transactional
    public void transferTbCallLogToQaTaskJob(LocalDate startDate, LocalDate endDate) throws Exception {
        log.info("transferTbCallLogToQaTaskJob start execute startTime= {} endTime = {}", startDate, endDate);

        if (!startDate.toString().equals("222")) {
            throw new Exception("test");
        }
        Map<String, List<String>> map = new HashMap<>();
        int page = 1, perPage = 30;
        List<FirstLineApiResponseVo> firstLineApiResponseVos = firstLineApiService.getAllInteractCollection(startDate, endDate, perPage, page);
        for (var f : firstLineApiResponseVos) {
            var _firstLineApiResponseVo = firstLineApiService.getInteractCollectionById(startDate, endDate, f.getId());
            var logs = _firstLineApiResponseVo.getLogs();
            var reasons = _firstLineApiResponseVo.getReasons();
            for (var _log : logs) {
                Provider provider = _log.getProvider();
                String cid = provider.getCid();
                if (!map.containsKey(cid)) {
                    List<String> serviceReason = new ArrayList<>();
                    for (var reason : reasons) {
                        serviceReason.add(reason.getCategory().getName());
                    }
                    map.put(cid, serviceReason);
                }
            }
        }


        List<TbCallLog> tbCallLogs = tbCallLogService.findByStartTimeAndEndTime(startDate, endDate);
        List<QaTaskJob> qaTaskJobs = new ArrayList<>();
        for (TbCallLog tbCallLog : tbCallLogs) {
            var qaTaskJob = new QaTaskJob();
            String callID = tbCallLog.getF_call_id();
            qaTaskJob.setCall_id(callID);
            qaTaskJob.setCall_start_time(tbCallLog.getF_start_time());
            qaTaskJob.setCall_stop_time(tbCallLog.getF_stop_time());
            qaTaskJob.setCall_type(tbCallLog.getF_call_type());
            qaTaskJob.setCall_agent_id(tbCallLog.getF_agent_id());
            qaTaskJob.setCall_customer_id(tbCallLog.getF_customer_id());
            qaTaskJob.setCall_ext_no(tbCallLog.getF_ext_no());
            qaTaskJob.setCreator(0L);
            qaTaskJob.setCreatedAt(LocalDateTime.now());
            qaTaskJob.setStatus("0");

            var serviceReasons = map.get(callID);
            if (serviceReasons != null) {
                qaTaskJob.setService_reason(String.join(",", serviceReasons));
                for (String serviceReason : serviceReasons) {
                    String productName = serviceReason.split("_")[0];
                    Optional<QADesignTemplate> qaDesignTemplateOptional = qaDesignTemplateService.findByProduct(productName);
                    if (qaDesignTemplateOptional.isPresent()) {
                        qaTaskJob.setTemplateId(qaDesignTemplateOptional.get().getId());
                        qaTaskJob.setProduct_name(productName);
                    }
                }
            }
            qaTaskJobs.add(qaTaskJob);
        }
        try {
            qaTaskJobs = qaTaskJobService.saveAll(qaTaskJobs);
        } catch (Exception e) {
            log.error("transferTbCallLogToQaTaskJob  errorMessage = {}", String.valueOf(e));
        }

        log.info("transferTbCallLogToQaTaskJob finish");
    }

}
