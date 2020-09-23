package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.CouponListActivity;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.beijing.tenfingers.view.ToastUtils;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

public class CouponAdapter extends BaseRecycleAdapter<CouponList> {

    private ArrayList<CouponList> list = new ArrayList<>();
    private Context context;

    public CouponAdapter(Context contex, ArrayList<CouponList> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);

        ((TextView) holder.getView(R.id.tv_money)).setText(list.get(position).getC_price());
        ((TextView) holder.getView(R.id.tv_condition)).setText(list.get(position).getC_condition());
        ((TextView) holder.getView(R.id.tv_name)).setText(list.get(position).getC_name());
        ((TextView) holder.getView(R.id.tv_time)).setText(list.get(position).getC_start()+"-"+list.get(position).getC_end());
        if("0".equals(list.get(position).getStatus())){
            ((TextView) holder.getView(R.id.tv_use)).setText("领取");
        }else{
            ((TextView) holder.getView(R.id.tv_use)).setText("已领取");
        }

        holder.getView(R.id.tv_use).setTag(list.get(position));
        holder.getView(R.id.tv_use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp=((TextView) holder.getView(R.id.tv_use)).getText().toString();
                CouponList c= (CouponList) v.getTag();
                if("领取".equals(temp)){
                    Activity activity= XtomActivityManager.getLastActivity();
                    if(activity instanceof CouponListActivity){
                        ((CouponListActivity) activity).getCoupon(c.getId());
                    }
                }else{
                    ToastUtils.show("您已经领取过该优惠券");
                }

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_coupon;
    }
}
