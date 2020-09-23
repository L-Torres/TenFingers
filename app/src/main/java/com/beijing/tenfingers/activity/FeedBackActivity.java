package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ReasonAdapter;
import com.beijing.tenfingers.bean.Reason;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.ArrayList;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_hand;
    private RecyclerView rcy_list;
    private ReasonAdapter adapter;
    private ArrayList<Reason> list=new ArrayList<>();
    private EditText ed_advice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feedback);
        super.onCreate(savedInstanceState);
        list.add(new Reason("卡顿",false));
        list.add(new Reason("闪退",false));
        list.add(new Reason("白屏",false));
        list.add(new Reason("其他",false));
        adapter=new ReasonAdapter(mContext,list);
        rcy_list.setAdapter(adapter);

    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case FEED_BACK:
                showProgressDialog("请稍后");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case FEED_BACK:
                cancelProgressDialog();

                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case FEED_BACK:
                ToastUtils.show("反馈成功！");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAc(mContext);
                    }
                },1000);
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case FEED_BACK:

                break;
        }
    }

    @Override
    protected void findView() {
        tv_hand=findViewById(R.id.tv_hand);
        ed_advice=findViewById(R.id.ed_advice);
        rcy_list=findViewById(R.id.rcy_list);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        tv_hand.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("意见反馈");
        BaseUtil.initRecyleVertical(mContext,rcy_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_hand:
                String advice=ed_advice.getText().toString().trim();
                String r="";
                for (Reason reason : list) {
                    if(reason.isFlag()){
                        r=reason.getContent();
                    }
                }
                getNetWorker().feed_back(MyApplication.getInstance().getUser().getToken(),r,advice);
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
