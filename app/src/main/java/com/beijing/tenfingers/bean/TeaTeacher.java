package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 品茶师/评茶师-详情
 */
public class TeaTeacher extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String realname;
    private String image_link;
    private String banner_link;
    private String level;
    private String tea_age;
    private String remark;
    private String count;
    private String job;
    private ArrayList<TeaCommandResult> results=new ArrayList<>();

    public TeaTeacher(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count=get(jsonObject,"count");

                if (!jsonObject.isNull("detail")
                        && !isNull(jsonObject.getString("detail"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("detail");
                    JSONObject temp= (JSONObject) jsonList.get(0);
                    realname=get(temp,"realname");
                    image_link=get(temp,"image_link");
                    level=get(temp,"level");
                    tea_age=get(temp,"tea_age");
                    remark=get(temp,"remark");
                    banner_link = get(temp, "banner_link");
                    job = get(temp, "role");
                }
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("result");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        results.add(new TeaCommandResult(jsonList.getJSONObject(i)));
                }


                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getRealname() {
        return realname;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getLevel() {
        return level;
    }

    public String getTea_age() {
        return tea_age;
    }

    public String getJob() {
        return job;
    }

    public String getRemark() {
        return remark;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<TeaCommandResult> getResults() {
        return results;
    }
}
