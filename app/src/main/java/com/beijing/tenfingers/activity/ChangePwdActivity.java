package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.GetCode;
import com.beijing.tenfingers.bean.User;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_right,tv_code,tv_hand;
    private String phone="";
    private String code="";
    private EditText ed_phone,ed_code;
    private String sign = "";
    private int count = 60;
    private Handler timeHandler = new Handler();
    private EditText ed_pwd,ed_pwd_again;
    private   String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bindphone);
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CHANGE_PWD:
            case CODE_GET:
                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CHANGE_PWD:
            case CODE_GET:
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
                XtomSharedPreferencesUtil.save(mContext, "username",phone);
                XtomSharedPreferencesUtil.save(mContext, "password", pwd);
                XtomSharedPreferencesUtil.save(mContext, "token", user.getToken());
                ToastUtils.show("修改密码成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAc(mContext);
                    }
                },1000);
                break;
            case CHANGE_PWD:


                getNetWorker().clientLogin(phone,HemaUtil.getMD5String(pwd));


                break;
            case CODE_GET:
                HemaArrayResult<GetCode> gResult = (HemaArrayResult<GetCode>) baseResult;
                GetCode getCode = (GetCode) gResult.getObjects().get(0);
                sign = getCode.getSign();
                timeHandler.post(runnable);
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CHANGE_PWD:
            case CODE_GET:
                ToastUtils.show(baseResult.getError_message());
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_hand=findViewById(R.id.tv_hand);
        ed_pwd_again=findViewById(R.id.ed_pwd_again);
        ed_pwd=findViewById(R.id.ed_pwd);
        tv_right=findViewById(R.id.tv_right);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        tv_right.setVisibility(View.VISIBLE);
        tv_code=findViewById(R.id.tv_code);
        ed_phone=findViewById(R.id.ed_phone);
        ed_code=findViewById(R.id.ed_code);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("修改密码");
        tv_right.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        tv_hand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_hand:
                phone=ed_phone.getText().toString().trim();
                code=ed_code.getText().toString().trim();
                 pwd=ed_pwd.getText().toString().trim();
                String again=ed_pwd_again.getText().toString().trim();
                if(isNull(phone)){
                    ToastUtils.show("请填写手机号！");
                    return;
                }
                if(isNull(code)){
                    ToastUtils.show("请填写验证码！");
                    return;
                }
                if(isNull(pwd)){
                    ToastUtils.show("请填写新密码！");
                    return;
                }
                if(isNull(again)){
                    ToastUtils.show("请再次填写新密码！");
                    return;
                }

                if(isNull(sign)){
                    ToastUtils.show("请先获取验证码！");
                    return;
                }

                getNetWorker().change_pwd(phone, HemaUtil.getMD5String(pwd),code,sign);

                break;
            case R.id.tv_code:
                if (isNull(ed_phone.getText().toString().trim())) {
                    ToastUtils.show("请输入手机号！");
                    return;
                }
                getNetWorker().code_get(ed_phone.getText().toString(), "forget");
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
            case R.id.tv_right:
                phone=ed_phone.getText().toString().trim();
                code=ed_code.getText().toString().trim();
                if (isNull(phone)) {
                    XtomToastUtil.showLongToast(mContext,"请填写手机号！");
                    return;
                }
                if (isNull(code)) {
                    XtomToastUtil.showLongToast(mContext,"请填写验证码！");
                    return;
                }
                String org= XtomSharedPreferencesUtil.get(mContext,"username");

                if(isNull(sign)){

                }


                break;
        }
    }

    /**
     * 验证码计时器
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count == 0) {
                timeHandler.removeCallbacks(runnable);
                tv_code.setText("发送验证码");
                tv_code.setEnabled(true);
                count = 60;
            } else {
                count -= 1;
                tv_code.setEnabled(false);
                tv_code.setText(count + "秒重新发送");
                timeHandler.postDelayed(runnable, 1000);
            }
        }
    };
}
