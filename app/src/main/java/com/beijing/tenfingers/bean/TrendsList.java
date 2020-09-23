package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class TrendsList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String img;
    private String url;
    private String content;
    private String type;
    private String status;
    private String createdTime;
    private String id;
    private String isThumb;
    public TrendsList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                img=get(jsonObject,"img");
                url=get(jsonObject,"url");
                content=get(jsonObject,"content");
                type=get(jsonObject,"type");
                status=get(jsonObject,"status");
                createdTime=get(jsonObject,"createTime");
                id=get(jsonObject,"id");
                isThumb=get(jsonObject,"isLike");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "TrendsList{" +
                "img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", id='" + id + '\'' +
                ", isThumb='" + isThumb + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getId() {
        return id;
    }

    public String getIsThumb() {
        return isThumb;
    }

    public void setIsThumb(String isThumb) {
        this.isThumb = isThumb;
    }
}
