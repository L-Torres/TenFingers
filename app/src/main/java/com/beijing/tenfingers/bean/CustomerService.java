package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 客服
 */
public class CustomerService  extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String csc_id;
    private String img_link;
    private String realname;
    private String remark;
    private String sex;
    private String age;
    private String csc_user_id;
    private String mobile;

    public CustomerService(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                csc_id=get(jsonObject,"csc_id");
                img_link=get(jsonObject,"img_link");
                realname=get(jsonObject,"realname");
                remark=get(jsonObject,"remark");
                sex=get(jsonObject,"sex");
                age=get(jsonObject,"age");
                csc_user_id=get(jsonObject,"csc_user_id");
                mobile = get(jsonObject, "mobile");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getMobile() {
        return mobile;
    }

    public String getCsc_user_id() {
        return csc_user_id;
    }

    public String getSex() {
        return sex;
    }


    public String getAge() {
        return age;
    }

    public String getCsc_id() {
        return csc_id;
    }

    public String getImg_link() {
        return img_link;
    }

    public String getRealname() {
        return realname;
    }

    public String getRemark() {
        return remark;
    }
}
