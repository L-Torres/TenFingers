package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 茶友详情
 */
public class FriendDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private ArrayList<notifies> notifies = new ArrayList<>();
    private String img_link;
    private String username;
    private String comment_count;
    private String buy_count;
    private ArrayList<Hobby> hobbies=new ArrayList<>();
    private ArrayList<Comments> comments=new ArrayList<>();
    public FriendDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {

                if (!jsonObject.isNull("info")
                        && !isNull(jsonObject.getString("info"))) {
                    JSONObject info = jsonObject.getJSONObject("info");
                    img_link = get(info, "img_link");
                    username = get(info, "username");
                    comment_count = get(info, "comment_count");
                    buy_count = get(info, "buy_count");
                }

                if (!jsonObject.isNull("notifies")
                        && !isNull(jsonObject.getString("notifies"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("notifies");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        notifies.add(new notifies(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("comments")
                        && !isNull(jsonObject.getString("comments"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("comments");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        comments.add(new Comments(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("hobbies")
                        && !isNull(jsonObject.getString("hobbies"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("hobbies");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        hobbies.add(new Hobby(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public ArrayList<FriendDetail.notifies> getNotifies() {
        return notifies;
    }

    public String getImg_link() {
        return img_link;
    }

    public String getUsername() {
        return username;
    }

    public String getComment_count() {
        return comment_count;
    }

    public String getBuy_count() {
        return buy_count;
    }

    public ArrayList<Hobby> getHobbies() {
        return hobbies;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public class notifies {
        String notify_content;
        String target_type;
        String notify_target_id;

        public notifies(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    notify_content = get(jsonObject, "notify_content");
                    target_type = get(jsonObject, "target_type");
                    notify_target_id = get(jsonObject, "notify_target_id");
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public String getNotify_content() {
            return notify_content;
        }

        public String getTarget_type() {
            return target_type;
        }

        public String getNotify_target_id() {
            return notify_target_id;
        }
    }


}
