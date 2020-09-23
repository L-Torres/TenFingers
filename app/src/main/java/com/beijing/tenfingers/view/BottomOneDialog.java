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
import android.widget.TextView;


import com.beijing.tenfingers.R;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

//拨打电话弹窗
public class BottomOneDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private Button btnTop;
    private Button btnMiddle;
    private Button btnCancel;
    private OnButtonListener buttonListener;
    private TextView tv_top;
    public BottomOneDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_bottom_one, null);
        btnTop = (Button) view.findViewById(R.id.btnTop);
        btnMiddle = (Button)view.findViewById(R.id.btnMiddle);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tv_top=view.findViewById(R.id.tv_top);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        btnTop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.onTopButtonClick(BottomOneDialog.this);
            }
        });
        btnMiddle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.onMiddleButtonClick(BottomOneDialog.this);
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

    public void setTopButtonText(String text) {
        btnTop.setText(text);
    }

    public void setTopButtonText(int textID) {
        btnTop.setText(textID);
    }

    public void setMiddleButtonText(String text) {
        btnMiddle.setText(text);
    }

    public void setMiddleButtonText(int textID) {
        btnMiddle.setText(textID);
    }

    public void setMiddleButtonTextColor(int color) {
        btnMiddle.setTextColor(color);
    }
    public void setTop(String text){
        tv_top.setText(text);
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
        public void onTopButtonClick(BottomOneDialog dialog);

        public void onMiddleButtonClick(BottomOneDialog dialog);
    }

}
