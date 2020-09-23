package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 结算页面数据
 */
public class OrderSettle extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String  address;
    private String p_name;
    private String mobile;
    private String p_desc;
    private String p_image_link;
    private String p_service_time;
    private String is_default;
    private String p_price;
    private String addr_id;
    private String username;
    private String coupon_id;
    private String c_price;
    private String c_condition;
    private String addr_city_id;
    private String addr_province_id;
    private String addr_distinct_id;
    private String addr_city;
    private String addr_province;
    private String addr_distinct;
    private String latitude;
    private String longitude;
    private String addr_detail;
    private String discount_price;
    private String is_discount;
    private String vip_discount_price;
    public OrderSettle(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                address=get(jsonObject,"address");
                p_name=get(jsonObject,"p_name");
                mobile=get(jsonObject,"mobile");
                p_desc=get(jsonObject,"p_desc");
                p_image_link=get(jsonObject,"p_image_link");
                p_service_time=get(jsonObject,"p_service_time");
                is_default=get(jsonObject,"is_default");
                p_price=get(jsonObject,"p_price");
                addr_id=get(jsonObject,"addr_id");
                username=get(jsonObject,"username");
                coupon_id=get(jsonObject,"coupon_id");
                c_price=get(jsonObject,"c_price");
                c_condition=get(jsonObject,"c_condition");
                addr_city_id=get(jsonObject,"addr_city_id");
                addr_province_id=get(jsonObject,"addr_province_id");
                addr_distinct_id=get(jsonObject,"addr_distinct_id");
                addr_city=get(jsonObject,"addr_city");
                addr_province=get(jsonObject,"addr_province");
                addr_distinct=get(jsonObject,"addr_distinct");
                latitude=get(jsonObject,"latitude");
                longitude=get(jsonObject,"longitude");
                addr_detail=get(jsonObject,"addr_detail");
                discount_price=get(jsonObject,"discount_price");
                is_discount=get(jsonObject,"is_discount");
                vip_discount_price=get(jsonObject,"vip_discount_price");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    @Override
    public String toString() {
        return "OrderSettle{" +
                "address='" + address + '\'' +
                ", p_name='" + p_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", p_desc='" + p_desc + '\'' +
                ", p_image_link='" + p_image_link + '\'' +
                ", p_service_time='" + p_service_time + '\'' +
                ", is_default='" + is_default + '\'' +
                ", p_price='" + p_price + '\'' +
                ", addr_id='" + addr_id + '\'' +
                ", username='" + username + '\'' +
                ", coupon_id='" + coupon_id + '\'' +
                ", c_price='" + c_price + '\'' +
                ", c_condition='" + c_condition + '\'' +
                ", addr_city_id='" + addr_city_id + '\'' +
                ", addr_province_id='" + addr_province_id + '\'' +
                ", addr_distinct_id='" + addr_distinct_id + '\'' +
                ", addr_city='" + addr_city + '\'' +
                ", addr_province='" + addr_province + '\'' +
                ", addr_distinct='" + addr_distinct + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", is_discount='" + is_discount + '\'' +
                ", vip_discount_price='" + vip_discount_price + '\'' +
                '}';
    }

    public String getVip_discount_price() {
        return vip_discount_price;
    }

    public String getIs_discount() {
        return is_discount;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getP_name() {
        return p_name;
    }

    public String getMobile() {
        return mobile;
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

    public String getIs_default() {
        return is_default;
    }

    public String getP_price() {
        return p_price;
    }

    public String getAddr_id() {
        return addr_id;
    }

    public String getUsername() {
        return username;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public String getC_price() {
        return c_price;
    }

    public String getC_condition() {
        return c_condition;
    }

    public String getAddr_city_id() {
        return addr_city_id;
    }

    public String getAddr_province_id() {
        return addr_province_id;
    }

    public String getAddr_distinct_id() {
        return addr_distinct_id;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public String getAddr_province() {
        return addr_province;
    }

    public String getAddr_distinct() {
        return addr_distinct;
    }
}
