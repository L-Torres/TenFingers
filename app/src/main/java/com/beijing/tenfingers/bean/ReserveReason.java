package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

//预购-取消原因
public class ReserveReason extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String reason;
    private boolean flag=false;

    public ReserveReason(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                reason = get(jsonObject, "reason");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ReserveReason{" +
                "reason='" + reason + '\'' +
                '}';
    }

    public String getReason() {
        return reason;
    }
}
