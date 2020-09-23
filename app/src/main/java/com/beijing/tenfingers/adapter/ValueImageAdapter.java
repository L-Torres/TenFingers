package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ValueActivity;
import com.beijing.tenfingers.bean.MediaList;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaAdapter;
import com.hemaapp.hm_FrameWork.showlargepic.ShowLargePicActivity;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.hemaapp.hm_FrameWork.view.ShowLargeImageView;

import java.util.ArrayList;

/**
 * 选择图片适配器
 */
public class ValueImageAdapter extends HemaAdapter implements View.OnClickListener {
    private static final int TYPE_ADD = 0;
    private static final int TYPE_IMAGE = 1;

    private View rootView;
    private ArrayList<String> images;

    public ValueImageAdapter(Context mContext, View rootView,
                             ArrayList<String> images) {
        super(mContext);
        this.rootView = rootView;
        this.images = images;
    }

    @Override
    public int getCount() {
        int count;
        int size = images == null ? 0 : images.size();
        if (size < 3)
            count = size + 1;
        else
            count = 3;
        return count;
    }

    @Override
    public boolean isEmpty() {
        int size = images == null ? 0 : images.size();
        return size == 0;
    }

    @Override
    public int getItemViewType(int position) {
        int size = images == null ? 0 : images.size();
        int count = getCount();
        if (size < 3) {
            if (position == count - 1)
                return TYPE_ADD;
            else
                return TYPE_IMAGE;
        } else {
            return TYPE_IMAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_ADD:
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.griditem_sendblog_camera_value, null);
                    holder = new ViewHolder();
                    findView(convertView, holder);
                    convertView.setTag(R.id.TAG_VIEWHOLDER, holder);
                    break;
                case TYPE_IMAGE:
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.griditem_sendblog_image_value, null);
                    holder = new ViewHolder();
                    findView(convertView, holder);
                    convertView.setTag(R.id.TAG_VIEWHOLDER, holder);
                    break;
            }
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.TAG_VIEWHOLDER);
        }

        switch (type) {
            case TYPE_ADD:
                setDataAdd(position, holder);
                break;
            case TYPE_IMAGE:
                setDataImage(position, holder);
                break;
        }

        return convertView;
    }

    private void setDataAdd(int position, ViewHolder holder) {
        holder.addButton.setOnClickListener(this);
    }

    private void setDataImage(int position, ViewHolder holder) {
        String path = images.get(position);

        holder.iv_delete.setTag(path);
        holder.iv_delete.setOnClickListener(this);
        holder.imageView.setCornerRadius(5);
        ValueActivity activity = (ValueActivity) mContext;
        BaseUtil.loadBitmap(path,R.mipmap.icon_default_shu,holder.imageView);
        holder.imageView.setTag(R.id.TAG, path);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                for (String adImage : images) {
                    mImgs.add(adImage);
                    bigImgs.add(adImage);
                }
                Intent it = new Intent(mContext, ShowLargePicActivity.class);
                it.putExtra("position", position);
                it.putExtra("imagelist", mImgs);
                it.putExtra("bigImageList", bigImgs);
                mContext.startActivity(it);
            }
        });
    }

    private void findView(View view, ViewHolder holder) {
        holder.addButton = (Button) view.findViewById(R.id.btn_select);
        holder.imageView = (RoundedImageView) view.findViewById(R.id.iv_value_pic);
        holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
    }

    private static class ViewHolder {
        Button addButton;
        RoundedImageView imageView;
        ImageView iv_delete;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                ValueActivity activity = (ValueActivity) mContext;
                activity.showImageWay();
                break;
            case R.id.iv_delete:
                String dPath = (String) v.getTag();
//                File file = new File(dPath);
//                file.delete();
                images.remove(dPath);
                notifyDataSetChanged();
                break;
//            case R.id.iv_value_pic:
//                String iPath = (String) v.getTag(R.id.TAG);
//                mView = new ShowLargeImageView((Activity) mContext, rootView);
//                mView.show();
//                mView.setImagePath(iPath);
//                break;
            default:
                break;
        }
    }

    private ShowLargeImageView mView;
}
