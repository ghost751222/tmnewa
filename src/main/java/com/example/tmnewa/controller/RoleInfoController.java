package com.example.tmnewa.controller;


import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/roleInfo")
public class RoleInfoController {

    @Autowired
    RoleInfoService roleInfoService;

    @RequestMapping(value = "")
    @ResponseBody
    public List<RoleInfo> index(@RequestParam Map<String, Object> params) {
        return roleInfoService.findAll();
    }
}
