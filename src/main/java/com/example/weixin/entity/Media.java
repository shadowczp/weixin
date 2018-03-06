package com.example.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Media {
    @JsonProperty(value = "media_id" ,required = true)
    private String mediaId;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "update_time")
    private String updateTime;
    @JsonProperty(value = "url")
    private String url;
    @JsonProperty(value = "content")
    private List<NewsItem> content;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NewsItem> getContent() {
        return content;
    }

    public void setContent(List<NewsItem> content) {
        this.content = content;
    }
}
