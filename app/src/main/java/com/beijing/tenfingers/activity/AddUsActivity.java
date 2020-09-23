package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class AddUsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title, tv_teach, tv_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_addus);
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
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        tv_teach = findViewById(R.id.tv_teach);
        tv_shop = findViewById(R.id.tv_shop);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("加入我们");
        tv_teach.setOnClickListener(this);
        tv_shop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()) {
            case R.id.tv_teach:
                it=new Intent(mContext,TeacherJoinActivity.class);
                startActivity(it);
                changeAc();
                break;
            case R.id.tv_shop:
                it=new Intent(mContext,ShopJoinActivity.class);
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
