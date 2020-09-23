package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 所爱-优惠券列表
 */
public class CouponList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String c_end;
    private String c_shop_name;
    private String c_condition;
    private String c_start;
    private String c_type;
    private String c_price;
    private String c_name;
    private String status;
    private String id;
    private boolean checked = false;
    public CouponList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                c_end = get(jsonObject, "c_end");
                c_shop_name = get(jsonObject, "c_shop_name");
                c_condition = get(jsonObject, "c_condition");
                c_start = get(jsonObject, "c_start");
                c_type = get(jsonObject, "c_type");
                c_price = get(jsonObject, "c_price");
                c_name = get(jsonObject, "c_name");
                status = get(jsonObject, "status");
                id = get(jsonObject, "id");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CouponList{" +
                "c_end='" + c_end + '\'' +
                ", c_shop_name='" + c_shop_name + '\'' +
                ", c_condition='" + c_condition + '\'' +
                ", c_start='" + c_start + '\'' +
                ", c_type='" + c_type + '\'' +
                ", c_price='" + c_price + '\'' +
                ", c_name='" + c_name + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getC_end() {
        return c_end;
    }

    public String getC_shop_name() {
        return c_shop_name;
    }

    public String getC_condition() {
        return c_condition;
    }

    public String getC_start() {
        return c_start;
    }

    public String getC_type() {
        return c_type;
    }

    public String getC_price() {
        return c_price;
    }

    public String getC_name() {
        return c_name;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
