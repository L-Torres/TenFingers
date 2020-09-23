package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.MainActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ReasonAdapter;
import com.beijing.tenfingers.bean.User;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText  ed_phone,ed_pwd;
    private TextView tv_register,tv_title,tv_forget,tv_tip,tv_secret;
    private ImageView iv_login;
    private LinearLayout ll_back;
    private String phone="";
    private String pwd="";
    private String purchase="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
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
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CLIENT_LOGIN:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.CLIENT_LOGIN));
                HemaArrayResult<User> fResult = (HemaArrayResult<User>) baseResult;
                User user = fResult.getObjects().get(0);
                getApplicationContext().setUser(user);
                XtomSharedPreferencesUtil.save(mContext, "user_id", user.getId());
                XtomSharedPreferencesUtil.save(mContext, "username",phone);
                XtomSharedPreferencesUtil.save(mContext, "password", pwd);
                XtomSharedPreferencesUtil.save(mContext, "token", user.getToken());
                if (isNull(purchase)) {
                    XtomActivityManager.finishAll();
                    Intent it = new Intent(mContext, MainActivity.class);
                    startActivity(it);
                    changeAc();
                }else{
                    finishAc(mContext);
                }
                break;
        }



    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_tip=findViewById(R.id.tv_tip);
        tv_secret=findViewById(R.id.tv_secret);
        tv_forget=findViewById(R.id.tv_forget);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        tv_register=findViewById(R.id.tv_register);
        ed_phone=findViewById(R.id.ed_phone);
        ed_pwd=findViewById(R.id.ed_pwd);
        iv_login=findViewById(R.id.iv_login);

    }

    @Override
    protected void getExras() {
        purchase=mIntent.getStringExtra("purchase");

    }

    @Override
    protected void setListener() {
        tv_title.setVisibility(View.INVISIBLE);
        iv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_tip.setOnClickListener(this);
        tv_secret.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_secret:
                it = new Intent(mContext, WebViewActivity.class);
                it.putExtra("Title", "十指间隐私政策");
                it.putExtra("URL", "https://10zhijian.com/privacy.html");
                startActivity(it);
                changeAc();
                break;
            case R.id.tv_tip://
                it = new Intent(mContext, WebViewActivity.class);
                it.putExtra("Title", "服务协议");
                it.putExtra("URL", "https://10zhijian.com/sla.html");
                startActivity(it);
                changeAc();
                break;
            case R.id.tv_forget:
                it=new Intent(mContext,ChangePwdActivity.class);
                startActivity(it);
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
            case R.id.tv_register:
                it=new Intent(mContext,RegisterOneActivity.class);
                startActivity(it);
                changeAc();
                break;
            case R.id.iv_login:
                phone=ed_phone.getText().toString().trim();
                pwd=ed_pwd.getText().toString().trim();
                if(isNull(phone)){
                    XtomToastUtil.showLongToast(mContext,"请输入手机号！");
                    return;
                }
                if(isNull(pwd)){
                    XtomToastUtil.showLongToast(mContext,"请输入密码！");
                    return;
                }
                getNetWorker().clientLogin(phone, HemaUtil.getMD5String(pwd));

                break;
        }
    }
}
