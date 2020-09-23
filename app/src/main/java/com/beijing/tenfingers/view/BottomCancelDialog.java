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
import android.widget.EditText;
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
 * 取消订单
 */
public class BottomCancelDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private OnButtonListener buttonListener;
    private TextView tv_top;
    private LinearLayout ll_bxy,ll_msj,ll_mc,ll_qt;
    private Button btn_confirm;
    private ImageView iv_bxy,iv_msj,iv_mc,iv_qt;
    private String reason="";
    private EditText ed_reason;
    private String type="";
    public BottomCancelDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_cancel_order, null);
        ll_bxy = (LinearLayout) view.findViewById(R.id.ll_bxy);
        ll_msj = (LinearLayout) view.findViewById(R.id.ll_msj);
        ll_mc = (LinearLayout) view.findViewById(R.id.ll_mc);
        ll_qt = (LinearLayout) view.findViewById(R.id.ll_qt);
        ed_reason=view.findViewById(R.id.ed_reason);
        iv_bxy=view.findViewById(R.id.iv_bxy);
        iv_msj=view.findViewById(R.id.iv_msj);
        iv_mc=view.findViewById(R.id.iv_mc);
        iv_qt=view.findViewById(R.id.iv_qt);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
                if(isNull(type)){

                    ToastUtils.show("请先选择退款原因的标识符哦～");
                    return;
                }
                if("1".equals(type)){
                    reason=ed_reason.getText().toString().trim();
                }
                if(isNull(reason)){
                    if("1".equals(type)){
                        ToastUtils.show("请填写退款原因");
                    }else{
                        ToastUtils.show("请选择/填写退款原因");
                    }

                }else{

                }
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.APPLY_REASON,reason));
            }
        });
        iv_bxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_bxy.setImageResource(R.mipmap.select_y_green);
                iv_msj.setImageResource(R.mipmap.select_n);
                iv_mc.setImageResource(R.mipmap.select_n);
                iv_qt.setImageResource(R.mipmap.select_n);
                reason="不想要了";
                type="0";
            }
        });
        iv_msj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_msj.setImageResource(R.mipmap.select_y_green);
                iv_bxy.setImageResource(R.mipmap.select_n);
                iv_mc.setImageResource(R.mipmap.select_n);
                iv_qt.setImageResource(R.mipmap.select_n);
                reason="没时间进行服务";
                type="0";
            }
        });
        iv_mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_mc.setImageResource(R.mipmap.select_y_green);
                iv_bxy.setImageResource(R.mipmap.select_n);
                iv_msj.setImageResource(R.mipmap.select_n);
                iv_qt.setImageResource(R.mipmap.select_n);
                reason="买多/买错";
                type="0";
            }
        });
        iv_qt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_qt.setImageResource(R.mipmap.select_y_green);
                iv_msj.setImageResource(R.mipmap.select_n);
                iv_bxy.setImageResource(R.mipmap.select_n);
                iv_mc.setImageResource(R.mipmap.select_n);
                type="1";
            }
        });



        mDialog.setCancelable(true);
        mDialog.setContentView(view);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
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
        public void onTopButtonClick(BottomCancelDialog dialog);

        public void onMiddleButtonClick(BottomCancelDialog dialog);
    }
}
