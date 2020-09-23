package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 订单详情
 */
public class OrderDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String o_status;
    private String o_no;
    private String address;
    private String o_refund_status;
    private String mobile;
    private String p_desc;
    private String o_pay_type;
    private String p_price;
    private String shop_name;
    private String o_real_total;
    private String o_remark;
    private String tech_mobile;
    private String o_total;
    private String o_service_type;
    private String o_discount;
    private String o_trip_type;
    private String p_name;
    private String o_advance_time;
    private String p_image_link;
    private String p_service_time;
    private String username;
    private String o_p_name;
    private String t_image_link;
    private String o_p_desc;
    private String o_count;
    private String t_id;
    private String p_id;
    private String o_refund_reason;
    private String t_latitude;
    private String t_longitude;
    private String id;
    private String o_code_url;
    private String t_name;
    private String addr_detail;
    private String s_telephone;
    private String ori_price;
    private String is_activity;
    private String coupon_fee;
    private String vip_discount_price;
    private String o_car_fee;
    private String o_red_fee;
    private String o_refund_price;
    public OrderDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                o_red_fee=get(jsonObject,"o_red_fee");
                o_car_fee=get(jsonObject,"o_car_fee");
                vip_discount_price=get(jsonObject,"vip_discount_price");
                coupon_fee=get(jsonObject,"coupon_fee");
                o_status = get(jsonObject, "o_status");
                o_no = get(jsonObject, "o_no");
                address = get(jsonObject, "address");
                o_refund_status = get(jsonObject, "o_refund_status");
                mobile = get(jsonObject, "mobile");
                p_desc = get(jsonObject, "p_desc");
                o_pay_type = get(jsonObject, "o_pay_type");
                p_price = get(jsonObject, "p_price");
                shop_name = get(jsonObject, "shop_name");
                o_real_total = get(jsonObject, "o_real_total");
                o_remark = get(jsonObject, "o_remark");
                tech_mobile = get(jsonObject, "t_mobile");
                o_total = get(jsonObject, "o_total");
                o_service_type = get(jsonObject, "o_service_type");
                o_discount = get(jsonObject, "o_discount");
                o_trip_type = get(jsonObject, "o_trip_type");
                p_name = get(jsonObject, "p_name");
                o_advance_time = get(jsonObject, "o_advance_time");
                p_image_link = get(jsonObject, "p_image_link");
                p_service_time = get(jsonObject, "p_service_time");
                username = get(jsonObject, "username");
                o_p_name = get(jsonObject, "o_p_name");
                o_p_desc = get(jsonObject, "o_p_desc");
                o_count = get(jsonObject, "o_count");
                t_id= get(jsonObject, "t_id");
                p_id= get(jsonObject, "p_id");
                id=get(jsonObject,"id");
                o_refund_reason= get(jsonObject, "o_refund_reason");
                t_latitude= get(jsonObject, "t_latitude");
                t_longitude= get(jsonObject, "t_longitude");
                t_image_link=get(jsonObject,"t_image_link");
                o_code_url=get(jsonObject,"o_code_url");
                t_name=get(jsonObject,"t_name");
                addr_detail=get(jsonObject,"addr_detail");
                s_telephone=get(jsonObject,"s_telephone");
                ori_price=get(jsonObject,"ori_price");
                is_activity=get(jsonObject,"is_activity");
                o_refund_price=get(jsonObject,"o_refund_price");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "o_status='" + o_status + '\'' +
                ", o_no='" + o_no + '\'' +
                ", address='" + address + '\'' +
                ", o_refund_status='" + o_refund_status + '\'' +
                ", mobile='" + mobile + '\'' +
                ", p_desc='" + p_desc + '\'' +
                ", o_pay_type='" + o_pay_type + '\'' +
                ", p_price='" + p_price + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", o_real_total='" + o_real_total + '\'' +
                ", o_remark='" + o_remark + '\'' +
                ", tech_mobile='" + tech_mobile + '\'' +
                ", o_total='" + o_total + '\'' +
                ", o_service_type='" + o_service_type + '\'' +
                ", o_discount='" + o_discount + '\'' +
                ", o_trip_type='" + o_trip_type + '\'' +
                ", p_name='" + p_name + '\'' +
                ", o_advance_time='" + o_advance_time + '\'' +
                ", p_image_link='" + p_image_link + '\'' +
                ", p_service_time='" + p_service_time + '\'' +
                ", username='" + username + '\'' +
                ", o_p_name='" + o_p_name + '\'' +
                ", t_image_link='" + t_image_link + '\'' +
                ", o_p_desc='" + o_p_desc + '\'' +
                ", o_count='" + o_count + '\'' +
                ", t_id='" + t_id + '\'' +
                ", p_id='" + p_id + '\'' +
                ", o_refund_reason='" + o_refund_reason + '\'' +
                ", t_latitude='" + t_latitude + '\'' +
                ", t_longitude='" + t_longitude + '\'' +
                ", id='" + id + '\'' +
                ", o_code_url='" + o_code_url + '\'' +
                ", t_name='" + t_name + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", s_telephone='" + s_telephone + '\'' +
                ", ori_price='" + ori_price + '\'' +
                ", is_activity='" + is_activity + '\'' +
                ", coupon_fee='" + coupon_fee + '\'' +
                ", vip_discount_price='" + vip_discount_price + '\'' +
                ", o_car_fee='" + o_car_fee + '\'' +
                ", o_red_fee='" + o_red_fee + '\'' +
                ", o_refund_price='" + o_refund_price + '\'' +
                '}';
    }

    public String getO_refund_price() {
        return o_refund_price;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public String getVip_discount_price() {
        return vip_discount_price;
    }

    public String getO_car_fee() {
        return o_car_fee;
    }

    public String getO_red_fee() {
        return o_red_fee;
    }

    public String getOri_price() {
        return ori_price;
    }

    public String getIs_activity() {
        return is_activity;
    }

    public String getS_telephone() {
        return s_telephone;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public String getT_name() {
        return t_name;
    }

    public String getO_code_url() {
        return o_code_url;
    }

    public String getId() {
        return id;
    }

    public String getT_image_link() {
        return t_image_link;
    }

    public String getT_latitude() {
        return t_latitude;
    }

    public String getT_longitude() {
        return t_longitude;
    }

    public String getT_id() {
        return t_id;
    }

    public String getP_id() {
        return p_id;
    }

    public String getO_refund_reason() {
        return o_refund_reason;
    }

    public String getO_status() {
        return o_status;
    }

    public String getO_no() {
        return o_no;
    }

    public String getAddress() {
        return address;
    }

    public String getO_refund_status() {
        return o_refund_status;
    }

    public String getMobile() {
        return mobile;
    }

    public String getP_desc() {
        return p_desc;
    }

    public String getO_pay_type() {
        return o_pay_type;
    }

    public String getP_price() {
        return p_price;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getO_real_total() {
        return o_real_total;
    }

    public String getO_remark() {
        return o_remark;
    }

    public String getTech_mobile() {
        return tech_mobile;
    }

    public String getO_total() {
        return o_total;
    }

    public String getO_service_type() {
        return o_service_type;
    }

    public String getO_discount() {
        return o_discount;
    }

    public String getO_trip_type() {
        return o_trip_type;
    }

    public String getP_name() {
        return p_name;
    }

    public String getO_advance_time() {
        return o_advance_time;
    }

    public String getP_image_link() {
        return p_image_link;
    }

    public String getP_service_time() {
        return p_service_time;
    }

    public String getUsername() {
        return username;
    }

    public String getO_p_name() {
        return o_p_name;
    }

    public String getO_p_desc() {
        return o_p_desc;
    }

    public String getO_count() {
        return o_count;
    }
}
