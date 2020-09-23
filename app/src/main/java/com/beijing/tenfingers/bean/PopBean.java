package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 活动-弹出窗口
 */
public class PopBean extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String title;
    private String key_type;
    private String key_id;
    private String url;
    private String created_at;
    private String updated_at;
    private String flag="0";
    public PopBean(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                title = get(jsonObject, "title");
                key_type = get(jsonObject, "key_type");
                key_id = get(jsonObject, "key_id");
                url = get(jsonObject, "url");
                created_at = get(jsonObject, "created_at");
                updated_at = get(jsonObject, "updated_at");
                flag="0";
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public PopBean(String id, String title, String key_type, String key_id, String url, String created_at, String updated_at, String flag) {
        this.id = id;
        this.title = title;
        this.key_type = key_type;
        this.key_id = key_id;
        this.url = url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PopBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", key_type='" + key_type + '\'' +
                ", key_id='" + key_id + '\'' +
                ", url='" + url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getKey_type() {
        return key_type;
    }

    public String getKey_id() {
        return key_id;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
