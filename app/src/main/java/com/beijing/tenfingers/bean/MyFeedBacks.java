package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class MyFeedBacks extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String img_link;
    private String username;
    private ArrayList<FeedBack> feedBacks=new ArrayList<>();
    public MyFeedBacks(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONObject info=jsonObject.getJSONObject("result");
                    if (!info.isNull("user")
                            && !isNull(info.getString("user"))) {
                        JSONObject user=info.getJSONObject("user");
                        img_link =user.getString("img_link");
                        username =user.getString("username");
                    }
                    if (!info.isNull("feedbacks")
                            && !isNull(info.getString("feedbacks"))) {
                        JSONArray jsonList = info.getJSONArray("feedbacks");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++){
                            feedBacks.add(new FeedBack(jsonList.getJSONObject(i)));
                        }
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }
    public String getImg_link() {
        return img_link;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<FeedBack> getFeedBacks() {
        return feedBacks;
    }

}
