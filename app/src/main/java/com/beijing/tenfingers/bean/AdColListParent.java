package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class AdColListParent extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String count;
    private ArrayList<AdColList> children = new ArrayList<>();

    public AdColListParent(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count=get(jsonObject,"count");
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("result");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new AdColList(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    @Override
    public String toString() {
        return "AdColListParent{" +
                "count='" + count + '\'' +
                ", children=" + children +
                '}';
    }

    public String getCount() {
        return count;
    }

    public ArrayList<AdColList> getChildren() {
        return children;
    }
}
