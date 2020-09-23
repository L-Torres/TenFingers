package com.hemaapp.hm_FrameWork.newnet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import com.hemaapp.hm_FrameWork.ToastUtil;

import xtom.frame.XtomActivityManager;
import xtom.frame.XtomIntent;
import xtom.frame.image.cache.XtomImageCache;
import xtom.frame.image.load.XtomImageWorker;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomLogger;

public abstract class TorresActivity extends AppCompatActivity {
    protected static final String NO_NETWORK = "无网络连接，请检查网络设置。";
    protected static final String FAILED_GETDATA_HTTP = "请求异常。";
    protected static final String FAILED_GETDATA_DATAPARSE = "数据异常。";
    protected boolean isDestroyed = false;
    private String TAG = this.getLogTag();
    protected Activity mContext;
    private BaseNetWorkerNew netWorker;
    public XtomImageWorker imageWorker;
    protected Intent mIntent;
    protected InputMethodManager mInputMethodManager;
    private LayoutInflater mLayoutInflater;

    protected TorresActivity() {
    }


    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XtomActivityManager.add(this);
        this.mContext = this;
        this.imageWorker = new XtomImageWorker(this.mContext);
        this.mIntent = this.getIntent();
        this.mInputMethodManager = (InputMethodManager)this.getSystemService("input_method");
        this.init(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("intent", new XtomIntent(this.mIntent));
        super.onSaveInstanceState(outState);
    }

    protected void onDestroy() {
        this.destroy();
        super.onDestroy();
        this.recyclePics();
    }

    private void destroy() {
        this.isDestroyed = true;
        XtomActivityManager.remove(this);
        this.stopNetThread();
        if (this.imageWorker != null) {
            this.imageWorker.clearTasks();
        }

        ToastUtil.cancelAllToast();
    }

    public void finish() {
        this.destroy();
        super.finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case 4:
                if (this.onKeyBack()) {
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            case 82:
                if (this.onKeyMenu()) {
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getSerializable("intent") != null) {
            this.mIntent = (XtomIntent)savedInstanceState.getSerializable("intent");
        }
        this.getExras();
        this.findView();
        this.setListener();
    }

    public void getDataFromServer(BaseNetTask task) {
        if (this.netWorker == null) {
            this.netWorker = new BaseNetWorkerNew(this.mContext);
            this.netWorker.setOnTaskExecuteListener(new TorresActivity.OnTaskExecuteListener((TorresActivity.OnTaskExecuteListener)null));
        }
        this.netWorker.executeTask(task);
    }

    protected void noNetWork(BaseNetTask task) {
        this.noNetWork(task.getId());
    }

    protected void noNetWork(int taskID) {
        this.noNetWork();
    }

    protected void noNetWork() {
        ToastUtil.showLongToast(this.mContext, "无网络连接，请检查网络设置。");
    }

    protected abstract boolean onKeyBack();

    protected abstract boolean onKeyMenu();

    protected abstract void findView();

    protected abstract void getExras();

    protected abstract void setListener();

    protected abstract void callBeforeDataBack(BaseNetTask var1);

    protected abstract void callAfterDataBack(BaseNetTask var1);

    protected abstract void callBackForGetDataSuccess(BaseNetTask var1, Object var2);

    public void fresh() {
    }

    protected void callBackForGetDataFailed(int type, BaseNetTask netTask) {
        this.callBackForGetDataFailed(type, netTask.getId());
    }

    protected void callBackForGetDataFailed(int type, int taskID) {
        switch(type) {
            case -3:
                ToastUtil.showLongToast(this.mContext, "数据异常。TASKID:" + taskID);
                break;
            case -2:
                ToastUtil.showLongToast(this.mContext, "请求异常。TASKID:" + taskID);
        }

    }

    protected void log_v(String msg) {
        XtomLogger.v(this.TAG, msg);
    }

    protected void log_d(String msg) {
        XtomLogger.d(this.TAG, msg);
    }

    protected void log_i(String msg) {
        XtomLogger.i(this.TAG, msg);
    }

    protected void log_w(String msg) {
        XtomLogger.w(this.TAG, msg);
    }

    protected void log_e(String msg) {
        XtomLogger.e(this.TAG, msg);
    }

    protected void println(Object msg) {
        XtomLogger.println(msg);
    }

    protected boolean isNull(String str) {
        return XtomBaseUtil.isNull(str);
    }

    protected boolean isNetTasksFinished() {
        return this.netWorker == null || this.netWorker.isNetTasksFinished();
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater == null ? (this.mLayoutInflater = LayoutInflater.from(this)) : this.mLayoutInflater;
    }

    private void recyclePics() {
        XtomImageCache.getInstance(this).reMoveCacheInMemByObj(this);
        XtomImageCache.getInstance(this).recyclePics();
    }

    private void stopNetThread() {
        if (this.netWorker != null) {
            this.netWorker.cancelTasks();
        }

    }

    private String getLogTag() {
        return this.getClass().getSimpleName();
    }

    public boolean hasNetWork() {
        @SuppressLint("WrongConstant") ConnectivityManager con = (ConnectivityManager)this.getSystemService("connectivity");
        NetworkInfo info = con.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    private class OnTaskExecuteListener implements BaseNetWorkerNew.OnTaskExecuteListener {
        private OnTaskExecuteListener() {
        }

        public OnTaskExecuteListener(OnTaskExecuteListener onTaskExecuteListener) {

        }

        public void onPreExecute(BaseNetWorkerNew netWorker, BaseNetTask task) {
            TorresActivity.this.callBeforeDataBack(task);
        }

        public void onPostExecute(BaseNetWorkerNew netWorker, BaseNetTask task) {
            TorresActivity.this.callAfterDataBack(task);
        }

        public void onExecuteSuccess(BaseNetWorkerNew netWorker, BaseNetTask task, Object result) {
            TorresActivity.this.callBackForGetDataSuccess(task, result);
        }

        public void onExecuteFailed(BaseNetWorkerNew netWorker, BaseNetTask task, int failedType) {
            switch(failedType) {
                case -4:
                    TorresActivity.this.noNetWork(task);
                    break;
                case -3:
                case -2:
                    TorresActivity.this.callBackForGetDataFailed(failedType, task);
            }

        }
    }
}
