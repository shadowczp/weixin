package com.example.weixin.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public Map<String,Object> handle(HttpServletRequest request,Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put("success",false);
        result.put("errMsg",e.getMessage());
        return result;
    }
}
