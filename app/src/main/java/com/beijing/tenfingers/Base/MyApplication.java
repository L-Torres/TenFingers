package com.beijing.tenfingers.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.OrderDetailActivity;
import com.beijing.tenfingers.bean.AdvanceDetail;
import com.beijing.tenfingers.bean.City;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.bean.QuestionList;
import com.beijing.tenfingers.bean.SysInitInfo;
import com.beijing.tenfingers.bean.TeaDetail;
import com.beijing.tenfingers.bean.User;
import com.beijing.tenfingers.db.SysInfoDBHelper;
import com.beijing.tenfingers.db.UserDBHelper;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.push.PushModel;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaApplication;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomConfig;
import xtom.frame.fileload.XtomFileDownLoader;
import xtom.frame.util.XtomLogger;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * Created by Torres on 2016/11/8.
 */

public class MyApplication extends HemaApplication {

    private static final String TAG = MyApplication.class.getSimpleName();
    private SysInitInfo sysInitInfo;// 系统初始化信息
    private User user;
    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    private static DemoHandler handler;
//    private City cityInfo;
//    public static ArrayList<CartList> SimpleOrNot=new ArrayList<>();//是一个商品还是多个商品
    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    private static XtomFileDownLoader downLoader;
    private City cityInfo;
    private static String payType="";//支付类型 0普通订单支付和尾款支付  1预购定金支付 2预购定金支付 需停留在订单详情页
    private static String flag = "";
    public static ArrayList<PopBean> lists = new ArrayList<>();
    public static QuestionList question;

    public static TeaDetail getTeaDetail() {
        return teaDetail;
    }

    public static void setTeaDetail(TeaDetail teaDetail) {
        MyApplication.teaDetail = teaDetail;
    }

    public static void setFlag(String flag){
        MyApplication.flag = flag;
    }

    public static String getFlag() {
        return flag;
    }

    public static TeaDetail teaDetail;
    public static AdvanceDetail advanceDetail;

    public static AdvanceDetail getAdvanceDetail(){
        return advanceDetail;
    }

    public static void setAdvanceDetail(AdvanceDetail advanceDetail) {
        MyApplication.advanceDetail = advanceDetail;
    }


    public static String getPayType() {
        return payType;
    }

    public static void setPayType(String payType) {
        MyApplication.payType = payType;
    }

    @SuppressLint("MissingSuperCall")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        application = this;

        ToastUtils.init(application);

        XtomConfig.LOG = MyConfig.DEBUG;
//        UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "");
        // 选用AUTO页面采集模式
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);


        String iow = XtomSharedPreferencesUtil.get(this, "imageload_onlywifi");
        XtomConfig.IMAGELOAD_ONLYWIFI = "true".equals(iow);
        XtomConfig.MAX_IMAGE_SIZE = 200;
        XtomConfig.DIGITAL_CHECK = true;
        XtomConfig.DATAKEY ="cef095aed1d49ee112b5fe0fbb30d288";
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        if (handler == null) {
            handler = new DemoHandler();
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                //通常情况下application与activity得到的resource虽然不是一个实例，但是displayMetrics是同一个实例，只需调用一次即可
                //为了面对一些不可预计的情况以及向上兼容，分别调用一次较为保险
                resetDensity(MyApplication.this, 750);
                resetDensity(activity, 750);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

    }

    private static String oaid;


    private static boolean isSupportOaid;

    public static String getOaid() {
        return oaid;
    }

    public static boolean isSupportOaid() {
        return isSupportOaid;
    }

    public static void setIsSupportOaid(boolean isSupportOaid) {
        MyApplication.isSupportOaid = isSupportOaid;
    }

    public void setCityInfo(City cityInfo) {
        this.cityInfo = cityInfo;
    }

    /**
     * 设置问题数据
     *
     * @param lists
     */
    public void setQuestion(ArrayList<PopBean> lists) {
        this.lists = lists;
    }

    /**
     * 获取问题数据
     *
     * @return
     */
    public ArrayList<PopBean> getQuestion() {
        return lists;
    }


    /**
     * 获取行政区列表
     *
     * @return
     */
    public City getCityInfo() {
        return cityInfo;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static class DemoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    if (demoActivity != null) {
//                        payloadData.append((String) msg.obj);
//                        payloadData.append("\n");
//                        if (GetuiSdkDemoActivity.tLogView != null) {
//                            GetuiSdkDemoActivity.tLogView.append(msg.obj + "\n");
//                        }
//                    }
                    break;

                case 1:
                    PushModel currentModel = (PushModel) msg.obj;
                    makeNotification(currentModel);
                    break;
            }
        }
    }
    public static NotificationCompat.Builder getONotificationBuidler(Context context) {
        String CHANNEL_ID = "push";
        String CHANNEL_NAME = "运营推送";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            // 通知才能正常弹出
            if(context != null){
                NotificationManager mNotificationManager = (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
        }
        return new NotificationCompat.Builder(context, CHANNEL_ID);
    }

    private static void makeNotification(PushModel pushModel) {
        EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_ORDER));
        int smallIcon = R.mipmap.ic_launcher;
        NotificationCompat.Builder mBuilder = getONotificationBuidler(application)
                .setSmallIcon(smallIcon)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT >= 26) {
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(application.getResources(), R.mipmap.ic_launcher));
        }
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.setContentTitle(pushModel.getTitle());
        mBuilder.setContentText(pushModel.getMsg());
        mBuilder.setTicker(pushModel.getMsg());
        mBuilder.setAutoCancel(true);

        int noti_mindflag = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
        mBuilder.setDefaults(noti_mindflag);
        if(application == null){
            return;
        }
        Intent intent2 = click(pushModel);
