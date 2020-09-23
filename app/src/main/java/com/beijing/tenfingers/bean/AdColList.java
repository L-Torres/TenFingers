package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

//预购收藏列表
public class AdColList  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String r_tea_title;
    private String r_discount;
    private String r_collect_count;
    private String r_deposit;
    private String r_img_link;
    private String rid;

    public AdColList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                r_tea_title = get(jsonObject, "r_tea_title");
                r_discount = get(jsonObject, "r_discount");
                r_collect_count = get(jsonObject, "r_collect_count");
                r_deposit = get(jsonObject, "r_deposit");
                r_img_link = get(jsonObject, "r_img_link");
                rid = get(jsonObject, "rid");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "AdColList{" +
                "r_tea_title='" + r_tea_title + '\'' +
                ", r_discount='" + r_discount + '\'' +
                ", r_collect_count='" + r_collect_count + '\'' +
                ", r_deposit='" + r_deposit + '\'' +
                ", r_img_link='" + r_img_link + '\'' +
                ", rid='" + rid + '\'' +
                '}';
    }

    public String getR_tea_title() {
        return r_tea_title;
    }

    public String getR_discount() {
        return r_discount;
    }

    public String getR_collect_count() {
        return r_collect_count;
    }

    public String getR_deposit() {
        return r_deposit;
    }

    public String getR_img_link() {
        return r_img_link;
    }

    public String getRid() {
        return rid;
    }
}
