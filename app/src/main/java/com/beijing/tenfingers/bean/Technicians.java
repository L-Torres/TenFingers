package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Technicians extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String t_name;
    private String t_username;
    private String t_english_name;
    private String t_image_link;
    private String t_collect_count;
    private String t_hot_value;
    private String t_high_value;
    private String t_distance;
    private String t_longitude;
    private String t_latitude;
    private String is_week;
    private String is_month;
    private String u_distance;
    private String s_id;
    private String is_vip;
    private String t_sex;
    private String t_mobile;
    public Technicians(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                t_name = get(jsonObject, "t_name");
                t_username = get(jsonObject, "t_username");
                t_english_name = get(jsonObject, "t_english_name");
                t_image_link = get(jsonObject, "t_image_link");
                t_collect_count = get(jsonObject, "t_collect_count");
                t_hot_value = get(jsonObject, "t_hot_value");
                t_high_value = get(jsonObject, "t_high_value");
                t_distance = get(jsonObject, "t_distance");
                t_longitude = get(jsonObject, "t_longitude");
                t_latitude = get(jsonObject, "t_latitude");
                is_week = get(jsonObject, "is_week");
                is_month = get(jsonObject, "is_month");
                u_distance=get(jsonObject, "u_distance");
                s_id=get(jsonObject,"s_id");
                is_vip=get(jsonObject,"is_vip");
                t_sex=get(jsonObject,"t_sex");
                t_mobile=get(jsonObject,"t_mobile");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Technicians{" +
                "id='" + id + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_username='" + t_username + '\'' +
                ", t_english_name='" + t_english_name + '\'' +
                ", t_image_link='" + t_image_link + '\'' +
                ", t_collect_count='" + t_collect_count + '\'' +
                ", t_hot_value='" + t_hot_value + '\'' +
                ", t_high_value='" + t_high_value + '\'' +
                ", t_distance='" + t_distance + '\'' +
                ", t_longitude='" + t_longitude + '\'' +
                ", t_latitude='" + t_latitude + '\'' +
                ", is_week='" + is_week + '\'' +
                ", is_month='" + is_month + '\'' +
                ", u_distance='" + u_distance + '\'' +
                ", s_id='" + s_id + '\'' +
                ", is_vip='" + is_vip + '\'' +
                '}';
    }

    public String getT_sex() {
        return t_sex;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public String getS_id() {
        return s_id;
    }

    public String getU_distance() {
        return u_distance;
    }

    public String getIs_week() {
        return is_week;
    }

    public String getIs_month() {
        return is_month;
    }

    public String getId() {
        return id;
    }

    public String getT_name() {
        return t_name;
    }

    public String getT_username() {
        return t_username;
    }

    public String getT_english_name() {
        return t_english_name;
    }

    public String getT_image_link() {
        return t_image_link;
    }

    public String getT_collect_count() {
        return t_collect_count;
    }

    public String getT_hot_value() {
        return t_hot_value;
    }

    public String getT_high_value() {
        return t_high_value;
    }

    public String getT_distance() {
        return t_distance;
    }

    public String getT_longitude() {
        return t_longitude;
    }

    public String getT_latitude() {
        return t_latitude;
    }
}
