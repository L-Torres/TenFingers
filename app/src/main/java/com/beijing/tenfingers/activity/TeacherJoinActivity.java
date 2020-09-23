package com.beijing.tenfingers.activity;

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
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.view.BottomThreeDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class TeacherJoinActivity  extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_sex,tv_join;
    private EditText ed_name,ed_phone;
    private BottomThreeDialog sexDialog;
    private String s_sex="";
    private String s_name="";
    private String s_mobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_teachjoin);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
                showProgressDialog("请稍后");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
                ToastUtils.show("提交成功，稍后将有客服人员联系您！");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAc(mContext);
                    }
                },2000);

                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:

                break;
        }
    }

    @Override
    protected void findView() {
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        ed_name=findViewById(R.id.ed_name);
        ed_phone=findViewById(R.id.ed_phone);
        tv_sex=findViewById(R.id.tv_sex);
        tv_join=findViewById(R.id.tv_join);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("技师入驻");
        tv_sex.setOnClickListener(this);
        tv_join.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_join:
                s_name=ed_name.getText().toString().trim();
                s_mobile=ed_phone.getText().toString().trim();
                if(isNull(s_name)){
                    ToastUtils.show("请填写姓名！");
                    return;
                }
                if(isNull(s_mobile)){
                    ToastUtils.show("请填联系电话！");
                    return;
                }
                if(isNull(s_sex)){
                    ToastUtils.show("请选择性别！");
                    return;
                }

                getNetWorker().my_join(s_name,s_mobile,"5",s_sex,"","","","");
                break;
            case R.id.tv_sex:
                selectSex();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }

    private void selectSex(){
        if(sexDialog==null){
            sexDialog=new BottomThreeDialog(mContext);
            sexDialog.setCancelable(true);
            sexDialog.setTop("性别选择");
            sexDialog.setTopButtonText("男");
            sexDialog.setMiddleButtonText("女");
            sexDialog
                    .setButtonListener(new BottomThreeDialog.OnButtonListener() {
                        @Override
                        public void onTopButtonClick(BottomThreeDialog dialog) {
                            dialog.cancel();
                            tv_sex.setText("男");
                            s_sex="1";
                        }

                        @Override
                        public void onMiddleButtonClick(BottomThreeDialog dialog) {
                            dialog.cancel();
                            tv_sex.setText("女");
                            s_sex="2";
                        }
                    });
        }
        sexDialog.show();
    }
}
