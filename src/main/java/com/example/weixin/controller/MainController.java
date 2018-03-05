package com.example.weixin.controller;

import com.example.weixin.entity.FromMessage;
import com.example.weixin.entity.ToMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController()
public class MainController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/weixin")
    public String validate(String signature, String timestamp, String nonce, String echostr) {
        String token = "shadowczp";
        if ((timestamp == null) || nonce == null) {
            return null;
        }
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        String str = arr[0] + arr[1] + arr[2];
        str = DigestUtils.sha1Hex(str);
        System.out.println("str is  : " + str);
        System.out.println("signature is  : " + signature);
        if (str.equals(signature)) {
            return echostr;
        } else {
            return null;
        }

    }

    @PostMapping(value = "/weixin", consumes = {"text/xml;charset=UTF-8"}, produces = {"application/xml;charset=UTF-8"})
    public ToMessage serve(@RequestBody FromMessage xml) throws Exception {
        System.out.println("\n=============\n"+xml.toString()+"\n=============\n");
        ToMessage toMessage = new ToMessage();
        toMessage.setContent("success");
        toMessage.setCreateTime(System.currentTimeMillis() + "");
        toMessage.setFromUserName(xml.getToUserName());
        toMessage.setMsgType("text");
        toMessage.setToUserName(xml.getFromUserName());
        if ("text".equals(xml.getMsgType())) {
            toMessage.setContent("非常感谢您关注影子的公众号，您有什么需要的资源可以发送资源名称给影子，影子会帮您在后台中查询的");
        }
        return toMessage;
    }

    @RequestMapping("/exception")
    public Integer exception() {
        return 1 / 0;
    }
}
