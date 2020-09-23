package com.hemaapp.hm_FrameWork.newnet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONObject;

import java.util.ArrayList;

import xtom.frame.XtomConfig;
import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;
import xtom.frame.exception.HttpException;

/**
 * 基础网络请求工具类 取代xtomNetWorker
 * created by Torres
 * 2018年11月16日14:52:51
 */
public class BaseNetWorkerNew extends XtomObject {
    protected static final int SUCCESS = -1;
    public static final int FAILED_HTTP = -2;
    public static final int FAILED_DATAPARSE = -3;
    public static final int FAILED_NONETWORK = -4;
    private static final int BEFORE = -5;
    private Context context;
    private BaseNetWorkerNew.EventHandler eventHandler;
    private BaseNetWorkerNew.NetThread netThread;
    private BaseNetWorkerNew.OnTaskExecuteListener onTaskExecuteListener;

    public BaseNetWorkerNew(Context mContext) {
        Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            this.eventHandler = new BaseNetWorkerNew.EventHandler(this, looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            this.eventHandler = new BaseNetWorkerNew.EventHandler(this, looper);
        } else {
            this.eventHandler = null;
        }

        this.context = mContext.getApplicationContext();
    }

    public void executeTask(BaseNetTask task) {
        if (this.hasNetWork()) {
            synchronized (this) {
                if (this.netThread == null) {
                    this.netThread = new BaseNetWorkerNew.NetThread(task);
                    this.netThread.start();
                    this.log_d("网络线程不存在或已执行完毕,开启新线程：" + this.netThread.getName());
                } else {
                    this.log_d(this.netThread.getName() + "执行中,添加网络任务");
                    this.netThread.addTask(task);
                }
            }
        } else if (this.onTaskExecuteListener != null) {
            this.onTaskExecuteListener.onPostExecute(this, task);
            this.onTaskExecuteListener.onExecuteFailed(this, task, -4);
        }

    }

    public boolean isNetTasksFinished() {
        synchronized (this) {
            return this.netThread == null || this.netThread.tasks.size() <= 0;
        }
    }

    public void cancelTasks() {
        synchronized (this) {
            if (this.netThread != null) {
                this.netThread.cancelTasks();
            }
        }
    }

