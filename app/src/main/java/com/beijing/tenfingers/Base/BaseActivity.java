package com.beijing.tenfingers.Base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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


public abstract class BaseActivity extends HemaFragmentActivity {


    private BaseDialog dialog;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String activityName = am.getRunningTasks(1).get(0).topActivity.getClassName();
        if ("com.beijing.tenfingers.activity.StartActivity".equals(activityName)) {
            ImmersionBar.with(this).fullScreen(true).transparentNavigationBar()
                    .statusBarDarkFont(true).init();
        }else{
            ImmersionBar.with(this)
                    // 默认状态栏字体颜色为黑色
                    .statusBarColor(R.color.bg_total,0.07f)
                    .statusBarDarkFont(true).init();
        }
//            initSystemBar(true);
//            // 在BaseActivity里初始化
//            mImmersionBar = ImmersionBar.with(this)
//                    // 默认状态栏字体颜色为黑色
//                    .statusBarDarkFont(false);
//            if (!isTaskRoot()) {
//                Intent intent = getIntent();
//                String action = intent.getAction();
//                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
//                    finish();
//                    return;
//                }
//            }
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

    /**
     * 加载框
     *
     * @param text
     */
    public void showLoading(String text) {
        dialog = new WaitDialog.Builder(this)
                // 消息文本可以不用填写
                .setMessage(text)
                .show();
    }

    /**
     * 取消加载
     * @param
     */
    public void cancelLoading() {
        dialog.dismiss();
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
    public boolean onAutoLoginFailed(HemaNetWorker netWorker,
                                     HemaNetTask netTask, int failedType, HemaBaseResult baseResult) {
        switch (failedType) {
            case 0:// 服务器处理失败
                int error_code = baseResult.getError_code();
                switch (error_code) {
                    case 4004:
                    case 401:
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
    public void changeAc() {
        overridePendingTransition(R.anim.right_in, R.anim.none);
    }

    public void finishAc(Context mContext) {
        ((HemaFragmentActivity) mContext).finish();
        overridePendingTransition(R.anim.none, R.anim.right_out);
    }


    @Override
    protected boolean onKeyBack() {

        finish(R.anim.none, R.anim.right_out);
        return super.onKeyBack();
    }

    /**
     * 保存城市名称
     *
     * @param cityName
     */
    public void saveCityName(String cityName) {
        XtomSharedPreferencesUtil.save(this, "city_name", cityName);
    }


    /**
     * @return 获取城市名称
     */
    public String getCityName() {
        return XtomSharedPreferencesUtil.get(this, "city_name");
    }


    /**
     * 保存城市id
     *
     * @param cityId
     */
    public void saveCityId(String cityId) {
        XtomSharedPreferencesUtil.save(this, "city_id", cityId);
    }

    /**
     * @return 获取城市id
     */
    public String getCityId() {
        return XtomSharedPreferencesUtil.get(this, "city_id");
    }

    /**
     * 隐藏输入框
     */
    public void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     *
     * @param editText
     */
    protected void showSoftInput(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
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
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

}
