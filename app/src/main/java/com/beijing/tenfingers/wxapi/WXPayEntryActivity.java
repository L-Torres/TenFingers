package com.beijing.tenfingers.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyConfig;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wxpayentry);
		api = WXAPIFactory.createWXAPI(this, MyConfig.APPID_WEIXIN);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		//0成功，展示成功页面
		//-1错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
		//-2用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
//		(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
//			if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		String temp=XtomSharedPreferencesUtil.get(this,"charge");
		switch (
			 resp.errCode){
				case 0:

					if(!XtomBaseUtil.isNull(temp)){
						EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.CHARGE));
                        XtomSharedPreferencesUtil.save(this,"charge","");
						finish();
					}else{
						EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_SUCCESS));
						finish();
						ToastUtils.show("支付成功");
					}
					break;
				case -1:
					if(!XtomBaseUtil.isNull(temp)){
						EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.CHARGE));
						XtomSharedPreferencesUtil.save(this,"charge","");
					}else{
						MyApplication.setPayType("");
						EventBus.getDefault().post(new EventBusModel(true,MyEventBusConfig.PAY_ERROR));
						finish();
						ToastUtils.show("其它异常");
					}

					break;
				case -2:
					if(!XtomBaseUtil.isNull(temp)){
						EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.CHARGE));
						XtomSharedPreferencesUtil.save(this,"charge","");
						finish();
						ToastUtils.show("支付取消");
					}else{
						MyApplication.setPayType("");
						EventBus.getDefault().post(new EventBusModel(true,MyEventBusConfig.PAY_CANCEL));
						finish();
						ToastUtils.show("支付取消");
					}

					break;
			}
		}

    }
