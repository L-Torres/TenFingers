package com.beijing.tenfingers.fileload;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import xtom.frame.XtomObject;

/**
 * 新的下载
 * 2020年5月7日10:30:44
 * by LeeJian
 */
public class XtomFileDownLoader extends XtomObject {
    private static final int START = 0;
    private static final int LOADING = 1;
    private static final int STOP = 2;
    private static final int FAILED = 3;
    private static final int SUCCESS = 4;
    private int threadCount;
    private Context context;
    private String downPath;
    private String savePath;
    private EventHandler eventHandler;
    private FileInfo fileInfo;
    private ControlThread controlThread;
    private XtomDownLoadListener xtomDownLoadListener;

    private XtomFileDownLoader() {
        this.threadCount = 1;
        Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            this.eventHandler = new EventHandler(this, looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            this.eventHandler = new EventHandler(this, looper);
        } else {
            this.eventHandler = null;
        }

    }

    public XtomFileDownLoader(Context context, String downPath, String savePath) {
        this();
        this.context = context;
        this.downPath = downPath;
        this.savePath = savePath;
    }

    public void start() {
        if (this.isLoading()) {
            this.log_d("正在下载,请勿重复操作");
        } else {
            this.controlThread = new ControlThread();
            this.controlThread.start();
        }
    }

    public boolean isLoading() {
        return this.controlThread != null && this.controlThread.isAlive();
    }

    public void stop() {
        if (this.controlThread != null) {
            this.controlThread.stopLoad();
        }

    }

