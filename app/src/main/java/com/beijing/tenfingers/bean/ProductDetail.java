package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ProductDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String can;
    private String activity_price;
    private String p_name;
    private String p_desc;
    private String p_image_link;
    private String p_service_time;
    private String p_price;
    private String p_detail;
    private String id;
    private String s_id;
    private String is_vip;
    private String p_admits;
    private String shop_id;
    public ProductDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                shop_id=get(jsonObject,"shopId");
                if (!jsonObject.isNull("activity")
                        && !isNull(jsonObject.getString("activity"))) {
                    JSONObject info = jsonObject.getJSONObject("activity");

                    can = get(info, "can");
                    activity_price = get(info, "activity_price");
                }
                if (!jsonObject.isNull("detail")
                        && !isNull(jsonObject.getString("detail"))) {
                    JSONObject info = jsonObject.getJSONObject("detail");

                    p_name = get(info, "p_name");
                    p_desc = get(info, "p_desc");
                    p_image_link = get(info, "p_image_link");
                    p_service_time = get(info, "p_service_time");
                    p_price = get(info, "p_price");
                    p_detail = get(info, "p_detail");
                    id = get(info, "id");
                    s_id=get(info,"s_id");
                    is_vip=get(info,"is_vip");
                    p_admits=get(info,"p_admits");

                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public String getShop_id() {
        return shop_id;
    }

    public String getP_admits() {
        return p_admits;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public String getCan() {
        return can;
    }

    public String getActivity_price() {
        return activity_price;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_desc() {
        return p_desc;
    }

    public String getP_image_link() {
        return p_image_link;
    }

    public String getP_service_time() {
        return p_service_time;
    }

    public String getP_price() {
        return p_price;
    }

    public String getP_detail() {
        return p_detail;
    }

    public String getId() {
        return id;
    }

    public String getS_id() {
        return s_id;
    }
}
