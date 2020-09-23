package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ShopDetailActivity;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

public class SearchEmptyAdapter extends BaseRecycleAdapter<String> {

    private ArrayList<String> list = new ArrayList<>();
    private Context context;

    public SearchEmptyAdapter(Context contex, ArrayList<String> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);
        holder.getView(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, ShopDetailActivity.class);
                context.startActivity(it);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_rc_shop;
    }
}
