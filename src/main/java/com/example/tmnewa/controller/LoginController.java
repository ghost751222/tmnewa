package com.example.tmnewa.controller;


import com.example.tmnewa.config.TWNEWAConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/login")
@Slf4j
public class LoginController {

    @Bean(name = "twnewaConfigProperties")
    public TWNEWAConfigProperties twnewaConfigProperties() {
        return new TWNEWAConfigProperties();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String page() {
        return "login";
    }


}
