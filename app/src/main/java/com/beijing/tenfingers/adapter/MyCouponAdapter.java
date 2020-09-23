package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MyCouponAdapter extends BaseRecycleAdapter<CouponList> {

    private ArrayList<CouponList> list = new ArrayList<>();
    private Context context;

    public MyCouponAdapter(Context contex, ArrayList<CouponList> datas) {
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
        ((TextView) holder.getView(R.id.tv_use)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.GO_TO_SERVICE));
            }
        });



    }

    @Override
    public int getLayoutId() {
        return R.layout.item_coupon;
    }
}
