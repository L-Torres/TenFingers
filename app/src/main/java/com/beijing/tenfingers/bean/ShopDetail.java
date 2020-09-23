package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ShopDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String s_name;
    private String s_major;
    private String s_image_link;
    private String s_addr_id;
    private String addr_name;
    private String status;
    private String s_hot_value;
    private String s_logo_link;
    private String s_is_business;
    private String s_telephone;
    private ArrayList<ShopImage> imgs=new ArrayList<>();
    private String finish_count;
    private String collect_count;
    private String shop_score;
    private String is_collect;
    public ShopDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                s_major = get(jsonObject, "s_major");
                s_name = get(jsonObject, "s_name");
                s_image_link = get(jsonObject, "s_image_link");
                s_addr_id= get(jsonObject, "s_addr_id");
                addr_name= get(jsonObject, "addr_name");
                status= get(jsonObject, "status");
                s_hot_value= get(jsonObject, "s_hot_value");
                s_logo_link= get(jsonObject, "s_logo_link");
                s_is_business= get(jsonObject, "s_is_business");
                s_telephone= get(jsonObject, "s_telephone");
                finish_count= get(jsonObject, "finish_count");
                collect_count= get(jsonObject, "collect_count");
                shop_score= get(jsonObject, "shop_score");
                is_collect= get(jsonObject, "is_collect");
                if (!jsonObject.isNull("images")
                        && !isNull(jsonObject.getString("images"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("images");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++){
                        imgs.add(new ShopImage(jsonList.getJSONObject(i)));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getFinish_count() {
        return finish_count;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public String getShop_score() {
        return shop_score;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public String getId() {
        return id;
    }

    public String getS_name() {
        return s_name;
    }

    public String getS_major() {
        return s_major;
    }

    public String getS_image_link() {
        return s_image_link;
    }

    public String getS_addr_id() {
        return s_addr_id;
    }

    public String getAddr_name() {
        return addr_name;
    }

    public String getStatus() {
        return status;
    }

    public String getS_hot_value() {
        return s_hot_value;
    }

    public String getS_logo_link() {
        return s_logo_link;
    }

    public String getS_is_business() {
        return s_is_business;
    }

    public String getS_telephone() {
        return s_telephone;
    }

    public ArrayList<ShopImage> getImgs() {
        return imgs;
    }
}
