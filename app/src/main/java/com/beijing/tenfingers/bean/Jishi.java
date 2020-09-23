package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Jishi  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String t_collect_count;
    private String t_high_value;
    private String is_month;
    private String t_image_link;
    private String is_week;
    private String t_name;
    private String t_english_name;
    private String u_distance;
    private String t_hot_value;
    private String id;
    private String t_distance;
    public Jishi(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {

                    t_collect_count = get(jsonObject, "t_collect_count");
                    t_high_value = get(jsonObject, "t_high_value");
                    is_month = get(jsonObject, "is_month");
                    t_image_link = get(jsonObject, "t_image_link");
                    is_week = get(jsonObject, "is_week");
                    t_name = get(jsonObject, "t_name");
                    t_english_name = get(jsonObject, "t_english_name");
                    u_distance = get(jsonObject, "u_distance");
                    t_hot_value = get(jsonObject, "t_hot_value");
                    id = get(jsonObject, "id");
                    t_distance = get(jsonObject, "t_distance");

                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getT_collect_count() {
        return t_collect_count;
    }

    public String getT_high_value() {
        return t_high_value;
    }

    public String getIs_month() {
        return is_month;
    }

    public String getT_image_link() {
        return t_image_link;
    }

    public String getIs_week() {
        return is_week;
    }

    public String getT_name() {
        return t_name;
    }

    public String getT_english_name() {
        return t_english_name;
    }

    public String getU_distance() {
        return u_distance;
    }

    public String getT_hot_value() {
        return t_hot_value;
    }

    public String getId() {
        return id;
    }

    public String getT_distance() {
        return t_distance;
    }

    @Override
    public String toString() {
        return "Jishi{" +
                "t_collect_count='" + t_collect_count + '\'' +
                ", t_high_value='" + t_high_value + '\'' +
                ", is_month='" + is_month + '\'' +
                ", t_image_link='" + t_image_link + '\'' +
                ", is_week='" + is_week + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_english_name='" + t_english_name + '\'' +
                ", u_distance='" + u_distance + '\'' +
                ", t_hot_value='" + t_hot_value + '\'' +
                ", id='" + id + '\'' +
                ", t_distance='" + t_distance + '\'' +
                '}';
    }
}
