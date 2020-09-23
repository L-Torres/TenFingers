package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ArtificerActivity;
import com.beijing.tenfingers.activity.CollectActivity;
import com.beijing.tenfingers.activity.FArtificerActivity;
import com.beijing.tenfingers.bean.Hobby;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

public class CollectAdapter extends BaseRecycleAdapter<Hobby> {

    private ArrayList<Hobby> list = new ArrayList<>();
    private Context context;

    public CollectAdapter(Context contex, ArrayList<Hobby> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);
        Hobby h=list.get(position);
        if("1".equals(h.getIs_vip())){
            holder.getView(R.id.iv_king).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.iv_king).setVisibility(View.INVISIBLE);
        }
        BaseUtil.loadBitmap(h.getT_image_link(),R.mipmap.ic_launcher, (RoundedImageView) holder.getView(R.id.iv_head));
        ((TextView) holder.getView(R.id.tv_name)).setText(h.getT_name());
        ((TextView) holder.getView(R.id.tv_e_name)).setText(h.getT_english_name());
        ((TextView) holder.getView(R.id.tv_hot)).setText(h.getT_hot_value());

        holder.getView(R.id.ll_all).setTag(h);
        holder.getView(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hobby h= (Hobby) v.getTag();
                Intent it=new Intent(context, FArtificerActivity.class);
                it.putExtra("id",h.getId());
                context.startActivity(it);

//                Activity activity= XtomActivityManager.getLastActivity();
//                if(activity instanceof CollectActivity){
//                    ((CollectActivity) activity).cancel(h.getId());
//                }

            }
        });
        holder.getView(R.id.iv_cancel).setTag(h);
        holder.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hobby h= (Hobby) v.getTag();

                Activity activity= XtomActivityManager.getLastActivity();
                if(activity instanceof CollectActivity){
                    ((CollectActivity) activity).cancel(h.getId());
                }

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_collect;
    }
}
