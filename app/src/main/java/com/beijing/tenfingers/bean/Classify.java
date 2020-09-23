package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 分类
 */
public class Classify extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String group_id;
    private String group_name;
    private ArrayList<ClassifyChildren> list=new ArrayList<>();

    public Classify(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                group_id = get(jsonObject, "group_id");
                group_name = get(jsonObject, "group_name");
                if (!jsonObject.isNull("type")
                        && !isNull(jsonObject.getString("type"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("type");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        list.add(new ClassifyChildren(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public ArrayList<ClassifyChildren> getList() {
        return list;
    }
}
