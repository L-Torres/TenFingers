package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class TimePoint extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String id;
    private String pointId;
    private String pointText;
    private String serviceStatus;
    private boolean flag=false;

    public TimePoint(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id=get(jsonObject,"id");
                pointId=get(jsonObject,"pointId");
                pointText=get(jsonObject,"pointText");
                serviceStatus=get(jsonObject,"serviceStatus");

                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "TimePoint{" +
                "id='" + id + '\'' +
                ", pointId='" + pointId + '\'' +
                ", pointText='" + pointText + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getPointId() {
        return pointId;
    }

    public String getPointText() {
        return pointText;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
