package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class WikiAdvancedChild  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String user_id;
    private String name;
    private String needs;
    private String remark;
    private String created_at;
    private String is_read;
    private String advanced_user_count;
    private String image_link;
    private String status;
    private String type_id;
    public WikiAdvancedChild(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                user_id = get(jsonObject, "user_id");
                name =  get(jsonObject, "name");
                needs =  get(jsonObject, "needs");
                remark =  get(jsonObject, "remark");
                created_at = get(jsonObject, "created_at");
                is_read=get(jsonObject,"is_read");
                advanced_user_count=get(jsonObject,"advanced_user_count");
                image_link=get(jsonObject,"image_link");
                status=get(jsonObject,"status");
                type_id=get(jsonObject,"type_id");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "WikiAdvancedChild{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", needs='" + needs + '\'' +
                ", remark='" + remark + '\'' +
                ", created_at='" + created_at + '\'' +
                ", is_read='" + is_read + '\'' +
                ", advanced_user_count='" + advanced_user_count + '\'' +
                ", image_link='" + image_link + '\'' +
                ", status='" + status + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }

    public String getType_id() {
        return type_id;
    }

    public String getStatus() {
        return status;
    }

    public String getAdvanced_user_count() {
        return advanced_user_count;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getNeeds() {
        return needs;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreated_at() {
        return created_at;
    }
}
