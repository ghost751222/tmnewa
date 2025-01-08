package com.example.tmnewa.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Dictionary;

@Controller
@RequestMapping(value = "/login")
public class LoginController {


    @RequestMapping(value = "",method = RequestMethod.GET)
    public String page() {
        return  "login";
    }



}