    public boolean hasNetWork() {
        @SuppressLint("WrongConstant") ConnectivityManager con = (ConnectivityManager) this.context.getSystemService("connectivity");
        NetworkInfo info = con.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public Context getContext() {
        return this.context;
    }

    public BaseNetWorkerNew.OnTaskExecuteListener getOnTaskExecuteListener() {
        return this.onTaskExecuteListener;
    }

    public void setOnTaskExecuteListener(BaseNetWorkerNew.OnTaskExecuteListener onTaskExecuteListener) {
        this.onTaskExecuteListener = onTaskExecuteListener;
    }

    private static class EventHandler extends Handler {
        private BaseNetWorkerNew netWorker;

        public EventHandler(BaseNetWorkerNew netWorker, Looper looper) {
            super(looper);
            this.netWorker = netWorker;
        }

        private BaseNetWorkerNew.OnTaskExecuteListener getOnTaskExecuteListener() {
            return this.netWorker.getOnTaskExecuteListener();
        }

        public void handleMessage(Message msg) {
            BaseNetWorkerNew.OnTaskExecuteListener listener = this.getOnTaskExecuteListener();
            if (listener != null) {
                BaseNetWorkerNew.TR<BaseNetTask, Object> result = (BaseNetWorkerNew.TR) msg.obj;
                switch (msg.what) {
                    case -5:
                        listener.onPreExecute(this.netWorker, (BaseNetTask) result.getTask());
                        break;
                    case -4:
                    default:
                        listener.onPostExecute(this.netWorker, (BaseNetTask) result.getTask());
                        break;
                    case -3:
                        listener.onExecuteFailed(this.netWorker, (BaseNetTask) result.getTask(), -3);
                        listener.onPostExecute(this.netWorker, (BaseNetTask) result.getTask());
                        break;
                    case -2:
                        listener.onExecuteFailed(this.netWorker, (BaseNetTask) result.getTask(), -2);
                        listener.onPostExecute(this.netWorker, (BaseNetTask) result.getTask());
                        break;
                    case -1:
                        listener.onExecuteSuccess(this.netWorker, (BaseNetTask) result.getTask(), result.getResult());
                        listener.onPostExecute(this.netWorker, (BaseNetTask) result.getTask());
                }
            }

            super.handleMessage(msg);
        }
    }

    private class NetThread extends Thread {
        private ArrayList<BaseNetTask> tasks = new ArrayList();
        private boolean isRun = true;

        NetThread(BaseNetTask task) {
            this.tasks.add(task);
            this.setName("网络线程(" + this.getName() + ")");
        }

        void addTask(BaseNetTask task) {
            BaseNetWorkerNew var2 = BaseNetWorkerNew.this;
            synchronized (BaseNetWorkerNew.this) {
                this.tasks.add(task);
            }
        }

        void cancelTasks() {
            BaseNetWorkerNew var1 = BaseNetWorkerNew.this;
            synchronized (BaseNetWorkerNew.this) {
                this.tasks.clear();
                BaseNetWorkerNew.this.netThread = null;
                this.isRun = false;
            }
        }

        boolean isHaveTask() {
            return this.tasks.size() > 0;
        }

        public void run() {
            BaseNetWorkerNew.this.log_d(this.getName() + "开始执行");

            while (this.isRun) {
                BaseNetWorkerNew var1 = BaseNetWorkerNew.this;
                synchronized (BaseNetWorkerNew.this) {
                    if (!this.isHaveTask()) {
                        this.isRun = false;
                        BaseNetWorkerNew.this.netThread = null;
                        break;
                    }
                }

                BaseNetTask currTask = (BaseNetTask) this.tasks.get(0);
                BaseNetWorkerNew.TR<BaseNetTask, Object> tr = new BaseNetWorkerNew.TR<BaseNetTask, Object>();
//                TR<BaseNetTask, Object> tr =this.new TR((BaseNetWorkerNew.TR)null);
                tr.setTask(currTask);
                this.beforeDoTask(tr);
                Message mess = BaseNetWorkerNew.this.eventHandler.obtainMessage();
                this.doTask(tr, mess);
            }

            BaseNetWorkerNew.this.log_d(this.getName() + "执行完毕");
        }

        private void beforeDoTask(BaseNetWorkerNew.TR<BaseNetTask, Object> result) {
            Message before = new Message();
            before.what = -5;
            before.obj = result;
            BaseNetWorkerNew.this.eventHandler.sendMessage(before);
        }

        private void doTask(BaseNetWorkerNew.TR<BaseNetTask, Object> result, Message mess) {
            BaseNetTask task = (BaseNetTask) result.getTask();
            BaseNetWorkerNew.this.log_d("Do task !!!Try " + (task.getTryTimes() + 1));
            BaseNetWorkerNew.this.log_d("The Task Description: " + task.getDescription());

            try {
                Object object;
                JSONObject jsonObject;
                if (task.getFiles() == null) {
                    jsonObject = BaseHttpUtil.sendPOSTForJSONObject(task.getPath(), task.getParams(), XtomConfig.ENCODING);
                    object = task.parse(jsonObject);
                } else {
                    jsonObject = BaseHttpUtil.sendPOSTWithFilesForJSONObject(task.getPath(), task.getFiles(), task.getParams(), XtomConfig.ENCODING);
                    object = task.parse(jsonObject);
                }

                mess.obj = result.put(task, object);
                mess.what = -1;
                this.tasks.remove(task);
                BaseNetWorkerNew.this.eventHandler.sendMessage(mess);
            } catch (HttpException var6) {
                this.tryAgain(task, -2, mess, result);
            } catch (DataParseException var7) {
                this.tryAgain(task, -3, mess, result);
            }

        }

        private void tryAgain(BaseNetTask task, int type, Message mess, BaseNetWorkerNew.TR<BaseNetTask, Object> result) {
            task.setTryTimes(task.getTryTimes() + 1);
            if (task.getTryTimes() >= XtomConfig.TRYTIMES_HTTP) {
                mess.what = type;
                mess.obj = result;
                this.tasks.remove(task);
                BaseNetWorkerNew.this.eventHandler.sendMessage(mess);
            }

        }
    }

    public interface OnTaskExecuteListener {
        void onPreExecute(BaseNetWorkerNew var1, BaseNetTask var2);

        void onPostExecute(BaseNetWorkerNew var1, BaseNetTask var2);

        void onExecuteSuccess(BaseNetWorkerNew var1, BaseNetTask var2, Object var3);

        void onExecuteFailed(BaseNetWorkerNew var1, BaseNetTask var2, int var3);
    }

    private class TR<Task, Result> {
        private Task t;
        private Result r;

        private TR() {
        }


        public BaseNetWorkerNew.TR<Task, Result> put(Task t, Result r) {
            this.setTask(t);
            this.setResult(r);
            return this;
        }

        public void setTask(Task t) {
            this.t = t;
        }

        public void setResult(Result r) {
            this.r = r;
        }

        public Task getTask() {
            return this.t;
        }

        public Result getResult() {
            return this.r;
        }
    }
}
