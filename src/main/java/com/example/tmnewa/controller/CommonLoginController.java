package com.example.tmnewa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/common/login")
public class CommonLoginController {

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public String commonLogin() {
        return "commonLogin";
    }
}
