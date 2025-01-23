package com.example.tmnewa.vo;


import lombok.Data;

@Data
public class ResponseVo<T> {

    private int statusCode = 200;
    private String message;
    private T data;

}
