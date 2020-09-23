package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.beijing.tenfingers.Alipay.PayResult;
import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.FeeAdapter;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.bean.OrderSettle;
import com.beijing.tenfingers.bean.Price;
import com.beijing.tenfingers.bean.TimePoint;
import com.beijing.tenfingers.bean.WeixinTrade;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BottomTimeDialog;
import com.beijing.tenfingers.view.CouponBottomDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Map;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomSharedPreferencesUtil;

public class DoChargeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title, tv_charge, tv_price;
    private String price = "";
    private ImageView iv_wx, iv_zfb;
    private int type = 0;// 0 未选择 1 微信 2 支付宝
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_docharge);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        tv_price.setText(price);
        getNetWorker().charge_price(MyApplication.getInstance().getUser().getToken());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void findView() {
        iv_zfb = findViewById(R.id.iv_zfb);
        iv_wx = findViewById(R.id.iv_wx);
        tv_price = findViewById(R.id.tv_price);
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        tv_charge = findViewById(R.id.tv_charge);

    }

    @Override
    protected void getExras() {
        id = mIntent.getStringExtra("id");
        price = mIntent.getStringExtra("price");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("支付页面");
        tv_charge.setOnClickListener(this);
        iv_zfb.setOnClickListener(this);
        iv_wx.setOnClickListener(this);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.CHARGE:
//                ToastUtils.show("充值成功");
                finishAc(mContext);
                break;
            default:
//                ToastUtils.show("充值失败");
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wx:
                type = 1;
                iv_wx.setImageResource(R.mipmap.select_y_green);
                iv_zfb.setImageResource(R.mipmap.select_n);
                break;
            case R.id.iv_zfb:
                type = 2;
                iv_zfb.setImageResource(R.mipmap.select_y_green);
                iv_wx.setImageResource(R.mipmap.select_n);
                break;
            case R.id.tv_charge:
                if (type == 0) {
                    ToastUtils.show("请选择支付类型～");
                    return;
                }
                if (type == 1) {
                    getNetWorker().my_invest(id, String.valueOf(type));
                } else if (type == 2) {
                    getNetWorker().my_invest(id, String.valueOf(type));
                }
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case MY_INVEST:
                if (type == 1) {
                    XtomSharedPreferencesUtil.save(mContext, "charge", "c");
                    HemaArrayResult<WeixinTrade> wResult = (HemaArrayResult<WeixinTrade>) baseResult;
                    WeixinTrade wTrade = wResult.getObjects().get(0);
                    if (BaseUtil.isWeixinAvilible(mContext)) {
                        goWeixin(wTrade);
                    } else {
                        ToastUtil.showLongToast(mContext, "请先安装微信！");
                    }
                    return;
                } else {
                    HemaArrayResult<String> wResult = (HemaArrayResult<String>) baseResult;
                    String orderInfo = wResult.getObjects().get(0).toString();
                    new AlipayThread(orderInfo).start();
                }
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }


    /*微信支付开始*/

    IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    private void goWeixin(WeixinTrade trade) {
//        IWXAPI api = WXAPIFactory.createWXAPI(this, trade.getAppid());
//        //data  根据服务器返回的json数据创建的实体类对象
//        PayReq req = new PayReq(); req.appId = trade.getAppid();
//        req.partnerId = trade.getPartnerid();
//        req.prepayId = trade.getPrepayid();
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = trade.getNoncestr();
//        req.timeStamp = trade.getTimestamp();
//        req.sign = trade.getSign();
//        api.registerApp(trade.getAppid());
//        //发起请求
//        api.sendReq(req);

        msgApi.registerApp(trade.getAppid());
        PayReq request = new PayReq();
        request.appId = trade.getAppid();
        request.partnerId = trade.getPartnerid().trim();
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
            alipayHandler = new AlipayHandler(DoChargeActivity.this);
        }

        @Override
        public void run() {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(DoChargeActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            log_i("result = " + result);
            Message msg = new Message();
            msg.obj = result;
            alipayHandler.sendMessage(msg);
        }
    }

    private class AlipayHandler extends Handler {
        DoChargeActivity activity;

        public AlipayHandler(DoChargeActivity activity) {
            this.activity = activity;
        }

        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            switch (resultStatus) {
                case "9000":
                    ToastUtils.show("支付成功");
                    break;
                default:
                    ToastUtils.show("支付取消");
                    break;
            }
        }
    }

}
