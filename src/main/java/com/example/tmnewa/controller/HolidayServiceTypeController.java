package com.example.tmnewa.controller;


import com.example.tmnewa.entity.HolidayServiceType;
import com.example.tmnewa.service.HolidayServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/holidayServiceType")
public class HolidayServiceTypeController {


    @Autowired
    HolidayServiceTypeService holidayServiceTypeService;

    @RequestMapping(value = "")
    @ResponseBody
    public List<HolidayServiceType> findAll(){
        return  holidayServiceTypeService.findAll();
    }
}
