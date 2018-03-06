package com.example.weixin.schedule;

import com.example.weixin.entity.AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;


@Component
public class Scheduler {
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    @Value("${appID}")
    private String appId;
    @Value("${appsecret}")
    private String appsecret;
    @Autowired
    private AccessToken accessToken;

    //10分钟刷新一次token
    @Scheduled(fixedDelay = 600000)
    public void refreshToken() {
        HttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
        HttpGet httpGet = null;
        try {
            URIBuilder uriBuilder = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token");
            uriBuilder.setParameter("grant_type", "client_credential");
            uriBuilder.setParameter("appid", appId);
            uriBuilder.setParameter("secret", appsecret);
            httpGet = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            logger.error("URISyntaxException generate httpGet default");
            return;
        }

        httpGet.setConfig(requestConfig);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("IOException call access_token interface default");
            return;
        }
        HttpEntity entity = httpResponse.getEntity();
        String jsonStr = null;
        try {
            jsonStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("IOException call access_token interface with an error data");
            return;
        }
        logger.info("call access_token interface with data : "+jsonStr);
        ObjectMapper objectMapper = new ObjectMapper();
        AccessToken newToken = null;
        try {
            newToken = objectMapper.readValue(jsonStr, AccessToken.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("IOException call access_token interface has some errors");
            return;
        }
        accessToken.setAccessToken(newToken.getAccessToken());
        accessToken.setExpireIn(newToken.getExpireIn());
    }
}
