package com.example.tmnewa.service;


import com.example.tmnewa.entity.TbCallLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbCallLogService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<TbCallLog> findByStartTimeAndEndTime(LocalDate startTime , LocalDate endTime){
        String sql = " select * from ezacd8000.tb_call_log " +
                     " where date(f_start_time) >= date(:startTime)" +
                     "   and date(f_stop_time) <= date(:endTime) " +
                     "   and f_start_time != '0000-00-00 00:00:00'" +
                    "    and f_stop_time !='0000-00-00 00:00:00' " +
                     " order by f_start_time ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        return namedParameterJdbcTemplate.query(sql, parameters,new BeanPropertyRowMapper<>(TbCallLog.class));
    }

}
