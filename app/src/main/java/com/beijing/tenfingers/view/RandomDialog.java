package com.beijing.tenfingers.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ChargeActivity;
import com.beijing.tenfingers.activity.CouponListActivity;
import com.beijing.tenfingers.activity.DoChargeActivity;
import com.beijing.tenfingers.activity.ProductDetailActivity;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomObject;
import xtom.frame.util.XtomWindowSize;


public class RandomDialog extends XtomObject {

    private Context context;
    private Dialog dialog;

    private ImageView iv_img; //整体图案
    private ImageView iv_close; //关闭弹框
    private PopBean adAlert;

    public RandomDialog(Context context, PopBean adAlert) {
        if(context == null){
            return;
        }
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_all, null);
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        iv_close = (ImageView) view.findViewById(R.id.iv_close);
        int w=(int) (XtomWindowSize.getWidth(context) * 0.87);
        int h=(int) (w* 1.2);
//        MyUtil.loadBitmap(adAlert.getPoster_image_link(), R.mipmap.icon_default_shu, iv_img);
        Glide.with(context)
                .load(adAlert.getUrl()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_DIALOG, ""));
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.ACTIVITY_TOTAL, adAlert.getId()));
                dialog.show();
                return false;
            }
        }).override(w,h).into(iv_img);

        iv_close.setTag(adAlert);
        //只弹一次 发生操作后 改变其弹出属性 然后更新本地存储
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_DIALOG, ""));
                dialog.dismiss();
            }
        });
        changeStatus(adAlert);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopBean p= adAlert;

                Intent it=null;
                switch (p.getKey_type()){//1 项目跳转 2 优惠券提醒 3vip充值
                    case "1":
                        it=new Intent(context, ProductDetailActivity.class);
                        it.putExtra("id",p.getKey_id());
                        context.startActivity(it);
                        break;
                    case "2":
                        if(BaseUtil.IsLogin()){

                            it=new Intent(context, CouponListActivity.class);
                            context.startActivity(it);
                        }else{
                            BaseUtil.toLogin(context,"1");
                        }
                        break;
                    case "3":
                        if(BaseUtil.IsLogin()){
                            it=new Intent(context, ChargeActivity.class);
                            context.startActivity(it);
                        }else{
                            BaseUtil.toLogin(context,"1");
                        }
                        break;
                        default:

                            break;

                }
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_DIALOG, ""));
                dialog.dismiss();
            }
        });
    }

    private void changeStatus(PopBean a) {
        ArrayList<PopBean> temp = (ArrayList<PopBean>) BaseUtil.getArray(context);
        if (temp != null && temp.size() > 0) {
            for (PopBean adAlert : temp) {
                if (adAlert.getId().equals(a.getId())) {
                    adAlert.setFlag("1");
                    break;
                }
            }
        }
        BaseUtil.putArray(context, temp);
    }
    public void show() {
        if (context != null) {
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            int w=(int) (XtomWindowSize.getWidth(context) * 0.87);
            iv_img.getLayoutParams().width = w;
            iv_img.getLayoutParams().height = (int) (w* 1.2);
            dialogWindow.setLayout(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            dialogWindow.setGravity(Gravity.CENTER);
            dialogWindow.setAttributes(lp);
            dialog.setCanceledOnTouchOutside(true);//点击背景不消失  点击返回键可以消失
            dialog.setCancelable(true);//点击背景不消失  点击返回键也不消失
            dialog.show();
        }
    }


    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
