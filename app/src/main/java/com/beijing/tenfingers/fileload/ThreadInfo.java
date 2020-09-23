package com.beijing.tenfingers.fileload;

import xtom.frame.XtomObject;

public class ThreadInfo extends XtomObject {
    private int id;
    private int fileID;
    private int threadID;
    private int startPosition;
    private int endPosition;
    private int currentPosition;

    public ThreadInfo(int id, int fileID, int threadID, int startPosition, int endPosition, int currentPosition) {
        this.id = id;
        this.fileID = fileID;
        this.threadID = threadID;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.currentPosition = currentPosition;
    }

    public int getId() {
        return this.id;
    }

    public int getFileID() {
        return this.fileID;
    }

    public int getThreadID() {
        return this.threadID;
    }

    public int getStartPosition() {
        return this.startPosition;
    }

    public int getEndPosition() {
        return this.endPosition;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

}
