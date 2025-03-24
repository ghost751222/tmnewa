package com.example.tmnewa.service;

import com.example.tmnewa.entity.HolidayServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HolidayServiceTypeService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<HolidayServiceType> findAll() {
        String sql = " select * from holiday_service_type ";
        Map<String, Object> parameters = new HashMap<>();
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(HolidayServiceType.class));
    }

    public List<HolidayServiceType> findAllByValue(String value) {
        String sql = " select * from holiday_service_type where value=:value";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("value",value);
        return namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(HolidayServiceType.class));
    }
}
