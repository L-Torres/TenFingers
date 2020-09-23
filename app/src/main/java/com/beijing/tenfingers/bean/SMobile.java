package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class SMobile extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String mobile;

    public SMobile(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                mobile = get(jsonObject, "mobile");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getMobile() {
        return mobile;
    }
}
