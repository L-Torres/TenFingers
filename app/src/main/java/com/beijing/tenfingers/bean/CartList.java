package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 购物车列表
 */
public class CartList extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;

    private String count;
    private ArrayList<child> children = new ArrayList<>();
    public child c = new child();
    private ArrayList<TeaCommandResult> teaCommandResults = new ArrayList<>();

    public CartList(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                count = get(jsonObject, "count");
                if (!jsonObject.isNull("result")
                        && !isNull(jsonObject.getString("result"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("result");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        children.add(new child(jsonList.getJSONObject(i)));
                }
                if (!jsonObject.isNull("recommand")
                        && !isNull(jsonObject.getString("recommand"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("recommand");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        teaCommandResults.add(new TeaCommandResult(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "CartList{" +
                "count='" + count + '\'' +
                ", children=" + children +
                ", c=" + c +
                ", teaCommandResults=" + teaCommandResults +
                '}';
    }

    public ArrayList<TeaCommandResult> getTeaCommandResults() {
        return teaCommandResults;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<child> getChildren() {
        return children;
    }

    public static class child extends XtomObject implements Serializable {

        private static final long serialVersionUID = 1265819772736012294L;
        String tea_title;
        String tea_price;
        String tea_period;
        String tea_date;
        String tea_type_id;
        String tea_img_link;
        String advance_date;
        String tea_left_count;
        String is_advance;
        String tea_count;
        String tea_id;
        String id;
        boolean isCheck = false;
        String activity_price;
        String can;
        String tea_alltype;

        public child(String tea_title, String tea_price, String tea_count, String tea_id, String id, String tea_img_link,
                     String tea_alltype, String can, String activity_price) {
            this.tea_title = tea_title;
            this.tea_price = tea_price;
            this.tea_count = tea_count;
            this.tea_id = tea_id;
            this.id = id;
            this.tea_img_link = tea_img_link;
            this.tea_alltype = tea_alltype;
            this.can = can;
            this.activity_price = activity_price;
        }

        public child(JSONObject jsonObject) throws DataParseException {
            if (jsonObject != null) {
                try {
                    id = get(jsonObject, "id");
                    tea_title = get(jsonObject, "tea_title");
                    tea_price = get(jsonObject, "tea_price");
                    tea_period = get(jsonObject, "tea_period");
                    tea_date = get(jsonObject, "tea_date");
                    tea_type_id = get(jsonObject, "tea_type_id");
                    tea_img_link = get(jsonObject, "tea_img_link");
                    advance_date = get(jsonObject, "advance_date");
                    tea_left_count = get(jsonObject, "tea_left_count");
                    is_advance = get(jsonObject, "is_advance");
                    tea_count = get(jsonObject, "tea_count");
                    tea_id = get(jsonObject, "tea_id");
                    activity_price = get(jsonObject, "activity_price");
                    can = get(jsonObject, "can");
                    tea_alltype = get(jsonObject, "tea_alltype");

                } catch (JSONException e) {
                    throw new DataParseException(e);
                }
            }
        }

        public child() {
        }

        public void setTea_title(String tea_title) {
            this.tea_title = tea_title;
        }

        public void setTea_price(String tea_price) {
            this.tea_price = tea_price;
        }

        public void setTea_period(String tea_period) {
            this.tea_period = tea_period;
        }

        public String getTea_alltype() {
            return tea_alltype;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public String getCan() {
            return can;
        }

        public void setTea_date(String tea_date) {
            this.tea_date = tea_date;
        }

        public void setTea_type_id(String tea_type_id) {
            this.tea_type_id = tea_type_id;
        }

        public void setTea_img_link(String tea_img_link) {
            this.tea_img_link = tea_img_link;
        }

        public void setAdvance_date(String advance_date) {
            this.advance_date = advance_date;
        }

        public void setTea_left_count(String tea_left_count) {
            this.tea_left_count = tea_left_count;
        }

        public void setIs_advance(String is_advance) {
            this.is_advance = is_advance;
        }

        public void setTea_count(String tea_count) {
            this.tea_count = tea_count;
        }

        public void setTea_id(String tea_id) {
            this.tea_id = tea_id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getTea_title() {
            return tea_title;
        }

        public String getTea_price() {
            return tea_price;
        }

        public String getTea_period() {
            return tea_period;
        }

        public String getTea_date() {
            return tea_date;
        }

        public String getTea_type_id() {
            return tea_type_id;
        }

        public String getTea_img_link() {
            return tea_img_link;
        }

        public String getAdvance_date() {
            return advance_date;
        }

        public String getTea_left_count() {
            return tea_left_count;
        }

        public String getIs_advance() {
            return is_advance;
        }

        public String getTea_count() {
            return tea_count;
        }

        public String getTea_id() {
            return tea_id;
        }

        public String getId() {
            return id;
        }
    }
}
