package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class CountList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String total;
    private String finish;
    private String p_name;
    public CountList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                total=get(jsonObject,"total");
                finish=get(jsonObject,"finish");
                p_name=get(jsonObject,"p_name");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "CountList{" +
                "total='" + total + '\'' +
                ", finish='" + finish + '\'' +
                ", p_name='" + p_name + '\'' +
                '}';
    }

    public String getP_name() {
        return p_name;
    }

    public String getTotal() {
        return total;
    }

    public String getFinish() {
        return finish;
    }
}
