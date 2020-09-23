package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 评茶师
 */
public class Valuator extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String realname;
    private String image_link;
    private String name;

    public Valuator(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                realname = get(jsonObject, "realname");
                image_link = get(jsonObject, "image_link");
                name=get(jsonObject,"name");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Valuator{" +
                "id='" + id + '\'' +
                ", realname='" + realname + '\'' +
                ", image_link='" + image_link + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRealname() {
        return realname;
    }

    public String getImage_link() {
        return image_link;
    }
}
