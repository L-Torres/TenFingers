package com.beijing.tenfingers.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.beijing.tenfingers.Alipay.PayResult;
import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.OrderDetail;
import com.beijing.tenfingers.bean.SMobile;
import com.beijing.tenfingers.bean.WeixinTrade;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BottomCancelDialog;
import com.beijing.tenfingers.view.CodeDialog;
import com.beijing.tenfingers.view.PopPhoneDialog;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.ToastDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

import de.greenrobot.event.EventBus;

import static com.beijing.tenfingers.activity.KefuActivity.REQUEST_CALL_PERMISSION;

//申请售后页面
public class ApplyRefundActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back,ll_teacher,ll_refund,ll_refund_process,ll_person,ll_go,ll_advance_time;
    private TextView tv_title,tv_status,tv_top,tv_apply,tv_name,tv_address,
            tv_teach_name,tv_time,tv_pro_name,tv_pro_price,tv_pro_content,
            tv_pro_time,tv_oder_number,tv_pay,tv_s_time,tv_trip,tv_remark,tv_call,tv_reason,
            tv_total,tv_coupon,tv_real,tv_to_pay,tv_tip,tv_shop,tv_phone,tv_service_type;
    private MapView mapView;
    private AMap mAMap;
    double latitude = 39.908127;
    double longtitude = 116.375257;
    private OrderDetail detail;
    private RoundedImageView iv_teach;
    private ImageView iv_pro;
    private String id;
    private CodeDialog dialog;
    private String url="";
    private PopTipDialog popTipDialog;
    private BottomCancelDialog cancelDialog;
    private FrameLayout fl_top;
    private String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_orderdetail);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        getNetWorker().order_detail(MyApplication.getInstance().getUser().getToken(),id);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void setData(){
        if("到店服务".equals(detail.getO_service_type())){
            ll_teacher.setVisibility(View.GONE);
            ll_person.setVisibility(View.GONE);
            tv_service_type.setText("到店服务");
            ll_go.setVisibility(View.GONE);
            ll_advance_time.setVisibility(View.GONE);
        }
        tv_shop.setText(detail.getShop_name());
        BaseUtil.loadCircleBitmap(mContext,detail.getT_image_link(),0,iv_teach);
        tv_name.setText(detail.getUsername()+"  "+detail.getMobile());
        tv_address.setText(detail.getAddress()+detail.getAddr_detail());
        BaseUtil.loadBitmap(detail.getP_image_link(),R.mipmap.icon_service,iv_pro);
        tv_pro_name.setText(detail.getP_name());
        tv_pro_price.setText("¥"+detail.getP_price());
        tv_pro_content.setText(detail.getP_desc());
        tv_pro_time.setText(detail.getP_service_time());
        tv_oder_number.setText(detail.getO_no());
        tv_pay.setText(detail.getO_pay_type());
        tv_s_time.setText(detail.getO_advance_time());
        tv_trip.setText(detail.getO_trip_type());
        tv_remark.setText(detail.getO_remark());
        tv_total.setText("¥"+detail.getO_total());
        tv_coupon.setText("-¥"+detail.getO_discount());
        tv_real.setText("¥"+detail.getO_real_total());

        if(detail.getT_latitude()!=null && !"".equals(detail.getT_latitude())){
            latitude=Double.valueOf(detail.getT_latitude());
        }
        if(detail.getT_longitude()!=null && !"".equals(detail.getT_longitude())){
            longtitude=Double.valueOf(detail.getT_longitude());
        }
        initAMap();
        //	订单状态 1 未支付 2 支付成功 待接单 3 已接单待服务 4 进行中 5 待评价 6 已完成 7 已取消
        switch (detail.getO_status()){
            case "1":
                tv_status.setText("待支付");
                tv_top.setText("取消订单");
                tv_apply.setVisibility(View.INVISIBLE);
                ll_teacher.setVisibility(View.GONE);
                mapView.setVisibility(View.GONE);
                tv_tip.setText("订单已经提交，请您尽快完成支付");
                tv_top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_order(detail.getId());
                    }
                });
                tv_to_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopTipDialog popTipDialog=new PopTipDialog(mContext);
                        popTipDialog.setTip("确认申请售后？");
                        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
                            @Override
                            public void onLeftButtonClick(PopTipDialog dialog) {
                                dialog.cancel();
                            }

                            @Override
                            public void onRightButtonClick(PopTipDialog dialog) {
                                getNetWorker().order_proceed(MyApplication.getInstance().getUser().getToken(),id);
                                dialog.cancel();
                            }
                        });


                    }
                });

                break;



            case "2":
                ll_teacher.setVisibility(View.VISIBLE);
                tv_tip.setText("订单已支付完成，等待技师接单");
                tv_status.setText("待服务");
                tv_top.setText("显示二维码");
