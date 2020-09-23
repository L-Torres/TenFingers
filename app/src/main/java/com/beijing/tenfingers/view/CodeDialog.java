package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

public class CodeDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private ImageView iv_code;

    public CodeDialog(Context context,String url) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog_white);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_code, null);
        iv_code=view.findViewById(R.id.iv_code);
        mDialog.setCancelable(true);
        mDialog.setContentView(view);
        BaseUtil.loadBitmap(url,0,iv_code);
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



    public interface OnButtonListener {
        public void onLeftButtonClick(CodeDialog dialog);

        public void onRightButtonClick(CodeDialog dialog);
    }

}
