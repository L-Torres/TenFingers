package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 首页所爱
 */
public class FirstHobby extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String tea_title;
    private String tea_desc;
    private String tea_format;
    private String tea_score;
    private String tea_total;
    private String tea_count;
    private String tea_price;
    private String tea_img_link;
    private String tea_period;
    private String tea_date;
    private String tea_play_count;
    private String tea_vidio_link;
    private String tea_type_id;
    private String id;
    private String type_name;
    private String type_id;
    private String tea_sell_count;
    private String tea_vidio_time;
    private String can;
    private String activity_price;
    private String tea_alltype;

    public FirstHobby(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                tea_title = get(jsonObject, "tea_title");
                tea_desc = get(jsonObject, "tea_desc");
                tea_format = get(jsonObject, "tea_format");
                tea_score = get(jsonObject, "tea_score");
                tea_total = get(jsonObject, "tea_total");
                tea_count = get(jsonObject, "tea_count");
                tea_price = get(jsonObject, "tea_price");
                tea_img_link = get(jsonObject, "tea_img_link");
                tea_period = get(jsonObject, "tea_period");
                tea_date = get(jsonObject, "tea_date");
                tea_play_count = get(jsonObject, "tea_play_count");
                tea_vidio_link = get(jsonObject, "tea_vidio_link");
                tea_type_id = get(jsonObject, "tea_type_id");
                id = get(jsonObject, "id");
                tea_sell_count= get(jsonObject, "tea_sell_count");
                tea_vidio_time = get(jsonObject, "tea_vidio_time");
                can = get(jsonObject, "can");
                activity_price = get(jsonObject, "activity_price");
                tea_alltype = get(jsonObject, "tea_alltype");

                if (!jsonObject.isNull("type")
                        && !isNull(jsonObject.getString("type"))) {
                    JSONObject object = jsonObject.getJSONObject("type");
                    type_id=get(object,"id");
                    type_name=get(object,"name");
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getTea_sell_count() {
        return tea_sell_count;
    }

    public String getTea_vidio_time() {
        return tea_vidio_time;
    }

    public String getTea_alltype() {
        return tea_alltype;
    }

    public String getCan() {
        return can;
    }

    public String getActivity_price() {
        return activity_price;
    }

    public String getTea_title() {
        return tea_title;
    }

    public String getTea_desc() {
        return tea_desc;
    }

    public String getTea_format() {
        return tea_format;
    }

    public String getTea_score() {
        return tea_score;
    }

    public String getTea_total() {
        return tea_total;
    }

    public String getTea_count() {
        return tea_count;
    }

    public String getTea_price() {
        return tea_price;
    }

    public String getTea_img_link() {
        return tea_img_link;
    }

    public String getTea_period() {
        return tea_period;
    }

    public String getTea_date() {
        return tea_date;
    }

    public String getTea_play_count() {
        return tea_play_count;
    }

    public String getTea_vidio_link() {
        return tea_vidio_link;
    }

    public String getTea_type_id() {
        return tea_type_id;
    }

    public String getId() {
        return id;
    }

    public String getType_name() {
        return type_name;
    }

    public String getType_id() {
        return type_id;
    }
}
