package com.beijing.tenfingers.adapter;

import android.content.Context;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

/**
 * 店铺列表适配器
 */
public class ShopAdapter extends BaseRecycleAdapter<String> {

    private ArrayList<String> list = new ArrayList<>();
    private Context context;

    public ShopAdapter(Context contex, ArrayList<String> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);


    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop;
    }
}