//        intent2.putExtra("noticeId", pushModel.getKeyId());
//        intent2.setAction("android.intent.action.oppopush");
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int notifyid = 0x42000000;
        PendingIntent contentIntent = PendingIntent.getActivity(application, notifyid,
                intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyid, notification);
    }

    @Override
    public void onLowMemory() {
        XtomLogger.i(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        XtomLogger.i(TAG, "onTerminate");
        super.onTerminate();
    }


    public static void setDownLoader(XtomFileDownLoader downLoader) {
        MyApplication.downLoader = downLoader;
    }

    public XtomFileDownLoader getDownLoader() {
        return downLoader;
    }

    /**
     * @return 当前用户
     */
    public User getUser() {
        if (user == null) {
            UserDBHelper helper = new UserDBHelper(this);
            String username = XtomSharedPreferencesUtil.get(this, "username");
            String user_id = XtomSharedPreferencesUtil.get(this, "user_id");
            flag = XtomSharedPreferencesUtil.get(getApplicationContext(), "flag");
//            if("0".equals(flag)){//验证码登录
//                username=username.substring(username.length()-4,username.length());
//            }else if("1".equals(flag)){//账号密码登录
////                username=username.substring(username.length()-4,username.length());
//            }

//            user = helper.select(username);
            user = helper.select(user_id);
            helper.close();
        }
        return user;
    }

    /**
     * 设置保存当前用户
     * <p>
     * 当前用户
     */
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            UserDBHelper helper = new UserDBHelper(this);
            helper.insertOrUpdate(user);
            helper.close();
        }
    }

    /**
     * @return 系统初始化信息
     */
    public SysInitInfo getSysInitInfo() {
        if (sysInitInfo == null) {
            SysInfoDBHelper helper = new SysInfoDBHelper(this);
            sysInitInfo = helper.select();
            helper.close();
        }
        return sysInitInfo;
    }

    /**
     * 设置保存系统初始化信息
     *
     * @param sysInitInfo 系统初始化信息
     */
    public void setSysInitInfo(SysInitInfo sysInitInfo) {
        this.sysInitInfo = sysInitInfo;
        if (sysInitInfo != null) {
            SysInfoDBHelper helper = new SysInfoDBHelper(this);
            helper.insertOrUpdate(sysInitInfo);
            helper.close();
        }
    }

    /**
     * 重新计算displayMetrics.xhdpi, 使单位pt重定义为设计稿的相对长度
     *
     * @param context
     * @param designWidth 设计稿的宽度
     */
    private static void resetDensity(Context context, float designWidth) {
        Point size = new Point();
        ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        context.getResources().getDisplayMetrics().xdpi = size.x / designWidth * 72f;
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private static Intent click(PushModel pushModel) {
        Intent it=null;
        switch (pushModel.getKeyType()) {
            case "4":
                it=new Intent(application, OrderDetailActivity.class);
                it.putExtra("id", pushModel.getKeyId());
                break;
            case "2":
            case "5":
                 it=new Intent(application, OrderDetailActivity.class);
//                it=new Intent(application, RefundDetailActivity.class);
                it.putExtra("id", pushModel.getKeyId());
                break;
            default:
                it=new Intent(application, OrderDetailActivity.class);
                it.putExtra("id", pushModel.getKeyId());
                return it;
        }
        return  it;
    }
}
