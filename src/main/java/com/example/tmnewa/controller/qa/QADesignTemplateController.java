package com.example.tmnewa.controller.qa;


import com.example.tmnewa.entity.qa.QADesignTemplate;
import com.example.tmnewa.service.qa.QADesignTemplateService;
import com.example.tmnewa.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.tmnewa.vo.RequestQueryVo;
import com.example.tmnewa.vo.ResponseVo;

import java.util.Map;

@Controller
@RequestMapping(value = "/qaDesignTemplate")
@Slf4j
public class QADesignTemplateController {

    @Autowired
    QADesignTemplateService qaDesignTemplateService;

    @RequestMapping(value = {"/",""},method = RequestMethod.GET)
    public String page() {
        return "qaDesignTemplate";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Page<QADesignTemplate> index(@RequestParam Map<String, Object> params) {

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

        return qaDesignTemplateService.findByQueryParameter(requestQueryVo, pageRequest);
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo<QADesignTemplate> addQADesignTemplate(@RequestBody QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        ResponseVo<QADesignTemplate> responseVo = new ResponseVo<>();
        responseVo.setMessage("新增成功");
        responseVo.setData(qaDesignTemplateService.addQADesignTemplate(qaDesignTemplate).getV1());
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVo<QADesignTemplate> updateQADesignTemplate(@RequestBody QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        ResponseVo<QADesignTemplate> responseVo = new ResponseVo<>();
        responseVo.setMessage("更新成功");
        responseVo.setData(qaDesignTemplateService.updateQADesignTemplate(qaDesignTemplate));
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo<QADesignTemplate> deleteQADesignTemplate(@RequestBody QADesignTemplate qaDesignTemplate) {
        qaDesignTemplateService.deleteQADesignTemplate(qaDesignTemplate);
        ResponseVo<QADesignTemplate> responseVo = new ResponseVo<>();
        responseVo.setMessage("刪除成功");
        responseVo.setData(qaDesignTemplateService.deleteQADesignTemplate(qaDesignTemplate));
        return responseVo;
    }


    @RequestMapping(value = {"/copy"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo<QADesignTemplate> copyQADesignTemplate(@RequestBody QADesignTemplate qaDesignTemplate) throws JsonProcessingException {
        ResponseVo<QADesignTemplate> responseVo = new ResponseVo<>();
        responseVo.setMessage("複製成功");
        responseVo.setData(qaDesignTemplateService.copyQADesignTemplate(qaDesignTemplate));
        return responseVo;
    }
}
