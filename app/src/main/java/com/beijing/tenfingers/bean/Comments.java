package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 评论
 */
public class Comments extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String created_at;
    private String comment_count;
    private String thumb_count;
    private String content;
    private String id;

    private String comment_id;
    private String vidio;
    private String vidio_link;
    private String user_id;
    private String is_anonymous;
    private String is_thumb;
    private String is_attitude;
    private String is_quality;

    private ArrayList<String> imgs = new ArrayList<>();
    private ValueUser valueUser;

    public Comments() {
    }

    public Comments(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                created_at = get(jsonObject, "created_at");
                comment_count = get(jsonObject, "comment_count");
                thumb_count = get(jsonObject, "thumb_count");
                id = get(jsonObject, "id");
                content = get(jsonObject, "content");
                comment_id = get(jsonObject, "comment_id");
                vidio = get(jsonObject, "vidio");
                vidio_link = get(jsonObject, "vidio_link");
                user_id = get(jsonObject, "user_id");
                is_thumb = get(jsonObject, "is_thumb");
                is_anonymous = get(jsonObject, "is_anonymous");
                is_attitude = get(jsonObject, "is_attitude");
                is_quality = get(jsonObject, "is_quality");
                if (!jsonObject.isNull("image")
                        && !isNull(jsonObject.getString("image"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("image");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++) {
                        String temp = jsonList.getJSONObject(i).getString("image_link");
                        imgs.add(temp);
                    }
                }
                if (!jsonObject.isNull("user")
                        && !isNull(jsonObject.getString("user"))) {
                    JSONObject temp = jsonObject.getJSONObject("user");
                    valueUser = new ValueUser(temp);
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getIs_anonymous() {
        return is_anonymous;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getVidio() {
        return vidio;
    }

    public String getVidio_link() {
        return vidio_link;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIs_attitude() {
        return is_attitude;
    }

    public String getIs_quality() {
        return is_quality;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getComment_count() {
        return comment_count;
    }

    public String getThumb_count() {
        return thumb_count;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public String getIs_thumb() {
        return is_thumb;
    }

    public ValueUser getValueUser() {
        return valueUser;
    }

    public class ValueUser implements Serializable {
        private String id;
        private String img_link;
        private String username;

        public ValueUser(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    img_link = get(jsonObject, "img_link");
                    username = get(jsonObject, "username");
                    log_i(toString());
                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public String getId() {
            return id;
        }

        public String getImg_link() {
            return img_link;
        }

        public String getUsername() {
            return username;
        }
    }
}
