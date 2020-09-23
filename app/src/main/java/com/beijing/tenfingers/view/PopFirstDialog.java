package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.StartActivity;
import com.beijing.tenfingers.activity.WebViewActivity;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

public class PopFirstDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private TextView tv_tip, tv_left, tv_right,tv_content;
    private OnButtonListener buttonListener;

    public PopFirstDialog(Context context) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog_white);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_first, null);
        tv_tip = view.findViewById(R.id.tv_tip);
        tv_left = view.findViewById(R.id.tv_left);
        tv_right = view.findViewById(R.id.tv_right);
        tv_content=view.findViewById(R.id.tv_content);
        tv_content.setText(getClickableSpan());
        //设置超链接可点击
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(context instanceof StartActivity){
                    ((StartActivity) context).finish();
                }
                mDialog.cancel();
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.onRightButtonClick(PopFirstDialog.this);
            }
        });

        mDialog.setCancelable(true);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }
    /**
     * 获取可点击的SpannableString
     * @return
     */
    private SpannableString getClickableSpan() {
        SpannableString spannableString = new SpannableString("为了更好的保护您的权益，请您务必阅读、充分理解《服务协议》和《隐私政策》各条款，包括但不限于：为向您提供基本服务，我们可能会基于您的授权进行收集和使用您的位置信息和设备信息。你可以对上述信息进行访问、更正、删除并管理您的权限。如您同意，请点击“同意”开始接受我们的服务");
        //设置下划线文字
//        spannableString.setSpan(new UnderlineSpan(), 23, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
              Intent  it = new Intent(context, WebViewActivity.class);
                it.putExtra("Title", "服务协议");
                it.putExtra("URL", "https://10zhijian.com/sla.html");
                context.startActivity(it);
//                Toast.makeText(context,"使用条款",Toast.LENGTH_SHORT).show();
            }
        }, 23, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF006F55")), 23, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下划线文字
//        spannableString.setSpan(new UnderlineSpan(), 30, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
               Intent it = new Intent(context, WebViewActivity.class);
                it.putExtra("Title", "十指间隐私政策");
                it.putExtra("URL", "https://10zhijian.com/privacy.html");
                context. startActivity(it);
//                Toast.makeText(context,"隐私政策",Toast.LENGTH_SHORT).show();
            }
        },  30, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF006F55")), 30, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
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


    public void setButtonListener(OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onLeftButtonClick(PopFirstDialog dialog);

        public void onRightButtonClick(PopFirstDialog dialog);
    }

}
