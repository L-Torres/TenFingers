package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class MediaList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String image;
    private String url;
    private String trendsId;
    private String id;
    public MediaList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                image=get(jsonObject,"image");
                url=get(jsonObject,"url");
                trendsId=get(jsonObject,"trendsId");
                id=get(jsonObject,"id");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getTrendsId() {
        return trendsId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MediaList{" +
                "image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", trendsId='" + trendsId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
