package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 茶厂数据
 */
public class TeaFactory  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String name;
    private String desc;
    private ArrayList<ImageTea> imageTeas=new ArrayList<>();
    private ArrayList<TeaCommandResult> teaSames=new ArrayList<>();
    private String image_link;
    public TeaFactory(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("detail")
                        && !isNull(jsonObject.getString("detail"))) {
                    JSONObject info=jsonObject.getJSONObject("detail");
                    id=get(info,"id");
                    name=get(info,"name");
                    desc=get(info,"desc");
                    image_link=get(info,"image_link");
                    if (!jsonObject.isNull("image")
                            && !isNull(jsonObject.getString("image"))) {
                        JSONArray jsonList = info.getJSONArray("image");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            imageTeas.add(new ImageTea(jsonList.getJSONObject(i)));
                    }
                }
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("result");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        teaSames.add(new TeaCommandResult(jsonList.getJSONObject(i)));
                }

                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getImage_link() {
        return image_link;
    }

    @Override
    public String toString() {
        return "TeaFactory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", imageTeas=" + imageTeas +
                ", teaSames=" + teaSames +
                ", image_link='" + image_link + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<ImageTea> getImageTeas() {
        return imageTeas;
    }

    public ArrayList<TeaCommandResult> getTeaSames() {
        return teaSames;
    }
}
