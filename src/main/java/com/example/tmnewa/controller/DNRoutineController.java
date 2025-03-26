package com.example.tmnewa.controller;

import com.example.tmnewa.entity.DNRoutine;
import com.example.tmnewa.service.DNRoutineService;
import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.RequestQueryVo;
import com.example.tmnewa.vo.ResponseVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/dnRoutine")
public class DNRoutineController {

    @Autowired
    DNRoutineService dnRoutineService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String page() {
        return "dnRoutine";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Page<DNRoutine> data(@RequestParam Map<String, Object> params, Model model) {
        PageRequest pageRequest = (PageRequest) model.getAttribute("pageRequest");
        RequestQueryVo requestQueryVo = JacksonUtils.mapToObject(params, RequestQueryVo.class);
        return dnRoutineService.findByQueryParameter(requestQueryVo, pageRequest);
    }


    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo<DNRoutine> add(@RequestBody DNRoutine dnRoutine) throws JsonProcessingException {
        ResponseVo<DNRoutine> responseVo = new ResponseVo<>();
        responseVo.setMessage("新增成功");
        responseVo.setData(dnRoutineService.add(dnRoutine));
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVo<DNRoutine> update(@RequestBody DNRoutine dnRoutine) throws JsonProcessingException {
        ResponseVo<DNRoutine> responseVo = new ResponseVo<>();
        responseVo.setMessage("更新成功");
        responseVo.setData(dnRoutineService.update(dnRoutine));
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo<DNRoutine> delete(@RequestBody DNRoutine dnRoutine) throws JsonProcessingException {
        ResponseVo<DNRoutine> responseVo = new ResponseVo<>();
        responseVo.setMessage("刪除成功");
        responseVo.setData(dnRoutineService.delete(dnRoutine));
        return responseVo;
    }
}
