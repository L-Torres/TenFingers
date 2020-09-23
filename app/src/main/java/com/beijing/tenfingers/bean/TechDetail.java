package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class TechDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String t_collect_count;
    private String is_collect;
    private String t_media_count;
    private String t_image_link;
    private String t_name;
    private String t_english_name;
    private String t_dynamic_count;
    private String t_desc;
    private String t_hot_value;
    private String is_vip;
    private String t_cover_link;
    private ArrayList<CountList> countLists=new ArrayList<>();
    private ArrayList<TrendsList> trendsLists=new ArrayList<>();
    private ArrayList<TechImage> techImages=new ArrayList<>();
    private ArrayList<TechImage> richList=new ArrayList<>();
    private String t_skill_link;
    private String t_health_link;
    private String is_month;
    private String is_week;
    public TechDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                t_collect_count=get(jsonObject,"t_collect_count");
                is_collect=get(jsonObject,"is_collect");
                t_media_count=get(jsonObject,"t_media_count");
                t_image_link=get(jsonObject,"t_image_link");
                t_name=get(jsonObject,"t_name");
                t_english_name=get(jsonObject,"t_english_name");
                t_dynamic_count=get(jsonObject,"t_dynamic_count");
                t_desc=get(jsonObject,"t_desc");
                t_hot_value=get(jsonObject,"t_hot_value");
                is_vip=get(jsonObject,"is_vip");
                t_cover_link=get(jsonObject,"t_cover_link");
                t_skill_link=get(jsonObject,"t_skill_link");
                t_health_link=get(jsonObject,"t_health_link");
                is_month=get(jsonObject,"is_month");
                is_week=get(jsonObject,"is_week");
                if (!jsonObject.isNull("countList")
                        && !isNull(jsonObject.getString("countList"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("countList");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        countLists.add(new CountList(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("trendsList")
                        && !isNull(jsonObject.getString("trendsList"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("trendsList");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        trendsLists.add(new TrendsList(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("imageList")
                        && !isNull(jsonObject.getString("imageList"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("imageList");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        techImages.add(new TechImage(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("richList")
                        && !isNull(jsonObject.getString("richList"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("richList");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        richList.add(new TechImage(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getIs_month() {
        return is_month;
    }

    public String getIs_week() {
        return is_week;
    }

    public String getT_skill_link() {
        return t_skill_link;
    }

    public String getT_health_link() {
        return t_health_link;
    }

    public String getT_cover_link() {
        return t_cover_link;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public ArrayList<TrendsList> getTrendsLists() {
        return trendsLists;
    }

    public ArrayList<TechImage> getTechImages() {
        return techImages;
    }

    public ArrayList<TechImage> getRichList() {
        return richList;
    }

    public String getT_collect_count() {
        return t_collect_count;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public String getT_media_count() {
        return t_media_count;
    }

    public String getT_image_link() {
        return t_image_link;
    }

    public String getT_name() {
        return t_name;
    }

    public String getT_english_name() {
        return t_english_name;
    }

    public String getT_dynamic_count() {
        return t_dynamic_count;
    }

    public String getT_desc() {
        return t_desc;
    }

    public String getT_hot_value() {
        return t_hot_value;
    }

    public ArrayList<CountList> getCountLists() {
        return countLists;
    }
}
