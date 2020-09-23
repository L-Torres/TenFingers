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
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.GetCode;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class RegisterOneActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private ImageView iv_login;
    private TextView  tv_code,tv_title;
    private EditText  ed_phone,ed_code;
    private String sign = "";
    private int count = 60;
    private Handler timeHandler = new Handler();
    private  String mobile="";
    private  String code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register1);
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
            case CODE_GET:
                HemaArrayResult<GetCode> gResult = (HemaArrayResult<GetCode>) baseResult;
                GetCode getCode = (GetCode) gResult.getObjects().get(0);
                sign = getCode.getSign();
                timeHandler.post(runnable);
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {

        ll_back=findViewById(R.id.ll_back);
        iv_login=findViewById(R.id.iv_login);
        tv_code=findViewById(R.id.tv_code);
        tv_title=findViewById(R.id.tv_title);
        ed_phone=findViewById(R.id.ed_phone);
        ed_code=findViewById(R.id.ed_code);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setVisibility(View.INVISIBLE);
        tv_code.setOnClickListener(this);
        iv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.iv_login:
                mobile=ed_phone.getText().toString().trim();
                code=ed_code.getText().toString().trim();
                if(isNull(mobile)){
                    ToastUtils.show("请填写手机号！");
                    return;
                }
                if(isNull(code)){
                    ToastUtils.show("请填写验证码！");
                    return;
                }
                it=new Intent(mContext,RegisterTwoActivity.class);
                it.putExtra("mobile",mobile);
                it.putExtra("code",code);
                it.putExtra("sign",sign);
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
            case R.id.tv_code://发送验证码
                if (isNull(ed_phone.getText().toString().trim())) {
                    ToastUtil.showLongToast(mContext, "请输入手机号！");
                    return;
                }
                getNetWorker().code_get(ed_phone.getText().toString(), "register");
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
