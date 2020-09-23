package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;
public class ProductList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String p_name;
    private String created_at;
    private String finish_count;
    private String p_image_link;
    private String p_desc;
    private String p_price;
    private String p_detail;
    private String p_service_time;
    public ProductList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                p_name = get(jsonObject, "p_name");
                created_at = get(jsonObject, "created_at");
                finish_count = get(jsonObject, "finish_count");
                p_image_link = get(jsonObject, "p_image_link");
                p_desc = get(jsonObject, "p_desc");
                p_price = get(jsonObject, "p_price");
                p_detail = get(jsonObject, "p_detail");
                p_service_time = get(jsonObject, "p_service_time");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "id='" + id + '\'' +
                ", p_name='" + p_name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", finish_count='" + finish_count + '\'' +
                ", p_image_link='" + p_image_link + '\'' +
                ", p_desc='" + p_desc + '\'' +
                ", p_price='" + p_price + '\'' +
                ", p_detail='" + p_detail + '\'' +
                ", p_service_time='" + p_service_time + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getP_name() {
        return p_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFinish_count() {
        return finish_count;
    }

    public String getP_image_link() {
        return p_image_link;
    }

    public String getP_desc() {
        return p_desc;
    }

    public String getP_price() {
        return p_price;
    }

    public String getP_detail() {
        return p_detail;
    }

    public String getP_service_time() {
        return p_service_time;
    }
}
