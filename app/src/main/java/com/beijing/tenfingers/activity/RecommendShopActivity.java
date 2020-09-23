package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.DianpuAdapter;
import com.beijing.tenfingers.adapter.FirstFuwuAdapter;
import com.beijing.tenfingers.adapter.JishiAdapter;
import com.beijing.tenfingers.adapter.JsMoreAdapter;
import com.beijing.tenfingers.adapter.RcShopAdapter;
import com.beijing.tenfingers.bean.Count;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ShopChild;
import com.beijing.tenfingers.bean.ShopList;
import com.beijing.tenfingers.bean.Technician;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class RecommendShopActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private ArrayList<ShopChild> list=new ArrayList<>();
    private RcShopAdapter adapter;
    private Integer page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rcshop_list);
        super.onCreate(savedInstanceState);

        getNetWorker().get_shops("1",page.toString(),"20",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext));
        adapter=new RcShopAdapter(mContext,list);
        rcy_list.setAdapter(adapter);


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
            case INDEX_SHOP:
                HemaArrayResult<ShopList> pResult= (HemaArrayResult<ShopList>) baseResult;
                ArrayList<ShopChild> temp=pResult.getObjects().get(0).getChildren();
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
                if(adapter==null){
                    adapter=new RcShopAdapter(mContext,list);
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
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("推荐店铺");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=0;
                getNetWorker().get_shops("1",page.toString(),"20",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext));
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_shops("1",page.toString(),"20",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext));

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
