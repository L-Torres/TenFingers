package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 评论追加
 */
public class ValueAppend extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String content;
    private String id;
    private String user_id;
    private String thumb_count;
    private String created_at;
    private String is_append;
    private String is_thumb;//是否点赞
    private String username;//用户名
    private String img_link;//用户头像
    private ArrayList<ImageList> lists=new ArrayList<>();
    private VideoList video;
    public ValueAppend(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                content = get(jsonObject, "content");
                id = get(jsonObject, "id");
                user_id = get(jsonObject, "user_id");
                thumb_count = get(jsonObject, "thumb_count");
                created_at = get(jsonObject, "created_at");
                is_append = get(jsonObject, "is_append");
                is_thumb = get(jsonObject, "is_thumb");
                username = get(jsonObject, "username");
                img_link = get(jsonObject, "img_link");
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("image"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("image");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        lists.add(new ImageList(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("vidio")
                        && !isNull(jsonObject.getString("vidio"))) {
                    JSONObject temp=jsonObject.getJSONObject("vidio");
                    video=new VideoList(temp);
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getThumb_count() {
        return thumb_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIs_append() {
        return is_append;
    }

    public String getIs_thumb() {
        return is_thumb;
    }

    public String getUsername() {
        return username;
    }

    public String getImg_link() {
        return img_link;
    }
}
