package com.example.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

//GET_MEDIA_LIST接口需要传入的参数
public class GetMediaListParam {
    @JsonProperty("type")
    private String type;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("count")
    private Integer count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
