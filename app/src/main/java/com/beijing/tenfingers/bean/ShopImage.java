package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ShopImage extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String s_id;
    private String s_image_link;


    public ShopImage(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                s_id = get(jsonObject, "s_id");
                s_image_link = get(jsonObject, "s_image_link");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "ShopImage{" +
                "id='" + id + '\'' +
                ", s_id='" + s_id + '\'' +
                ", s_image_link='" + s_image_link + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getS_id() {
        return s_id;
    }

    public String getS_image_link() {
        return s_image_link;
    }
}
