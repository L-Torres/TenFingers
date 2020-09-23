package com.beijing.tenfingers.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class CarFee extends XtomObject implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String duration;
    private String longFee;
    private String distance;
    private String oilFee;
    private String now;
    private String startFee;
    private String overAll;
    private String distanceFee;
    private String total;
    public CarFee(JSONObject jsonObject) throws DataParseException {
        if (jsonObject != null) {
            try {
                duration = get(jsonObject, "duration");
                longFee = get(jsonObject, "longFee");
                distance = get(jsonObject, "distance");
                oilFee = get(jsonObject, "oilFee");
                now = get(jsonObject, "now");
                startFee = get(jsonObject, "startFee");
                overAll = get(jsonObject, "overAll");
                distanceFee = get(jsonObject, "distanceFee");
                total=get(jsonObject,"total");
                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "CarFee{" +
                "duration='" + duration + '\'' +
                ", longFee='" + longFee + '\'' +
                ", distance='" + distance + '\'' +
                ", oilFee='" + oilFee + '\'' +
                ", now='" + now + '\'' +
                ", startFee='" + startFee + '\'' +
                ", overAll='" + overAll + '\'' +
                ", distanceFee='" + distanceFee + '\'' +
                '}';
    }

    public String getTotal() {
        return total;
    }

    public String getDuration() {
        return duration;
    }

    public String getLongFee() {
        return longFee;
    }

    public String getDistance() {
        return distance;
    }

    public String getOilFee() {
        return oilFee;
    }

    public String getNow() {
        return now;
    }

    public String getStartFee() {
        return startFee;
    }

    public String getOverAll() {
        return overAll;
    }

    public String getDistanceFee() {
        return distanceFee;
    }
}
