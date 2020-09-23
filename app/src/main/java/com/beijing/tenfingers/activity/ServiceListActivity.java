package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ServiceListAdapter;
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
 * 根据技师选择项目
 */
public class ServiceListActivity  extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private ArrayList<String> list=new ArrayList<>();
    private ServiceListAdapter adapter;
    private String tid="";
    private Integer page=1;
    private ArrayList<Product> products=new ArrayList<>();
    private ServiceList serviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopteachers);
        super.onCreate(savedInstanceState);
        getNetWorker().get_pro_teach(tid,page.toString(),"20");
    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case GET_PRO_TEACH:
                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case GET_PRO_TEACH:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case GET_PRO_TEACH:
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
                    adapter=new ServiceListAdapter(mContext,products,tid);
                    rcy_list.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case GET_PRO_TEACH:

                break;
        }
    }

    @Override
    protected void findView() {
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);

    }

    @Override
    protected void getExras() {
        tid=mIntent.getStringExtra("tid");
    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("选择项目");
        BaseUtil.initRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNetWorker().get_pro_teach(tid,page.toString(),"20");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_pro_teach(tid,page.toString(),"20");
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
