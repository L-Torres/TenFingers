package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 预购-预购列表
 */
public class WikiAdvanced extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String count;
    private String unread_count;
   private ArrayList<WikiAdvancedChild> children=new ArrayList<>();


    public WikiAdvanced(JSONObject jsonObject) throws DataParseException {


        if (jsonObject != null) {
            try {
                unread_count=get(jsonObject,"unread_count");
                count = get(jsonObject, "count");
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("result");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new WikiAdvancedChild(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getUnread_count() {
        return unread_count;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<WikiAdvancedChild> getChildren() {
        return children;
    }
}
