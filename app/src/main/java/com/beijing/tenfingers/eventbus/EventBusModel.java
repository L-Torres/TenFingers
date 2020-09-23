package com.beijing.tenfingers.eventbus;

import java.io.Serializable;

import xtom.frame.XtomObject;

/**
 * EventBus传递数据使用
 * Created by Torres on 2018/1/3.
 */

public class EventBusModel extends XtomObject implements Serializable {
    private static final long serialVersionUID = 2457040098627212163L;
    private boolean state;
    private int type;
    private String content = "";
    private Object object;

    public EventBusModel(boolean state, int type) {
        this.state = state;
        this.type = type;
    }

    public EventBusModel(boolean state, int type, String content) {
        this(state, type);
        this.content = content;
    }
    public EventBusModel(boolean state, int type, Object object) {
        this(state, type);
        this.object = object;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}