package com.beijing.tenfingers.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.beijing.tenfingers.view.WaitDialog;
import com.hemaapp.hm_FrameWork.HemaFragment;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaNetWorker;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.List;

import xtom.frame.XtomActivityManager;
import xtom.frame.net.XtomNetWorker;
import xtom.frame.util.XtomSharedPreferencesUtil;

public abstract class MyBaseFragment extends HemaFragment {


    private BaseDialog dialog;
    /**
     * 加载框
     * @param text
     */
    public void showLoading(String text){
        dialog = new WaitDialog.Builder(getActivity())
                // 消息文本可以不用填写
                .setMessage(text)
                .show();
    }

    /**
     * 取消加载
     * @param
     */
    public void cancelLoading(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
    public View findViewById(int id) {
        View view = null;
        if (rootView != null)
            view = rootView.findViewById(id);
        return view;
    }

    /**
     * 显示或更换Fragment
     *
     * @param fragmentClass
     *            Fragment.class
     * @param containerViewId
     *            Fragment显示的空间ID
     * @param replace
     *            是否替换
     */
    public void toogleFragment(Class<? extends Fragment> fragmentClass,
                               int containerViewId, boolean replace) {
        FragmentManager manager = getChildFragmentManager();
        String tag = fragmentClass.getName();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(tag);

        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                if (replace)
                    transaction.replace(containerViewId, fragment, tag);
                else
                    // 替换时保留Fragment,以便复用
                    transaction.add(containerViewId, fragment, tag);
            } catch (Exception e) {
                // ignore
            }
        } else {
            // nothing
        }
        // 遍历存在的Fragment,隐藏其他Fragment
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null)
            for (Fragment fm : fragments)
                if (!fm.equals(fragment))
                    transaction.hide(fm);

        transaction.show(fragment);
        transaction.commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }




    // 友盟相关
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    // 友盟相关end


    @Override
    protected HemaNetWorker initNetWorker() {
        return new MyNetWorker(getActivity());
    }

    @Override
    public MyNetWorker getNetWorker() {
        return (MyNetWorker) super.getNetWorker();
    }
    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        switch (baseResult.getError_code()){
            case 20001:
                ToastUtils.show(baseResult.getError_message());
                break;
            case 401:
                XtomSharedPreferencesUtil.save(getActivity(),"token","");
                MyApplication.getInstance().setUser(null);
                XtomActivityManager.finishAll();
                BaseUtil.toLogin(getActivity(),"1");
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
                    case 4004:
                    case 401:
                    case 1003:// 密码错误
//                        BaseUtil.LoginOut(getActivity());
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

    public void changeAc(Activity activity) {
        activity.overridePendingTransition(R.anim.right_in, R.anim.none);
    }

    /**
     * 接收广播（必须的）
     *
     * @param event
     */
    public abstract void onEventMainThread(EventBusModel event);

    /**
     * 是否加入回退栈的判断，用于实现一些有关回退栈的需求
     * @return
     */
    public boolean isNeedToAddBackStack() {
        return true;
    }

}
