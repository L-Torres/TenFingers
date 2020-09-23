package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class AboutUs extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String set_logo;
    private String set_busi_mobile;
    private String set_csc_mobile;
    private String set_weixin;
    private String set_email;
    private String set_weibo;
    private String set_website;
    public AboutUs(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                set_logo = get(jsonObject, "logo");
                set_busi_mobile = get(jsonObject, "busi_mobile");
                set_csc_mobile = get(jsonObject, "busi_mobile");
                set_weixin = get(jsonObject, "weixin");
                set_email = get(jsonObject, "email");
                set_weibo = get(jsonObject, "weibo");
                set_website = get(jsonObject, "website");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getSet_logo() {
        return set_logo;
    }

    public String getSet_busi_mobile() {
        return set_busi_mobile;
    }

    public String getSet_csc_mobile() {
        return set_csc_mobile;
    }

    public String getSet_weixin() {
        return set_weixin;
    }

    public String getSet_email() {
        return set_email;
    }

    public String getSet_weibo() {
        return set_weibo;
    }

    public String getSet_website() {
        return set_website;
    }
}
