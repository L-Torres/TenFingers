package com.hemaapp.hm_FrameWork;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: ToastUtil
 * Author: wangyuxia
 * Date: 2020/4/11 20:20
 * Description: 新的Toast类
 */
public class ToastUtil {

    private static Toast mLongToast;
    private static Toast mShortToast;
    private static Handler mHandler;

    static {
        Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            mHandler = new Handler(looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            mHandler = new Handler(looper);
        } else {
            mHandler = null;
        }

    }

    public ToastUtil() {
    }

    public static void showLongToast(Context context, String msg, long delayMillis) {
        showLongToast(context, msg);
        cancel(delayMillis, 1);
    }

    public static void showShortToast(Context context, String msg, long delayMillis) {
        showShortToast(context, msg);
        cancel(delayMillis, 0);
    }

    public static void showLongToast(Context context, int msg, long delayMillis) {
        showLongToast(context, msg);
        cancel(delayMillis, 1);
    }

    public static void showShortToast(Context context, int msg, long delayMillis) {
        showShortToast(context, msg);
        cancel(delayMillis, 0);
    }

    public static void showLongToast(Context context, String msg) {
        showLong(context, 0, msg);
    }

    public static void showShortToast(Context context, String msg) {
        showShort(context, 0, msg);
    }

    public static void showLongToast(Context context, int msg) {
        showLong(context, msg, (String)null);
    }

    public static void showShortToast(Context context, int msg) {
        showShort(context, msg, (String)null);
    }

    private static synchronized void showLong(Context context, int msg, String msgs) {
        if (msgs == null) {
            try {
                msgs = context.getResources().getString(msg);
            } catch (Exception var4) {
                msgs = "吐司信息为空或资源不存在";
            }
        }

        mHandler.post(new ToastUtil.LongRunable(context, msgs));
    }

    private static synchronized void showShort(Context context, int msg, String msgs) {
        if (msgs == null) {
            try {
                msgs = context.getResources().getString(msg);
            } catch (Exception var4) {
                msgs = "吐司信息为空或资源不存在";
            }
        }

        mHandler.post(new ToastUtil.ShortRunable(context, msgs));
    }

    private static void cancel(long delayMillis, final int style) {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                switch(style) {
                    case 0:
                        ToastUtil.cancelShortToast();
                        break;
                    case 1:
                        ToastUtil.cancelLongToast();
                }

            }
        }, delayMillis);
    }

    public static void cancelAllToast() {
        cancelLongToast();
        cancelShortToast();
    }

    public static void cancelLongToast() {
        if (mLongToast != null) {
            mLongToast.cancel();
            mLongToast = null;
        }

    }

    public static void cancelShortToast() {
        if (mShortToast != null) {
            mShortToast.cancel();
            mShortToast = null;
        }

    }

    private static class LongRunable implements Runnable {
        Context context;
        String msgs;

        public LongRunable(Context context, String msgs) {
            this.context = context;
            this.msgs = msgs;
        }

        public void run() {
            if (ToastUtil.mLongToast != null) {
                ToastUtil.mLongToast.setText(this.msgs);
            } else {
                ToastUtil.mLongToast = Toast.makeText(this.context, this.msgs, 1);
            }
            ToastUtil.mLongToast.setGravity(Gravity.CENTER, 0, 0);
            ToastUtil.mLongToast.show();
        }
    }

    private static class ShortRunable implements Runnable {
        Context context;
        String msgs;

        public ShortRunable(Context context, String msgs) {
            this.context = context;
            this.msgs = msgs;
        }

        public void run() {
            if (ToastUtil.mShortToast != null) {
                ToastUtil.mShortToast.setText(this.msgs);
            } else {
                ToastUtil.mShortToast = Toast.makeText(this.context, this.msgs, 0);
            }
            ToastUtil.mShortToast.setGravity(Gravity.CENTER, 0, 0);
            ToastUtil.mShortToast.show();
        }
    }
    
}
