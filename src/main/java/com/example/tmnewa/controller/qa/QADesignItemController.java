package com.example.tmnewa.controller.qa;


import com.example.tmnewa.entity.qa.QADesignItem;
import com.example.tmnewa.servcive.qa.QADesignItemService;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/qaDesignItem")
@Slf4j
public class QADesignItemController {

    @Autowired
    QADesignItemService qaDesignItemService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String page() {
        return "qaDesignItem";
    }

    @RequestMapping(value = "/{templateId}", method = RequestMethod.GET)
    @ResponseBody
    public List<QADesignItem> findByTemplateIDAndPidOrderBySeqAsc(@PathVariable("templateId") Long templateId) {
        return qaDesignItemService.findAllChildrenByTemplateIDAndPid(templateId, null);
    }


    @RequestMapping(value = "/data")
    @ResponseBody
    public Page<QADesignItem> index(@RequestParam Map<String, Object> params) {

        RequestQueryVo requestQueryVo = JacksonUtils.mapToObject(params, RequestQueryVo.class);
        Long qaTemplateId = params.containsKey("qaTemplateId") ?Long.parseLong((String) params.get("qaTemplateId")) : null;
        Long parentId = params.containsKey("parentId") ? Long.parseLong((String)params.get("parentId")) : null;
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

        return qaDesignItemService.findPageByQaTemplateIdAndParentId(qaTemplateId, parentId, pageRequest);
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo<QADesignItem> addQADesignItem(@RequestBody QADesignItem qaDesignItem) throws JsonProcessingException {
        ResponseVo<QADesignItem> responseVo = new ResponseVo<>();
        responseVo.setMessage("新增成功");
        responseVo.setData(qaDesignItemService.addQADesignItem(qaDesignItem));
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVo<QADesignItem> updateQADesignItem(@RequestBody QADesignItem qaDesignItem) throws JsonProcessingException {
        ResponseVo<QADesignItem> responseVo = new ResponseVo<>();
        responseVo.setMessage("更新成功");
        responseVo.setData(qaDesignItemService.updateQADesignItem(qaDesignItem));
        return responseVo;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo<QADesignItem> deleteQADesignItem(@RequestBody QADesignItem qaDesignItem) {
        qaDesignItemService.deleteQADesignItem(qaDesignItem);
        ResponseVo<QADesignItem> responseVo = new ResponseVo<>();
        responseVo.setMessage("刪除成功");
        responseVo.setData(qaDesignItemService.deleteQADesignItem(qaDesignItem));
        return responseVo;
    }
}
