package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class VersionModle extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String type;
    private String v_name;
    private String v_version;
    private String v_desc;
    private String v_current;
    private String v_link;
    private String created_at;
    private String updated_at;
    public VersionModle(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                type = get(jsonObject, "type");
                v_name=get(jsonObject,"v_name");
                v_version=get(jsonObject,"v_version");
                v_desc=get(jsonObject,"v_desc");
                v_current=get(jsonObject,"v_current");
                v_link=get(jsonObject,"v_link");
                created_at=get(jsonObject,"created_at");
                updated_at=get(jsonObject,"updated_at");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "VersionModle{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", v_name='" + v_name + '\'' +
                ", v_version='" + v_version + '\'' +
                ", v_desc='" + v_desc + '\'' +
                ", v_current='" + v_current + '\'' +
                ", v_link='" + v_link + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getV_name() {
        return v_name;
    }

    public String getV_version() {
        return v_version;
    }

    public String getV_desc() {
        return v_desc;
    }

    public String getV_current() {
        return v_current;
    }

    public String getV_link() {
        return v_link;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
