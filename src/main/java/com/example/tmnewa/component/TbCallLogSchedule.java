package com.example.tmnewa.component;


import com.example.tmnewa.entity.TbCallLog;
import com.example.tmnewa.entity.qa.QaTaskJob;
import com.example.tmnewa.service.TbCallLogService;
import com.example.tmnewa.service.qa.QATaskJobService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class TbCallLogSchedule {

    @Autowired
    TbCallLogService tbCallLogService;

    @Autowired
    QATaskJobService qaTaskJobService;

    @PostConstruct
    public void transferTbCallLogToQaTaskJob() {
        LocalDateTime start = LocalDateTime.now().minusYears(3);
        LocalDateTime end = start.plusDays(60);
        transferTbCallLogToQaTaskJob(start, end);
    }


    public void transferTbCallLogToQaTaskJob(LocalDateTime startTime, LocalDateTime endTime) {
        log.info("transferTbCallLogToQaTaskJob start execute startTime= " + startTime + " endTime = " + endTime);
        List<TbCallLog> tbCallLogs = tbCallLogService.findByStartTimeAndEndTime(startTime, endTime);
        List<QaTaskJob> qaTaskJobs = new ArrayList<>();
        for(TbCallLog tbCallLog :tbCallLogs){
            var qaTaskJob = new QaTaskJob();
            qaTaskJob.setCall_id(tbCallLog.getF_call_id());
            qaTaskJob.setCall_start_time(tbCallLog.getF_start_time());
            qaTaskJob.setCall_stop_time(tbCallLog.getF_stop_time());
            qaTaskJob.setCall_agent_id(tbCallLog.getF_agent_id());
            qaTaskJob.setCall_customer_id(tbCallLog.getF_customer_id());
            qaTaskJob.setCall_ext_no(tbCallLog.getF_ext_no());
            qaTaskJob.setCreator(0L);
            qaTaskJob.setCreatedAt(LocalDateTime.now());
            qaTaskJobs.add(qaTaskJob);
        }
        try{
            qaTaskJobs = qaTaskJobService.saveAll(qaTaskJobs);
        }catch (Exception e){
            log.error("transferTbCallLogToQaTaskJob  errorMessage = {}", String.valueOf(e));
        }

        log.info("transferTbCallLogToQaTaskJob finish");
    }

}
