package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class FeedBack extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String content;
    private String id;
    private String created_at;
    private ArrayList<Replies> replies=new ArrayList<>();
    public FeedBack(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                content = get(jsonObject, "content");
                id = get(jsonObject, "id");
                created_at = get(jsonObject, "created_at");
                if (!jsonObject.isNull("replies")
                        && !isNull(jsonObject.getString("replies"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("replies");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++){
                        replies.add(new Replies(jsonList.getJSONObject(i)));
                    }
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public FeedBack(String id, String content, String created_at) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
    }

    public ArrayList<Replies> getReplies() {
        return replies;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }
}
