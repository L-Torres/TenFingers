package com.beijing.tenfingers.bean;

import java.io.Serializable;

public class TimeData implements Serializable {

    private static final long serialVersionUID = 1265819772736012294L;
    private String advance;
    private String advanceTime;
    private String timeId;

    public TimeData() {
    }

    public TimeData( String advance, String advanceTime, String timeId) {
        this.advance = advance;
        this.advanceTime = advanceTime;
        this.timeId = timeId;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(String advanceTime) {
        this.advanceTime = advanceTime;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }
}
