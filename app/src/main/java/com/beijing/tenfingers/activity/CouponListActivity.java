package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.CouponAdapter;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;

public class CouponListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_get;
    private XRecyclerView rcy_list;
    private ArrayList<CouponList> list=new ArrayList<>();
    private CouponAdapter adapter;
    private Integer page = 1;
    private ImageView iv_empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_list);
        super.onCreate(savedInstanceState);
        getNetWorker().coupon_list(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");


    }

    public void getCoupon(String id){
        getNetWorker().coupon_get(MyApplication.getInstance().getUser().getToken(),id);
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
            case COUPON_GET:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.COUPON_GET));
                ToastUtils.show("领取成功！");
                getNetWorker().coupon_list(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finishAc(mContext);
//                    }
//                },2000);
                break;
            case COUPON_LIST:
                HemaArrayResult<CouponList> tResult = (HemaArrayResult<CouponList>) baseResult;
                ArrayList<CouponList> temp = tResult.getObjects();
                int count = 0;
                if ("1".equals(page.toString())) {
                    rcy_list.refreshSuccess();
                    list.clear();
                    list.addAll(temp);
                    if (temp.size() < 20) {
                        rcy_list.setLoadingMoreEnabled(false);
                    } else {
                        rcy_list.setLoadingMoreEnabled(true);
                    }
                } else {
                    rcy_list.loadMoreComplete();
                    if (temp.size() > 0) {
                        list.addAll(temp);
                    } else {
                        rcy_list.setLoadingMoreEnabled(false);
                        ToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }
                adapter=new CouponAdapter(mContext,list);
                rcy_list.setAdapter(adapter);
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_get=findViewById(R.id.tv_get);
        iv_empty=findViewById(R.id.iv_empty);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        tv_get.setVisibility(View.GONE);
        ll_back.setOnClickListener(this);
        tv_title.setText("领取优惠券");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().coupon_list(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().coupon_list(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
