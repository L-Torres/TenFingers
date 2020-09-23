package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 订单
 */
public class Order extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String o_status;
    private String p_name;
    private String o_price;
    private String p_desc;
    private String o_count;
    private String id;
    private String p_service_time;
    private String status_text;
    private String shop_name;
    private String o_p_name;
    private String o_p_desc;
    private String p_image_link;
    private String t_mobile;
    private String t_id;
    private String p_id;
    private String s_id;
    private String o_code_url;
    private String t_image_link;
    private String pay_type;
    private String o_refund_status;
    private String o_service_type;
    public Order(String o_status, String p_name, String o_price, String p_desc, String o_count, String id, String p_service_time, String status_text, String shop_name, String o_p_name, String o_p_desc, String p_image_link, String t_mobile, String t_id, String p_id, String s_id, String o_code_url, String t_image_link, String pay_type, String o_refund_status) {
        this.o_status = o_status;
        this.p_name = p_name;
        this.o_price = o_price;
        this.p_desc = p_desc;
        this.o_count = o_count;
        this.id = id;
        this.p_service_time = p_service_time;
        this.status_text = status_text;
        this.shop_name = shop_name;
        this.o_p_name = o_p_name;
        this.o_p_desc = o_p_desc;
        this.p_image_link = p_image_link;
        this.t_mobile = t_mobile;
        this.t_id = t_id;
        this.p_id = p_id;
        this.s_id = s_id;
        this.o_code_url = o_code_url;
        this.t_image_link = t_image_link;
        this.pay_type = pay_type;
        this.o_refund_status = o_refund_status;
    }

    public Order(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                o_status = get(jsonObject, "o_status");
                p_name = get(jsonObject, "p_name");
                o_price = get(jsonObject, "o_price");
                p_desc = get(jsonObject, "p_desc");
                o_count = get(jsonObject, "o_count");
                id = get(jsonObject, "id");
                p_service_time = get(jsonObject, "p_service_time");
                status_text = get(jsonObject, "status_text");
                shop_name = get(jsonObject, "shop_name");
                o_p_name = get(jsonObject, "o_p_name");
                o_p_desc = get(jsonObject, "o_p_desc");
                p_image_link = get(jsonObject, "p_image_link");
                t_mobile= get(jsonObject, "t_mobile");
                t_id= get(jsonObject, "t_id");
                p_id= get(jsonObject, "p_id");
                o_code_url= get(jsonObject, "o_code_url");
                t_image_link= get(jsonObject, "t_image_link");
                pay_type=get(jsonObject,"o_pay_type");
                s_id=get(jsonObject,"s_id");
                o_refund_status=get(jsonObject,"o_refund_status");
                o_service_type=get(jsonObject,"o_service_type");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "o_status='" + o_status + '\'' +
                ", p_name='" + p_name + '\'' +
                ", o_price='" + o_price + '\'' +
                ", p_desc='" + p_desc + '\'' +
                ", o_count='" + o_count + '\'' +
                ", id='" + id + '\'' +
                ", p_service_time='" + p_service_time + '\'' +
                ", status_text='" + status_text + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", o_p_name='" + o_p_name + '\'' +
                ", o_p_desc='" + o_p_desc + '\'' +
                ", p_image_link='" + p_image_link + '\'' +
                ", t_mobile='" + t_mobile + '\'' +
                ", t_id='" + t_id + '\'' +
                ", p_id='" + p_id + '\'' +
                ", s_id='" + s_id + '\'' +
                ", o_code_url='" + o_code_url + '\'' +
                ", t_image_link='" + t_image_link + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", o_refund_status='" + o_refund_status + '\'' +
                ", o_service_type='" + o_service_type + '\'' +
                '}';
    }

    public String getO_service_type() {

        return o_service_type;
    }

    public String getO_refund_status() {
        return o_refund_status;
    }
    public String getS_id() {
        return s_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getT_image_link() {
        return t_image_link;
    }

    public String getT_mobile() {
        return t_mobile;
    }

    public String getT_id() {
        return t_id;
    }

    public String getP_id() {
        return p_id;
    }

    public String getO_code_url() {
        return o_code_url;
    }

    public String getO_status() {
        return o_status;
    }

    public String getP_name() {
        return p_name;
    }

    public String getO_price() {
        return o_price;
    }

    public String getP_desc() {
        return p_desc;
    }

    public String getO_count() {
        return o_count;
    }

    public String getId() {
        return id;
    }

    public String getP_service_time() {
        return p_service_time;
    }

    public String getStatus_text() {
        return status_text;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getO_p_name() {
        return o_p_name;
    }

    public String getO_p_desc() {
        return o_p_desc;
    }

    public String getP_image_link() {
        return p_image_link;
    }
}
