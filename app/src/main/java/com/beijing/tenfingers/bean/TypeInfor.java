package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 产区分类 实体类
 * Created by Torres on 2018/7/13.
 */
public class TypeInfor extends XtomObject {

    private String id;
    private String name;
    private String imgurl;
    private String imgurlbig;
    private ArrayList<TypeInfor> second;

    private boolean isCheck = false;

    public TypeInfor(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                name = get(jsonObject, "name");
                imgurl = get(jsonObject, "imgurl");
                imgurlbig = get(jsonObject, "imgurlbig");

                if (!jsonObject.isNull("second")
                        && !isNull(jsonObject.getString("second"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("second");
                    int size = jsonList.length();
                    second = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        second.add(new TypeInfor(jsonList.getJSONObject(i)));
                    }
                }

            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public TypeInfor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeInfor(String id, String name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "TypeInfor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", imgurlbig='" + imgurlbig + '\'' +
                ", second=" + second +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TypeInfor> getSecond() {
        return second;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getImgurlbig() {
        return imgurlbig;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
