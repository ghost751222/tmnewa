package com.example.tmnewa.controller.azure;


import com.example.tmnewa.service.azure.AzureApiService;
import com.example.tmnewa.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/azure")
@Slf4j
public class AzureController {

    @Autowired
    AzureApiService azureApiService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String azureLogin(@RequestBody String body) {

        try {
            var params =  decodeQuery(URLDecoder.decode(body, StandardCharsets.UTF_8));
            if(params.containsKey("code")){
                var code = params.get("code");
                log.info(code);
                var azureResponseVo =azureApiService.accessToken(code);
                log.info(JacksonUtils.writeValueAsString(azureResponseVo));
                azureApiService.me(azureResponseVo.getAccess_token());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return body;
    }

    public Map<String, String> decodeQuery(String query) {
        Map<String, String> queryParams = new HashMap<>();
        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
            queryParams.put(key, value);
        }
        return queryParams;
    }
}
