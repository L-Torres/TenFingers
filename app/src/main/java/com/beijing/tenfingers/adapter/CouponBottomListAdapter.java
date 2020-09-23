package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ConfirmOrderActivity;
import com.beijing.tenfingers.bean.CouponList;

import java.util.ArrayList;

/**
 * 结算界面，优惠券列表的数据适配器
 */
public class CouponBottomListAdapter extends RecyclerView.Adapter<CouponBottomListAdapter.MyViewHolder> {

    private ArrayList<CouponList> lists = new ArrayList<>();
    private Context context;
    private String coupon_id;

    public CouponBottomListAdapter(Context context, ArrayList<CouponList> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setLists(ArrayList<CouponList> lists) {
        this.lists = lists;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view_choice = LayoutInflater.from(context).inflate(R.layout.item_coupon_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view_choice);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CouponList infor = lists.get(position);

        holder.tv_title.setText(infor.getC_price());
        holder.tv_unit.setText("元");
        holder.tv_time.setText(infor.getC_start()+"-"+infor.getC_end());
        if(infor.isChecked()){
            holder.img_selected.setImageResource(R.mipmap.select_y_green);
        }else{
            holder.img_selected.setImageResource(R.mipmap.select_n);
        }
        holder.ll_select.setTag(infor);
        holder.ll_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponList selectedCouponInfor = (CouponList) v.getTag();
                for (int i=0;i<lists.size();i++){
                    if(lists.get(i).getId().equals(selectedCouponInfor.getId())){
                        lists.get(i).setChecked(true);
                    }else{
                        lists.get(i).setChecked(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        if (position == getItemCount() - 1) {
            holder.item_bottom.setVisibility(View.VISIBLE);
        } else {
            holder.item_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_unit, tv_limit_des;
        private ImageView img_left;
        private TextView tv_name, tv_time, tv_content, tv_tips;
        private ImageView img_selected;
        private ImageView img_cover, item_bottom;
        private LinearLayout ll_select;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_discount);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_limit_des = itemView.findViewById(R.id.tv_factor);
            img_left = itemView.findViewById(R.id.img_coupon_left);
            tv_name = (TextView) itemView.findViewById(R.id.tv_type_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_desc);
            img_selected = (ImageView) itemView.findViewById(R.id.img_select);
            img_cover = (ImageView) itemView.findViewById(R.id.openImg);
            item_bottom = (ImageView) itemView.findViewById(R.id.view_bottom);
            tv_tips = (TextView) itemView.findViewById(R.id.tv_tips);
            ll_select=itemView.findViewById(R.id.ll_select);
        }
    }

    public void freshData() {
        notifyDataSetChanged();
    }

}
