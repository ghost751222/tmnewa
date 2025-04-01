package com.example.tmnewa.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RequestQueryVo {
    private String account;
    private String sort;
    private int pageIndex;
    private int pageSize;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dn;
    private String dayOfWeek;
    private Integer holidayType;
    private String productName;
}
