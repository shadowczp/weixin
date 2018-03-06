package com.example.weixin.service;

import com.example.weixin.entity.AccessToken;
import com.example.weixin.entity.GetMediaListParam;
import com.example.weixin.entity.MediaList;
import com.example.weixin.schedule.Scheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@Service
@Transactional
public class MainService {
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String UPLOAD_FOREVER_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/upload";
    public static final String GET_MEDIA_LIST_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
    private static final Logger logger = LoggerFactory.getLogger(MainService.class);
    @Value("${token}")
    private String token;
    @Value("${appID}")
    private String appId;
    @Value("${appsecret}")
    private String appsecret;
    @Autowired
    private AccessToken accessToken;

    public String validate(String signature, String timestamp, String nonce, String echostr) {
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        String str = arr[0] + arr[1] + arr[2];
        str = DigestUtils.sha1Hex(str);
        logger.info("str is  : " + str);
        logger.info("signature is  : " + signature);
        if (str.equals(signature)) {
            return echostr;
        } else {
            return null;
        }
    }

    public String getToken() {
        return accessToken.getAccessToken();
    }

    public void refreshToken() {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            URIBuilder uriBuilder =  new URIBuilder(GET_ACCESS_TOKEN_URL);
            uriBuilder.setParameter("grant_type", "client_credential");
            uriBuilder.setParameter("appid", appId);
            uriBuilder.setParameter("secret", appsecret);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            String jsonStr = EntityUtils.toString(entity, "UTF-8");
            logger.info("call access_token interface with data : " + jsonStr);
            ObjectMapper objectMapper = new ObjectMapper();
            AccessToken newToken = objectMapper.readValue(jsonStr, AccessToken.class);
            accessToken.setAccessToken(newToken.getAccessToken());
            accessToken.setExpireIn(newToken.getExpireIn());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public MediaList getMedia(String type) {
        return getMedia(type, 0, 20);
    }

    public MediaList getMedia(String type, Integer offset) {
        return getMedia(type, offset, 20);
    }

    public MediaList getMedia(String type, Integer offset, Integer count) {
        GetMediaListParam getMediaListParam = new GetMediaListParam();
        getMediaListParam.setCount(count);
        getMediaListParam.setOffset(offset);
        getMediaListParam.setType(type);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String paramStr = objectMapper.writeValueAsString(getMediaListParam);
            HttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            URIBuilder uriBuilder = new URIBuilder(GET_MEDIA_LIST_URL);
            uriBuilder.setParameter("access_token", accessToken.getAccessToken());
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            StringEntity param = new StringEntity(paramStr);
            param.setContentEncoding("UTF-8");
            param.setContentType("application/json");
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(param);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String jsonStr = EntityUtils.toString(entity, "UTF-8");
            logger.info("call get_media_list interface with data : " + jsonStr);
            MediaList mediaList = objectMapper.readValue(jsonStr, MediaList.class);
            return mediaList;
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
