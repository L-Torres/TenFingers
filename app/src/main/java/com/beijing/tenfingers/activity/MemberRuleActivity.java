package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class MemberRuleActivity extends BaseActivity {


    private LinearLayout ll_back;
    private TextView tv_title,tv_rule;
    private String priceLevelText="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_memberrule);
        super.onCreate(savedInstanceState);

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

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_rule=findViewById(R.id.tv_rule);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);


    }

    @Override
    protected void getExras() {
        priceLevelText=mIntent.getStringExtra("priceLevelText");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAc(mContext);
            }
        });
        tv_title.setText("会员规则");
        String temp = priceLevelText.replace("\\n", "n");
        tv_rule.setText(priceLevelText);
    }
}
