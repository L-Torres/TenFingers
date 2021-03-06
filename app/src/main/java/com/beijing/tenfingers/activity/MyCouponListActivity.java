package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.CouponAdapter;
import com.beijing.tenfingers.adapter.MyCouponAdapter;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MyCouponListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_get;
    private XRecyclerView rcy_list;
    private ArrayList<CouponList> list=new ArrayList<>();
    private MyCouponAdapter adapter;
    private Integer page = 1;
    private ImageView iv_empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_list);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().my_coupon_available(MyApplication.getInstance().getUser().getToken());
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.GO_TO_SERVICE:

                finishAc(mContext);
                break;
            case MyEventBusConfig.COUPON_GET:
                getNetWorker().my_coupon_available(MyApplication.getInstance().getUser().getToken());
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
        rcy_list.refreshSuccess();
        switch (information){
            case COUPON_GET:
                getNetWorker().my_coupon(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
                break;
            case COUPON_AVAILABLE:
                HemaArrayResult<CouponList> tResult = (HemaArrayResult<CouponList>) baseResult;
               list= tResult.getObjects();
//                int count = 0;
//                if ("1".equals(page.toString())) {
//                    rcy_list.refreshSuccess();
//                    list.clear();
//                    list.addAll(temp);
//                    if (temp.size() < 20) {
//                        rcy_list.setLoadingMoreEnabled(false);
//                    } else {
//                        rcy_list.setLoadingMoreEnabled(true);
//                    }
//
//                } else {
//                    rcy_list.loadMoreComplete();
//                    if (temp.size() > 0) {
//                        list.addAll(temp);
//                    } else {
//                        rcy_list.setLoadingMoreEnabled(false);
//                        ToastUtil.showShortToast(mContext, "已经到最后啦");
//                    }
//                }
                adapter=new MyCouponAdapter(mContext,list);
                rcy_list.setAdapter(adapter);
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        rcy_list.refreshFail();
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        iv_empty=findViewById(R.id.iv_empty);
        tv_get=findViewById(R.id.tv_get);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        tv_get.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("我的优惠券");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingMoreEnabled(false);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().my_coupon_available(MyApplication.getInstance().getUser().getToken());

//                getNetWorker().my_coupon(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }

            @Override
            public void onLoadMore() {
                page++;
//                getNetWorker().my_coupon(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_get:
                it=new Intent(mContext,CouponListActivity.class);
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
