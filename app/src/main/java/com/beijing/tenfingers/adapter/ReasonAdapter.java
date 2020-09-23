package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.OrderDetailActivity;
import com.beijing.tenfingers.bean.Reason;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

public class ReasonAdapter extends BaseRecycleAdapter<Reason> {

    private ArrayList<Reason> list = new ArrayList<>();
    private Context context;

    public ReasonAdapter(Context contex, ArrayList<Reason> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        ((TextView) holder.getView(R.id.tv_reason)).setText(list.get(position).getContent());
        if(list.get(position).isFlag()){
            ((ImageView) holder.getView(R.id.iv_pic)).setVisibility(View.VISIBLE);
        }else{
            ((ImageView) holder.getView(R.id.iv_pic)).setVisibility(View.INVISIBLE);
        }
        holder.getView(R.id.ll_all).setTag(list.get(position));
        holder.getView(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reason r= (Reason) v.getTag();
                for (Reason reason : list) {
                    if(r.getContent().equals(reason.getContent())){
                        reason.setFlag(true);
                    }else{
                        reason.setFlag(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_reason;
    }
}
