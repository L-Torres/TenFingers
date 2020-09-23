package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 分类父项
 */
public class ClassifyFather extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private ArrayList<Classify> list=new ArrayList<>();

    public ClassifyFather(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("types")
                        && !isNull(jsonObject.getString("types"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("types");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        list.add(new Classify(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public ArrayList<Classify> getList() {
        return list;
    }
}
