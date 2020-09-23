package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 推荐 && 预告
 */
public class TeaRecommand extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String count;

    private ArrayList<TeaCommandResult> results = new ArrayList<>();

    public TeaRecommand(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count = get(jsonObject, "count");
                if (!jsonObject.isNull("tea")
                        && !isNull(jsonObject.getString("tea"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("tea");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        results.add(new TeaCommandResult(jsonList.getJSONObject(i)));
                } else {
                    if (!jsonObject.isNull("result")
                            && !isNull(jsonObject.getString("result"))) {
                        JSONArray jsonList = jsonObject.getJSONArray("result");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            results.add(new TeaCommandResult(jsonList.getJSONObject(i)));
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

    public ArrayList<TeaCommandResult> getResults() {
        return results;
    }
}
