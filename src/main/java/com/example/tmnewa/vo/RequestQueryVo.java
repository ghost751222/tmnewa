package com.example.tmnewa.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RequestQueryVo {
    private String account;
    private String sort;
    private int pageIndex;
    private int pageSize;

}
