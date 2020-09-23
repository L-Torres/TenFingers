package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * v1/my
 * 我的数据 需登录
 */
public class MyData extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String comment_count;
    private String u_image_link;
    private String u_nickname;
    private String is_vip;
    private String u_account;
    private String id;
    private String slot_count;
    private String collect_count;
    private String u_sex;


    public MyData(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                comment_count=get(jsonObject,"comment_count");
                u_image_link=get(jsonObject,"u_image_link");
                u_nickname=get(jsonObject,"u_nickname");
                is_vip=get(jsonObject,"is_vip");
                u_account=get(jsonObject,"u_account");
                id=get(jsonObject,"id");
                slot_count=get(jsonObject,"slot_count");
                collect_count=get(jsonObject,"collect_count");
                u_sex=get(jsonObject,"u_sex");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "MyData{" +
                "comment_count='" + comment_count + '\'' +
                ", u_image_link='" + u_image_link + '\'' +
                ", u_nickname='" + u_nickname + '\'' +
                ", is_vip='" + is_vip + '\'' +
                ", u_account='" + u_account + '\'' +
                ", id='" + id + '\'' +
                ", slot_count='" + slot_count + '\'' +
                ", collect_count='" + collect_count + '\'' +
                ", u_sex='" + u_sex + '\'' +
                '}';
    }

    public String getComment_count() {
        return comment_count;
    }

    public String getU_image_link() {
        return u_image_link;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public String getU_account() {
        return u_account;
    }

    public String getId() {
        return id;
    }

    public String getSlot_count() {
        return slot_count;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public String getU_sex() {
        return u_sex;
    }
}
