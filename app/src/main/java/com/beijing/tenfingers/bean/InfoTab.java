package com.beijing.tenfingers.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.exception.DataParseException;

/**
 * Created by Administrator on 2018/11/8 0008.
 */
public class InfoTab  implements Parcelable {
    private String channelid;
    private String name;

    public InfoTab(){
        super();
    }

    /**
     * 创建一个InfoTab实例
     *
     * @param jsonObject
     *            Json实例
     * @throws DataParseException
     *             数据异常
     */
    public InfoTab(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                channelid = get(jsonObject, "id");
                name = get(jsonObject, "name");
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public InfoTab(String name){
        this.name = name;
    }

    protected InfoTab(Parcel in) {
        channelid = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channelid);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InfoTab> CREATOR = new Creator<InfoTab>() {
        @Override
        public InfoTab createFromParcel(Parcel in) {
            return new InfoTab(in);
        }

        @Override
        public InfoTab[] newArray(int size) {
            return new InfoTab[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    protected String get(JSONObject jsonObject, String s) throws JSONException {
        if (!jsonObject.isNull(s)) {
            return jsonObject.getString(s);
        }
        return null;
    }
}
