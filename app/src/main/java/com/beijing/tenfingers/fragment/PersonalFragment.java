package com.beijing.tenfingers.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.MainActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.AboutUsActivity;
import com.beijing.tenfingers.activity.AddUsActivity;
import com.beijing.tenfingers.activity.AddressListActivity;
import com.beijing.tenfingers.activity.ChargeActivity;
import com.beijing.tenfingers.activity.ChargeCardActivity;
import com.beijing.tenfingers.activity.CollectActivity;
import com.beijing.tenfingers.activity.CouponListActivity;
import com.beijing.tenfingers.activity.FeedBackActivity;
import com.beijing.tenfingers.activity.HistoryListActivity;
import com.beijing.tenfingers.activity.KefuActivity;
import com.beijing.tenfingers.activity.MyCouponListActivity;
import com.beijing.tenfingers.activity.PersonaInfoActivity;
import com.beijing.tenfingers.activity.ValueActivity;
import com.beijing.tenfingers.activity.ValueListActivity;
import com.beijing.tenfingers.activity.VipActivity;
import com.beijing.tenfingers.bean.City;
import com.beijing.tenfingers.bean.CityChildren;
import com.beijing.tenfingers.bean.MyData;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.MyRefreshLoadmoreLayout;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.PopVipDialog;
import com.beijing.tenfingers.view.RandomDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

public class PersonalFragment extends MyBaseFragment implements View.OnClickListener {


