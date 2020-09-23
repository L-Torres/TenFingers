package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class QuestionList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String question_nid;
    private String type;
    private String title;
    private String value;
    private String id;
    private ArrayList<QuestionChildren> children=new ArrayList<>();
    public QuestionList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                id= get(jsonObject, "id");
                question_nid = get(jsonObject, "question_nid");
                type = get(jsonObject, "type");
                title = get(jsonObject, "title");
                value = get(jsonObject, "value");

                if (!jsonObject.isNull("values")
                        && !isNull(jsonObject.getString("values"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("values");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new QuestionChildren(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public void setQuestion_nid(String question_nid) {
        this.question_nid = question_nid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getQuestion_nid() {
        return question_nid;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public ArrayList<QuestionChildren> getChildren() {
        return children;
    }
}
