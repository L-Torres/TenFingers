package com.beijing.tenfingers.push;

import java.io.Serializable;

import xtom.frame.XtomObject;

/**
 * Created by lenovo on 2017/5/26.
 */

public class PushModel extends XtomObject implements Serializable {

    private static final long serialVersionUID = -2059126470520707606L;
    private String messageId;
    private String id;
    private String keyType;
    private String keyId;
    private String msg;
    private String reserve_type;
    private String targetid;
    private String web_title;
    private String web_link;
    private String title;

    public PushModel(String keyType, String keyId, String msg,String reserve_type,String targetid, String web_title,
                     String web_link, String id, String title) {
        this.keyType = keyType;
        this.keyId = keyId;
        this.msg = msg;
        this.reserve_type = reserve_type;
        this.targetid = targetid;
        this.web_title = web_title;
        this.web_link = web_link;
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getMsg() {
        return msg;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReserve_type() {
        return reserve_type;
    }

    public void setReserve_type(String reserve_type) {
        this.reserve_type = reserve_type;
    }

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    public String getWeb_title() {
        return web_title;
    }

    public void setWeb_title(String web_title) {
        this.web_title = web_title;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }
}

