package com.example.weixin.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController()
public class MainController {
    @RequestMapping("hello")
    public String hello(){
        return "hello world";
    }
    @GetMapping("weixin")
    public String validate(String signature,String timestamp,String nonce,String echostr){
        String token="shadowczp";
        if((timestamp == null) || nonce == null){
            return null;
        }
        String[] arr = new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        String str = arr[0]+arr[1]+arr[2];
        str= DigestUtils.sha1Hex(str);
        System.out.println("str is  : "+str);
        System.out.println("signature is  : "+signature);
        if(str.equals(signature)){
            return echostr;
        }else {
            return null;
        }

    }
    @PostMapping("weixin")
    public void weixin(){

    }
}
