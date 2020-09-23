package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.VideoListActivity;
import com.beijing.tenfingers.bean.MediaList;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

public class VidoAdapter extends RecyclerView.Adapter<VidoAdapter.BaseViewHolder> {
    private ArrayList<MediaList> dataList = new ArrayList<>();

    public void replaceAll(ArrayList<MediaList> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position,ArrayList<MediaList> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public VidoAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_water_fall, parent, false));
    }

    @Override
    public void onBindViewHolder(VidoAdapter.BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data,int position) {

        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private RoundedImageView ivImage;

        public OneViewHolder(View view) {
            super(view);
            ivImage = (RoundedImageView) view.findViewById(R.id.iv_item_water_fall);
        }

        @Override
        void setData(Object data,int position) {
            if (data != null) {
                //需要Item高度不同才能出现瀑布流的效果，此处简单粗暴地设置一下高度
                if (position % 2 == 0) {
                    ivImage.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                } else {
                    ivImage.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                }
            }
            BaseUtil.loadBitmap(dataList.get(position).getImage(),0, ivImage);

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity= XtomActivityManager.getLastActivity();
                    if(activity instanceof VideoListActivity){
                        ((VideoListActivity) activity).showDialog(dataList.get(position).getImage(),
                                dataList.get(position).getUrl());
                    }
                }
            });
        }
    }

}
