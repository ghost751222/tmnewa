package com.example.tmnewa.controller;


import com.example.tmnewa.entity.DNProduct;
import com.example.tmnewa.entity.HolidayServiceType;
import com.example.tmnewa.service.DNProductService;
import com.example.tmnewa.service.HolidayServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/dnProduct")
public class DNProductController {


    @Autowired
    DNProductService dnProductService;

    @RequestMapping(value = "")
    @ResponseBody
    public List<DNProduct> findAll(){
        return  dnProductService.findAll();
    }
}
