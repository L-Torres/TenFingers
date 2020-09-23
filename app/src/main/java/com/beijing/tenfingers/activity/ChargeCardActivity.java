package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class ChargeCardActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView     tv_title,tv_charge;
    private EditText ed_no,ed_pwd;
    private String no="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cahrgecard);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case AUTH_INVEST:
                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case AUTH_INVEST:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case AUTH_INVEST:
                ToastUtils.show("充值成功！");
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case AUTH_INVEST:
                ToastUtils.show(baseResult.getError_message());
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_charge=findViewById(R.id.tv_charge);
        ed_pwd=findViewById(R.id.ed_pwd);
        ed_no=findViewById(R.id.ed_no);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {

        tv_title.setText("充值卡充值");
        ll_back.setOnClickListener(this);
        tv_charge.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_charge:
                no=ed_no.getText().toString().trim();
                password=ed_pwd.getText().toString().trim();
                if(isNull(no)){
                    ToastUtils.show("请输入充值卡号！");
                    return;
                }
                if(isNull(password)){
                    ToastUtils.show("请输入充值卡密码！");
                    return;
                }

                getNetWorker().auth_charge(MyApplication.getInstance().getUser().getToken(),no, password);
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
