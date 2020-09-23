package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Price extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String price;
    private boolean flag=false;

    public Price(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id= get(jsonObject, "id");
                price = get(jsonObject, "price");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Price{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", flag=" + flag +
                '}';
    }

    public String getId() {
        return id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getPrice() {
        return price;
    }
}
