package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.TrendsList;
import com.beijing.tenfingers.interf.ZanClickListener;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayerStandard;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.showlargepic.ShowLargePicActivity;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.util.ArrayList;
import java.util.Date;

import xtom.frame.util.XtomWindowSize;

public class TrendsAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<TrendsList> list = new ArrayList<>();
    private int w;
    private double rh;
    final static int PIC_ONE = 1;//文本
    final static int PIC_TWO = 2;//图片
    final static int PIC_THREE = 3;//视频
    final static int EMPTY = 4;//空
    private ZanClickListener listener;
    private int type=0;
    public TrendsAdapter(Context context, ArrayList<TrendsList> list,int type) {
        this.context = context;
        this.list = list;
        this.type=type;
        w = context.getResources().getDisplayMetrics().widthPixels; //屏幕宽度
        rh = (XtomWindowSize.getWidth(context) - BaseUtil.dip2px(context, 24)) / 3;
    }
    public void setListener(ZanClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case PIC_ONE:
                View view = LayoutInflater.from(context).inflate(R.layout.item_trends_hanzi, parent, false);
                holder = new ViewHolder_First(view);
                break;
            case PIC_TWO:
                View view_t = LayoutInflater.from(context).inflate(R.layout.item_trends_imger, parent, false);
                holder = new ViewHolder_Second(view_t);
                break;
            case PIC_THREE:
                View view_3 = LayoutInflater.from(context).inflate(R.layout.item_trends_video, parent, false);
                holder = new ViewHolder_Third(view_3);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        TrendsList trendsList=list.get(position);
        switch (trendsList.getType()){
            case "1":
                return PIC_ONE;
            case "2":
                return PIC_TWO;
            case "3":
                return PIC_THREE;
        }

        return  EMPTY;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TrendsList t=list.get(position);
            switch (t.getType()){
                case "1":
                    SetDate_First((ViewHolder_First) holder,t,position);
                    break;
                case "2":
                    SetDate_Second((ViewHolder_Second) holder,t,position);
                    break;
                case "3":
                    SetDate_Third((ViewHolder_Third) holder,t,position);
                    break;
            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder_First extends RecyclerView.ViewHolder {
        private TextView tv_content,tv_day,tv_month;
        private ImageView iv_zan;

        public ViewHolder_First(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_day= itemView.findViewById(R.id.tv_day);
            tv_month= itemView.findViewById(R.id.tv_month);
            iv_zan= itemView.findViewById(R.id.iv_zan);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetDate_First(ViewHolder_First h, TrendsList t, int position) {
        h.tv_content.setText(t.getContent());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=format.parse(t.getCreatedTime());
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(date);
            int month=calendar.get(Calendar.MONTH)+1;
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            h.tv_day.setText(day+"");
            h.tv_month.setText(month+"月");
        }catch (Exception e){

        }
        if(type==0){

            if("1".equals(t.getIsThumb())){
                h.iv_zan.setImageResource(R.mipmap.zan_y);
            }else{
                h.iv_zan.setImageResource(R.mipmap.zan_n);
            }
        }else{
            h.iv_zan.setVisibility(View.GONE);
        }
        h.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && t != null) {
                    if("1".equals(t.getIsThumb())){
                        listener.thumb(t.getId(),0);
                       t.setIsThumb("0");
                        h.iv_zan.setImageResource(R.mipmap.zan_n);
                    }else{
                        listener.thumb(t.getId(),1);
                        t.setIsThumb("1");
                        h.iv_zan.setImageResource(R.mipmap.zan_y);
                    }
                }
            }
        });
    }
    public static class ViewHolder_Second extends RecyclerView.ViewHolder {
        private TextView tv_content,tv_day,tv_month;
        private RoundedImageView iv_one,iv_two,iv_three;
        private ImageView iv_zan;

        public ViewHolder_Second(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_one = itemView.findViewById(R.id.iv_one);
            iv_two = itemView.findViewById(R.id.iv_two);
            iv_three = itemView.findViewById(R.id.iv_three);
            tv_day= itemView.findViewById(R.id.tv_day);
            tv_month= itemView.findViewById(R.id.tv_month);
            iv_zan= itemView.findViewById(R.id.iv_zan);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetDate_Second(ViewHolder_Second h, TrendsList t, int position) {
        h.tv_content.setText(t.getContent());
        if(type==0){

            if("1".equals(t.getIsThumb())){
                h.iv_zan.setImageResource(R.mipmap.zan_y);
            }else{
                h.iv_zan.setImageResource(R.mipmap.zan_n);
            }
        }else{
            h.iv_zan.setVisibility(View.GONE);
        }
        h.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && t != null) {
                    if("1".equals(t.getIsThumb())){
                        listener.thumb(t.getId(),0);
                        t.setIsThumb("0");
                        h.iv_zan.setImageResource(R.mipmap.zan_n);
                    }else{
                        listener.thumb(t.getId(),1);
                        t.setIsThumb("1");
                        h.iv_zan.setImageResource(R.mipmap.zan_y);
                    }
                }
            }
        });
        String temp=t.getImg();
        if(temp!=null && !"".equals(temp)){
            if(temp.contains(",")){
                String[] imgs=temp.split(",");
                if(imgs.length==1){
                    BaseUtil.loadBitmap(context,imgs[0],0,h.iv_one,true);
                    h.iv_one.setVisibility(View.VISIBLE);
                    h.iv_two.setVisibility(View.INVISIBLE);
                    h.iv_three.setVisibility(View.INVISIBLE);

                    ArrayList<String> mImgs = new ArrayList<>();
                    ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imgs[0]);
                    bigImgs.add(imgs[0]);
                    h.iv_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 0);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                }else if(imgs.length==2){
                    BaseUtil.loadBitmap(context,imgs[0],0,h.iv_one,true);
                    BaseUtil.loadBitmap(context,imgs[1],0,h.iv_two,true);
                    h.iv_one.setVisibility(View.VISIBLE);
                    h.iv_two.setVisibility(View.VISIBLE);
                    h.iv_three.setVisibility(View.INVISIBLE);
                    ArrayList<String> mImgs = new ArrayList<>();
                    ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imgs[0]);
                    bigImgs.add(imgs[0]);
                    mImgs.add(imgs[1]);
                    bigImgs.add(imgs[1]);
                    h.iv_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 0);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                    h.iv_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 1);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                }else if(imgs.length==3){
                    BaseUtil.loadBitmap(context,imgs[0],0,h.iv_one,true);
                    BaseUtil.loadBitmap(context,imgs[1],0,h.iv_two,true);
                    BaseUtil.loadBitmap(context,imgs[2],0,h.iv_three,true);
                    h.iv_one.setVisibility(View.VISIBLE);
                    h.iv_two.setVisibility(View.VISIBLE);
                    h.iv_three.setVisibility(View.VISIBLE);
                    ArrayList<String> mImgs = new ArrayList<>();
                    ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imgs[0]);
                    bigImgs.add(imgs[0]);
                    mImgs.add(imgs[1]);
                    bigImgs.add(imgs[1]);
                    mImgs.add(imgs[2]);
                    bigImgs.add(imgs[2]);
                    h.iv_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 0);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                    h.iv_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 1);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                    h.iv_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 1);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                }
            }else{
                BaseUtil.loadBitmap(context,temp,0,h.iv_one,true);
                h.iv_one.setVisibility(View.VISIBLE);
                h.iv_two.setVisibility(View.INVISIBLE);
                h.iv_three.setVisibility(View.INVISIBLE);
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                mImgs.add(temp);
                bigImgs.add(temp);
                h.iv_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(context, ShowLargePicActivity.class);
                        it.putExtra("position", 0);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        context.startActivity(it);
                    }
                });
            }
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=format.parse(t.getCreatedTime());
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(date);
            int month=calendar.get(Calendar.MONTH)+1;
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            h.tv_day.setText(day+"");
            h.tv_month.setText(month+"月");
        }catch (Exception e){
        }


    }
    public static class ViewHolder_Third extends RecyclerView.ViewHolder {
        private TextView tv_content,tv_day,tv_month;
        private JCVideoPlayerStandard videoPlayer;
        private ImageView iv_zan;
        public ViewHolder_Third(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            videoPlayer=itemView.findViewById(R.id.videoplayer);
            tv_day= itemView.findViewById(R.id.tv_day);
            tv_month= itemView.findViewById(R.id.tv_month);
            iv_zan= itemView.findViewById(R.id.iv_zan);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetDate_Third(ViewHolder_Third h, TrendsList t, int position) {
        h.tv_content.setText(t.getContent());
        if(type==0){

            if("1".equals(t.getIsThumb())){
                h.iv_zan.setImageResource(R.mipmap.zan_y);
            }else{
                h.iv_zan.setImageResource(R.mipmap.zan_n);
            }
        }else{
            h.iv_zan.setVisibility(View.GONE);
        }
        h.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && t != null) {
                    if("1".equals(t.getIsThumb())){
                        listener.thumb(t.getId(),0);
                        t.setIsThumb("0");
                        h.iv_zan.setImageResource(R.mipmap.zan_n);
                    }else{
                        listener.thumb(t.getId(),1);
                        t.setIsThumb("1");
                        h.iv_zan.setImageResource(R.mipmap.zan_y);
                    }
                }
            }
        });
        BaseUtil.loadBitmap(t.getImg(),0,h.videoPlayer.thumbImageView);
        h.videoPlayer.setUp(t.getUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=format.parse(t.getCreatedTime());
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(date);
            int month=calendar.get(Calendar.MONTH)+1;
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            h.tv_day.setText(day+"");
            h.tv_month.setText(month+"月");
        }catch (Exception e){

        }
    }


}
