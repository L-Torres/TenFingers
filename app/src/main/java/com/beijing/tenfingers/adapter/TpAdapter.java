package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.TimePoint;
import com.beijing.tenfingers.view.BottomTimeDialog;
import com.hemaapp.hm_FrameWork.HemaAdapter;

import java.util.ArrayList;

import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;

public class TpAdapter extends HemaAdapter implements View.OnClickListener {

    private ArrayList<TimePoint> times=new ArrayList<>();
    public TpAdapter(Context mContext) {
        super(mContext);
    }
    public void setData( ArrayList<TimePoint> times){
        if(this.times!=null){

            this.times.clear();
        }
        this.times.addAll(times);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(times == null || times.size() == 0){
            return 1;
        }else{
            return times.size();
        }
    }

    @Override
    public boolean isEmpty() {
        if(times == null || times.size() == 0)
            return true;
        return false;
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
        if(isEmpty())
            return getEmptyView(parent);
        int type = getItemViewType(position);

        ViewHolder holder = null;
//        if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.item_timepoint, null);
                    holder = new ViewHolder();
                    findView(convertView, holder);
                    setDataAdd(position,holder);
                    convertView.setTag(R.id.TAG_VIEWHOLDER, holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag(R.id.TAG_VIEWHOLDER);
//        }
        return convertView;
    }

    private void setDataAdd(int position, final ViewHolder holder) {
        holder.tv_time.setText(times.get(position).getPointText());
        holder.tv_time.setTag(times.get(position));
        if(times.get(position).isFlag()){
            holder.tv_time.setBackgroundResource(R.drawable.bg_tag_green_green_5);
            holder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.tv_time.setBackgroundResource(R.drawable.bg_tag_green_white_5);
            holder.tv_time.setTextColor(Color.parseColor("#FF05775D"));
        }
//        if(isNull(times.get(position).getServiceStatus()) || "3".equals(times.get(position).getServiceStatus())){
//
//
//        }else if("0".equals(times.get(position).getServiceStatus())){
//
//        }else if("1".equals(times.get(position).getServiceStatus()) || "2".equals(times.get(position).getServiceStatus())){
//            holder.tv_time.setBackgroundResource(R.drawable.bg_tag_green_red_5);
//            holder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
//        }

        holder.tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePoint t= (TimePoint) v.getTag();
                for (TimePoint time : times) {
                    if(time.getId().equals(t.getId())){
                        time.setFlag(true);
                        XtomSharedPreferencesUtil.save(mContext,"time",time.getId());
                        XtomSharedPreferencesUtil.save(mContext,"timepoint",time.getPointText());
                    }else{
                        time.setFlag(false);
                    }
                }
                notifyDataSetChanged();
//                if(times.get(position).isFlag()){
//
//                }else{
//                    holder.tv_time.setBackgroundResource(R.drawable.bg_tag_green_white_5);
//                    holder.tv_time.setTextColor(Color.parseColor("#FF05775D"));
//                }
//                if(isNull(t.getServiceStatus()) || "3".equals(t.getServiceStatus())){
//                    t.setServiceStatus("0");
//                    holder.tv_time.setBackgroundResource(R.drawable.bg_tag_green_white_5);
//                    holder.tv_time.setTextColor(Color.parseColor("#FF05775D"));
//                }else if("0".equals(t.getServiceStatus())){
//                    holder.tv_time.setBackgroundResource(R.drawable.bg_tag_green_green_5);
//                    holder.tv_time.setTextColor(Color.parseColor("#FFFFFF"));
//                    t.setServiceStatus("3");
//
//                }else if("1".equals(t.getServiceStatus()) || "2".equals(t.getServiceStatus())){
//                    XtomToastUtil.showLongToast(mContext,"您当前时间已被占用暂时不能操作！");
//                }
            }
        });
    }


    private void findView(View view, ViewHolder holder) {
        holder.tv_time = (TextView) view.findViewById(R.id.tv_time);

    }

    private static class ViewHolder {
        TextView tv_time;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:


                break;
            default:
                break;
        }
    }

}
