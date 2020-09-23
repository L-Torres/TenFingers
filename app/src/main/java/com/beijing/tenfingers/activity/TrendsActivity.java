package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.TrendsAdapter;
import com.beijing.tenfingers.bean.TrendsList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.interf.ZanClickListener;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayerManager;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;

public class TrendsActivity extends BaseActivity implements View.OnClickListener, ZanClickListener {
    private LinearLayout ll_back;
    private TextView tv_title,tv_right;
    private XRecyclerView rcy_list;
    private Integer page=0;
    private TrendsAdapter adapter;
    private ArrayList<TrendsList> list=new ArrayList<>();
    private String id;
    private String is_vip="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trends);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().trendsList(page.toString(),"20",
                id,is_vip);

    }
    @Override
    protected void onDestroy() {
        if(JCVideoPlayerManager.getCurrentJcvd()!=null){
            JCVideoPlayerManager.getCurrentJcvd().release();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.TRENDS_UPDATE:
                getNetWorker().trendsList(page.toString(),"20",
                        id,is_vip);
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
            case THUMB_OPERATE:

                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.DIAN_ZAN));
                getNetWorker().trendsList(page.toString(),"20",
                        id,is_vip);

                break;
            case TRENDS_LIST:
                HemaArrayResult<TrendsList> mResult= (HemaArrayResult<TrendsList>) baseResult;
                ArrayList<TrendsList> temp=new ArrayList<>();
                temp=mResult.getObjects();

                if ("0".equals(page.toString())) {
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
                        XtomToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }

                if(adapter==null){
                    adapter=new TrendsAdapter(mContext,list,0);
                    adapter.setListener(this);
                    rcy_list.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
                break;
        }

    }


    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_right=findViewById(R.id.tv_right);

        rcy_list=findViewById(R.id.rcy_list);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=0;
                getNetWorker().trendsList(page.toString(),"20",
                        id,is_vip);
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().trendsList(page.toString(),"20",
                        id,is_vip);
            }
        });
    }

    @Override
    protected void getExras() {
        id=mIntent.getStringExtra("id");
        is_vip=mIntent.getStringExtra("is_vip");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_title.setText("动态");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_right:
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }

    @Override
    public void thumb(String id,int type) {
        if(type==0){
            getNetWorker().thumb("2",id);
        }else{
            getNetWorker().thumb("1",id);
        }

    }

    @Override
    public void collect(String id) {

    }

    @Override
    public void addToCart(String id) {

    }
}
