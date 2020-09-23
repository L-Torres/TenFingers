package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 评论子类
 */
public class ValueChild extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String content;
    private String id;
    private String user_id;
    private String thumb_count;
    private String created_at;
    private String is_append;
    private String is_anonymous;
    private String is_thumb;//是否点赞
    private String username;//用户名
    private String img_link;//用户头像
    private String reply_content; //回复内容
    private String reply_at; //回复时间
    private String is_attitude;
    private String is_quality;

    private ArrayList<ImageList> lists = new ArrayList<>();
    private VideoList video;

    private ArrayList<ValueChild> append = new ArrayList<>();

    public ValueChild(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                content = get(jsonObject, "content");
                id = get(jsonObject, "id");
                user_id = get(jsonObject, "user_id");
                thumb_count = get(jsonObject, "thumb_count");
                created_at = get(jsonObject, "created_at");
                is_append = get(jsonObject, "is_append");
                is_anonymous = get(jsonObject, "is_anonymous");
                is_thumb = get(jsonObject, "is_thumb");
                reply_content = get(jsonObject, "reply_content");
                reply_at = get(jsonObject, "reply_at");
                is_attitude = get(jsonObject, "is_attitude");
                is_quality = get(jsonObject, "is_quality");

                if (!jsonObject.isNull("image")
                        && !isNull(jsonObject.getString("image"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("image");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        lists.add(new ImageList(jsonList.getJSONObject(i)));
                }

                if (!jsonObject.isNull("user")
                        && !isNull(jsonObject.getString("user"))) {
                    JSONObject temp = jsonObject.getJSONObject("user");
                    username = get(temp, "username");
                    img_link = get(temp, "img_link");
                }
                if (!jsonObject.isNull("vidio")
                        && !isNull(jsonObject.getString("vidio"))) {
                    JSONObject temp = jsonObject.getJSONObject("vidio");
                    video = new VideoList(temp);
                }
                if (!jsonObject.isNull("append")
                        && !isNull(jsonObject.getString("append"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("append");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        append.add(new ValueChild(jsonList.getJSONObject(i)));
                }

            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getIs_anonymous() {
        return is_anonymous;
    }

    @Override
    public String toString() {
        return "ValueChild{" +
                "content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", thumb_count='" + thumb_count + '\'' +
                ", created_at='" + created_at + '\'' +
                ", is_append='" + is_append + '\'' +
                ", is_anonymous='" + is_anonymous + '\'' +
                ", is_thumb='" + is_thumb + '\'' +
                ", username='" + username + '\'' +
                ", img_link='" + img_link + '\'' +
                ", reply_content='" + reply_content + '\'' +
                ", reply_at='" + reply_at + '\'' +
                ", is_attitude='" + is_attitude + '\'' +
                ", is_quality='" + is_quality + '\'' +
                ", lists=" + lists +
                ", video=" + video +
                ", append=" + append +
                '}';
    }

    public ArrayList<ValueChild> getAppend() {
        return append;
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

    public String getIs_attitude() {
        return is_attitude;
    }

    public String getIs_quality() {
        return is_quality;
    }

    public ArrayList<ImageList> getLists() {
        return lists;
    }

    public VideoList getVideo() {
        return video;
    }

    public String getReply_content() {
        return reply_content;
    }

    public String getReply_at() {
        return reply_at;
    }
}
