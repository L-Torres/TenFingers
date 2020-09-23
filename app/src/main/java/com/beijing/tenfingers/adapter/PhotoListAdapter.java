package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;


import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.MediaList;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.hemaapp.hm_FrameWork.showlargepic.ShowLargePicActivity;
import com.hemaapp.hm_FrameWork.view.ShowLargeImageView;

import java.util.ArrayList;

/**
 * 技师相册
 */
public class PhotoListAdapter extends BaseRecycleAdapter<MediaList> {

    private ArrayList<MediaList> list = new ArrayList<>();
    private Context context;
    private ShowLargeImageView mView;
    private View rootView;
    public PhotoListAdapter(Context contex, ArrayList<MediaList> datas,View rootView) {
        super(datas);
        this.context = contex;
        this.list = datas;
        this.rootView=rootView;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);

       int w= BaseUtil.getScreenWidth(context)/3-BaseUtil.dip2px(context,16);
        holder.getView(R.id.iv_pic).getLayoutParams().height=w;
        BaseUtil.loadBitmap(list.get(position).getUrl(),0, (ImageView) holder.getView(R.id.iv_pic));

        holder.getView(R.id.iv_pic).setTag(list.get(position).getImage());
        holder.getView(R.id.iv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                for (MediaList adImage : list) {
                    mImgs.add(adImage.getImage());
                    bigImgs.add(adImage.getImage());
                }
                Intent it = new Intent(context, ShowLargePicActivity.class);
                it.putExtra("position", position);
                it.putExtra("imagelist", mImgs);
                it.putExtra("bigImageList", bigImgs);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_photo;
    }
}
