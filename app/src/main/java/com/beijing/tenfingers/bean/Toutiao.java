package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 头条
 */
public class Toutiao extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private ArrayList<String> news=new ArrayList<>();
    private ArrayList<String> ids=new ArrayList<>();
    public Toutiao(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("news")
                        && !isNull(jsonObject.getString("news"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("news");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        news.add(jsonList.getString(i));
                }
                if (!jsonObject.isNull("ids")
                        && !isNull(jsonObject.getString("ids"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("ids");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        ids.add(jsonList.getString(i));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public ArrayList<String> getNews() {
        return news;
    }
}
