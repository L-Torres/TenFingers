package com.hemaapp.hm_FrameWork.newnet;

import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;
import xtom.frame.exception.DataParseException;
import xtom.frame.util.XtomSharedPreferencesUtil;

public abstract class LoginArrayResult<T> extends HemaBaseResult {
    private ArrayList<T> objects = new ArrayList<T>() ;
    public  static String token="";
    public LoginArrayResult(JSONObject jsonObject) throws DataParseException {
        super(jsonObject);
        if (jsonObject != null) {
            try {
                if (!jsonObject.isNull("infor")
                        && !isNull(jsonObject.getString("infor"))) {

                    //modify by torres
                    JSONObject temp=jsonObject.getJSONObject("infor");
                    token=temp.getString("token");
                    XtomSharedPreferencesUtil.save(XtomActivityManager.getLastActivity(),"token",token);
                    objects.add(parse(temp.getJSONObject("user")));
                }
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    /**
     * 获取服务器返回的实例集合
     *
     * @return 服务器返回的实例集合
     */
    public ArrayList<T> getObjects() {
        return objects;
    }

    /**
     * 该方法将JSONObject解析为具体的数据实例
     */
    public abstract T parse(JSONObject jsonObject) throws DataParseException;
}
