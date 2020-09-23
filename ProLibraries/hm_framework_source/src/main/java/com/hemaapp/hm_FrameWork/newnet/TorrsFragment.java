package com.hemaapp.hm_FrameWork.newnet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemaapp.hm_FrameWork.ToastUtil;

import java.util.Iterator;
import java.util.List;

import xtom.frame.XtomFragment;
import xtom.frame.image.cache.XtomImageCache;
import xtom.frame.image.load.XtomImageWorker;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomLogger;

public abstract class TorrsFragment extends Fragment {
    protected static final String NO_NETWORK = "无网络连接，请检查网络设置。";
    protected static final String FAILED_GETDATA_HTTP = "请求异常。";
    protected static final String FAILED_GETDATA_DATAPARSE = "数据异常。";
    public String TAG = this.getLogTag();
    public XtomImageWorker imageWorker;
    private BaseNetWorkerNew netWorker;
    protected Intent mIntent;
    protected View rootView;
    private int rootViewId;
    private static Fragment currForResultFragment;

    protected TorrsFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.imageWorker = new XtomImageWorker(this.getActivity());
        if (this.rootView == null) {
            this.rootView = LayoutInflater.from(this.getActivity()).inflate(this.rootViewId, (ViewGroup)null, false);
        }

        this.init();
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (currForResultFragment == null) {
            currForResultFragment = this;
        }

        if (this.getParentFragment() != null) {
            this.getParentFragment().startActivityForResult(intent, requestCode);
        } else {
            super.startActivityForResult(intent, requestCode);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (currForResultFragment != null && !currForResultFragment.equals(this)) {
            currForResultFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        currForResultFragment = null;
    }

    public void onHiddenChanged(boolean hidden) {
        List<Fragment> fragments = this.getChildFragmentManager().getFragments();
        if (fragments != null) {
            Iterator var4 = fragments.iterator();

            while(var4.hasNext()) {
                Fragment fragment = (Fragment)var4.next();
                fragment.onHiddenChanged(hidden);
            }
        }

        super.onHiddenChanged(hidden);
    }

    public void onDestroy() {
        super.onDestroy();
        this.stopNetThread();
        if (this.imageWorker != null) {
            this.imageWorker.clearTasks();
        }

        this.recyclePics();
        ToastUtil.cancelAllToast();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.rootView;
    }

    private void init() {
        this.findView();
        this.setListener();
    }

    public void setContentView(int layoutResID) {
        this.rootViewId = layoutResID;
    }

    public void setContentView(View v) {
        this.rootView = v;
    }

    public void getDataFromServer(BaseNetTask task) {
        if (this.netWorker == null) {
            this.netWorker = new BaseNetWorkerNew(this.getActivity());
            this.netWorker.setOnTaskExecuteListener(new OnTaskExecuteListener());
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
        ToastUtil.showLongToast(this.getActivity(), "无网络连接，请检查网络设置。");
    }

    protected abstract void findView();

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
                ToastUtil.showLongToast(this.getActivity(), "数据异常。TASKID:" + taskID);
                break;
            case -2:
                ToastUtil.showLongToast(this.getActivity(), "请求异常。TASKID:" + taskID);
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

    private void recyclePics() {
        XtomImageCache.get().reMoveCacheInMemByObj(this);
        XtomImageCache.get().recyclePics();
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
        @SuppressLint("WrongConstant") ConnectivityManager con = (ConnectivityManager)this.getActivity().getSystemService("connectivity");
        NetworkInfo info = con.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    private class OnTaskExecuteListener implements BaseNetWorkerNew.OnTaskExecuteListener {
        private OnTaskExecuteListener() {
        }

        public void onPreExecute(BaseNetWorkerNew netWorker, BaseNetTask task) {
            TorrsFragment.this.callBeforeDataBack(task);
        }

        public void onPostExecute(BaseNetWorkerNew netWorker, BaseNetTask task) {
            TorrsFragment.this.callAfterDataBack(task);
        }

        public void onExecuteSuccess(BaseNetWorkerNew netWorker, BaseNetTask task, Object result) {
            TorrsFragment.this.callBackForGetDataSuccess(task, result);
        }

        public void onExecuteFailed(BaseNetWorkerNew netWorker, BaseNetTask task, int failedType) {
            switch(failedType) {
                case -4:
                    TorrsFragment.this.noNetWork(task);
                    break;
                case -3:
                case -2:
                    TorrsFragment.this.callBackForGetDataFailed(failedType, task);
            }

        }
    }
}
