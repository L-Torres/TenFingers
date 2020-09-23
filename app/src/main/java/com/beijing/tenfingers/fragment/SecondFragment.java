package com.beijing.tenfingers.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.SearchActivity;
import com.beijing.tenfingers.adapter.FuwuAdapter;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ServiceList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.RandomDialog;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomSharedPreferencesUtil;

public class SecondFragment  extends MyBaseFragment {

    private XRecyclerView rcy_list;
    private FuwuAdapter adapter;
    private ArrayList<Product> products=new ArrayList<>();
    private ServiceList serviceList;
    private Integer page=1;
    private ImageView iv_search;
    private RandomDialog randomDialog;//根据后台返回来判断弹窗的位置和弹窗时间
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_second);
        super.onCreate(savedInstanceState);
        getNetWorker().get_service("1",page.toString(),"20",
                BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),"","");


        ArrayList<PopBean> temp=new ArrayList<>();
        if(MyApplication.getInstance().getQuestion()!=null){
            temp=MyApplication.getInstance().getQuestion();
            if(temp.size()>0){
                String date=BaseUtil.getCurrentDate();
                String coupon= XtomSharedPreferencesUtil.get(getActivity(),"project");
                for (PopBean popBean : temp) {
                    if(popBean.getKey_type().equals("1")){//项目
                        String flag=popBean.getId()+"1"+date;
                        if(!coupon.equals(flag)){
                            //加载的弹窗组合标识跟存储的组合标识不一致时需要弹出
                            randomDialog = new RandomDialog(XtomActivityManager.getLastActivity(), popBean);
                            XtomSharedPreferencesUtil.save(getActivity(),"project",flag);
                            break;
                        }
                    }
                }
            }
        }




    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case SERVICE_PRODUCT:
//                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case SERVICE_PRODUCT:
//                cancelProgressDialog();
                break;
        }
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
                        ToastUtil.showShortToast(getActivity(), "已经到最后啦");
                    }
                }
                if(adapter==null){
                    adapter=new FuwuAdapter(getActivity(),products);
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
            case SERVICE_PRODUCT:

                break;
        }
    }

    @Override
    protected void findView() {
        iv_search= (ImageView) findViewById(R.id.iv_search);
        rcy_list= (XRecyclerView) findViewById(R.id.rcy_list);
    }

    @Override
    protected void setListener() {
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getActivity(), SearchActivity.class);
                startActivity(it);
            }
        });
        BaseUtil.initXRecyleVertical(getActivity(),rcy_list);
       rcy_list.setPullRefreshEnabled(true);
       rcy_list.setLoadingMoreEnabled(true);
       rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().get_service("1",page.toString(),"20",
                        BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),"","");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_service("1",page.toString(),"20",
                        BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),"","");
            }
        });
    }
}
