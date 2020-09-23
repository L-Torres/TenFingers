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
import com.beijing.tenfingers.adapter.SelectJsAdapter;
import com.beijing.tenfingers.bean.Technician;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.PopTipDialog;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * 选择技师
 */
public class SelectTecActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_right;
    private XRecyclerView rcy_list;
    private SelectJsAdapter adapter;
    private Integer page = 1;
    private ArrayList<Technicians> lists=new ArrayList<>();
    private String id;
    private String type="";//0 服务项目到技师 1技师到服务项目
    private String sid;
    private String is_vip="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
        if("1".equals(is_vip)){
            getNetWorker().get_teach_pro(id,page.toString(),"10",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),is_vip);
        }else{

            getNetWorker().get_teach_pro(id,page.toString(),"20",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),"");
        }

    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case GET_TEACH_PRO:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case GET_TEACH_PRO:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case GET_TEACH_PRO:
                HemaArrayResult<Technician> mResult= (HemaArrayResult<Technician>) baseResult;
                Technician t=mResult.getObjects().get(0);
                ArrayList<Technicians> temp=new ArrayList<>();
                temp=t.getList();
                if ("1".equals(page.toString())) {
                    rcy_list.refreshSuccess();
                    lists.clear();
                    lists.addAll(temp);
                    if (temp.size() < 20) {
                        rcy_list.setLoadingMoreEnabled(false);
                    } else {
                        rcy_list.setLoadingMoreEnabled(true);
                    }
                } else {
                    rcy_list.loadMoreComplete();
                    if (temp.size() > 0) {
                        lists.addAll(temp);
                    } else {
                        rcy_list.setLoadingMoreEnabled(false);
                        ToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }
                if(adapter==null){
                    adapter =new SelectJsAdapter(mContext,lists,type,id);
                    rcy_list.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case NOTIFY:

                break;
        }
    }

    @Override
    protected void findView() {
        tv_right=findViewById(R.id.tv_right);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);

    }

    @Override
    protected void getExras() {
        sid=mIntent.getStringExtra("sid");
        id=mIntent.getStringExtra("id");
        type=mIntent.getStringExtra("type");
        is_vip=mIntent.getStringExtra("is_vip");
    }

    @Override
    protected void setListener() {
        tv_right.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("技师");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setPullRefreshEnabled(true);
        rcy_list.setLoadingMoreEnabled(true);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().get_teach_pro(id,page.toString(),"20",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),is_vip);
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_teach_pro(id,page.toString(),"20",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),is_vip);

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }

}
