package com.example.tmnewa.controller;

import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.RequestQueryVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController implements ResponseBodyAdvice<Object> {


    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest req, @RequestParam Map<String, Object> params) {
        // 取得當前認證的使用者
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            RequestQueryVo requestQueryVo = JacksonUtils.mapToObject(params, RequestQueryVo.class);
            String sortSource = requestQueryVo.getSort();
            String sortField = null;
            String sortDirection = null;
            int page = requestQueryVo.getPageIndex() - 1;
            if (page <= 0) page = 0;
            int size = requestQueryVo.getPageSize();
            if (size <= 0) size = 10;
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

            model.addAttribute("pageRequest", pageRequest);
            model.addAttribute("roles", authentication.getAuthorities());
        }

    }

    //@ExceptionHandler(Exception.class)
    public void handleError(HttpServletRequest req, Exception ex) {
        log.error("{}", "Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        //return mav;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  java.lang.Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return body;
//        if (body instanceof PageImpl<?>) {
//            return body;
//        }
//        int status = HttpStatus.OK.value();
//        if (response instanceof ServletServerHttpResponse) {
//            status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
//        }
//        response.setStatusCode(HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.valueOf(status)).body(body);
    }
}
