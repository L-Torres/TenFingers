package com.beijing.tenfingers.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.MainActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.SysInitInfo;
import com.beijing.tenfingers.bean.User;
import com.beijing.tenfingers.bean.VersionModle;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.fileload.FileInfo;
import com.beijing.tenfingers.fileload.XtomFileDownLoader;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.until.MapLocation;
import com.beijing.tenfingers.view.PopFirstDialog;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.UpgradeDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.io.File;

import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;

public class StartActivity extends BaseActivity implements MapLocation.ILocationListener  {
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    private ImageView start_img;
    private SysInitInfo sysInitInfo;
    private User user;
    private String savePath;
    private String city;
    private String lng;
    private String lat;

    private String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"};

    private String flag = "";

    private boolean isShowed;// 展示页是否看过，预留下来，现在默认为已经展示过了


    private static final int REQUEST_PERMISSION = 0;
    private String channelName;

    private static final int REQUEST_READ_PHONE_STATE = 1;

    private String device_id = "";
    private String app_version = "";
    private String device_model = "";
    private String token = "";

    private String url;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private UpgradeDialog upgradeDialog;
    private VersionModle versionModle;


    private PopFirstDialog dialog;
    private String pop="";

    @SuppressLint({"NewApi", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);
        super.onCreate(savedInstanceState);
        pop=XtomSharedPreferencesUtil.get(mContext,"pop");
        if(isNull(pop)){
            dialog=new PopFirstDialog(mContext);
            dialog.setButtonListener(new PopFirstDialog.OnButtonListener() {
                @Override
                public void onLeftButtonClick(PopFirstDialog dialog) {
                    dialog.cancel();
                }

                @Override
                public void onRightButtonClick(PopFirstDialog dialog) {
                    dialog.cancel();
                    XtomSharedPreferencesUtil.save(mContext,"pop","1");
                    requestPermission();
                }
            });
            dialog.show();

        }else{
            requestPermission();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION);
    }

    private int getVersion() {
        //获取packageManager实例
        PackageManager packageManager = getPackageManager();
        // 获取包名
        String packageName = getPackageName();
        int flag = 0;
        PackageInfo packageInfo = null;
        // 获取packageInfo
        try {
            packageInfo = packageManager.getPackageInfo(packageName, flag);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 获取packageInfo中的版本信息等信息
        if (packageInfo != null) {
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.i("TAG", versionCode + "");
            Log.i("TAG", versionName);
            return versionCode;
        }
        return 0;
    }

    //检测是否有定位权限
    private void checkLocation() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {//判断是否拥有定位权限

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    3);
        } else {
            new MapLocation(this,this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 3) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED)//未获得定位权限
            {
                showTextDialog("没有定位权限，请添加");
                toLogin();
            } else {
                new MapLocation(this,this);
            }
            return;
        }else if(requestCode==0){

            checkLocation();

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
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
            case VERSION_COMPARE:
                HemaArrayResult<VersionModle> vResult= (HemaArrayResult<VersionModle>) baseResult;
                if(vResult.getObjects().size()==0){
                    afterCheckUp();
                }else{
                    versionModle=vResult.getObjects().get(0);
                  checkUpGrade(versionModle.getV_version(),versionModle.getV_link(),"","有新版本，请更新");
                }

                break;
            case CLIENT_LOGIN:
                HemaArrayResult<User> fResult = (HemaArrayResult<User>) baseResult;
                User user = fResult.getObjects().get(0);
                getApplicationContext().setUser(user);
                XtomSharedPreferencesUtil.save(mContext, "token", user.getToken());
                XtomSharedPreferencesUtil.save(mContext, "is_vip", user.getViptype());
                Intent mainIt = new Intent(mContext, MainActivity.class);
                startActivity(mainIt);
                break;
        }
    }



    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
