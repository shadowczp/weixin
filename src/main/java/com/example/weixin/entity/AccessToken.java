package com.example.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AccessToken implements Serializable{
    @JsonProperty(value = "access_token" ,required = true)
    private String accessToken;

    @JsonProperty(value = "expires_in" ,required = true)
    private Integer expireIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", expireIn=" + expireIn +
                '}';
    }
}
