package com.beijing.tenfingers.bean;

import java.io.Serializable;

import xtom.frame.XtomObject;

/**
 * 广告
 */
public class Advertise extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String link;
    private String type;//0 视频 1 图片

    private String isThumb;//是否点赞
    private String tea_thumb_count;//点赞数

    private String isCollect;//是否收藏
    private String tea_collect_count;//收藏数量
    private String tea_img_link;//视频播放默认图片
    private String tea_title;//茶标题


    public Advertise(String link, String type, String isThumb, String tea_thumb_count, String isCollect, String tea_collect_count, String tea_img_link, String tea_title) {
        this.link = link;
        this.type = type;
        this.isThumb = isThumb;
        this.tea_thumb_count = tea_thumb_count;
        this.isCollect = isCollect;
        this.tea_collect_count = tea_collect_count;
        this.tea_img_link = tea_img_link;
        this.tea_title = tea_title;
    }

    public String getIsThumb() {
        return isThumb;
    }

    public String getTea_thumb_count() {
        return tea_thumb_count;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public String getTea_collect_count() {
        return tea_collect_count;
    }

    public String getTea_img_link() {
        return tea_img_link;
    }

    public String getTea_title() {
        return tea_title;
    }

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }
}
