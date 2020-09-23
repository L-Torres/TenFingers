package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class MessageListChildren  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String notify_content;
    private String notify_target_id;
    private String target_type;
    private String sender_id;
    private String created_at;
    private String tea_img_link;
    private String is_read;
    private String action;
    private String image_link;

    private String ext;

    public MessageListChildren(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id  =  get(jsonObject, "id");
                notify_content =  get(jsonObject, "notify_content");
                notify_target_id = get(jsonObject, "notify_target_id");
                target_type = get(jsonObject, "target_type");
                sender_id = get(jsonObject, "sender_id");
                created_at = get(jsonObject, "created_at");
                tea_img_link = get(jsonObject, "tea_img_link");
                is_read= get(jsonObject, "is_read");
                action=get(jsonObject,"action");
                image_link=get(jsonObject,"image_link");
                ext = get(jsonObject, "ext");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getImage_link() {
        return image_link;
    }

    public String getAction() {
        return action;
    }

    public String getIs_read() {
        return is_read;
    }

    public MessageListChildren(String id, String notify_content) {
        this.id = id;
        this.notify_content = notify_content;
    }

    public String getExt() {
        return ext;
    }

    public String getId() {
        return id;
    }

    public String getNotify_content() {
        return notify_content;
    }

    public String getNotify_target_id() {
        return notify_target_id;
    }

    public String getTarget_type() {
        return target_type;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getTea_img_link() {
        return tea_img_link;
    }
}