    private LinearLayout ll_vip, ll_personal, ll_coupon,
            ll_address, ll_feed, ll_collect, ll_value, ll_scan, ll_charge, ll_add, ll_kefu,ll_about;
    private TextView tv_join, tv_name, tv_id, tv_collect, tv_value, tv_foot, tv_out,tv_tip,tv_info;
    private MyData myData;
    private RoundedImageView iv_pic;
    private PopTipDialog dialog;
    private ImageView iv_set,iv_sex;
    private ImageView iv_king;
    private PopVipDialog vipDialog;
    private MyRefreshLoadmoreLayout my_refresh;
    private RandomDialog randomDialog;//根据后台返回来判断弹窗的位置和弹窗时间
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_my);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
        ArrayList<PopBean> temp=new ArrayList<>();
        if(MyApplication.getInstance().getQuestion()!=null){
            temp=MyApplication.getInstance().getQuestion();
            if(temp.size()>0){
                String date=BaseUtil.getCurrentDate();
                String coupon= XtomSharedPreferencesUtil.get(getActivity(),"vip_charge");
                for (PopBean popBean : temp) {
                    if(popBean.getKey_type().equals("3")){//项目
                        String flag=popBean.getId()+"3"+date;
                        if(!coupon.equals(flag)){
                            //加载的弹窗组合标识跟存储的组合标识不一致时需要弹出
                            randomDialog = new RandomDialog(XtomActivityManager.getLastActivity(), popBean);
                            XtomSharedPreferencesUtil.save(getActivity(),"vip_charge",flag);
                            break;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onEventMainThread(EventBusModel event) {

        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.CLIENT_LOGIN:
            case MyEventBusConfig.UPDATE_ORDER:
            case MyEventBusConfig.COLLECT:
            case MyEventBusConfig.CHARGE:
            case MyEventBusConfig.UPDATE_HEAD:
                getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
                break;
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_LOGINOUT:
                showProgressDialog("");
                break;
            case MY_DATA:
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_LOGINOUT:
                cancelProgressDialog();
                break;
            case MY_DATA:
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        my_refresh.refreshSuccess();
        switch (information) {
            case CLIENT_LOGINOUT:
                cancellationSuccess();
                break;
            case DISTRICT_LIST:
                HemaArrayResult<CityChildren> cResult = (HemaArrayResult<CityChildren>) baseResult;
                ArrayList<CityChildren> cityChildren = cResult.getObjects();
                break;
            case MY_DATA:
                HemaArrayResult<MyData> mResult = (HemaArrayResult<MyData>) baseResult;
                myData = mResult.getObjects().get(0);
                setData();
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        my_refresh.refreshFailed();
        switch (information) {
            case CLIENT_LOGINOUT:
            case MY_DATA:
                ToastUtils.show(baseResult.getError_message());
                break;
        }
    }

    private void setData() {
        if("1".equals(myData.getU_sex())){
            iv_sex.setVisibility(View.VISIBLE);
            iv_sex.setImageResource(R.mipmap.man);
        }else  if("2".equals(myData.getU_sex())){
            iv_sex.setVisibility(View.VISIBLE);
            iv_sex.setImageResource(R.mipmap.female);
        }else{
            iv_sex.setVisibility(View.INVISIBLE);
        }
        if("1".equals(myData.getIs_vip())){
            iv_king.setVisibility(View.VISIBLE);
            tv_tip.setText("欢迎您，尊贵的VIP");
            tv_join.setText("再次充值");

        }else{
            iv_king.setVisibility(View.INVISIBLE);
            tv_tip.setText("升级VIP享受更多权益");
            tv_join.setText("立即加入");
            tv_info.setVisibility(View.INVISIBLE);
        }
        tv_info.setVisibility(View.VISIBLE);
        tv_info.setText("账户余额："+myData.getU_account());
        BaseUtil.loadCircleBitmap(getActivity(), myData.getU_image_link(), R.mipmap.ic_launcher_round, iv_pic);
        iv_pic.setCornerRadius(100);
        tv_name.setText(myData.getU_nickname());
        tv_id.setText("ID:"+myData.getId());
        if (isNull(myData.getCollect_count())) {
            tv_collect.setText("0");
        } else {
            tv_collect.setText(myData.getCollect_count());
        }
        if (isNull(myData.getSlot_count())) {
            tv_foot.setText("0");
        } else {
            tv_foot.setText(myData.getSlot_count());
        }
        if (isNull(myData.getComment_count())) {
            tv_value.setText("0");
        } else {
            tv_value.setText(myData.getComment_count());
        }


    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case MY_DATA:
                break;
        }
    }

    @Override
    protected void findView() {
        iv_sex= (ImageView) findViewById(R.id.iv_sex);
        my_refresh= (MyRefreshLoadmoreLayout) findViewById(R.id.my_refresh);
        tv_info= (TextView) findViewById(R.id.tv_info);
        tv_tip= (TextView) findViewById(R.id.tv_tip);
        iv_king= (ImageView) findViewById(R.id.iv_king);
        ll_about= (LinearLayout) findViewById(R.id.ll_about);
        iv_set= (ImageView) findViewById(R.id.iv_set);
        tv_out = (TextView) findViewById(R.id.tv_out);
        ll_kefu = (LinearLayout) findViewById(R.id.ll_kefu);
        ll_add = (LinearLayout) findViewById(R.id.ll_add);
        ll_charge = (LinearLayout) findViewById(R.id.ll_charge);
        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        iv_pic = (RoundedImageView) findViewById(R.id.iv_pic);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        tv_value = (TextView) findViewById(R.id.tv_value);
        tv_foot = (TextView) findViewById(R.id.tv_foot);
        tv_join = (TextView) findViewById(R.id.tv_join);
        ll_feed = (LinearLayout) findViewById(R.id.ll_feed);
        ll_coupon = (LinearLayout) findViewById(R.id.ll_coupon);
        ll_vip = (LinearLayout) findViewById(R.id.ll_vip);
        ll_personal = (LinearLayout) findViewById(R.id.ll_personal);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_value = (LinearLayout) findViewById(R.id.ll_value);
    }

    @Override
    protected void setListener() {
        ll_about.setOnClickListener(this);
        tv_out.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        ll_charge.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_personal.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_feed.setOnClickListener(this);
        tv_join.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_value.setOnClickListener(this);
        ll_scan.setOnClickListener(this);
        iv_set.setOnClickListener(this);
        my_refresh.setOnStartListener(new XtomRefreshLoadmoreLayout.OnStartListener() {
            @Override
            public void onStartRefresh(XtomRefreshLoadmoreLayout xtomRefreshLoadmoreLayout) {
                getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout xtomRefreshLoadmoreLayout) {
                xtomRefreshLoadmoreLayout.setLoadmoreable(false);
            }
        });
        my_refresh.setLoadmoreable(false);
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch (v.getId()) {
            case R.id.ll_about:
                it = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.tv_out:
                dialog = new PopTipDialog(getActivity());
                dialog.setTip("确定退出登录？");
                dialog.setButtonListener(new PopTipDialog.OnButtonListener() {
                    @Override
                    public void onLeftButtonClick(PopTipDialog dialog) {
                        dialog.cancel();
                    }

                    @Override
                    public void onRightButtonClick(PopTipDialog dialog) {
                        dialog.cancel();
                        getNetWorker().clientLoginout(MyApplication.getInstance().getUser().getToken());
                    }
                });
                dialog.show();
                break;
            case R.id.ll_kefu:
                it = new Intent(getActivity(), KefuActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_add:
                it = new Intent(getActivity(), AddUsActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_charge:
                it = new Intent(getActivity(), ChargeCardActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_scan:
                it = new Intent(getActivity(), HistoryListActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_value:
                it = new Intent(getActivity(), ValueListActivity.class);
                it.putExtra("id",myData.getId());
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_collect:
                it = new Intent(getActivity(), CollectActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;

            case R.id.tv_join:
                it = new Intent(getActivity(), ChargeActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_feed:
                it = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_address:
                it = new Intent(getActivity(), AddressListActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_coupon:
                it = new Intent(getActivity(), MyCouponListActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.iv_set:
            case R.id.ll_personal:
                it = new Intent(getActivity(), PersonaInfoActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.ll_vip:
                if(myData.getIs_vip().equals("1")){
                    it = new Intent(getActivity(), VipActivity.class);
                    startActivity(it);
                    changeAc(getActivity());
                }else{
                    vipDialog=new PopVipDialog(getActivity());
                    vipDialog.setCancelable(true);
                    vipDialog.show();
//                    ToastUtils.show("充值成为会员，可以享受高端服务哦～");
                }
                break;
        }
    }

    /**
     * 退出登录处理
     */
    private void cancellationSuccess() {
        MyApplication.getInstance().getUser().setToken(null);
        XtomSharedPreferencesUtil.save(getActivity(), "username", "");// 清空手机号
        XtomSharedPreferencesUtil.save(getActivity(), "password", "");// 清空密码
        XtomSharedPreferencesUtil.save(getActivity(), "token", "");// 清空密码
        XtomActivityManager.finishAll();
        Intent it=new Intent(getActivity(),MainActivity.class);
        startActivity(it);
    }


}
