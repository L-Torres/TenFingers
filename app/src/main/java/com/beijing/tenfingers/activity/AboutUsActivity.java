package com.beijing.tenfingers.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.AboutUs;
import com.beijing.tenfingers.bean.VersionModle;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.fileload.FileInfo;
import com.beijing.tenfingers.fileload.XtomFileDownLoader;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.ToastUtils;
import com.beijing.tenfingers.view.UpgradeDialog;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.io.File;

import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;

import static com.beijing.tenfingers.activity.KefuActivity.REQUEST_CALL_PERMISSION;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back,ll_version,ll_kefu,ll_fw,ll_ys;
    private TextView tv_title, tv_teach, tv_shop,tv_wx,tv_wb,tv_web,tv_phone,tv_email,tv_version;
    private UpgradeDialog upgradeDialog;
    private VersionModle versionModle;
    private String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_aboutus);
        super.onCreate(savedInstanceState);
        getNetWorker().about_us();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

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
                    ToastUtils.show("已经是最新版本");
                }else{
                    versionModle=vResult.getObjects().get(0);
                    checkUpGrade(versionModle.getV_version(),versionModle.getV_link(),"","有新版本，请更新");
                }

                break;
            case  ABOUT_US:
                HemaArrayResult<AboutUs>   aResult= (HemaArrayResult<AboutUs>) baseResult;
                AboutUs a=aResult.getObjects().get(0);
                if(a!=null){
                    setData(a);
                    phone=a.getSet_busi_mobile();
                }

                break;
        }
    }

    private void setData(AboutUs a){
        tv_wb.setText(a.getSet_weibo());
        tv_web.setText(a.getSet_website());
        tv_wx.setText(a.getSet_weixin());
        tv_email.setText(a.getSet_email());
        tv_phone.setText(a.getSet_csc_mobile());
        tv_version.setText(HemaUtil.getAppVersionForTest(getApplicationContext()));

    }
    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        ll_fw=findViewById(R.id.ll_fw);
        ll_ys=findViewById(R.id.ll_ys);
        ll_kefu=findViewById(R.id.ll_kefu);
        ll_version=findViewById(R.id.ll_version);
        tv_version=findViewById(R.id.tv_version);
        tv_email=findViewById(R.id.tv_email);
        tv_phone=findViewById(R.id.tv_phone);
        tv_wb=findViewById(R.id.tv_wb);
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        tv_wx = findViewById(R.id.tv_wx);
        tv_web = findViewById(R.id.tv_web);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("关于我们");
        ll_version.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        ll_fw.setOnClickListener(this);
        ll_ys.setOnClickListener(this);
    }
    /**
     * 判断是否有某项权限
     * @param string_permission 权限
     * @param request_code 请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission,int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    /**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this,"请允许拨号权限后再试",Toast.LENGTH_SHORT).show();
                } else {//成功
                    if(isNull(phone)){
                        return;
                    }
                    call("tel:"+phone);
                }
                break;
        }
    }
    /**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     */
    public void call(String telPhone){
        if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telPhone));
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()) {
            case R.id.ll_ys:
                it = new Intent(mContext, WebViewActivity.class);
                it.putExtra("Title", "十指间隐私政策");
                it.putExtra("URL", "https://10zhijian.com/privacy.html");
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_fw:
                it = new Intent(mContext, WebViewActivity.class);
                it.putExtra("Title", "服务协议");
                it.putExtra("URL", "https://10zhijian.com/sla.html");
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_kefu:
                call(phone);
                break;
            case R.id.ll_version:
                getNetWorker().checkVersion("1",String.valueOf(getVersion()));
                break;
            case R.id.tv_teach:
                it=new Intent(mContext,TeacherJoinActivity.class);
                startActivity(it);
                changeAc();
                break;
            case R.id.tv_shop:
                it=new Intent(mContext,ShopJoinActivity.class);
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
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
                    }
                }
            });
            upgradeDialog.show();
        } else {
        }
    }
    private String savePath;
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
}