//        if(baseResult.getError_code()==401 || baseResult.getError_code()==20001){
//            BaseUtil.toLogin(mContext,"1");
//            return;
//        }
        switch (information) {

            case VERSION_COMPARE:
            case INIT:
            case CLIENT_LOGIN:
                Intent it=new Intent(mContext,MainActivity.class);
                startActivity(it);
                break;
            default:
                break;
        }
    }
    /**
     * 检查更新
     *
     * @param sysVersion
     * @param url
     * @param flag       1 必须更新
     */
    private void checkUpGrade(String sysVersion, String url, String flag, String content) {
//        String sysVersion = sysInitInfo.getAndroid_last_version();
        String version = HemaUtil.getAppVersionForSever(mContext);

        String currentDate = BaseUtil.getCurrentDate();
        String date = XtomSharedPreferencesUtil.get(mContext, "update_date");

        boolean isNeedShow = HemaUtil.isNeedUpDate(version, sysVersion);
        if (isNeedShow) { //需要升级
            if ("1".equals(flag)) {
                isNeedShow = true;
            } else if (isNull(date) || !date.equals(currentDate)) {
                isNeedShow = true;
            } else {
                isNeedShow = false;
            }
        }
        Activity activity = XtomActivityManager.getLastActivity();
        if (activity != null && isNeedShow && !isFinishing() && !isDestroyed()) {
            upgradeDialog = new UpgradeDialog(XtomActivityManager.getLastActivity(), content);
            upgradeDialog.setVersion(sysVersion);
            upgradeDialog.setButtonListener(new UpgradeDialog.OnButtonListener() {
                @Override
                public void onUpgradeClick(UpgradeDialog dialog) {
                    dialog.cancel();
                    upGrade(url);
                    XtomSharedPreferencesUtil.save(XtomActivityManager.getLastActivity(), "isShowed", "false");
                }

                @Override
                public void onCloseClick(UpgradeDialog dialog) {
                    if ("1".equals(flag)) {
                        dialog.cancel();
                        upGrade(url);
                        XtomSharedPreferencesUtil.save(XtomActivityManager.getLastActivity(), "isShowed", "false");
                    } else {
                        dialog.cancel();
                        XtomSharedPreferencesUtil.save(mContext, "update_date", currentDate);
                        afterCheckUp();
                    }
                }
            });
            upgradeDialog.show();
        } else {
            afterCheckUp();
        }
    }

        private void upGrade(String url) {
        String downPath = url;
        savePath = XtomFileUtil.getFileDir(XtomActivityManager.getLastActivity()) + "apps/cjs_"
                + "cjs" + ".apk";
        XtomFileDownLoader downLoader = new XtomFileDownLoader(XtomActivityManager.getLastActivity(),
                downPath, savePath);
        downLoader.setThreadCount(3);
        downLoader.setXtomDownLoadListener(new DownLoadListener());
        downLoader.start();
    }
    /**
     * 更新下载监听
     */
    private class DownLoadListener implements XtomFileDownLoader.XtomDownLoadListener {
        private ProgressDialog pBar;

        @Override
        public void onStart(final XtomFileDownLoader loader) {
            pBar = new ProgressDialog(XtomActivityManager.getLastActivity()) {
                @Override
                public void onBackPressed() {
                    loader.stop();
                }
            };
            pBar.setTitle("正在下载");
            pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pBar.setMax(100);
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        public void onSuccess(XtomFileDownLoader loader) {
            if (pBar != null) {
                pBar.cancel();
            }
            install();
        }

        void install() {
            //Androapp_versionid 10 安装
            if (Build.VERSION.SDK_INT >= 29) {

                String packageName = mContext.getPackageName();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri apkUri = FileProvider.getUriForFile(XtomActivityManager.getLastActivity(), packageName + ".FileProvider", new File(savePath));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savePath)),
                        "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        @Override
        public void onFailed(XtomFileDownLoader loader) {
            if (pBar != null) {
                pBar.cancel();
            }
            ToastUtil.showShortToast(XtomActivityManager.getLastActivity(), "下载失败了");
        }

        @Override
        public void onLoading(XtomFileDownLoader loader) {
            FileInfo fileInfo = loader.getFileInfo();
            int curr = fileInfo.getCurrentLength();
            int cont = fileInfo.getContentLength();
            int per = (int) ((float) curr / (float) cont * 100);
            if (pBar != null) {
                pBar.setProgress(per);
            }
        }

        @Override
        public void onStop(XtomFileDownLoader loader) {
            if (pBar != null) {
                pBar.cancel();
            }
            ToastUtil.showShortToast(XtomActivityManager.getLastActivity(), "下载停止");
        }

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information = (MyHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case INIT:
                break;
            case CLIENT_LOGIN:
                break;
            default:
                break;
        }
    }







    @Override
    public void onEventMainThread(EventBusModel event) {

    }




    private void afterCheckUp() {
       toLogin();
    }
    /**
     * 去登录页
     */
    private void toLogin() {
//        String token=XtomSharedPreferencesUtil.get(mContext,"token");
//        if (!isNull(token)) {
//
//            Intent it=new Intent(mContext, MainActivity.class);
//            startActivity(it);
//            changeAc();
//        }else{
            String mobile=XtomSharedPreferencesUtil.get(mContext,"username");
            String password=XtomSharedPreferencesUtil.get(mContext,"password");
            if(!isNull(mobile) && !isNull(password)){
                getNetWorker().clientLogin(mobile, HemaUtil.getMD5String(password));
            }else{
                Intent it=new Intent(mContext,MainActivity.class);
                startActivity(it);
                changeAc();
            }



//        }
    }


    @Override
    protected void findView() {
    }


    @Override
    protected void getExras() {
    }

    @Override
    protected void setListener() {
    }


    @Override
    public void getLocation(AMapLocation aMapLocation) {

        String lng = String.valueOf(aMapLocation.getLongitude());
        if (!isNull(lng)) {
            XtomSharedPreferencesUtil.save(mContext, "lng", lng);
        }
        String lat = String.valueOf(aMapLocation.getLatitude());
        if (!isNull(lat)) {
            XtomSharedPreferencesUtil.save(mContext, "lat", lat);
        }
        String address = aMapLocation.getAddress();
        if (!isNull(address)) {
            XtomSharedPreferencesUtil.save(mContext, "address", address);
        }
        String cityName = aMapLocation.getCity();
        if (!isNull(cityName)) {
            XtomSharedPreferencesUtil.save(mContext, "city_name", cityName);
        }
        String district_name = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict();
        if (!isNull(district_name)) {
            XtomSharedPreferencesUtil.save(mContext, "district_name", district_name);
        }
        getNetWorker().checkVersion("1",String.valueOf(getVersion()));
    }
}
