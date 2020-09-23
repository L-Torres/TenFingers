package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: ProtocalInfo
 * Author: wangyuxia
 * Date: 2019/12/20 15:53
 * Description: ${DESCRIPTION}
 */
public class ProtocalInfo extends XtomObject {

    private String id;
    private String title;
    private String content;
    private String type;
    private String status;
    private String created_at;
    private String updated_at;

    public ProtocalInfo(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                title = get(jsonObject, "title");
                content = get(jsonObject, "content");
                type = get(jsonObject, "type");
                status = get(jsonObject, "status");
                created_at = get(jsonObject, "created_at");
                updated_at = get(jsonObject, "updated_at");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
