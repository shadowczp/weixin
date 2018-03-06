package com.example.weixin.controller;

import com.example.weixin.entity.AccessToken;
import com.example.weixin.entity.FromMessage;
import com.example.weixin.entity.ToMessage;
import com.example.weixin.entity.User;
import com.example.weixin.schedule.Scheduler;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController()
public class MainController {
    @Autowired
    private AccessToken accessToken;
    @Autowired
    private Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Value("${token}")
    private String token;

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/weixin")
    public String validate(String signature, String timestamp, String nonce, String echostr) {
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

    @PostMapping("weixin")
    public ToMessage serve(@RequestBody FromMessage xml) throws Exception {
        logger.info("\n=============\n" + xml.toString() + "\n=============\n");
        ToMessage toMessage = new ToMessage();
        toMessage.setContent("success");
        toMessage.setCreateTime(System.currentTimeMillis() + "");
        toMessage.setFromUserName(xml.getToUserName());
        toMessage.setMsgType("text");
        toMessage.setToUserName(xml.getFromUserName());
        String msgType = xml.getMsgType();
        if ("text".equals(msgType)) {
            toMessage.setContent("非常感谢您关注影子的公众号，您有什么需要的资源可以发送资源名称给影子，影子会帮您在后台中查询的");
        } else if ("image".equals(msgType)) {
            toMessage.setMsgType("image");
            toMessage.setMediaId(Arrays.asList(xml.getMediaId()));
        }
        return toMessage;
    }

    @RequestMapping("/get/token")
    public AccessToken getToken() {
        scheduler.refreshToken();
        return accessToken;
    }

    @RequestMapping(value = "/get/user")
    public User getUser() {
        User user = new User();
        user.setName("czo");
        user.setAge(15);
        return user;
    }
}
