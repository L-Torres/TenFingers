package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ChargeActivity;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

public class PopVipDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private TextView tv_charge;
    private PopVipDialog.OnButtonListener buttonListener;
    private ImageView iv_close;
    public PopVipDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog_white);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_vip, null);
        tv_charge = view.findViewById(R.id.tv_charge);
        iv_close=view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        tv_charge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
                Intent it=new Intent(context, ChargeActivity.class);
                context.startActivity(it);
            }
        });


        mDialog.setCancelable(true);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }


    /**
     * 设置是否可以取消
     *
     * @param cancelable
     */
    public void setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
    }



    public void show() {
        mDialog.show();

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        mDialog.getWindow().setAttributes(lp);
    }

    public void cancel() {
        mDialog.cancel();
    }


    public void setButtonListener(PopVipDialog.OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onLeftButtonClick(PopVipDialog dialog);

        public void onRightButtonClick(PopVipDialog dialog);
    }

}
