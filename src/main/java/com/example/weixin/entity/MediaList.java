package com.example.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MediaList {
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonProperty("item_count")
    private Integer itemCount;
    @JsonProperty("item")
    private List<Media> item;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public List<Media> getItem() {
        return item;
    }

    public void setItem(List<Media> item) {
        this.item = item;
    }
}
