package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 地址列表
 */
public class AddressList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String total;
    private ArrayList<Address> children = new ArrayList<>();

    public AddressList(JSONObject jsonObject) throws DataParseException {

        if (jsonObject != null) {
            try {
                total=get(jsonObject,"total");
                if (!jsonObject.isNull("rows")
                        && !isNull(jsonObject.getString("rows"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("rows");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new Address(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public String getTotal() {
        return total;
    }

    public ArrayList<Address> getChildren() {
        return children;
    }

}