    public boolean isFileLoaded() {
        File file = new File(this.savePath);
        boolean isLoaded = file.exists();
        FileInfo fileInfo = this.getFileInfo();
        if (isLoaded) {
            if (fileInfo != null) {
                int curr = fileInfo.getCurrentLength();
                int cont = fileInfo.getContentLength();
                isLoaded = cont != 0 && curr == cont;
            } else {
                isLoaded = false;
            }
        } else if (fileInfo != null) {
            DownLoadDBHelper dbHelper = DownLoadDBHelper.getInstance(this.context);
            dbHelper.deleteThreadInfos(fileInfo.getId());
            dbHelper.close();
        }

        return isLoaded;
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public FileInfo getFileInfo() {
        if (this.fileInfo == null) {
            DownLoadDBHelper dbHelper = DownLoadDBHelper.getInstance(this.context);
            this.fileInfo = dbHelper.getFileInfo(this.downPath, this.savePath, this.threadCount);
            dbHelper.close();
        }

        return this.fileInfo;
    }

    public String getDownPath() {
        return this.downPath;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public XtomDownLoadListener getXtomDownLoadListener() {
        return this.xtomDownLoadListener;
    }

    public void setXtomDownLoadListener(XtomDownLoadListener xtomDownLoadListener) {
        this.xtomDownLoadListener = xtomDownLoadListener;
    }

    private class ControlThread extends Thread {
        private DownLoadDBHelper dbHelper;
        private ArrayList<DownLoadThread> downLoadThreads;
        int currentLength;
        boolean finished;
        boolean failed;
        boolean stoped;

        private ControlThread() {
            this.downLoadThreads = new ArrayList();
            this.currentLength = 0;
        }

        private void stopLoad() {
            Iterator var2 = this.downLoadThreads.iterator();

            while(var2.hasNext()) {
                DownLoadThread thread = (DownLoadThread)var2.next();
                thread.setStop(true);
            }

        }

        private FileInfo getFileInfo(int contentLenght) {
            FileInfo fileInfo = this.dbHelper.getFileInfo(XtomFileDownLoader.this.downPath, XtomFileDownLoader.this.savePath, XtomFileDownLoader.this.threadCount);
            if (fileInfo == null) {
                fileInfo = new FileInfo(0, XtomFileDownLoader.this.downPath, XtomFileDownLoader.this.savePath, XtomFileDownLoader.this.threadCount, contentLenght, 0);
                this.dbHelper.insertFileInfo(fileInfo);
                fileInfo = this.dbHelper.getFileInfo(XtomFileDownLoader.this.downPath, XtomFileDownLoader.this.savePath, XtomFileDownLoader.this.threadCount);
            }

            return fileInfo;
        }

        private ThreadInfo getThreadInfo(int fileID, int threadID, int startPosition, int endPosition) {
            ThreadInfo info = this.dbHelper.getThreadInfo(fileID, threadID);
            if (info == null) {
                info = new ThreadInfo(0, fileID, threadID, startPosition, endPosition, startPosition);
                this.dbHelper.insertThreadInfo(info);
                info = this.dbHelper.getThreadInfo(fileID, threadID);
            }

            return info;
        }

        public void run() {
            this.dbHelper = DownLoadDBHelper.getInstance(XtomFileDownLoader.this.context);
            XtomFileDownLoader.this.fileInfo = this.getFileInfo(0);
            if (XtomFileDownLoader.this.isFileLoaded()) {
                XtomFileDownLoader.this.eventHandler.sendEmptyMessage(4);
            } else {
                XtomFileDownLoader.this.eventHandler.sendEmptyMessage(0);
                try {
                    URL url = new URL(XtomFileDownLoader.this.downPath);
                    URLConnection conn = url.openConnection();
                    int contentLength = conn.getContentLength();
                    if (contentLength > 0) {
                        XtomFileDownLoader.this.fileInfo.setContentLength(contentLength);
                        this.dbHelper.updateFileInfo(XtomFileDownLoader.this.fileInfo);
                    }

                    File file = new File(XtomFileDownLoader.this.savePath);
                    File filedir = file.getParentFile();
                    if (!filedir.exists()) {
                        filedir.mkdirs();
                    }

                    int bs;
                    int endPosition;
                    if (!file.exists() && contentLength > 0) {
                        XtomFileDownLoader.this.log_d("开始创建临时文件");
                        long start = System.currentTimeMillis();
                        FileOutputStream fos = new FileOutputStream(file);
                        bs = contentLength % 1024;
                        endPosition = contentLength / 1024;
                        byte[] kbf = new byte[1024];
                        byte[] bf = new byte[1];
                        int i;
                        for(i = 0; i < endPosition; ++i) {
                            fos.write(kbf);
                        }
                        for(i = 0; i < bs; ++i) {
                            fos.write(bf);
                        }
                        fos.close();
                        long end = System.currentTimeMillis();
                       XtomFileDownLoader.this.log_d("结束创建临时文件,用时" + (end - start));
                    }

                    int threadLength = contentLength / XtomFileDownLoader.this.threadCount;
                    int remainLength = contentLength % XtomFileDownLoader.this.threadCount;
                    this.downLoadThreads.clear();

                    for(int ix = 0; ix < XtomFileDownLoader.this.threadCount; ++ix) {
                        bs = ix * threadLength;
                        endPosition = ix == XtomFileDownLoader.this.threadCount - 1 ? (ix + 1) * threadLength - 1 + remainLength : (ix + 1) * threadLength - 1;
                        ThreadInfo info = this.getThreadInfo(XtomFileDownLoader.this.fileInfo.getId(), ix, bs, endPosition);
                        DownLoadThread fdt = new DownLoadThread(XtomFileDownLoader.this.context, url, file, info);
                        this.downLoadThreads.add(fdt);
                    }

                    Iterator var20 = this.downLoadThreads.iterator();

                    while(var20.hasNext()) {
                        DownLoadThread thread = (DownLoadThread)var20.next();
                        thread.start();
                    }

                    for(boolean isRun = true; isRun;) {
                        this.checkThreads();
                        XtomFileDownLoader.this.fileInfo.setCurrentLength(this.currentLength);
                        this.dbHelper.updateFileInfo(XtomFileDownLoader.this.fileInfo);
                        XtomFileDownLoader.this.eventHandler.sendEmptyMessage(1);
                        if (this.failed) {
                            this.stopLoad();
                        }

                        if (this.stoped) {
                            if (this.failed) {
                                XtomFileDownLoader.this.eventHandler.sendEmptyMessage(3);
                            } else {
                                XtomFileDownLoader.this.eventHandler.sendEmptyMessage(2);
                            }

                            isRun = false;
                        }

                        if (this.finished) {
                            XtomFileDownLoader.this.eventHandler.sendEmptyMessage(4);
                            isRun = false;
                        }
                    }
                } catch (Exception var15) {
                    this.stopLoad();
                    XtomFileDownLoader.this.eventHandler.sendEmptyMessage(3);
                }
            }

            this.dbHelper.close();
        }

        private void checkThreads() {
            this.currentLength = 0;
            this.finished = true;
            this.stoped = true;
            this.failed = false;

            for(int i = 0; i < this.downLoadThreads.size(); ++i) {
                DownLoadThread thread = (DownLoadThread)this.downLoadThreads.get(i);
                this.currentLength += thread.getDownloadLength();
                if (!thread.isFinished()) {
                    this.finished = false;
                }

                if (!thread.isStop()) {
                    this.stoped = false;
                }

                if (thread.isFailed()) {
                    this.failed = true;
                }
            }

        }
    }

    private static class EventHandler extends Handler {
        private XtomFileDownLoader downLoader;

        public EventHandler(XtomFileDownLoader downLoader, Looper looper) {
            super(looper);
            this.downLoader = downLoader;
        }

        public void handleMessage(Message msg) {
            XtomDownLoadListener listener = this.downLoader.getXtomDownLoadListener();
            if (listener != null) {
                switch(msg.what) {
                    case 0:
                        listener.onStart(this.downLoader);
                        break;
                    case 1:
                        listener.onLoading(this.downLoader);
                        break;
                    case 2:
                        listener.onStop(this.downLoader);
                        break;
                    case 3:
                        listener.onFailed(this.downLoader);
                        break;
                    case 4:
                        listener.onSuccess(this.downLoader);
                }
            }

        }
    }

    public interface XtomDownLoadListener {
        void onStart(XtomFileDownLoader var1);

        void onSuccess(XtomFileDownLoader var1);

        void onFailed(XtomFileDownLoader var1);

        void onLoading(XtomFileDownLoader var1);

        void onStop(XtomFileDownLoader var1);
    }
}
