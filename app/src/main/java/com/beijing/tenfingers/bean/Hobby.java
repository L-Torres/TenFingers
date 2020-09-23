package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 技师收藏
 */
public class Hobby extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String t_longitude;
    private String t_latitude;
    private String t_distance;
    private String id;
    private String t_name;
    private String t_english_name;
    private String t_image_link;
    private String t_collect_count;
    private String t_hot_value;
    private String t_high_value;
    private String is_vip;
    public Hobby(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                t_longitude= get(jsonObject, "t_longitude");
                t_latitude= get(jsonObject, "t_latitude");
                t_distance= get(jsonObject, "t_distance");
                id= get(jsonObject, "id");
                t_name= get(jsonObject, "t_name");
                t_english_name= get(jsonObject, "t_english_name");
                t_image_link= get(jsonObject, "t_image_link");
                t_collect_count= get(jsonObject, "t_collect_count");
                t_hot_value= get(jsonObject, "t_hot_value");
                t_high_value= get(jsonObject, "t_high_value");
                is_vip=get(jsonObject,"is_vip");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "t_longitude='" + t_longitude + '\'' +
                ", t_latitude='" + t_latitude + '\'' +
                ", t_distance='" + t_distance + '\'' +
                ", id='" + id + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_english_name='" + t_english_name + '\'' +
                ", t_image_link='" + t_image_link + '\'' +
                ", t_collect_count='" + t_collect_count + '\'' +
                ", t_hot_value='" + t_hot_value + '\'' +
                ", t_high_value='" + t_high_value + '\'' +
                ", is_vip='" + is_vip + '\'' +
                '}';
    }

    public String getIs_vip() {
        return is_vip;
    }

    public String getT_longitude() {
        return t_longitude;
    }

    public String getT_latitude() {
        return t_latitude;
    }

    public String getT_distance() {
        return t_distance;
    }

    public String getId() {
        return id;
    }

    public String getT_name() {
        return t_name;
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
}
