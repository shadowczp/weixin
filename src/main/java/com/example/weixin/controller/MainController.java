package com.example.weixin.controller;

import com.example.weixin.entity.*;
import com.example.weixin.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController()
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private MainService mainService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/weixin")
    public String validate(String signature, String timestamp, String nonce, String echostr) {
        if ((timestamp == null) || nonce == null) {
            return null;
        }
        return mainService.validate(signature, timestamp, nonce, echostr);
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
        toMessage.setMsgType("news");
        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setDescription("描述1");
        news.setPicUrl("https://mmbiz.qpic.cn/mmbiz_jpg/rQGBQhgrgYzLfBMlOznF0DykIn5gm98EOPicmSvRfiaKEXCdtZanY5OiaUsYkm7spDJjJtgicEMbd21vicC37edMDmw/0?wx_fmt=jpeg");
        news.setTitle("标题1");
        news.setUrl("blog.lovepp.xin");
        newsList.add(news);
        news = new News();
        news.setPicUrl("http://img.blog.csdn.net/20161102104535158");
        news.setUrl("blog.lovepp.xin");
        news.setTitle("标题2");
        news.setDescription("描述2");
        newsList.add(news);
        toMessage.setArticleCount(2);
        toMessage.setArticles(newsList);
        return toMessage;
    }

    @RequestMapping("/get/token")
    public String getToken() {

        return mainService.getToken();
    }

    @RequestMapping(value = "/get/user")
    public User getUser() {
        User user = new User();
        user.setName("czo");
        user.setAge(15);
        return user;
    }

    @RequestMapping("/get/media")
    public MediaList getMedia() {
        return mainService.getMedia("news",20);
    }
}
