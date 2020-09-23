package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 图片通用类
 */
public class ImageList  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String image_link;
    private String comment_id;

    public ImageList(String image_link) {
        this.image_link = image_link;
    }

    public ImageList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                image_link = get(jsonObject, "image_link");
                comment_id = get(jsonObject, "comment_id");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getImage_link() {
        return image_link;
    }

    public String getComment_id() {
        return comment_id;
    }
}
