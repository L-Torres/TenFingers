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
 * 选择出行方式
 */
public class BottomWayOutDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private OnButtonListener buttonListener;
    private TextView tv_top;
    private LinearLayout ll_dc,ll_gj;
    private Button btn_confirm;
    private ImageView iv_dc,iv_gj;
    private String arrive_type="";
    double distance=0.00;
    private String type="";
    public BottomWayOutDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_bottom_chuxing, null);
        ll_dc = (LinearLayout) view.findViewById(R.id.ll_dc);
        ll_gj = (LinearLayout) view.findViewById(R.id.ll_gj);
        iv_dc=view.findViewById(R.id.iv_dc);
        iv_gj=view.findViewById(R.id.iv_gj);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.ARRIVE_TYPE,arrive_type));
            }
        });
        iv_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrive_type="1";
                iv_dc.setImageResource(R.mipmap.select_y_green);
                iv_gj.setImageResource(R.mipmap.select_n);
            }
        });

        iv_gj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(distance>5){
                    arrive_type="1";
                    iv_dc.setImageResource(R.mipmap.select_y_green);
                    iv_gj.setImageResource(R.mipmap.select_n);
                    ToastUtils.show("当前服务地点超过5KM，只支持打车出行");
                }else{
                    arrive_type="2";
                    iv_gj.setImageResource(R.mipmap.select_y_green);
                    iv_dc.setImageResource(R.mipmap.select_n);
                }

            }
        });

        mDialog.setCancelable(true);
        mDialog.setContentView(view);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }
    public void setType(String type){
        this.type=type;
        if("1".equals(type)){
            iv_dc.setImageResource(R.mipmap.select_y_green);
            iv_gj.setImageResource(R.mipmap.select_n);
        }else if("2".equals(type)){
            iv_gj.setImageResource(R.mipmap.select_y_green);
            iv_dc.setImageResource(R.mipmap.select_n);
        }else{
            iv_gj.setImageResource(R.mipmap.select_n);
            iv_dc.setImageResource(R.mipmap.select_n);
        }
    }
    public void setDistance(double distance){
        this.distance=distance;
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
        public void onTopButtonClick(BottomWayOutDialog dialog);

        public void onMiddleButtonClick(BottomWayOutDialog dialog);
    }
}
