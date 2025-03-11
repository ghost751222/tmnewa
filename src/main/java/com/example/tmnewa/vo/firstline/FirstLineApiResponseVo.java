package com.example.tmnewa.vo.firstline;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstLineApiResponseVo {

    private int id;
    private String token;
    private List<Object> reasons;
}
