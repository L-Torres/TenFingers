package com.beijing.tenfingers.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 预购详情
 */
public class AdvanceDetail extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String r_tea_title;
    private String id;
    private String r_remark;
    private String r_goal;
    private String r_real_count;
    private String r_count;
    private String r_deposit;
    private String r_format;
    private String r_discount;
    private String is_confirm;
    private String r_price;
    private String r_s_price;
    private String r_e_price;
    private String r_platation_id;
    private String r_area_id;
    private String r_type_id;
    private String r_level;
    private String r_feature;
    private String r_factory_id;
    private String r_detail;
    private String r_q_rate;
    private String r_assemble;
    private String r_assemble_need;
    private String r_taster_id;
    private String r_valuator_id;
    private String r_taster_reason;
    private String r_valuator_reason;
    private String reserve;
    private String is_collect;
    private String r_rate;
    private String r_video_time;
    private String r_img_link;
    private String r_video_link;
    private ArrayList<Comments> comments = new ArrayList<>();
    private ArrayList<ImageAd> images=new ArrayList<>();
    private Valuator platation;//
    private Valuator area;
    private Valuator type;
    private Valuator valuator;
    private Valuator taster;
    private String r_comment_count;
    private ArrayList<String> tea_detail_array = new ArrayList<>();
    public AdvanceDetail(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                reserve=get(jsonObject,"reserve");
                is_collect = get(jsonObject, "is_collect");
                if (!jsonObject.isNull("detail")
                        && !isNull(jsonObject.getString("detail"))) {
                    JSONObject temp = jsonObject.getJSONObject("detail");
                    r_comment_count=get(temp,"r_comment_count");
                    r_tea_title=get(temp,"r_tea_title");
                    id=get(temp,"id");
                    r_remark=get(temp,"r_remark");
                    r_goal=get(temp,"r_goal");
                    r_real_count=get(temp,"r_real_count");
                    r_count=get(temp,"r_count");
                    r_deposit=get(temp,"r_deposit");
                    r_format=get(temp,"r_format");
                    r_discount=get(temp,"r_discount");
                    is_confirm=get(temp,"is_confirm");
                    r_price=get(temp,"r_price");
                    r_rate=get(temp,"r_rate");
                    r_s_price=get(temp,"r_s_price");
                    r_e_price=get(temp,"r_e_price");
                    r_platation_id=get(temp,"r_platation_id");
                    r_area_id=get(temp,"r_area_id");
                    r_type_id=get(temp,"r_type_id");
                    r_level=get(temp,"r_level");
                    r_feature=get(temp,"r_feature");
                    r_factory_id=get(temp,"r_factory_id");
                    r_detail=get(temp,"r_detail");
                    r_q_rate=get(temp,"r_q_rate");
                    r_assemble=get(temp,"r_assemble");
                    r_assemble_need=get(temp,"r_assemble_need");
                    r_taster_id=get(temp,"r_taster_id");
                    r_valuator_id=get(temp,"r_valuator_id");
                    r_taster_reason=get(temp,"r_taster_reason");
                    r_valuator_reason=get(temp,"r_valuator_reason");

                    r_video_time = get(temp, "r_video_time");
                    r_img_link = get(temp, "r_img_link");
                    r_video_link = get(temp, "r_video_link");

                    if (!temp.isNull("platation")
                            && !isNull(temp.getString("platation"))) {
                        JSONObject temp_platation = temp.getJSONObject("platation");
                        platation=new Valuator(temp_platation);
                    }
                    if (!temp.isNull("type")
                            && !isNull(temp.getString("type"))) {
                        JSONObject temp_factory = temp.getJSONObject("type");
                        type=new Valuator(temp_factory);
                    }

                    if (!temp.isNull("area")
                            && !isNull(temp.getString("area"))) {
                        JSONObject temp_area = temp.getJSONObject("area");
                        area=new Valuator(temp_area);
                    }
                    if (!temp.isNull("valuator")
                            && !isNull(temp.getString("valuator"))) {
                        JSONObject temp_valuator = temp.getJSONObject("valuator");
                        valuator=new Valuator(temp_valuator);
                    }
                    if (!temp.isNull("r_detail_array")
                            && !isNull(temp.getString("r_detail_array"))) {
                        JSONArray jsonList = temp.getJSONArray("r_detail_array");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            tea_detail_array.add(jsonList.getString(i));
                    }
                    if (!temp.isNull("taster")
                            && !isNull(temp.getString("taster"))) {
                        JSONObject temp_taster = temp.getJSONObject("taster");
                        taster=new Valuator(temp_taster);
                    }
                    if (!temp.isNull("images")
                            && !isNull(temp.getString("images"))) {
                        JSONArray jsonList = temp.getJSONArray("images");
                        int size = jsonList.length();
                        for (int i = 0; i < size; i++)
                            images.add(new ImageAd(jsonList.getJSONObject(i)));
                    }
                }
                if (!jsonObject.isNull("comments")
                        && !isNull(jsonObject.getString("comments"))) {
                    JSONArray jsonList = jsonObject.getJSONArray("comments");
                    int size = jsonList.length();
                    for (int i = 0; i < size; i++)
                        comments.add(new Comments(jsonList.getJSONObject(i)));
                }
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }


    public ArrayList<String> getTea_detail_array() {
        return tea_detail_array;
    }

    @Override
    public String toString() {
        return "AdvanceDetail{" +
                "r_tea_title='" + r_tea_title + '\'' +
                ", id='" + id + '\'' +
                ", r_remark='" + r_remark + '\'' +
                ", r_goal='" + r_goal + '\'' +
                ", r_real_count='" + r_real_count + '\'' +
                ", r_count='" + r_count + '\'' +
                ", r_deposit='" + r_deposit + '\'' +
                ", r_format='" + r_format + '\'' +
                ", r_discount='" + r_discount + '\'' +
                ", is_confirm='" + is_confirm + '\'' +
                ", r_price='" + r_price + '\'' +
                ", r_s_price='" + r_s_price + '\'' +
                ", r_e_price='" + r_e_price + '\'' +
                ", r_platation_id='" + r_platation_id + '\'' +
                ", r_area_id='" + r_area_id + '\'' +
                ", r_type_id='" + r_type_id + '\'' +
                ", r_level='" + r_level + '\'' +
                ", r_feature='" + r_feature + '\'' +
                ", r_factory_id='" + r_factory_id + '\'' +
                ", r_detail='" + r_detail + '\'' +
                ", r_q_rate='" + r_q_rate + '\'' +
                ", r_assemble='" + r_assemble + '\'' +
                ", r_assemble_need='" + r_assemble_need + '\'' +
                ", r_taster_id='" + r_taster_id + '\'' +
                ", r_valuator_id='" + r_valuator_id + '\'' +
                ", r_taster_reason='" + r_taster_reason + '\'' +
                ", r_valuator_reason='" + r_valuator_reason + '\'' +
                ", reserve='" + reserve + '\'' +
                ", is_collect='" + is_collect + '\'' +
                ", r_rate='" + r_rate + '\'' +
                ", r_video_time='" + r_video_time + '\'' +
                ", r_img_link='" + r_img_link + '\'' +
                ", r_video_link='" + r_video_link + '\'' +
                ", comments=" + comments +
                ", images=" + images +
                ", platation=" + platation +
                ", area=" + area +
                ", type=" + type +
                ", valuator=" + valuator +
                ", taster=" + taster +
                '}';
    }

    public String getR_comment_count() {
        return r_comment_count;
    }

    public String getR_rate() {
        return r_rate;
    }

    public String getR_video_time() {
        return r_video_time;
    }

    public String getR_img_link() {
        return r_img_link;
    }

    public String getR_video_link() {
        return r_video_link;
    }

    public String getR_tea_title() {
        return r_tea_title;
    }

    public String getId() {
        return id;
    }

    public String getR_remark() {
        return r_remark;
    }

    public String getR_goal() {
        return r_goal;
    }

    public String getR_real_count() {
        return r_real_count;
    }

    public String getR_count() {
        return r_count;
    }

    public String getR_deposit() {
        return r_deposit;
    }

    public String getR_format() {
        return r_format;
    }

    public String getR_discount() {
        return r_discount;
    }

    public String getIs_confirm() {
        return is_confirm;
    }

    public String getR_price() {
        return r_price;
    }

    public String getR_s_price() {
        return r_s_price;
    }

    public String getR_e_price() {
        return r_e_price;
    }

    public String getR_platation_id() {
        return r_platation_id;
    }

    public String getR_area_id() {
        return r_area_id;
    }

    public String getR_type_id() {
        return r_type_id;
    }

    public String getR_level() {
        return r_level;
    }

    public String getR_feature() {
        return r_feature;
    }

    public String getR_factory_id() {
        return r_factory_id;
    }

    public String getR_detail() {
        return r_detail;
    }

    public String getR_q_rate() {
        return r_q_rate;
    }

    public String getR_assemble() {
        return r_assemble;
    }

    public String getR_assemble_need() {
        return r_assemble_need;
    }

    public String getR_taster_id() {
        return r_taster_id;
    }

    public String getR_valuator_id() {
        return r_valuator_id;
    }

    public String getR_taster_reason() {
        return r_taster_reason;
    }

    public String getR_valuator_reason() {
        return r_valuator_reason;
    }


    public String getReserve() {
        return reserve;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public Valuator getValuator() {
        return valuator;
    }

    public Valuator getTaster() {
        return taster;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public ArrayList<ImageAd> getImages() {
        return images;
    }

    public Valuator getPlatation() {
        return platation;
    }

    public Valuator getArea() {
        return area;
    }

    public Valuator getType() {
        return type;
    }
}
