package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class GetCode extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String sign;
    public GetCode(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                sign = get(jsonObject, "sign");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
