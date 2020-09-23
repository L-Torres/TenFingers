package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 标签列表
 * Created by Torres on 2018/4/10.
 */

public class TagList extends XtomObject implements Serializable {
    private static final long serialVersionUID = 1265819772736012294L;
    private String id;
    private String name;
    private String if_choice;
    private  boolean flag=false;
    private String number;

    public TagList(String id, String name, String if_choice, boolean flag) {
        this.id = id;
        this.name = name;
        this.if_choice = if_choice;
        this.flag = flag;
    }

    public TagList(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public TagList() {
    }

    public TagList(String id, String name, boolean flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public TagList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id = get(jsonObject, "id");
                name = get(jsonObject, "name");
                if_choice = get(jsonObject, "if_choice");
                number = get(jsonObject,"number");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    @Override
    public String toString() {
        return "TagList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", if_choice='" + if_choice + '\'' +
                ", flag=" + flag +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIf_choice() {
        return if_choice;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIf_choice(String if_choice) {
        this.if_choice = if_choice;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
