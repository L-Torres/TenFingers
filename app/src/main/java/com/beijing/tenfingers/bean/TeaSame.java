package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 同款茶
 */
public class TeaSame extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String tea_title;
    private String tea_period;
    private String tea_count;
    private String tea_score;
    private String id;
    private String tea_total;
    private String tea_date;
    private String tea_desc;
    private String tea_format;
    private String tea_price;
    private String tea_vidio_link;
    private String tea_play_count;
    private String tea_img_link;
    private String tea_thumb_count;
    private String tea_collect_count;
    private String tea_comment_count;
    private String tea_type_id;

    private String tea_sell_count;
    public TeaSame(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                tea_title = get(jsonObject, "tea_title");
                tea_period = get(jsonObject, "tea_period");
                tea_count = get(jsonObject, "tea_count");
                tea_score = get(jsonObject, "tea_score");
                id = get(jsonObject, "id");
                tea_total = get(jsonObject, "tea_total");
                tea_date = get(jsonObject, "tea_date");
                tea_desc = get(jsonObject, "tea_desc");
                tea_format = get(jsonObject, "tea_format");
                tea_price = get(jsonObject, "tea_price");
                tea_vidio_link = get(jsonObject, "tea_vidio_link");
                tea_play_count = get(jsonObject, "tea_play_count");
                tea_img_link = get(jsonObject, "tea_img_link");
                tea_thumb_count = get(jsonObject, "tea_thumb_count");
                tea_collect_count = get(jsonObject, "tea_collect_count");
                tea_comment_count = get(jsonObject, "tea_comment_count");
                tea_type_id = get(jsonObject, "tea_type_id");
                tea_sell_count=get(jsonObject,"tea_sell_count");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getTea_title() {
        return tea_title;
    }

    public String getTea_period() {
        return tea_period;
    }

    public String getTea_count() {
        return tea_count;
    }

    public String getTea_score() {
        return tea_score;
    }

    public String getId() {
        return id;
    }

    public String getTea_total() {
        return tea_total;
    }

    public String getTea_date() {
        return tea_date;
    }

    public String getTea_desc() {
        return tea_desc;
    }

    public String getTea_format() {
        return tea_format;
    }

    public String getTea_price() {
        return tea_price;
    }

    public String getTea_vidio_link() {
        return tea_vidio_link;
    }

    public String getTea_play_count() {
        return tea_play_count;
    }

    public String getTea_img_link() {
        return tea_img_link;
    }

    public String getTea_thumb_count() {
        return tea_thumb_count;
    }

    public String getTea_collect_count() {
        return tea_collect_count;
    }

    public String getTea_comment_count() {
        return tea_comment_count;
    }

    public String getTea_type_id() {
        return tea_type_id;
    }
}
