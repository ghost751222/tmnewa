package com.example.tmnewa.service;


import com.example.tmnewa.entity.TbCallLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbCallLogService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<TbCallLog> findByStartTimeAndEndTime(LocalDateTime startTime , LocalDateTime endTime){

        String sql = " select * from ezacd8000.tb_call_log " +
                     " where date(f_start_time) between date(:startTime) and date(:endTime) " +
                     " order by f_start_time ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        return namedParameterJdbcTemplate.query(sql, parameters,new BeanPropertyRowMapper<>(TbCallLog.class));
    }

}
