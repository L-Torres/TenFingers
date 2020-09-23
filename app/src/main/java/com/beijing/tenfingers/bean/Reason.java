package com.beijing.tenfingers.bean;

import java.io.Serializable;

import xtom.frame.XtomObject;

public class Reason extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String content;
    private boolean flag=false;

    public void setContent(String content) {
        this.content = content;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public boolean isFlag() {
        return flag;
    }

    public Reason(String content, boolean flag) {
        this.content = content;
        this.flag = flag;
    }
}
