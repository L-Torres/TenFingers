package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ImageAd  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String tea_id;
    private String image_url;
    private String id;
    private String reserve_id;
    private String big_image_url;
    private String status;
    private String created_at;
    private String updated_at;
    public ImageAd(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                tea_id = get(jsonObject, "tea_id");
                image_url = get(jsonObject, "image_url");
                id= get(jsonObject, "id");
                reserve_id= get(jsonObject, "reserve_id");
                big_image_url= get(jsonObject, "big_image_url");
                status= get(jsonObject, "status");
                created_at= get(jsonObject, "created_at");
                updated_at= get(jsonObject, "updated_at");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "ImageAd{" +
                "tea_id='" + tea_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", id='" + id + '\'' +
                ", reserve_id='" + reserve_id + '\'' +
                ", big_image_url='" + big_image_url + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getReserve_id() {
        return reserve_id;
    }

    public String getBig_image_url() {
        return big_image_url;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public ImageAd(String tea_id, String image_url) {
        this.tea_id = tea_id;
        this.image_url = image_url;
    }

    public String getTea_id() {
        return tea_id;
    }

    public String getImage_url() {
        return image_url;
    }
}
