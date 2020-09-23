package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Exercise  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String created_at;
    private String image_link;
    private String name;
    private String status;
    private String desc;
    private String participate;
    private String type;
    public Exercise(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                created_at = get(jsonObject, "created_at");
                image_link = get(jsonObject, "image_link");
                id = get(jsonObject, "id");
                name = get(jsonObject, "name");
                status= get(jsonObject, "status");
                desc= get(jsonObject, "desc");
                participate=get(jsonObject,"participate");
                type=get(jsonObject,"type");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id='" + id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", image_link='" + image_link + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                ", participate='" + participate + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public String getParticipate() {
        return participate;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getName() {
        return name;
    }
}
