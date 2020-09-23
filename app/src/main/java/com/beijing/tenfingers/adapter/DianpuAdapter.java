package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ShopDetailActivity;
import com.beijing.tenfingers.bean.ShopChild;
import com.beijing.tenfingers.bean.ShopImage;
import com.beijing.tenfingers.bean.ShopTag;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.beijing.tenfingers.view.RoundAutoImageView;

import java.util.ArrayList;

public class DianpuAdapter  extends BaseRecycleAdapter<ShopChild> {

    private ArrayList<ShopChild> list = new ArrayList<>();
    private Context context;

    public DianpuAdapter(Context contex, ArrayList<ShopChild> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        ArrayList<ShopImage> imgs=new ArrayList<>();
        imgs=list.get(position).getImgs();
        if(imgs.size()==1){
            BaseUtil.loadBitmap(imgs.get(0).getS_image_link(),0, (RoundAutoImageView)holder.getView(R.id.iv_main_second));
        }else if(imgs.size()==2){
            BaseUtil.loadBitmap(imgs.get(0).getS_image_link(),0, (RoundAutoImageView)holder.getView(R.id.iv_main_second));
            BaseUtil.loadBitmap(imgs.get(1).getS_image_link(),0, (RoundAutoImageView)holder.getView(R.id.iv_small_one_second));
        }else if(imgs.size()==3){
            BaseUtil.loadBitmap(imgs.get(0).getS_image_link(),0, (RoundAutoImageView)holder.getView(R.id.iv_main_second));
            BaseUtil.loadBitmap(imgs.get(1).getS_image_link(),0, (RoundAutoImageView)holder.getView(R.id.iv_small_one_second));
            BaseUtil.loadBitmap(imgs.get(2).getS_image_link(),0, (ImageView) holder.getView(R.id.iv_small_two_second));
        }
        ((TextView) holder.getView(R.id.tv_name)).setText(list.get(position).getS_name());
        ArrayList<ShopTag> tags=new ArrayList<>();
        tags=list.get(position).getShopTags();
        if(tags.size()==1){
            ((TextView) holder.getView(R.id.tag_one)).setText(tags.get(0).getT_name());
            ((TextView) holder.getView(R.id.tag_two)).setVisibility(View.GONE);
            ((TextView) holder.getView(R.id.tag_three)).setVisibility(View.GONE);
        }else if(tags.size()==2){
            ((TextView) holder.getView(R.id.tag_one)).setText(tags.get(0).getT_name());
          ((TextView) holder.getView(R.id.tag_two)).setText(tags.get(1).getT_name());
            ((TextView) holder.getView(R.id.tag_three)).setVisibility(View.GONE);

        }else if(tags.size()==3){
            ((TextView) holder.getView(R.id.tag_one)).setText(tags.get(0).getT_name());
            ((TextView) holder.getView(R.id.tag_two)).setText(tags.get(1).getT_name());
            ((TextView) holder.getView(R.id.tag_three)).setText(tags.get(2).getT_name());
        }
        ((TextView) holder.getView(R.id.tv_main)).setText("主营项目:"+list.get(position).getS_major());
        ((TextView) holder.getView(R.id.tv_address)).setText(list.get(position).getS_address());
        holder.getView(R.id.v_all).setTag(list.get(position));
        holder.getView(R.id.v_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopChild child= (ShopChild) v.getTag();
                Intent it=new Intent(context, ShopDetailActivity.class);
                it.putExtra("id",child.getId());
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop;
    }
}
