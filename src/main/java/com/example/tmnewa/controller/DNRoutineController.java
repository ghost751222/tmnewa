package com.example.tmnewa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dnRoutine")
public class DNRoutineController {

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public String page(){
        return "dnRoutine";
    }
}
