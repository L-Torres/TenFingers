package com.hemaapp.hm_FrameWork.result;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import xtom.frame.XtomActivityManager;
import xtom.frame.exception.DataParseException;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 对BaseResult的拓展，适用返回数据中有数组的情况
 */
public abstract class HemaArrayResult<T> extends HemaBaseResult {
    private ArrayList<T> objects = new ArrayList();

    public HemaArrayResult(JSONObject jsonObject) throws DataParseException {
        super(jsonObject);
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("data") && !this.isNull(jsonObject.getString("data"))) {
                    Object o = new JSONTokener(jsonObject.getString("data")).nextValue();
                    if (o instanceof JSONObject) {
                        JSONObject json = jsonObject.getJSONObject("data");
                        objects.add(parse(json));
                    } else if (o instanceof JSONArray) {
                        JSONArray jsonList = jsonObject.getJSONArray("data");
                        int size = jsonList.length();
                        for (int i = 0; i < size; ++i) {
                            this.objects.add(this.parse(jsonList.getJSONObject(i)));
                        }
                    }else{
                        objects.add((T) jsonObject.getString("data"));
                    }
                }
            } catch (JSONException var5) {
                throw new DataParseException(var5);
            }
        }

    }

    public ArrayList<T> getObjects() {
        return this.objects;
    }

    public abstract T parse(JSONObject var1) throws DataParseException;
}
