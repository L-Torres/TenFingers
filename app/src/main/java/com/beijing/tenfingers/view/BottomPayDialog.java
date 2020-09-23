package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomObject;

/**
 * 选择支付方式
 */
public class BottomPayDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private OnButtonListener buttonListener;
    private TextView tv_top;
    private LinearLayout ll_wx,ll_zfb;
    private ImageView iv_zfb,iv_wx,iv_yue;
    private Button btn_confirm;
    private String pay_type="";
    public BottomPayDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_bottom_pay, null);
        ll_wx = (LinearLayout) view.findViewById(R.id.ll_wx);
        ll_zfb = (LinearLayout) view.findViewById(R.id.ll_zfb);
        iv_zfb=view.findViewById(R.id.iv_zfb);
        iv_wx=view.findViewById(R.id.iv_wx);
        iv_yue=view.findViewById(R.id.iv_yue);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_TYPE,pay_type));
            }
        });
        payeType(pay_type);
        iv_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_type="1";
                payeType("0");
            }
        });
        iv_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_type="2";
                payeType("1");
            }
        });
        iv_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_type="0";
                payeType("2");
            }
        });
//        ll_wx.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (buttonListener != null)
//                    buttonListener.onTopButtonClick(BottomPayDialog.this);
//            }
//        });
//        ll_zfb.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (buttonListener != null)
//                    buttonListener.onMiddleButtonClick(BottomPayDialog.this);
//            }
//        });

        mDialog.setCancelable(true);
        mDialog.setContentView(view);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }


    public String getPayType(){
        return pay_type;
    }


    public void payeType(String pay_type){
        if("0".equals(pay_type)){
            iv_wx.setImageResource(R.mipmap.select_y_green);
            iv_zfb.setImageResource(R.mipmap.select_n);
            iv_yue.setImageResource(R.mipmap.select_n);
        }else if("1".equals(pay_type)){
            iv_zfb .setImageResource(R.mipmap.select_y_green);
            iv_wx.setImageResource(R.mipmap.select_n);
            iv_yue.setImageResource(R.mipmap.select_n);
        }else if("2".equals(pay_type)){
            iv_yue.setImageResource(R.mipmap.select_y_green);
            iv_wx.setImageResource(R.mipmap.select_n);
            iv_zfb.setImageResource(R.mipmap.select_n);
        }else{
            iv_yue.setImageResource(R.mipmap.select_n);
            iv_wx.setImageResource(R.mipmap.select_n);
            iv_zfb.setImageResource(R.mipmap.select_n);
        }
    }
    /**
     * 设置是否可以取消
     * @param cancelable
     */
    public void setCancelable(boolean cancelable)
    {
        mDialog.setCancelable(cancelable);
    }

    public void show() {
        mDialog.show();

        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        mDialog.getWindow().setAttributes(lp);
    }

    public void cancel() {
        mDialog.cancel();
    }

    public OnButtonListener getButtonListener() {
        return buttonListener;
    }

    public void setButtonListener(OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onTopButtonClick(BottomPayDialog dialog);

        public void onMiddleButtonClick(BottomPayDialog dialog);
    }
}
