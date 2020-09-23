package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ShopChild  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String s_major;
    private String s_name;
    private String s_address;
    private ArrayList<ShopTag> shopTags=new ArrayList<>();
    private ArrayList<ShopImage> imgs=new ArrayList<>();
    public ShopChild(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                s_major = get(jsonObject, "s_major");
                s_name = get(jsonObject, "s_name");
                s_address = get(jsonObject, "s_address");
                if (!jsonObject.isNull("images")
                        && !isNull(jsonObject.getString("images"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("images");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++){
                        imgs.add(new ShopImage(jsonList.getJSONObject(i)));
                    }
                }
                if (!jsonObject.isNull("tags")
                        && !isNull(jsonObject.getString("tags"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("tags");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        shopTags.add(new ShopTag(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public ArrayList<ShopTag> getShopTags() {
        return shopTags;
    }

    public ArrayList<ShopImage> getImgs() {
        return imgs;
    }

    public String getId() {
        return id;
    }

    public String getS_major() {
        return s_major;
    }

    public String getS_name() {
        return s_name;
    }

    public String getS_address() {
        return s_address;
    }

    @Override
    public String toString() {
        return "ShopChild{" +
                "id='" + id + '\'' +
                ", s_major='" + s_major + '\'' +
                ", s_name='" + s_name + '\'' +
                ", s_address='" + s_address + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
