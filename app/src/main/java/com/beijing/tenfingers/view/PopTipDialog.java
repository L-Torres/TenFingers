package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.beijing.tenfingers.R;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

public class PopTipDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private TextView tv_tip, tv_left, tv_right;
    private PopTipDialog.OnButtonListener buttonListener;

    public PopTipDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog_white);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_tip, null);
        tv_tip = view.findViewById(R.id.tv_tip);
        tv_left = view.findViewById(R.id.tv_left);
        tv_right = view.findViewById(R.id.tv_right);
        tv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.onRightButtonClick(PopTipDialog.this);
            }
        });

        mDialog.setCancelable(true);
        mDialog.setContentView(view);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }

    public void setRightTv(String value){
        tv_right.setText(value);
    }

    /**
     * 设置是否可以取消
     *
     * @param cancelable
     */
    public void setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
    }

    /**
     * 提示内容
     *
     * @param text
     */
    public void setTip(String text) {
        tv_tip.setText(text);
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


    public void setButtonListener(PopTipDialog.OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onLeftButtonClick(PopTipDialog dialog);

        public void onRightButtonClick(PopTipDialog dialog);
    }

}
