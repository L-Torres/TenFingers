package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Address extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String user_id;
    private String username;
    private String mobile;
    private String addr_province_id;
    private String addr_city_id;
    private String addr_distinct_id;
    private String addr_detail;
    private String addr_content;
    private String is_default;
    private String created_at;
    private String updated_at;
    private String latitude;
    private String longitude;
    private String province;
    private String city;
    private String distinct;
    public Address(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                user_id = get(jsonObject, "user_id");
                username = get(jsonObject, "username");
                mobile = get(jsonObject, "mobile");
                is_default = get(jsonObject, "is_default");
                addr_province_id = get(jsonObject, "addr_province_id");
                addr_distinct_id = get(jsonObject, "addr_distinct_id");
                addr_city_id = get(jsonObject, "addr_city_id");
                addr_detail = get(jsonObject, "addr_detail");
                addr_content = get(jsonObject, "addr_content");
                created_at = get(jsonObject, "created_at");
                updated_at=get(jsonObject,"updated_at");
                latitude=get(jsonObject,"latitude");
                longitude=get(jsonObject,"longitude");
                province=get(jsonObject,"province");
                city=get(jsonObject,"city");
                distinct=get(jsonObject,"distinct");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addr_province_id='" + addr_province_id + '\'' +
                ", addr_city_id='" + addr_city_id + '\'' +
                ", addr_distinct_id='" + addr_distinct_id + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", addr_content='" + addr_content + '\'' +
                ", is_default='" + is_default + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", distinct='" + distinct + '\'' +
                '}';
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistinct() {
        return distinct;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddr_province_id() {
        return addr_province_id;
    }

    public String getAddr_city_id() {
        return addr_city_id;
    }

    public String getAddr_distinct_id() {
        return addr_distinct_id;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public String getAddr_content() {
        return addr_content;
    }

    public String getIs_default() {
        return is_default;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
