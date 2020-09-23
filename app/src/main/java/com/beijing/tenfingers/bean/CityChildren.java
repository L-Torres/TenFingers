package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 城市子模型
 */
public class CityChildren extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;//"1",
    private String name;//"北京市",
    private String level;//"0",
    private String charindex;//索引
    private ArrayList<CityChildren0> children = new ArrayList<CityChildren0>();//

    public CityChildren(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                name = get(jsonObject, "area_name");
                level = get(jsonObject, "parentid");
                charindex = get(jsonObject, "charindex");
                if (!jsonObject.isNull("children")
                        && !isNull(jsonObject.getString("children"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("children");
                    int size = jsonList.length();
                    children = new ArrayList<CityChildren0>();
                    for (int i = 0; i < size; i++) {
                        children.add(new CityChildren0(jsonList.getJSONObject(i)));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public CityChildren(String id, String name, String level, String charindex, ArrayList<CityChildren0> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.charindex = charindex;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getCharindex() {
        return charindex;
    }

    @Override
    public String toString() {
        return "CityChildren{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", charindex='" + charindex + '\'' +
                ", children=" + children +
                '}';
    }
}
