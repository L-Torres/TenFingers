package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ClientGet  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String u_sex;
    private String u_image_link;
    private String u_birthday;
    private String u_nickname;
    public ClientGet(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                u_sex = get(jsonObject, "u_sex");
                u_image_link = get(jsonObject, "u_image_link");
                u_birthday = get(jsonObject, "u_birthday");
                u_nickname = get(jsonObject, "u_nickname");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getU_sex() {
        return u_sex;
    }

    public String getU_image_link() {
        return u_image_link;
    }

    public String getU_birthday() {
        return u_birthday;
    }

    public String getU_nickname() {
        return u_nickname;
    }
}
