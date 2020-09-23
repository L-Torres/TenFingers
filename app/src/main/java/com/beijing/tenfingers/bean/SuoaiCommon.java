package com.beijing.tenfingers.bean;

import java.io.Serializable;

import xtom.frame.XtomObject;

/**
 * 首页所爱 通用类（未评价&&所爱）
 */
public class SuoaiCommon extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String tea_img_link;
    private String tea_title;
    private String friend_count;
    private String tea_id;
    private String type;//0 未评价 1 所爱
    private String tea_sell_count;
    private String price;
    private String tea_vidio_link;
    private String tea_vidio_time;
    private String can;
    private String activity_price;
    private String tea_alltype;

    public SuoaiCommon() {
    }


    public SuoaiCommon(String id, String tea_img_link, String tea_title, String friend_count, String tea_id,
                       String type, String price, String tea_sell_count, String tea_vidio_link,
                       String tea_vidio_time, String can, String activity_price, String tea_alltype) {
        this.id = id;
        this.tea_img_link = tea_img_link;
        this.tea_title = tea_title;
        this.friend_count = friend_count;
        this.tea_id = tea_id;
        this.type = type;
        this.price = price;
        this.tea_sell_count = tea_sell_count;
        this.tea_vidio_link = tea_vidio_link;
        this.tea_vidio_time = tea_vidio_time;
        this.can = can;
        this.activity_price = activity_price;
        this.tea_alltype = tea_alltype;
    }

    public SuoaiCommon(String id, String tea_img_link, String tea_title, String friend_count, String tea_id, String type,
                       String tea_alltype) {
        this.id = id;
        this.tea_img_link = tea_img_link;
        this.tea_title = tea_title;
        this.friend_count = friend_count;
        this.tea_id = tea_id;
        this.type = type;
        this.tea_alltype = tea_alltype;
    }

    public SuoaiCommon(String id, String tea_img_link, String tea_title, String friend_count, String tea_id, String type, String price,
                       String tea_alltype) {
        this.id = id;
        this.tea_img_link = tea_img_link;
        this.tea_title = tea_title;
        this.friend_count = friend_count;
        this.tea_id = tea_id;
        this.type = type;
        this.price = price;
        this.tea_alltype = tea_alltype;
    }

    public String getTea_vidio_link() {
        return tea_vidio_link;
    }

    public String getTea_sell_count() {
        return tea_sell_count;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTea_alltype() {
        return tea_alltype;
    }

    public String getTea_img_link() {
        return tea_img_link;
    }

    public void setTea_img_link(String tea_img_link) {
        this.tea_img_link = tea_img_link;
    }

    public String getTea_title() {
        return tea_title;
    }

    public void setTea_title(String tea_title) {
        this.tea_title = tea_title;
    }

    public String getFriend_count() {
        return friend_count;
    }

    public void setFriend_count(String friend_count) {
        this.friend_count = friend_count;
    }

    public String getTea_id() {
        return tea_id;
    }

    public void setTea_id(String tea_id) {
        this.tea_id = tea_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTea_vidio_time() {
        return tea_vidio_time;
    }

    public String getCan() {
        return can;
    }

    public String getActivity_price() {
        return activity_price;
    }
}
