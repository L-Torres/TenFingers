package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ProductDetailActivity;
import com.beijing.tenfingers.activity.SelectTecActivity;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

import xtom.frame.util.XtomWindowSize;

public class FuwuVipAdapter extends BaseRecycleAdapter<Product> {

    private ArrayList<Product> list = new ArrayList<>();
    private Context context;

    public FuwuVipAdapter(Context contex, ArrayList<Product> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 30);
        int h= (int) (width*0.4);
        holder.getView(R.id.iv_img).getLayoutParams().height=h;
        BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.icon_service, (ImageView)holder.getView(R.id.iv_img));
        ((TextView)holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
        ((TextView)holder.getView(R.id.tv_time)).setText(list.get(position).getP_service_time());
        ((TextView)holder.getView(R.id.tv_num)).setText("成交"+list.get(position).getFinish_count()+"单");
        ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getP_price());
        if("1".equals(list.get(position).getIs_activity())){
            holder.getView(R.id.iv_dis).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.tv_dis_price)).setText("¥"+list.get(position).getP_price()+"次/人");
            BaseUtil.setLine((TextView) holder.getView(R.id.tv_dis_price));
            ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getActivity_price());
        }else{
            holder.getView(R.id.iv_dis).setVisibility(View.INVISIBLE);
            ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getP_price());
            ((TextView)holder.getView(R.id.tv_dis_price)).setVisibility(View.INVISIBLE);
        }
        holder.getView(R.id.tv_book).setTag(list.get(position));
        holder.getView(R.id.tv_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product p= (Product) v.getTag();
                if(BaseUtil.IsLogin()){
                    Intent it=new Intent(context, SelectTecActivity.class);
                    it.putExtra("id",p.getId());
                    it.putExtra("type","0");
                    it.putExtra("sid",p.getSid());
                    it.putExtra("is_vip",p.getIs_vip());
                    context.startActivity(it);
                }else{
                    BaseUtil.toLogin(context,"1");
                }
            }
        });
        holder.getView(R.id.v_all).setTag(list.get(position));
        holder.getView(R.id.v_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product p= (Product) v.getTag();
                Intent it=new Intent(context, ProductDetailActivity.class);
                it.putExtra("id",p.getId());
                it.putExtra("title",p.getP_name());
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_service;
    }
}
