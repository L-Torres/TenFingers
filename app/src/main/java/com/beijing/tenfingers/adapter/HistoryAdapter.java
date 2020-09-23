package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.HistoryListActivity;
import com.beijing.tenfingers.activity.ProductDetailActivity;
import com.beijing.tenfingers.bean.MyHistory;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

public class HistoryAdapter extends BaseRecycleAdapter<MyHistory> {

    private ArrayList<MyHistory> list = new ArrayList<>();
    private Context context;

    public HistoryAdapter(Context contex, ArrayList<MyHistory> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//        ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);

        ((TextView) holder.getView(R.id.tv_date)).setText(list.get(position).getDate());
        BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.order_pic, (ImageView) holder.getView(R.id.iv_img));
        ((TextView) holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
        ((TextView) holder.getView(R.id.tv_content)).setText(list.get(position).getP_desc());
        ((TextView) holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getP_price()+"元/次");
        if(list.size()>1 && position!=0){
            if(list.get(position).getDate().equals(list.get(position-1).getDate())){
                holder.getView(R.id.tv_date).setVisibility(View.GONE);
            }else{
                holder.getView(R.id.tv_date).setVisibility(View.VISIBLE);
            }
        }
        if(position==0){
            holder.getView(R.id.tv_date).setVisibility(View.VISIBLE);
        }


        ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
        ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyHistory m= (MyHistory) v.getTag();
                Intent it=new Intent(context, ProductDetailActivity.class);
                it.putExtra("id",m.getId());
                context.startActivity(it);
            }
        });
        ((ImageView) holder.getView(R.id.iv_del)).setTag(list.get(position));

        ((ImageView) holder.getView(R.id.iv_del)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyHistory m= (MyHistory) v.getTag();
                Activity activity= XtomActivityManager.getLastActivity();
                if (activity instanceof HistoryListActivity){
                    ((HistoryListActivity) activity).delete(m.getId());
                }
            }
        });


//        int w=(int) ((XtomWindowSize.getWidth(context)- BaseUtil.dip2px(context,50))/3.3);
//        holder.getView(R.id.ll_fee).getLayoutParams().width= w;
//        holder.getView(R.id.ll_fee).getLayoutParams().height= (int) (w*1.3);

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_scan;
    }
}
