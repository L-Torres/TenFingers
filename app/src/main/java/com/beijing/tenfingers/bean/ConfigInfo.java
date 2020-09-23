package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: ConfigInfo
 * Author: wangyuxia
 * Date: 2019/12/13 19:24
 * Description: ${DESCRIPTION}
 */
public class ConfigInfo extends XtomObject {

    private String phone;
    private String wx;

    public ConfigInfo(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                phone=get(jsonObject,"phone");
                wx = get(jsonObject, "wx");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getPhone() {
        return phone;
    }

    public String getWx() {
        return wx;
    }
}
