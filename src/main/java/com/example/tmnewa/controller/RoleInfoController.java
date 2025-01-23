package com.example.tmnewa.controller;


import com.example.tmnewa.entity.RoleInfo;
import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.servcive.RoleInfoService;
import com.example.tmnewa.servcive.UserInfoService;
import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.RequestQueryVo;
import com.example.tmnewa.vo.ResponseVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
