package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.AddressAdapter;
import com.beijing.tenfingers.adapter.CollectAdapter;
import com.beijing.tenfingers.adapter.MsgListAdapter;
import com.beijing.tenfingers.bean.Hobby;
import com.beijing.tenfingers.bean.MessageList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class CollectActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private CollectAdapter adapter;
    private ArrayList<Hobby> list=new ArrayList<>();
    private Integer page=1;
    private PopTipDialog popTipDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
        getNetWorker().tech_collect(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");

    }

    public void cancel(String tid){

        popTipDialog=new PopTipDialog(mContext);
        popTipDialog.setCancelable(true);
        popTipDialog.setTip("取消收藏？");
        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
            @Override
            public void onLeftButtonClick(PopTipDialog dialog) {
                dialog.cancel();
            }

            @Override
            public void onRightButtonClick(PopTipDialog dialog) {
                dialog.cancel();
                getNetWorker().collect_cancel(MyApplication.getInstance().getUser().getToken(),tid);
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
            case COLLECT_CANCEL:
            case TEACHER_COLLECT:

                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case COLLECT_CANCEL:
            case TEACHER_COLLECT:

                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case COLLECT_CANCEL:
                getNetWorker().tech_collect(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
                ToastUtils.show("操作成功！");
                break;
            case TEACHER_COLLECT:
                HemaArrayResult<Hobby> mResult= (HemaArrayResult<Hobby>) baseResult;
                ArrayList<Hobby> temp=new ArrayList<>();
                temp=mResult.getObjects();
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
                    adapter =new CollectAdapter(mContext,list);
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
        tv_title.setText("收藏列表");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setPullRefreshEnabled(true);
        rcy_list.setLoadingMoreEnabled(true);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().tech_collect(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().tech_collect(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
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
