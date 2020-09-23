package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

public class PopPhoneDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private TextView tv_tip, tv_left, tv_right;
    private PopPhoneDialog.OnButtonListener buttonListener;
    private EditText ed_phone;
    public PopPhoneDialog(Context context,String phone) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog_white);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_phone, null);
        tv_tip = view.findViewById(R.id.tv_tip);
        tv_left = view.findViewById(R.id.tv_left);
        tv_right = view.findViewById(R.id.tv_right);
        ed_phone=view.findViewById(R.id.ed_phone);
        ed_phone.setText(phone);
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
                    buttonListener.onRightButtonClick(PopPhoneDialog.this);
            }
        });

        mDialog.setCancelable(true);
        mDialog.setContentView(view);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
        mDialog.show();
    }


    public String getMobile(){
        String phone="";
        phone=ed_phone.getText().toString().trim();
        return phone;
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


    public void setButtonListener(PopPhoneDialog.OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onLeftButtonClick(PopPhoneDialog dialog);

        public void onRightButtonClick(PopPhoneDialog dialog);
    }

}
