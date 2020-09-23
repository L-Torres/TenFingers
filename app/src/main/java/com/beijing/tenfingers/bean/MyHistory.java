package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class MyHistory extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String date;
    private String p_name;
    private String p_desc;
    private String p_image_link;
    private String created_at;
    private String id;
    private String p_price;
    public MyHistory(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                date = get(jsonObject, "date");
                p_name = get(jsonObject, "p_name");
                p_desc = get(jsonObject, "p_desc");
                p_image_link = get(jsonObject, "p_image_link");
                created_at=get(jsonObject,"created_at");
                id=get(jsonObject,"id");
                p_price=get(jsonObject,"p_price");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "MyHistory{" +
                "date='" + date + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_desc='" + p_desc + '\'' +
                ", p_image_link='" + p_image_link + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", p_price='" + p_price + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_desc() {
        return p_desc;
    }

    public String getP_image_link() {
        return p_image_link;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }

    public String getP_price() {
        return p_price;
    }
}
