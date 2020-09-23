package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Technician extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String total;
    private ArrayList<Technicians> list=new ArrayList<>();
    public Technician(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                total=get(jsonObject,"total");

                if (!jsonObject.isNull("rows")
                        && !isNull(jsonObject.getString("rows"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("rows");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        list.add(new Technicians(jsonList.getJSONObject(i)));
                }

                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Technician{" +
                "total='" + total + '\'' +
                ", list=" + list +
                '}';
    }

    public String getTotal() {
        return total;
    }

    public ArrayList<Technicians> getList() {
        return list;
    }
}
