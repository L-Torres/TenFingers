package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Replies extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String content;
    private String created_at;
    public Replies(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                content = get(jsonObject, "content");
                created_at = get(jsonObject, "created_at");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }
}
