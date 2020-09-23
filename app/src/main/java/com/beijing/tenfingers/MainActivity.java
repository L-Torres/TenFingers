package com.beijing.tenfingers;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.beijing.tenfingers.Base.BaseFragmentActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.activity.OrderDetailActivity;
import com.beijing.tenfingers.bean.Count;
import com.beijing.tenfingers.bean.MyData;
import com.beijing.tenfingers.bean.OrderDetail;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.fragment.FirstFragment;
import com.beijing.tenfingers.fragment.PersonalFragment;
import com.beijing.tenfingers.fragment.SecondFragment;
import com.beijing.tenfingers.fragment.ThirdFragment;
import com.beijing.tenfingers.push.MyIntentService;
import com.beijing.tenfingers.push.MyPushService;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.until.MapLocation;
import com.beijing.tenfingers.view.RandomDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.model.Image;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.igexin.sdk.PushManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomLogger;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;

public class MainActivity extends BaseFragmentActivity implements MapLocation.ILocationListener {
    //是否需要检测后台定位权限，设置为true时，如果用户没有给予后台定位权限会弹窗提示
    private boolean needCheckBackLocation = false;
    //如果设置了target > 28，需要增加这个权限，否则不会弹出"始终允许"这个选择框
    private static String BACKGROUND_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int PERMISSON_REQUESTCODE = 0;
    public  RadioGroup rg;
    private long exitTime = 0;
    private int checkedNo = 0;
    private int[] i;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private ArrayList<PopBean> popBeans=new ArrayList<>();
    private RandomDialog randomDialog;//根据后台返回来判断弹窗的位置和弹窗时间
    private int position = 1;
    private String appkey = "";
    private String appsecret = "";
    private String appid = "";
    private static final int REQUEST_PERMISSION = 0;
    private static final String TAG = "GetuiSdkInit";
    private Class userPushService = MyPushService.class;
    private MyData myData;
    private int flag=0;// 0 首页 1 服务项目 2 订单 3个人
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarColor(R.color.bg_total,0.07f)
                .statusBarDarkFont(true).init();
        rg.check(R.id.main_rb0);
        getNetWorker().pop_common();

