package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.Address;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_area,tv_address_choice,tv_right;
    private EditText ed_name,ed_phone,ed_area;
    private ImageView iv_pic;
    private Button btn_save;
    private String province_id="0";
    private String city_id="0";
    private String distinct_id="0";
    private String addr_detail="";
    private String username="";
    private String mobile="";
    private String is_default="1";
    private String province="";
    private String city="";
    private String distinct="";
    private final int REQUEST_LOC = 0x400;//地址选择成功
    private String lng="";
    private String lat="";
    private Address address=null;
    private PopTipDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_newaddress);
        super.onCreate(savedInstanceState);
        if(address!=null){
            tv_title.setText("编辑收货地址");
            setData();
        }
    }

    private void setData(){
        tv_right.setOnClickListener(this);
        tv_right.setText("删除");
        tv_right.setVisibility(View.VISIBLE);
        is_default=address.getIs_default();
        province=address.getProvince();
        city=address.getCity();
        distinct=address.getDistinct();
        addr_detail=address.getAddr_detail();
        province=address.getAddr_content();
        username=address.getUsername();
        mobile=address.getMobile();
        ed_name.setText(username);
        ed_phone.setText(address.getMobile());
        tv_area.setText(address.getAddr_content());
        ed_area.setText(address.getAddr_detail());
        if("1".equals(address.getIs_default())){
            iv_pic.setImageResource(R.mipmap.default_n);
        }else{
            iv_pic.setImageResource(R.mipmap.default_y);
        }
        lat=address.getLatitude();
        lng=address.getLongitude();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_SAVE:
                showProgressDialog("请稍后");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_SAVE:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_DEL:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.ADDRESS_ADD));
                ToastUtils.show("地址删除成功！");
                finishAc(mContext);
                break;
            case DELIVERY_ADDRESS_EDIT:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.ADDRESS_ADD));
                ToastUtils.show("地址修改成功！");
                finishAc(mContext);
                break;
            case DELIVERY_ADDRESS_SAVE:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.ADDRESS_ADD));
                ToastUtils.show("地址添加成功！");
                finishAc(mContext);
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_SAVE:

                break;
        }
    }

    @Override
    protected void findView() {
        tv_right=findViewById(R.id.tv_right);
        tv_address_choice=findViewById(R.id.tv_address_choice);
        btn_save=findViewById(R.id.btn_save);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        tv_area=findViewById(R.id.tv_area);
        ed_name=findViewById(R.id.ed_name);
        ed_phone=findViewById(R.id.ed_phone);
        ed_area=findViewById(R.id.ed_area);
        iv_pic=findViewById(R.id.iv_pic);
    }

    @Override
    protected void getExras() {
        address= (Address) mIntent.getSerializableExtra("object");

    }

    @Override
    protected void setListener() {
        iv_pic.setOnClickListener(this);
        tv_area.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("新建收货地址");
        tv_address_choice.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_right:
                dialog = new PopTipDialog(mContext);
                dialog.setTip("确定删除？");
                dialog.setButtonListener(new PopTipDialog.OnButtonListener() {
                    @Override
                    public void onLeftButtonClick(PopTipDialog dialog) {
                        dialog.cancel();
                    }

                    @Override
                    public void onRightButtonClick(PopTipDialog dialog) {
                        dialog.cancel();
                        getNetWorker().addressDel(MyApplication.getInstance().getUser().getToken(),
                                address.getId());
                    }
                });
                dialog.show();

                break;
            case R.id.iv_pic:
                if("1".equals(is_default)){
                    is_default="0";
                    iv_pic.setImageResource(R.mipmap.default_n);
                }else{
                    is_default="1";
                    iv_pic.setImageResource(R.mipmap.default_y);
                }

                break;
            case R.id.btn_save:
                username=ed_name.getText().toString().trim();
                mobile=ed_phone.getText().toString().trim();
                addr_detail=ed_area.getText().toString().trim();
                if(isNull(username)){
                    XtomToastUtil.showLongToast(mContext,"请填写收货人！");
                    return;
                }
                if(isNull(mobile)){
                    XtomToastUtil.showLongToast(mContext,"请填写联系电话！");
                    return;
                }
                if(isNull(tv_area.getText().toString().trim())){
                    XtomToastUtil.showLongToast(mContext,"请选择所在地区");
                    return;
                }
                if(isNull(addr_detail)){
                    XtomToastUtil.showLongToast(mContext,"请填写所在地区");
                    return;
                }
//                addr_detail=tv_area.getText().toString().trim()+ed_area.getText().toString().trim();
                addr_detail=ed_area.getText().toString().trim();
                if(address!=null){
                    getNetWorker().AddressEdit(address.getId(),MyApplication.getInstance().getUser().getToken(),
                            "0","0","0",addr_detail,username,mobile,
                            "1",province,city,distinct,lng,lat);
                }else{

                    getNetWorker().AddressSave(MyApplication.getInstance().getUser().getToken(),
                            "0","0","0",addr_detail,username,mobile,
                            "1",province,city,distinct,lng,lat);
                }
                break;
            case R.id.tv_area:
            case R.id.tv_address_choice:
                it = new Intent(mContext, SelectShopPositionActivity.class);
                startActivityForResult(it, REQUEST_LOC);
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case REQUEST_LOC:
                lng = data.getStringExtra("lng");
                lat = data.getStringExtra("lat");
                String alladdress = data.getStringExtra("address");
                String  address = data.getStringExtra("detail");
                tv_area.setText(alladdress);
                ed_area.setText(address);
                province= data.getStringExtra("province");
                city= data.getStringExtra("city");
                distinct= data.getStringExtra("distinct");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
