package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.MainActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.User;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import xtom.frame.util.XtomSharedPreferencesUtil;

public class RegisterTwoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private ImageView iv_login;
    private TextView tv_title;
    private EditText ed_one,ed_two;
    private String sign = "";
    private String password="";
    private String again_password="";
    private String mobile="";
    private String code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register2);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CLIENT_LOGIN:
            case CLIENT_ADD:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CLIENT_LOGIN:
            case CLIENT_ADD:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CLIENT_LOGIN:
                HemaArrayResult<User> fResult = (HemaArrayResult<User>) baseResult;
                User user = fResult.getObjects().get(0);
                getApplicationContext().setUser(user);
                XtomSharedPreferencesUtil.save(mContext, "user_id", user.getId());
                XtomSharedPreferencesUtil.save(mContext, "username",mobile);
                XtomSharedPreferencesUtil.save(mContext, "password", password);
                XtomSharedPreferencesUtil.save(mContext, "token", user.getToken());
                Intent mainIt = new Intent(mContext, MainActivity.class);
                startActivity(mainIt);
                break;
            case CLIENT_ADD:
                getNetWorker().clientLogin(mobile, HemaUtil.getMD5String(password));
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        ed_one=findViewById(R.id.ed_one);
        ed_two=findViewById(R.id.ed_two);
        ll_back=findViewById(R.id.ll_back);
        iv_login=findViewById(R.id.iv_login);
        tv_title=findViewById(R.id.tv_title);

    }

    @Override
    protected void getExras() {

        mobile=mIntent.getStringExtra("mobile");
        code=mIntent.getStringExtra("code");
        sign=mIntent.getStringExtra("sign");

    }

    @Override
    protected void setListener() {

        tv_title.setVisibility(View.INVISIBLE);
        iv_login.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finishAc(mContext);
                break;
            case R.id.iv_login:
                password=ed_one.getText().toString().trim();
                again_password=ed_two.getText().toString().trim();
                if(isNull(password)){
                    ToastUtils.show("请填写密码！");
                    return;
                }
                if(isNull(again_password)){
                    ToastUtils.show("请填写确认密码！");
                    return;
                }
                if(!password.equals(again_password)){
                    ToastUtils.show("密码输入不一致！");
                    return;
                }
                getNetWorker().clientAdd(mobile, HemaUtil.getMD5String(password),HemaUtil.getMD5String(again_password),code,sign);
                break;
        }
    }
}
