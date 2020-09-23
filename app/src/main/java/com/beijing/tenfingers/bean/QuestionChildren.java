package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class QuestionChildren extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String question_nid;
    private String name;
    private String value;
    private String key;
    private boolean flag = false;

    public void setQuestion_nid(String question_nid) {
        this.question_nid = question_nid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public QuestionChildren(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                question_nid = get(jsonObject, "question_nid");
                name = get(jsonObject, "name");
                value = get(jsonObject, "value");
                key = get(jsonObject, "key");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public String getKey() {
        return key;
    }

    public String getQuestion_nid() {
        return question_nid;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
