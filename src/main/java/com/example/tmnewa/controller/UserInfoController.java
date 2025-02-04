package com.example.tmnewa.controller;


import com.example.tmnewa.entity.UserInfo;
import com.example.tmnewa.service.UserInfoService;
import com.example.tmnewa.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tmnewa.vo.RequestQueryVo;
import com.example.tmnewa.vo.ResponseVo;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    HttpSession httpSession;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String page() {
        return "userInfo";
    }


    @RequestMapping(value = "/data")
    @ResponseBody
    public Page<UserInfo> index(@RequestParam Map<String, Object> params) {

        RequestQueryVo requestQueryVo = JacksonUtils.mapToObject(params, RequestQueryVo.class);


        String sortSource = requestQueryVo.getSort();
        String sortField = null;
        String sortDirection = null;
        int page = requestQueryVo.getPageIndex() - 1;
        int size = requestQueryVo.getPageSize();
        PageRequest pageRequest = null;
        if (!Strings.isEmpty(sortSource)) {
            sortField = sortSource.split("\\|")[0];
            sortDirection = sortSource.split("\\|")[1];
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortField).ascending()
                    : Sort.by(sortField).descending();
            pageRequest = PageRequest.of(page, size, sort);
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        return userInfoService.findByQueryParameter(requestQueryVo, pageRequest);


    }


    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo<UserInfo> addUserInfo(@RequestBody UserInfo userInfo) throws JsonProcessingException {
        ResponseVo<UserInfo> responseVo = new ResponseVo<>();
        responseVo.setMessage("新增成功");
        responseVo.setData(userInfoService.addUserInfo(userInfo));
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVo<UserInfo> updateUserInfo(@RequestBody UserInfo userInfo) throws JsonProcessingException {
        ResponseVo<UserInfo> responseVo = new ResponseVo<>();
        responseVo.setMessage("更新成功");
        responseVo.setData(userInfoService.updateUserInfo(userInfo));
        return responseVo;
    }

    @RequestMapping(value = {"/rePassword"}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVo<UserInfo> rePassword(@RequestBody String  password) throws IOException {
        password = JacksonUtils.toJsonNode(password).get("password").asText();
        UserInfo userInfo=  JacksonUtils.readValue((String)httpSession.getAttribute("userInfo"),UserInfo.class);
        ResponseVo<UserInfo> responseVo = new ResponseVo<>();
        responseVo.setMessage("更新成功");
        userInfo.setPassword(password);
        responseVo.setData(userInfoService.updateUserInfo(userInfo));
        return responseVo;
    }


    @RequestMapping(value = {"/", ""}, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo<UserInfo> deleteUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.deleteUserInfo(userInfo);
        ResponseVo<UserInfo> responseVo = new ResponseVo<>();
        responseVo.setMessage("刪除成功");
        responseVo.setData(userInfoService.deleteUserInfo(userInfo));
        return responseVo;
    }
}
