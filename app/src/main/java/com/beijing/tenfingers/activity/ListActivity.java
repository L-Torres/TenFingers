package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ListAdapter;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ServiceList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * 列表页
 *
 */
public class ListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_get;
    private XRecyclerView rcy_list;
    private ArrayList<Product> products=new ArrayList<>();
    private ServiceList serviceList;
    private ListAdapter adapter;
    private Integer page = 1;
    private ImageView iv_empty;
    private String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_list);
        super.onCreate(savedInstanceState);
        getNetWorker().get_service("1",page.toString(),"20",
                BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),key,"");

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
            case SERVICE_PRODUCT:
                HemaArrayResult<ServiceList> pResult= (HemaArrayResult<ServiceList>) baseResult;
                serviceList=pResult.getObjects().get(0);
                ArrayList<Product> temp=serviceList.getChildren();
                if ("1".equals(page.toString())) {
                    rcy_list.refreshSuccess();
                    products.clear();
                    products.addAll(temp);
                    if (temp.size() < 20) {
                        rcy_list.setLoadingMoreEnabled(false);
                    } else {
                        rcy_list.setLoadingMoreEnabled(true);
                    }
                } else {
                    rcy_list.loadMoreComplete();
                    if (temp.size() > 0) {
                        products.addAll(temp);
                    } else {
                        rcy_list.setLoadingMoreEnabled(false);
                        ToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }
                if(adapter==null){
                    adapter=new ListAdapter(mContext,products);
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
        tv_get=findViewById(R.id.tv_get);
        iv_empty=findViewById(R.id.iv_empty);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);
    }

    @Override
    protected void getExras() {
        key=mIntent.getStringExtra("key");

    }

    @Override
    protected void setListener() {
        tv_get.setVisibility(View.GONE);
        ll_back.setOnClickListener(this);
        tv_title.setText("搜索结果");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().get_service("1",page.toString(),"20",
                        BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),key,"");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_service("1",page.toString(),"20",
                        BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),key,"");            }
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
