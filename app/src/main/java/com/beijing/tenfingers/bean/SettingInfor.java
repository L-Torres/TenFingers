package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: SettingInfor
 * Author: wangyuxia
 * Date: 2020/1/14 16:15
 * Description: 推荐页的配置内容
 */
public class SettingInfor extends XtomObject {

    private String name;
    private String value;
    private String status;
    private String image_link; //封面图
    private String position; //视频所在的位置

    public SettingInfor(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                name = get(jsonObject, "name");
                value = get(jsonObject, "value");
                status = get(jsonObject, "status");
                image_link = get(jsonObject, "image_link");
                position = get(jsonObject, "position");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getPosition() {
        return position;
    }
}
