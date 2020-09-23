package com.hemaapp.hm_FrameWork.newnet;

import org.json.JSONObject;

import java.util.HashMap;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 基础网络请求任务类 取代BaseNetTask
 * create by Torres
 * 2018年11月16日14:49:04
 */
public abstract class BaseNetTask extends XtomObject {
    private int id;
    private String path;
    private String description;
    private HashMap<String, String> params;
    private HashMap<String, String> files;
    private int tryTimes;

    public BaseNetTask(int id, String path, HashMap<String, String> params, String description) {
        this(id, path, params);
        this.description = description;
    }

    public BaseNetTask(int id, String path, HashMap<String, String> params, HashMap<String, String> files, String description) {
        this(id, path, params, files);
        this.description = description;
    }

    public BaseNetTask(int id, String path, HashMap<String, String> params, HashMap<String, String> files) {
        this(id, path, params);
        this.files = files;
    }

    public BaseNetTask(int id, String path, HashMap<String, String> params) {
        this.tryTimes = 0;
        this.id = id;
        this.path = path;
        this.params = params;
    }

    public abstract Object parse(JSONObject var1) throws DataParseException;

    public int getId() {
        return this.id;
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }

    public String getPath() {
        return this.path;
    }

    public HashMap<String, String> getFiles() {
        return this.files;
    }

    public int getTryTimes() {
        return this.tryTimes;
    }

    public void setTryTimes(int tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getDescription() {
        return this.description;
    }
}
