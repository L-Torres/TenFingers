package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 通用的image类
 */
public class ImageTea extends XtomObject implements Serializable {
    private static final long serialVersionUID = -7795537779923986245L;
    private String id;// 图片主键id
    private String img_link;//

    public ImageTea(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                img_link = get(jsonObject, "img_link");
                // log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getImg_link() {
        return img_link;
    }

    @Override
    public String toString() {
        return "ImageTea{" +
                "id='" + id + '\'' +
                ", img_link='" + img_link + '\'' +
                '}';
    }
}
