package com.beijing.tenfingers.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ConfirmOrderActivity;
import com.beijing.tenfingers.adapter.CouponBottomListAdapter;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomObject;

/**
 * 优惠券底部dialog
 */
public class CouponBottomDialog extends XtomObject {

    private Dialog mDialog;
    private ImageView iv_close;
    private Context context;
    private RecyclerView rcy_coupon;
    private CouponBottomListAdapter adapter;
    private ArrayList<CouponList> children = new ArrayList<>();
    private String selectedCoupon_id;
    private TextView tv_submit; //确定按钮
    private TextView tv_empty; //没有优惠券时显示

    public CouponBottomDialog(Context context, ArrayList<CouponList> children) {
        this.context = context;
        this.children = children;
        //初始化dialog样式
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_coupon_bottom, null);
        iv_close = view.findViewById(R.id.iv_close);
        rcy_coupon = view.findViewById(R.id.rcy_coupon);
        tv_submit = view.findViewById(R.id.button);
        tv_empty = view.findViewById(R.id.tv_empty_tip);

        adapter = new CouponBottomListAdapter(context, children);
        rcy_coupon.setAdapter(adapter);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponList selectedInfor = null;
                for(int i = 0; i < getChildren().size(); i++){
                    if(getChildren().get(i).isChecked()){
                        selectedInfor = getChildren().get(i);
                    }
                }
                ((ConfirmOrderActivity) context).dealCouponSelected(selectedInfor);
//                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.COUPON_SELECTED, selectedInfor));
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(view);
    }

    public void setChildren(ArrayList<CouponList> children) {
        this.children = children;
    }

    public void showDialog(String coupon_id) {
        if (context != null) {
            selectedCoupon_id = coupon_id;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcy_coupon.setLayoutManager(layoutManager);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);

            WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.BOTTOM;
            localLayoutParams.width = BaseUtil.getScreenWidth(context);
            if (children != null && children.size() >= 4) {
                rcy_coupon.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.GONE);
                localLayoutParams.height = BaseUtil.getScreenWidth(context) * 14 / 10;
            } else {
                localLayoutParams.height = BaseUtil.getScreenWidth(context);
                rcy_coupon.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.GONE);
                if(children != null && children.size() > 0){
                    adapter.setLists(children);
                    adapter.setCoupon_id(coupon_id);
                    adapter.notifyDataSetChanged();
                }else{
                    rcy_coupon.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.GONE);
                    tv_submit.setVisibility(View.GONE);
                }
            }
            tv_empty.setVisibility(View.GONE);
            mDialog.onWindowAttributesChanged(localLayoutParams);
            mDialog.show();
        }
    }

    public void hide() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public ArrayList<CouponList> getChildren() {
        return children;
    }

    public void setSelectedCoupon_id(String selectedCoupon_id) {
        this.selectedCoupon_id = selectedCoupon_id;
    }

    public void freshData(){
        adapter.setCoupon_id(selectedCoupon_id);
        adapter.setLists(children);
        adapter.notifyDataSetChanged();
    }

}
