package com.beijing.tenfingers.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

/**
 * 升级弹窗
 */
public class UpgradeDialog extends XtomObject {

    private Dialog mDialog;
    private ImageView iv_close;
    private Context context;
    private TextView tv_version, tv_info, tv_upgrade;
    private OnButtonListener buttonListener;
    private String tip;
    private FrameLayout layout;

    public UpgradeDialog(Context context, String tip) {
        this.context = context;
        this.tip = tip;
        //初始化dialog样式
        if(context != null){
            mDialog = new Dialog(context, R.style.dialog_white);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_upgrade, null);
            mDialog.setContentView(view);
            layout = view.findViewById(R.id.frameLayout);
            iv_close = view.findViewById(R.id.iv_close);
            tv_version = view.findViewById(R.id.tv_version);
            tv_info = view.findViewById(R.id.tv_info);
            tv_upgrade = view.findViewById(R.id.tv_upgrade);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if (buttonListener != null)
                        buttonListener.onCloseClick(UpgradeDialog.this);
                }
            });
            tv_upgrade.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if (buttonListener != null)
                        buttonListener.onUpgradeClick(UpgradeDialog.this);
                }
            });
        }
    }

    public void cancel() {
        mDialog.dismiss();
    }

    public void show() {
        if (context != null) {
            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.horizontalMargin = BaseUtil.dip2px(context, 55);
            dialogWindow.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setAttributes(lp);
            layout.setLayoutParams(new LinearLayout.LayoutParams(lp.width, LinearLayout.LayoutParams.WRAP_CONTENT));
            mDialog.setCanceledOnTouchOutside(true);//点击背景不消失  点击返回键可以消失
            mDialog.setCancelable(true);//点击背景不消失  点击返回键也不消失
            mDialog.show();
        }
    }

    /**
     * 设置版本号
     *
     * @param version
     */
    public void setVersion(String version) {
        tv_version.setText("新版本" + version);
        if(isNull(tip)){
            tip = "测试版升级内容.....";
        }
        tv_info.setText(tip);
    }

    public void setButtonListener(OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onUpgradeClick(UpgradeDialog dialog);

        public void onCloseClick(UpgradeDialog dialog);
    }
}
