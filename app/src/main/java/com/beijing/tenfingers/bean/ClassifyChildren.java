package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 分类子集
 */
public class ClassifyChildren extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String group_id;
    private String name;

    public ClassifyChildren(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                group_id = get(jsonObject, "group_id");
                name = get(jsonObject, "name");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public ClassifyChildren(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getName() {
        return name;
    }
}
