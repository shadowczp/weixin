package com.example.weixin.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.List;

@JacksonXmlRootElement(localName="xml")
public class ToMessage implements Serializable{

    @JacksonXmlCData
    @JacksonXmlProperty(localName="ToUserName")
    private String toUserName;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName="CreateTime")
    private String createTime;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="MsgType")
    private String msgType;

    @JacksonXmlCData
    @JacksonXmlProperty(localName="Content")
    private String content;

    @JacksonXmlElementWrapper(localName ="Image")
    @JacksonXmlCData
    @JacksonXmlProperty(localName="MediaId")
    private List<String> mediaId;

    public String getToUserName()
    {
        return this.toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName()
    {
        return this.fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType()
    {
        return this.msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMediaId() {
        return mediaId;
    }

    public void setMediaId(List<String> mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "ToMessage{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", mediaId=" + mediaId +
                '}';
    }
}