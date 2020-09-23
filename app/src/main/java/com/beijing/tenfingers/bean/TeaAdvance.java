package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: TeaAdvance
 * Author: wangyuxia
 * Date: 2020/2/21 14:37
 * Description: 预购列表的数据实体
 */
public class TeaAdvance extends XtomObject {

    private String id;
    private String r_tea_title; //标题
    private String r_img_link; //封面图
    private String r_count; //已售出数量
    private String r_deposit; //定金 单位 元
    private String r_discount; //折扣金额 单位 元
    private String r_goal; //目标量
    private String r_rate; //进度

    public TeaAdvance(JSONObject jsonObject) throws DataParseException {
        if(jsonObject != null){
            try {
                id = get(jsonObject, "id");
                r_tea_title = get(jsonObject, "r_tea_title");
                r_img_link = get(jsonObject, "r_img_link");
                r_count = get(jsonObject, "r_count");
                r_deposit = get(jsonObject, "r_deposit");
                r_discount = get(jsonObject, "r_discount");
                r_goal = get(jsonObject, "r_goal");
                r_rate = get(jsonObject, "r_rate");

            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public String getR_tea_title() {
        return r_tea_title;
    }

    public String getR_img_link() {
        return r_img_link;
    }

    public String getR_count() {
        return r_count;
    }

    public String getId() {
        return id;
    }

    public String getR_deposit() {
        return r_deposit;
    }

    public String getR_discount() {
        return r_discount;
    }

    public String getR_goal() {
        return r_goal;
    }

    public String getR_rate() {
        return r_rate;
    }
}
