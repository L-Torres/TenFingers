package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Count extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String count;
    public Count(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count = get(jsonObject, "count");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Count{" +
                "count='" + count + '\'' +
                '}';
    }
}
