package com.beijing.tenfingers.fileload;

import xtom.frame.XtomObject;

public class FileInfo extends XtomObject {
    private int id;
    private String downPath;
    private String savePath;
    private int threadCount;
    private int contentLength;
    private int currentLength;

    FileInfo(int id, String downPath, String savePath, int threadCount, int contentLength, int currentLength) {
        this.id = id;
        this.downPath = downPath;
        this.savePath = savePath;
        this.threadCount = threadCount;
        this.contentLength = contentLength;
        this.currentLength = currentLength;
    }

    public int getId() {
        return this.id;
    }

    public String getDownPath() {
        return this.downPath;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public int getCurrentLength() {
        return this.currentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }
}
