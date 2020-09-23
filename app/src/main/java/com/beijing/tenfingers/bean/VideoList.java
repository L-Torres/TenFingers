package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 视频通用类
 */
public class VideoList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String vidio_link;
    private String comment_id;

    public VideoList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                vidio_link = get(jsonObject, "vidio_link");
                comment_id = get(jsonObject, "comment_id");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getVidio_link() {
        return vidio_link;
    }

    public String getComment_id() {
        return comment_id;
    }
}
