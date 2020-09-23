package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ChargeActivity;
import com.beijing.tenfingers.bean.Price;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomWindowSize;

public class FeeAdapter  extends BaseRecycleAdapter<Price> {

    private ArrayList<Price> list = new ArrayList<>();
    private Context context;

    public FeeAdapter(Context contex, ArrayList<Price> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        int w=(int) ((XtomWindowSize.getWidth(context)- BaseUtil.dip2px(context,50))/3.3);
        holder.getView(R.id.ll_fee).getLayoutParams().width= w;
        holder.getView(R.id.ll_fee).getLayoutParams().height= (int) (w*1.3);
        ( (TextView) holder.getView(R.id.tv_price)).setText(list.get(position).getPrice());
        if(list.get(position).isFlag()){
            holder.getView(R.id.ll_fee).setBackgroundResource(R.drawable.bg_darkgreen_4);
            ( (TextView) holder.getView(R.id.tv_price)).setTextColor(Color.parseColor("#FFFFFF"));
            ( (TextView) holder.getView(R.id.tv_flag)).setTextColor(Color.parseColor("#FFFFFF"));

        }else{
            holder.getView(R.id.ll_fee).setBackgroundResource(R.drawable.bg_white_green_tag_4);
            ( (TextView) holder.getView(R.id.tv_price)).setTextColor(Color.parseColor("#ff007055"));
            ( (TextView) holder.getView(R.id.tv_flag)).setTextColor(Color.parseColor("#ff007055"));

        }
        holder.getView(R.id.ll_fee).setTag(list.get(position));
        holder.getView(R.id.ll_fee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Price p= (Price) v.getTag();
                for (Price price : list) {
                    if(price.getPrice().equals(p.getPrice())){
                        price.setFlag(true);
                    }else{
                        price.setFlag(false);
                    }
                }

                Activity activity= XtomActivityManager.getLastActivity();
                if(activity instanceof ChargeActivity){
                    ((ChargeActivity) activity).setFee(p.getPrice(),p.getId());
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_fee;
    }
}