//                tv_top.setVisibility(View.INVISIBLE);
                tv_apply.setVisibility(View.VISIBLE);
                tv_to_pay.setText("立即提交");
                tv_to_pay.setVisibility(View.VISIBLE);
                tv_teach_name.setText("等待技师接单");
                tv_apply.setVisibility(View.INVISIBLE);
                mapView.setVisibility(View.GONE);
                tv_top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.show("等待技师接单中......");
//                        dialog=new CodeDialog(mContext,url);
//                        dialog.setCancelable(true);
//                        dialog.show();
                    }
                });
                tv_to_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isNull(tv_reason.getText().toString().trim())){
                            ToastUtils.show("请填写退款原因");
                            return;
                        }
                        getNetWorker().order_apply(MyApplication.getInstance().getUser().getToken(),
                                id,tv_reason.getText().toString().trim());

                    }
                });
                break;
            case "8":
            case "3":
                tv_tip.setText("技师已接单，等待技师上门服务");
                tv_status.setText("待服务");
                tv_top.setText("显示二维码");
                tv_top.setVisibility(View.VISIBLE);
                tv_apply.setVisibility(View.VISIBLE);
                tv_to_pay.setText("立即提交");
                tv_to_pay.setVisibility(View.VISIBLE);
                tv_apply.setVisibility(View.INVISIBLE);
                tv_teach_name.setText("技师已接单");
                tv_top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog=new CodeDialog(mContext,detail.getO_code_url());
                        dialog.setCancelable(true);
                        dialog.show();
                    }
                });
                tv_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelDialog=new BottomCancelDialog(mContext);
                        cancelDialog.setCancelable(true);
                        cancelDialog.show();
                    }
                });
                tv_to_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isNull(tv_reason.getText().toString().trim())){
                            ToastUtils.show("请填写退款原因");
                            return;
                        }
                        getNetWorker().order_apply(MyApplication.getInstance().getUser().getToken(),
                                id,tv_reason.getText().toString().trim());
                    }
                });
                break;
            case "4":
                tv_tip.setText("技师已上门服务，请尽情享受本次服务");
                tv_status.setText("进行中");
                tv_top.setVisibility(View.INVISIBLE);
                tv_to_pay.setVisibility(View.GONE);
                tv_apply.setVisibility(View.INVISIBLE);
                break;
            case "5":
                tv_tip.setText("本次服务已完成，期待您对本次服务做出评价");
                tv_status.setText("待评价");
                tv_apply.setVisibility(View.GONE);
                tv_top.setVisibility(View.INVISIBLE);
                tv_to_pay.setText("去评价");

                break;
            case "6":
                tv_tip.setText("感谢您的评价，欢迎您下次光临");
                tv_status.setText("已完成");
                tv_apply.setVisibility(View.GONE);
                tv_top.setVisibility(View.INVISIBLE);
                tv_to_pay.setVisibility(View.GONE);
                break;


            default:
                tv_status.setText("已取消");
                tv_top.setVisibility(View.INVISIBLE);
                tv_to_pay.setVisibility(View.GONE);
                break;
        }


        if("到店服务".equals(detail.getO_service_type())){
            ll_teacher.setVisibility(View.GONE);
            ll_person.setVisibility(View.GONE);
            tv_service_type.setText("到店服务");
            ll_go.setVisibility(View.GONE);
            ll_advance_time.setVisibility(View.GONE);
        }



    }
    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.APPLY_REASON://申请售后
                String reason=event.getContent();
                tv_reason.setText(reason);
                break;
            case MyEventBusConfig.UPDATE_ORDER:
                getNetWorker().order_detail(MyApplication.getInstance().getUser().getToken(),id);
                break;
        }
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case ORDER_CANCEL:

                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case ORDER_CANCEL:

                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case COMMON_PHONE:
                HemaArrayResult<SMobile>  sResult= (HemaArrayResult<SMobile>) baseResult;
                SMobile sMobile=sResult.getObjects().get(0);
                call(sMobile.getMobile());
                break;
            case VERSION_2_ORDER_APPLY:
                ToastUtils.show("售后申请已发起，等待处理中！");
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.APPLY_FOR_RETURN));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAc(mContext);
                    }
                },1000);
                break;
            case ORDER_PROCEED:
                if("1".equals(detail.getO_pay_type())){
                    HemaArrayResult<WeixinTrade> wResult = (HemaArrayResult<WeixinTrade>) baseResult;
                    WeixinTrade wTrade = wResult.getObjects().get(0);
                    if (BaseUtil.isWeixinAvilible(mContext)) {
                        goWeixin(wTrade);
                    } else {
                        ToastUtil.showLongToast(mContext, "请先安装微信！");
                        return;
                    }
                }else{
                    HemaArrayResult<String>  wResult= (HemaArrayResult<String>) baseResult;
                    String orderInfo=wResult.getObjects().get(0).toString();
                    new AlipayThread(orderInfo).start();
                    ToastUtils.show("下单成功！");
                }
                break;
            case ORDER_CANCEL:
                ToastUtils.show("取消成功！");
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_ORDER));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAc(mContext);
                    }
                },1000);

                break;
            case ORDER_GET:
                HemaArrayResult<OrderDetail> oResult= (HemaArrayResult<OrderDetail>) baseResult;
                detail=oResult.getObjects().get(0);
                if(detail!=null){
                    setData();
                }
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case ORDER_APPLY:
            case ORDER_PROCEED:
            case ORDER_CANCEL:
            case ORDER_GET:
                ToastUtils.show(baseResult.getError_message());
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }
    //移动到指定经纬度
    private void initAMap() {
        mAMap = mapView.getMap();
        CameraPosition cameraPosition = new CameraPosition(new LatLng(latitude, longtitude), 15, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mAMap.moveCamera(cameraUpdate);
        drawMarkers();
    }
    //画定位标记图
    public void drawMarkers() {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(latitude, longtitude))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.run))
                .draggable(true);
        Marker marker = mAMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }
    @Override
    protected void findView() {
        ll_advance_time=findViewById(R.id.ll_advance_time);
        ll_go=findViewById(R.id.ll_go);
        tv_service_type=findViewById(R.id.tv_service_type);
        ll_person=findViewById(R.id.ll_person);
        tv_phone=findViewById(R.id.tv_phone);
        tv_reason=findViewById(R.id.tv_reason);
        fl_top=findViewById(R.id.fl_top);
        tv_shop=findViewById(R.id.tv_shop);
        tv_tip=findViewById(R.id.tv_tip);
        ll_refund_process=findViewById(R.id.ll_refund_process);
        tv_to_pay=findViewById(R.id.tv_to_pay);
        tv_real=findViewById(R.id.tv_real);
        tv_coupon=findViewById(R.id.tv_coupon);
        tv_total=findViewById(R.id.tv_total);
        tv_call=findViewById(R.id.tv_call);
        ll_refund=findViewById(R.id.ll_refund);
        tv_remark=findViewById(R.id.tv_remark);
        tv_trip=findViewById(R.id.tv_trip);
        tv_s_time=findViewById(R.id.tv_s_time);
        tv_pay=findViewById(R.id.tv_pay);
        tv_oder_number=findViewById(R.id.tv_oder_number);
        tv_pro_time=findViewById(R.id.tv_pro_time);
        tv_pro_content=findViewById(R.id.tv_pro_content);
        tv_pro_price=findViewById(R.id.tv_pro_price);
        tv_pro_name=findViewById(R.id.tv_pro_name);
        iv_pro=findViewById(R.id.iv_pro);
        tv_time=findViewById(R.id.tv_time);
        tv_teach_name=findViewById(R.id.tv_teach_name);
        iv_teach=findViewById(R.id.iv_teach);
        ll_teacher=findViewById(R.id.ll_teacher);
        tv_address=findViewById(R.id.tv_address);
        tv_name=findViewById(R.id.tv_name);
        tv_apply=findViewById(R.id.tv_apply);
        tv_top=findViewById(R.id.tv_top);
        tv_status=findViewById(R.id.tv_status);
        mapView = (MapView) findViewById(R.id.bmapView);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
    }

    @Override
    protected void getExras() {
        id=mIntent.getStringExtra("id");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("申请售后");
        fl_top.setVisibility(View.GONE);
        ll_teacher.setVisibility(View.GONE);
        mapView.setVisibility(View.GONE);
        tv_apply.setVisibility(View.INVISIBLE);
        ll_refund.setVisibility(View.VISIBLE);
        ll_refund.setOnClickListener(this);
        ll_refund_process.setVisibility(View.VISIBLE);
        tv_phone.setOnClickListener(this);
        tv_call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_call:
                if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
                    PopPhoneDialog dialog=new PopPhoneDialog(mContext,detail.getMobile());
                    dialog.setButtonListener(new PopPhoneDialog.OnButtonListener() {
                        @Override
                        public void onLeftButtonClick(PopPhoneDialog dialog) {
                            dialog.cancel();
                        }

                        @Override
                        public void onRightButtonClick(PopPhoneDialog dialog) {

                            String phone=dialog.getMobile();
                            if(isNull(phone)){
                                ToastUtils.show("请输入手机号！");
                                return;
                            }
                            if(phone.length()!=11){
                                ToastUtils.show("请输入正确手机号！");
                                return;
                            }
                            dialog.cancel();
                            getNetWorker().common_phone(phone, detail.getS_telephone());
                        }
                    });
                }
                break;
            case R.id.tv_phone:
                if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
                    PopPhoneDialog dialog=new PopPhoneDialog(mContext,detail.getMobile());
                    dialog.setButtonListener(new PopPhoneDialog.OnButtonListener() {
                        @Override
                        public void onLeftButtonClick(PopPhoneDialog dialog) {
                            dialog.cancel();
                        }

                        @Override
                        public void onRightButtonClick(PopPhoneDialog dialog) {

                            String phone=dialog.getMobile();
                            if(isNull(phone)){
                                ToastUtils.show("请输入手机号！");
                                return;
                            }
                            if(phone.length()!=11){
                                ToastUtils.show("请输入正确手机号！");
                                return;
                            }
                            dialog.cancel();
                            getNetWorker().common_phone(phone, detail.getTech_mobile());
                        }
                    });
                }
                break;
            case R.id.ll_refund:
                cancelDialog=new BottomCancelDialog(mContext);
                cancelDialog.setCancelable(true);
                cancelDialog.show();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }


    //取消订单
    public void cancel_order(String id) {
        popTipDialog = new PopTipDialog(mContext);
        popTipDialog.setCancelable(true);
        popTipDialog.setTip("确定取消订单？");
        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
            @Override
            public void onLeftButtonClick(PopTipDialog dialog) {

                dialog.cancel();

            }

            @Override
            public void onRightButtonClick(PopTipDialog dialog) {
                dialog.cancel();
                getNetWorker().orderCancel(MyApplication.getInstance().getUser().getToken(), id);
            }
        });
        popTipDialog.show();
    }

    /*微信支付开始*/

    private void goWeixin(WeixinTrade trade) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, trade.getAppid(), true);
        PayReq request = new PayReq();
        request.appId = trade.getAppid();
        request.partnerId = trade.getPartnerid();
        request.prepayId = trade.getPrepayid();
        request.packageValue = trade.getPackageValue();
        request.nonceStr = trade.getNoncestr();
        request.timeStamp = trade.getTimestamp();
        request.sign = trade.getSign();
        msgApi.sendReq(request);
    }

    /*支付宝支付*/
    private class AlipayThread extends Thread {
        String orderInfo;
        AlipayHandler alipayHandler;

        public AlipayThread(String orderInfo) {
            this.orderInfo = orderInfo;
            alipayHandler = new AlipayHandler();
        }

        @Override
        public void run() {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(ApplyRefundActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            log_i("result = " + result);
            Message msg = new Message();
            msg.obj = result;
            alipayHandler.sendMessage(msg);
        }
    }

    private class AlipayHandler extends Handler {

        public AlipayHandler() {


        }

        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            switch (resultStatus) {
                case "9000":
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_SUCCESS));
                    break;
                case "6001":
                    new ToastDialog.Builder(ApplyRefundActivity.this)
                            .setType(ToastDialog.Type.WARN)
                            .setMessage("支付已取消")
                            .show();
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_CANCEL));
                    break;
                default:
                    new ToastDialog.Builder(ApplyRefundActivity.this)
                            .setType(ToastDialog.Type.WARN)
                            .setMessage("支付异常，请选择其他支付方式！")
                            .show();
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_CANCEL));
                    break;
            }
        }
    }
    /**
     * 判断是否有某项权限
     * @param string_permission 权限
     * @param request_code 请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission,int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    /**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this,"请允许拨号权限后再试",Toast.LENGTH_SHORT).show();
                } else {//成功
                    call("tel:"+phone);
                }
                break;
        }
    }
    /**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     */
    public void call(String telPhone){
        if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telPhone));
            startActivity(intent);
        }
    }

}
