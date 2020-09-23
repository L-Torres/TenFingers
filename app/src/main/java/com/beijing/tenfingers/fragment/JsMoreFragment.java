package com.beijing.tenfingers.fragment;

import android.os.Bundle;

import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.JsMoreAdapter;
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

//技师查看更多
public class JsMoreFragment extends MyBaseFragment {

    private int position;
    private XRecyclerView rcy_list;
    private JsMoreAdapter adpter;
    private ArrayList<String> list=new ArrayList<>();
    public JsMoreFragment() {
        super();
    }
    private ArrayList<Technicians> teachers=new ArrayList<>();
    private Technician t;
    private Integer page=1;
    private String orderType="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_list);
        super.onCreate(savedInstanceState);
        getNetWorker().get_teacher("1","1","20",
                BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),orderType);

    }
    @Override
    protected void findView() {
        rcy_list= (XRecyclerView) findViewById(R.id.rcy_list);
    }

    @Override
    protected void setListener() {
        BaseUtil.initXRecyleVertical(getActivity(),rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().get_teacher("1","1","20",
                        BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),orderType);
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_teacher("1",page.toString(),"20",
                        BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),orderType);
            }
        });
    }

    public static JsMoreFragment getInstance(int position,String orderType) {
        JsMoreFragment fragment = new JsMoreFragment();
        fragment.position = position;
        fragment.orderType = orderType;
        return fragment;
    }





    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case TECH_NICIAN:
                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case TECH_NICIAN:
              cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case TECH_NICIAN:
                HemaArrayResult<Technician> pResult= (HemaArrayResult<Technician>) baseResult;
                t=pResult.getObjects().get(0);
                ArrayList<Technicians> temp=t.getList();
                if ("1".equals(page.toString())) {
                    rcy_list.refreshSuccess();
                    teachers.clear();
                    teachers.addAll(temp);
                    if (temp.size() < 20) {
                        rcy_list.setLoadingMoreEnabled(false);
                    } else {
                        rcy_list.setLoadingMoreEnabled(true);
                    }
                } else {
                    rcy_list.loadMoreComplete();
                    if (temp.size() > 0) {
                        teachers.addAll(temp);
                    } else {
                        rcy_list.setLoadingMoreEnabled(false);
                        ToastUtil.showShortToast(getActivity(), "已经到最后啦");
                    }
                }
                if(adpter==null){
                    adpter=new JsMoreAdapter(getActivity(),teachers);
                    rcy_list.setAdapter(adpter);
                }else{
                    adpter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case TECH_NICIAN:

                break;
        }
    }
}
