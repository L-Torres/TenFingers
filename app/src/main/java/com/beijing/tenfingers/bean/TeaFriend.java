package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 茶友
 */
public class TeaFriend extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String user_id;
    private String friend_id;
    private ArrayList<notifies> notifies = new ArrayList<>();
    private String img_link;
    private String username;
    private String id;

    public TeaFriend(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                user_id = get(jsonObject, "user_id");
                friend_id = get(jsonObject, "friend_id");

                if (!jsonObject.isNull("user")
                        && !isNull(jsonObject.getString("user"))) {
                    JSONObject info = jsonObject.getJSONObject("user");
                    img_link = get(info, "img_link");
                    username = get(info, "username");
                    id = get(info, "id");
                }

                if (!jsonObject.isNull("notifies")
                        && !isNull(jsonObject.getString("notifies"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("notifies");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        notifies.add(new notifies(jsonList.getJSONObject(i)));
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

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }


    public ArrayList<TeaFriend.notifies> getNotifies() {
        return notifies;
    }
//    }


    private class user {
        String img_link;
        String username;
        String id;

        public user(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    img_link = get(jsonObject, "img_link");
                    username = get(jsonObject, "username");
                    id = get(jsonObject, "id");
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

        public String getId() {
            return id;
        }
    }

    private class notifies {
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
    }
}
