package com.example.tmnewa.controller.qa;

import com.example.tmnewa.component.TbCallLogSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/qaTaskJob")
public class QaTaskJobController {

    @Autowired
    TbCallLogSchedule tbCallLogSchedule;



    @RequestMapping(value = {"/",""} ,method = RequestMethod.GET)
    public String page(){
        return "qaTaskJob";
    }
}
