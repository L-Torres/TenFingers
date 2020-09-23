package com.beijing.tenfingers.Base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.beijing.tenfingers.view.WaitDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.hemaapp.hm_FrameWork.HemaFragmentActivity;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaNetWorker;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jaeger.library.StatusBarUtil;

import xtom.frame.XtomActivityManager;
import xtom.frame.net.XtomNetWorker;
import xtom.frame.util.XtomSharedPreferencesUtil;

public abstract  class BaseFragmentActivity extends HemaFragmentActivity {

    private BaseDialog dialog;
    private ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarColor(R.color.bg_total,0.07f)
                .statusBarDarkFont(true).init();
//        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        initSystemBar(true);
//        mImmersionBar = ImmersionBar.with(this)
//                // 默认状态栏字体颜色为黑色
//                .statusBarDarkFont(false);
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration newConfig = new Configuration();

        //控制字体缩放 1.0为默认

        newConfig.fontScale = 1.0f;

        DisplayMetrics displayMetrics = res.getDisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            //7.0以上系统手机 显示大小 对APP的影响

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                if (displayMetrics.density < DisplayMetrics.DENSITY_DEVICE_STABLE / (float) DisplayMetrics.DENSITY_DEFAULT) {

                    displayMetrics.densityDpi = (int) (DisplayMetrics.DENSITY_DEVICE_STABLE * 0.92);

                } else {

                    displayMetrics.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;

                }

                newConfig.densityDpi = displayMetrics.densityDpi;

            }

            createConfigurationContext(newConfig);

        }

        res.updateConfiguration(newConfig, displayMetrics);

        return res;

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(MyConfig.UMENG_ENABLE){

//            MobclickAgent.onResume(this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MyConfig.UMENG_ENABLE){

//            MobclickAgent.onPause(this);
        }

    }

    @Override
    protected HemaNetWorker initNetWorker() {
        return new MyNetWorker(mContext);
    }

    @Override
    public MyNetWorker getNetWorker() {
        return (MyNetWorker) super.getNetWorker();
    }

    @Override
    public MyApplication getApplicationContext() {
        return (MyApplication) super.getApplicationContext();
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        switch (baseResult.getError_code()){
            case 20001:
                ToastUtils.show(baseResult.getError_message());
                break;
            case 401:
                XtomSharedPreferencesUtil.save(getApplicationContext(),"token","");
                MyApplication.getInstance().setUser(null);
                XtomActivityManager.finishAll();
                BaseUtil.toLogin(getApplicationContext(),"1");
                break;
        }
    }

    @Override
    public boolean onAutoLoginFailed(HemaNetWorker netWorker,
                                     HemaNetTask netTask, int failedType, HemaBaseResult baseResult) {
        switch (failedType) {

            case 0:// 服务器处理失败
                int error_code = baseResult.getError_code();
                switch (error_code) {
                    case 401://token 失效
                    case 4004:
                    case 1003:// 密码错误
//                        BaseUtil.LoginOut(getApplicationContext());
                        return true;
                    default:
                        break;
                }
            case XtomNetWorker.FAILED_HTTP:// 网络异常
            case XtomNetWorker.FAILED_DATAPARSE:// 数据异常
            case XtomNetWorker.FAILED_NONETWORK:// 无网络
                break;
        }
        return false;
    }
    // ------------------------下面填充项目自定义方法---------------------------

    /**
     * 转场动画
     */
    public void changeAc() {
        overridePendingTransition(R.anim.right_in, R.anim.none);
    }

    /**
     * 销毁页面动画
     */
    public void finishAc() {
        finish();
        overridePendingTransition(R.anim.none, R.anim.right_out);
    }

    /**
     * 接收广播（必须的）
     *
     * @param event
     */
    public abstract void onEventMainThread(EventBusModel event);

    /*
      设置状态栏颜色."启动页"不能使用，否则状态栏显示我们设置的颜色
    */
    public void initSystemBar(Boolean isLight) {

        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 10);

    }
}
