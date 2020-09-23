package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class AreaData  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String province;
    private ArrayList<TagList> children = new ArrayList<>();

    public AreaData(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id=get(jsonObject,"id");
                province=get(jsonObject,"province");
                if (!jsonObject.isNull("types")
                        && !isNull(jsonObject.getString("types"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("types");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new TagList(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public ArrayList<TagList> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "AreaData{" +
                "id='" + id + '\'' +
                ", province='" + province + '\'' +
                ", children=" + children +
                '}';
    }
}
