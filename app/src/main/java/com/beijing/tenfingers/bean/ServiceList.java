package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ServiceList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String total;
    private ArrayList<Product> children=new ArrayList<>();
    public ServiceList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                total= get(jsonObject, "total");

                if (!jsonObject.isNull("rows")
                        && !isNull(jsonObject.getString("rows"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("rows");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new Product(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "ServiceList{" +
                "total='" + total + '\'' +
                ", children=" + children +
                '}';
    }

    public String getTotal() {
        return total;
    }

    public ArrayList<Product> getChildren() {
        return children;
    }
}
