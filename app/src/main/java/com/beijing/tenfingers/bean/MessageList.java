package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 通知
 */
public class MessageList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String notify_content;
    private String target_type;
    private String notify_target_id;
    private String created_at;
    private String is_read;
    public MessageList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                notify_content = get(jsonObject, "notify_content");
                target_type = get(jsonObject, "target_type");
                notify_target_id = get(jsonObject, "notify_target_id");
                created_at=get(jsonObject,"created_at");
                is_read=get(jsonObject,"is_read");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "MessageList{" +
                "id='" + id + '\'' +
                ", notify_content='" + notify_content + '\'' +
                ", target_type='" + target_type + '\'' +
                ", notify_target_id='" + notify_target_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", is_read='" + is_read + '\'' +
                '}';
    }

    public String getIs_read() {
        return is_read;
    }

    public String getId() {
        return id;
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

    public String getCreated_at() {
        return created_at;
    }
}
