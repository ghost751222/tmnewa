package com.example.tmnewa.service;


import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {


    public final static String DefaultPassWord = "123456";

    @Autowired
    HttpSession httpSession;

    public UserInfo getUserInfo() throws JsonProcessingException {
        return  JacksonUtils.readValue((String)httpSession.getAttribute("userInfo"),UserInfo.class);
    }

    public Long getLoginId() throws JsonProcessingException {
        UserInfo userInfo = this.getUserInfo();
        return  userInfo.getId();
    }
}
