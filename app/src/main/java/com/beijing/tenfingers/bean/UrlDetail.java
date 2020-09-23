package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 商品详情-详情
 */
public class UrlDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String detail;
    public UrlDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                detail=get(jsonObject,"detail");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDetail() {
        return detail;
    }
}
