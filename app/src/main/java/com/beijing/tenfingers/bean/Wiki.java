package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class Wiki extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String count;

    private ArrayList<TeaCommandResult> results = new ArrayList<>();
    private String id;
    private String name;
    private String short_name;
    private String image_link;
    private String desc;
    private String prd_desc;
    private String prd_image_link;
    private String is_banner;
    private String redirect_url;
    private String banner_image_link;

    private ArrayList<ImageTea> img_head = new ArrayList<>();

    public Wiki(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count = get(jsonObject, "count");
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("result");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        results.add(new TeaCommandResult(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("wiki")
                        && !isNull(jsonObject.getString("wiki"))) {

                    JSONObject wiki = jsonObject.getJSONObject("wiki");
                    id = wiki.getString("id");
                    short_name = get(wiki, "short_name");
                    image_link = get(wiki, "image_link");
                    desc = get(wiki, "desc");
                    name = get(wiki, "name");

                    is_banner = get(wiki, "is_banner");
                    redirect_url = get(wiki, "redirect_url");
                    banner_image_link = get(wiki, "banner_image_link");

                    if (!wiki.isNull("image")
                            && !isNull(wiki.getString("image"))) {
                        JSONArray jsonList = wiki.getJSONArray("image");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            img_head.add(new ImageTea(jsonList.getJSONObject(i)));
                    }
                }
                if (!jsonObject.isNull("detail")
                        && !isNull(jsonObject.getString("detail"))) {
                    JSONObject prd_temp = jsonObject.getJSONObject("detail");
                    prd_desc = get(prd_temp, "desc");
                    prd_image_link = get(prd_temp, "image_link");
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Wiki{" +
                "count='" + count + '\'' +
                ", results=" + results +
                ", id='" + id + '\'' +
                ", short_name='" + short_name + '\'' +
                ", image_link='" + image_link + '\'' +
                ", desc='" + desc + '\'' +
                ", img_head=" + img_head +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getPrd_desc() {
        return prd_desc;
    }

    public String getPrd_image_link() {
        return prd_image_link;
    }

    public ArrayList<ImageTea> getImg_head() {
        return img_head;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<TeaCommandResult> getResults() {
        return results;
    }

    public String getId() {
        return id;
    }

    public String getShort_name() {
        return short_name;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getDesc() {
        return desc;
    }

    public String getIs_banner() {
        return is_banner;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public String getBanner_image_link() {
        return banner_image_link;
    }
}
