package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * Created by Torres on 2016/12/12.
 */

public class CityChildren0 extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;//"1",
    private String parentid;//"0",
    private String name;//"北京市",
    private ArrayList<CityChildren0> children = new ArrayList<CityChildren0>();//

    public CityChildren0(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                name = get(jsonObject, "area_name");
                parentid = get(jsonObject, "parentid");
//                charindex = get(jsonObject, "charindex");
                if (!jsonObject.isNull("children")
                        && !isNull(jsonObject.getString("children"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("children");
                    int size = jsonList.length();
                    children = new ArrayList<CityChildren0>();
                    for (int i = 0; i < size; i++) {
                        children.add(new CityChildren0(jsonList.getJSONObject(i)));
                    }
                }
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public CityChildren0(String id, String parentid, String name, ArrayList<CityChildren0> children) {
        this.id = id;
        this.parentid = parentid;
        this.name = name;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CityChildren0> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<CityChildren0> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "CityChildren0{" +
                "id='" + id + '\'' +
                ", parentid='" + parentid + '\'' +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
