package com.example.tmnewa.controller.qa;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/qa")
public class QAInspectionController {


    @RequestMapping(value = {"/",""} ,method = RequestMethod.GET)
    public String page() {
        return "qaInspection";
    }
}
