package com.beijing.tenfingers.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.view.BottomThreeDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import org.w3c.dom.Text;

import de.greenrobot.event.EventBus;

public class ShiTDFragment extends MyBaseFragment implements View.OnClickListener {

    private EditText ed_name,ed_phone,ed_shop,ed_address;
    private TextView tv_sex,tv_join;
    private String s_sex="";
    private String s_name="";
    private String s_mobile="";
    private String s_shop="";
    private String s_address="";
    private String s_image_link="";
    private String s_products="";
    private BottomThreeDialog sexDialog;
    public ShiTDFragment() {
        super();
    }
    public static ShiTDFragment getInstance(int position) {
        ShiTDFragment fragment = new ShiTDFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_shitidian);
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
                        EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.GO_TO_JOIN));
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
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_shop = (EditText) findViewById(R.id.ed_shop);
        ed_address = (EditText) findViewById(R.id.ed_address);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_join = (TextView) findViewById(R.id.tv_join);
    }

    @Override
    protected void setListener() {
        tv_sex.setOnClickListener(this);
        tv_join.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){

            case R.id.tv_sex:
                selectSex();
                break;
            case R.id.tv_join:
                s_name=ed_name.getText().toString().trim();
                s_mobile=ed_phone.getText().toString().trim();
                s_address=ed_address.getText().toString().trim();
                s_shop=ed_shop.getText().toString().trim();
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
                if(isNull(s_shop)){
                    ToastUtils.show("请填写店铺名称！");
                    return;
                }
                if(isNull(s_address)){
                    ToastUtils.show("请填写详细地址！");
                    return;
                }

                getNetWorker().my_join(s_name,s_mobile,"1",s_sex,s_shop,s_address,"","");

                break;
        }
    }

    private void selectSex(){
        if(sexDialog==null){
            sexDialog=new BottomThreeDialog(getActivity());
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
