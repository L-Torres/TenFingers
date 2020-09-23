package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class MyVip extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String is_next;
    private String current_total;
    private String next_level;
    private String u_image_link;
    private String current_level;
    private String next_total;
    private String id;
    private String username;
    private String priceLevelText;
    public MyVip(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                is_next = get(jsonObject, "is_next");
                current_total = get(jsonObject, "current_total");
                next_level = get(jsonObject, "next_level");
                u_image_link = get(jsonObject, "u_image_link");
                current_level=get(jsonObject,"current_level");
                id=get(jsonObject,"id");
                next_total=get(jsonObject,"next_total");
                username=get(jsonObject,"username");
                priceLevelText=get(jsonObject,"priceLevelText");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    @Override
    public String toString() {
        return "MyVip{" +
                "is_next='" + is_next + '\'' +
                ", current_total='" + current_total + '\'' +
                ", next_level='" + next_level + '\'' +
                ", u_image_link='" + u_image_link + '\'' +
                ", current_level='" + current_level + '\'' +
                ", next_total='" + next_total + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ",  ='" + priceLevelText + '\'' +
                '}';
    }

    public String getPriceLevelText() {
        return priceLevelText;
    }

    public String getIs_next() {
        return is_next;
    }

    public String getCurrent_total() {
        return current_total;
    }

    public String getNext_level() {
        return next_level;
    }

    public String getU_image_link() {
        return u_image_link;
    }

    public String getCurrent_level() {
        return current_level;
    }

    public String getNext_total() {
        return next_total;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
