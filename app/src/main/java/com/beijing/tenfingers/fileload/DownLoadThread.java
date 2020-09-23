package com.beijing.tenfingers.fileload;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

import xtom.frame.XtomConfig;
import xtom.frame.util.XtomLogger;

public class DownLoadThread extends Thread {
    private static final String TAG = "DownLoadThread";
    private static final int BUFFER_SIZE = 1024;
    private Context context;
    private URL url;
    private File file;
    private ThreadInfo threadInfo;
    private int downloadLength = 0;
    private boolean finished = false;
    private boolean failed = false;
    private boolean stop = false;
    private DownLoadDBHelper dbHelper;

    public DownLoadThread(Context context, URL url, File file, ThreadInfo threadInfo) {
        this.context = context;
        this.url = url;
        this.file = file;
        this.threadInfo = threadInfo;
        int start = threadInfo.getStartPosition();
        int curr = threadInfo.getCurrentPosition();
        int end = threadInfo.getEndPosition();
        this.downloadLength = curr - start + 1;
        String path = url.toString();
        String threadName = "[文件(" + path + ")下载(" + start + "-" + end + ")线程:" + threadInfo.getThreadID() + "]";
        this.setName(threadName);
    }

    private boolean isThreadFinished() {
        int curr = this.threadInfo.getCurrentPosition();
        int end = this.threadInfo.getEndPosition();
        boolean isFinished = end != 0 && curr == end;
        return isFinished;
    }

    public void run() {
        if (this.isThreadFinished()) {
            XtomLogger.d("DownLoadThread", this.getName() + "已经完成");
            this.finished = true;
        } else {
            XtomLogger.d("DownLoadThread", this.getName() + "开始执行");
            this.dbHelper = DownLoadDBHelper.getInstance(this.context);
            int startPosition = this.threadInfo.getStartPosition();
            int endPosition = this.threadInfo.getEndPosition();
            int currentPosition = this.threadInfo.getCurrentPosition();
            BufferedInputStream bis = null;
            RandomAccessFile fos = null;
            byte[] buf = new byte[8*1024];
            URLConnection con = null;

            try {
                con = this.url.openConnection();
                con.setConnectTimeout(XtomConfig.TIMEOUT_CONNECT_FILE);
                con.setReadTimeout(XtomConfig.TIMEOUT_READ_FILE);
                con.setAllowUserInteraction(true);
                con.setRequestProperty("Range", "bytes=" + currentPosition + "-" + endPosition);
                fos = new RandomAccessFile(this.file, "rw");
                fos.seek((long)currentPosition);
                bis = new BufferedInputStream(con.getInputStream());
                --currentPosition;

                while(currentPosition < endPosition && !this.stop) {
                    int len = bis.read(buf, 0, 6*1024);
                    if (len == -1) {
                        break;
                    }

                    fos.write(buf, 0, len);
                    currentPosition += len;
                    if (currentPosition > endPosition) {
                        currentPosition = endPosition;
                    }

                    this.downloadLength = currentPosition - startPosition + 1;
                    this.threadInfo.setCurrentPosition(currentPosition);
                    this.dbHelper.updateThreadInfo(this.threadInfo);
                }

                if (!this.stop) {
                    this.finished = true;
                    XtomLogger.d("DownLoadThread", this.getName() + "执行成功");
                } else {
                    XtomLogger.d("DownLoadThread", this.getName() + "执行中断");
                }
            } catch (Exception var17) {
                XtomLogger.d("DownLoadThread", this.getName() + "执行失败");
                this.failed = true;
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException var16) {
                }

            }

            this.dbHelper.close();
        }
    }

    public boolean isStop() {
        return this.stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isFailed() {
        return this.failed;
    }

    public int getDownloadLength() {
        return this.downloadLength;
    }
}