        if(BaseUtil.IsLogin()){
            getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
        }
        PushManager.getInstance().initialize(this.getApplicationContext(), MyPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);
        startGeTui();

    }


    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    protected void findView() {

        rg = (RadioGroup) findViewById(R.id.main_rg);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        rg.setOnCheckedChangeListener(new OnTabListener());
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {

            case MyEventBusConfig.CLIENT_LOGIN://
                if(BaseUtil.IsLogin()){
                    getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
                }
                break;
            case MyEventBusConfig.UPDATE_DIALOG://弹窗操作完毕 position++ 进行下一弹窗
                position++;
                showDialog();
                break;
            case MyEventBusConfig.LOGIN_OUT:
                rg.check(R.id.main_rb0);
                break;
            case MyEventBusConfig.PAY_SUCCESS:
            case MyEventBusConfig.PAY_ERROR:
            case MyEventBusConfig.PAY_CANCEL:
                XtomActivityManager.finishOtherActivity(this);
                rg.check(R.id.main_rb2);
                break;
            case MyEventBusConfig.GO_TO_SERVICE:
            case MyEventBusConfig.GO_TO_BOOK:
                rg.check(R.id.main_rb1);
                break;
            case MyEventBusConfig.PSUH_SUCCESS:
                String temp = event.getContent(); //各推的client_id
                if (BaseUtil.IsLogin()) {
                    getNetWorker().save_push_id(MyApplication.getInstance().getUser().getToken(), temp);
                } else {
                }
                break;
        }
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_DATA:
                HemaArrayResult<MyData> mResult = (HemaArrayResult<MyData>) baseResult;
                myData = mResult.getObjects().get(0);
                XtomSharedPreferencesUtil.save(mContext,"huiyuan",myData.getIs_vip());
                break;
            case PUSH_ID:
                XtomLogger.i("推送ID保存成功","Ok");
                break;
            case COMMON_POPUP:
                HemaArrayResult<PopBean> pResult= (HemaArrayResult<PopBean>) baseResult;
                ArrayList<PopBean> temp=pResult.getObjects();
                popBeans.clear();
                popBeans.addAll(temp);
                MyApplication.getInstance().setQuestion(popBeans);
                //优惠券弹窗标识为 id+keytype+data
                String date=BaseUtil.getCurrentDate();
                String coupon=XtomSharedPreferencesUtil.get(mContext,"coupon");
                for (PopBean popBean : popBeans) {
                    if(popBean.getKey_type().equals("2")){//优惠券
                        String flag=popBean.getId()+"2"+date;
                        if(!coupon.equals(flag)){
                            //加载的弹窗组合标识跟存储的组合标识不一致时需要弹出
                            randomDialog = new RandomDialog(XtomActivityManager.getLastActivity(), popBean);
                            XtomSharedPreferencesUtil.save(mContext,"coupon",flag);
                            break;
                        }
                    }
                }





//                HandlerThunder();
                break;
        }
    }




    private void HandlerThunder() {
        //1、处理数据
        ArrayList<PopBean> temp = new ArrayList<>();//用来提取已经存储在本地的数组
        temp = (ArrayList<PopBean>) BaseUtil.getArray(mContext);
        ArrayList<PopBean> showed = new ArrayList<>();//本地已经弹出过的
        if(temp != null){

            if ( temp.size() > 0) {
                //循环剔除不再展示的 保证每次加载后 都是可执行的弹窗
                for (PopBean adAlert : temp) {
                    for (PopBean alert : popBeans) {
                        if (alert.getId().equals(adAlert.getId())) {
                            String flag=adAlert.getFlag();
                            if (adAlert.getFlag().equals("1")) {
                                showed.add(alert);
                            }
                            alert.setFlag(adAlert.getFlag());
                        }
                    }
                }
                BaseUtil.putArray(mContext, popBeans);
                popBeans.removeAll(showed);
            }else {
                BaseUtil.putArray(mContext, popBeans);
            }
            //处理弹窗   position记录弹窗次数 第一次加载页面的时候取第零位置的  做出判断
            // 符合条件进行弹窗 不符合条件position++ 然后进行和数组对比 有继续进行下一项处理
            //在弹窗处监听操作 处理 position和对象的属性
            showDialog();
        }else {
            BaseUtil.putArray(mContext, popBeans);
            showDialog();
        }
    }

    /**
     * 是否弹窗
     */
    private void showDialog() {
        ArrayList<PopBean> temp = new ArrayList<>();
        try {
            temp = (ArrayList<PopBean>) BaseUtil.getArray(mContext);
        } catch (Exception e) {
        }

        if (temp != null && temp.size() > 0) {
            if (position <= temp.size()) {
//                //延时弹窗
                if (temp.get(position - 1).getFlag().equals("0")) {
                    int time = 6000 * position;
                    PopBean adAlert = temp.get(position - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Activity activity = XtomActivityManager.getLastActivity();
                            if (activity == null) {
                                return;
                            }
                            randomDialog = new RandomDialog(XtomActivityManager.getLastActivity(), adAlert);
                        }
                    }, time);
                } else {
                    position++;
                    showDialog();
                }
            }
        }
    }
    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    public void getLocation(AMapLocation aMapLocation) {



    }

    private class OnTabListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.main_rb0://首页
                    flag=0;
                    toogleFragment(FirstFragment.class);
                    break;
                case R.id.main_rb1://服务
                    flag=1;
                    toogleFragment(SecondFragment.class);
                    break;
                case R.id.main_rb2://订单
                    if(BaseUtil.IsLogin()){
                        flag=2;
                        toogleFragment(ThirdFragment.class);
                    }else{
                        if(flag==0){
                            rg.check(R.id.main_rb0);
                        }else if(flag==1){
                            rg.check(R.id.main_rb1);
                        }
                        BaseUtil.toLogin(mContext,"1");
                    }
                    break;
                case R.id.main_rb3://我的
                    if(BaseUtil.IsLogin()){
                        flag=3;

                        toogleFragment(PersonalFragment.class);
                    }else{
                        if(flag==0){
                            rg.check(R.id.main_rb0);
                        }else if(flag==1){
                            rg.check(R.id.main_rb1);
                        }
                        BaseUtil.toLogin(mContext,"1");
                    }
                    break;
                default:
                    break;
            }
        }

    }


    private void toogleFragment(Class<? extends Fragment> c) {
        FragmentManager manager = getSupportFragmentManager();
        String tag = c.getName();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(tag);

        if (fragment == null) {
            try {
                fragment = c.newInstance();
                // 替换时保留Fragment,以便复用
                transaction.add(R.id.main_content, fragment, tag);
                // Bundle bundle = new Bundle();
                // bundle.putSerializable("mall", mall);
                // fragment.setArguments(bundle);

            } catch (Exception e) {
                // ignore
            }
        } else {
            // nothing
        }
        // 遍历存在的Fragment,隐藏其他Fragment
        @SuppressLint("RestrictedApi") List<Fragment> fragments = manager.getFragments();
        if (fragments != null)
            for (Fragment fm : fragments)
                if (fm != null && !fm.equals(fragment))
                    transaction.hide(fm);

        transaction.show(fragment);
        // transaction.commit();
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                XtomToastUtil.showShortToast(mContext, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                NotificationManager nm = (NotificationManager) mContext
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                nm.cancelAll();
                XtomActivityManager.finishAll();
                finish();
                // System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void startGeTui() {
        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        }

        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);

        // 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
        if (MyApplication.payloadData != null) {
//            tLogView.append(MyApplication.payloadData);
        }

        // cpu 架构
        Log.d(TAG, "cpu arch = " + (Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0]));
        // 检查 so 是否存在
        File file = new File(this.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext2.so");
        Log.e(TAG, "libgetuiext2.so exist = " + file.exists());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }

    private void parseManifests() {
        String packageName = getApplicationContext().getPackageName();
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                appid = appInfo.metaData.getString("PUSH_APPID");
                appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
                appkey = appInfo.metaData.getString("PUSH_APPKEY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
