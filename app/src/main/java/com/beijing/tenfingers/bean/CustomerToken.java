package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class CustomerToken extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String custom_token;

    public CustomerToken(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                custom_token=get(jsonObject,"custom_token");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "CustomerToken{" +
                "custom_token='" + custom_token + '\'' +
                '}';
    }

    public String getCustom_token() {
        return custom_token;
    }
}
