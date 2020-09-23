package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: TeaAdvanceRecommand
 * Author: wangyuxia
 * Date: 2020/2/24 15:22
 * Description: 搜索--预购商品
 */
public class TeaAdvanceRecommand extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String count;

    private ArrayList<TeaAdvance> results = new ArrayList<>();

    public TeaAdvanceRecommand(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count = get(jsonObject, "count");
                if (!jsonObject.isNull("tea")
                        && !isNull(jsonObject.getString("tea"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("tea");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        results.add(new TeaAdvance(jsonList.getJSONObject(i)));
                } else {
                    if (!jsonObject.isNull("result")
                            && !isNull(jsonObject.getString("result"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("result");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            results.add(new TeaAdvance(jsonList.getJSONObject(i)));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getCount() {
        return count;
    }

    public ArrayList<TeaAdvance> getResults() {
        return results;
    }

}
