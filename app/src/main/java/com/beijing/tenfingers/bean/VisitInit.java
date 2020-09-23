package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class VisitInit  extends XtomObject {

    private String visit_id;
    public VisitInit(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                visit_id=get(jsonObject,"visit_id");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getVisit_id() {
        return visit_id;
    }
}
