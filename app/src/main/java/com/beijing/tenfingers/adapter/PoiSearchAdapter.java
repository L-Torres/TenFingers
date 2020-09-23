package com.beijing.tenfingers.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.SelectShopPositionActivity;
import com.hemaapp.hm_FrameWork.HemaAdapter;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

import static android.app.Activity.RESULT_OK;

/**
 * 地址搜索适配器
 */
public class PoiSearchAdapter extends HemaAdapter implements View.OnClickListener {
    private ArrayList<PoiItem> data = null;
    private boolean mark = false;

    private boolean isShowing = false;

    public PoiSearchAdapter(Context mContext, ArrayList<PoiItem> data) {
        super(mContext);
        this.data = data;
    }

    //是否标注第一项为当前位置
    public void setMarkFirstPoi(boolean mark) {
        this.mark = mark;
    }

    public void setShowLoc(boolean isShowing) {
        this.isShowing = isShowing;
    }

    //设置数据
    public void setData(ArrayList<PoiItem> data) {
        this.data = data;
    }

    @Override
    public boolean isEmpty() {
        if (data == null || data.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int getCount() {

        return (data == null || data.size() == 0) ? 1 : data.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isEmpty()) {
            return getEmptyView(parent);
        }
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.poi_search_item,
                    null);
            holder.locName = (TextView) convertView.findViewById(R.id.loc_name);
            holder.locAddr = (TextView) convertView.findViewById(R.id.loc_addr);
            holder.loc = (ImageView) convertView.findViewById(R.id.loc);
            holder.divider = (View) convertView.findViewById(R.id.divider);
            convertView.setTag(holder);
            convertView.setOnClickListener(this);
        }
        holder = (ViewHolder) convertView.getTag();
        PoiItem poiItem = data.get(position);
        if (mark && position == 0) {//标注第一项
            String curStr = "[当前]";
            SpannableString ss = new SpannableString(curStr + poiItem.getTitle());
            ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red));
            ss.setSpan(span, 0, curStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.locName.setText(ss);

        } else {
            holder.locName.setText(poiItem.getTitle());
        }
        holder.locAddr.setText(poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
        if (getCount() == 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            if (getCount() - 1 == position) {
                holder.divider.setVisibility(View.GONE);
            } else {
                holder.divider.setVisibility(View.VISIBLE);
            }
        }
        if (!isShowing) {
            holder.loc.setVisibility(View.GONE);
        }
        convertView.setTag(R.id.TAG, poiItem);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        PoiItem poiItem = (PoiItem) v.getTag(R.id.TAG);
        if (poiItem != null) {
            Activity activityMap = XtomActivityManager.getActivityByClass(SelectShopPositionActivity.class);
            if (activityMap != null) {
                Intent mIntent = new Intent();
                mIntent.putExtra("lng", String.valueOf(poiItem.getLatLonPoint().getLongitude()));
                mIntent.putExtra("lat", String.valueOf(poiItem.getLatLonPoint().getLatitude()));
                mIntent.putExtra("address", poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName());
                mIntent.putExtra("detail", poiItem.getSnippet());
               mIntent.putExtra("province", poiItem.getProvinceName());
               mIntent.putExtra("city",  poiItem.getCityName());
               mIntent.putExtra("distinct", poiItem.getAdName());
                activityMap.setResult(RESULT_OK, mIntent);
                activityMap.finish();
            }
        }
    }

    static class ViewHolder {
        private TextView locName;
        private TextView locAddr;

        private ImageView loc;
        private View divider;
    }
}
