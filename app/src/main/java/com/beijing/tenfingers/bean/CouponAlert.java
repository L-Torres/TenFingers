package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 弹框-优惠券
 */
public class CouponAlert extends XtomObject implements Serializable {
    private static final long serialVersionUID = 1265819772736012294L;
    private String can;
    private ArrayList<CouponList> children=new ArrayList<>();
    public CouponAlert(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                can = get(jsonObject, "can");
                if (!jsonObject.isNull("data")
                        && !isNull(jsonObject.getString("data"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("data");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new CouponList(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "CouponAlert{" +
                "can='" + can + '\'' +
                ", children=" + children +
                '}';
    }

    public String getCan() {
        return can;
    }

    public ArrayList<CouponList> getChildren() {
        return children;
    }
}
