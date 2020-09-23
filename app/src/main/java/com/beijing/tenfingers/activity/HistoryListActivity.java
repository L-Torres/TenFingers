package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.HistoryAdapter;
import com.beijing.tenfingers.bean.History;
import com.beijing.tenfingers.bean.MyHistory;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.PopTipDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class HistoryListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private HistoryAdapter adapter;
    private ArrayList<MyHistory> list=new ArrayList<>();
    private Integer page=1;
    private History history;
    private PopTipDialog popTipDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.bg_total, 0.07f).init();
        getNetWorker().my_history(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
    }
    public void delete(String id){
        popTipDialog = new PopTipDialog(mContext);
        popTipDialog.setCancelable(true);
        popTipDialog.setTip("确定删除？");
        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
            @Override
            public void onLeftButtonClick(PopTipDialog dialog) {
                dialog.cancel();
            }
            @Override
            public void onRightButtonClick(PopTipDialog dialog) {
                dialog.cancel();
                getNetWorker().history_delete(MyApplication.getInstance().getUser().getToken(), id);
            }
        });
        popTipDialog.show();
    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_HISTORY:

                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_HISTORY:

                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case HISTORY_DELETE:
                getNetWorker().my_history(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");

                break;
            case MY_HISTORY:
                HemaArrayResult<History> mResult= (HemaArrayResult<History>) baseResult;
                history=mResult.getObjects().get(0);
                ArrayList<MyHistory> temp=new ArrayList<>();
                temp=history.getChildren();
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
                    adapter =new HistoryAdapter(mContext,list);
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
            case TEACHER_COLLECT:

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

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("浏览历史");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setPullRefreshEnabled(true);
        rcy_list.setLoadingMoreEnabled(true);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().my_history(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().my_history(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
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
