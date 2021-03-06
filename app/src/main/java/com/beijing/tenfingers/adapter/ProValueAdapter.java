package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.CommonList;
import com.beijing.tenfingers.bean.ValueList;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.hemaapp.hm_FrameWork.showlargepic.ShowLargePicActivity;

import java.text.ParseException;
import java.util.ArrayList;

public class ProValueAdapter extends BaseRecycleAdapter<ValueList> {

    private ArrayList<ValueList> list = new ArrayList<>();
    public ProValueAdapter(Context contex, ArrayList<ValueList> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    private Context context;

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
        int width =  BaseUtil.getScreenWidth(context)-BaseUtil.dip2px(context,138);
        BaseUtil.setRatingBarHeight(context, R.mipmap.star_y, (RatingBar) holder.getView(R.id.rating_at));
        ((LinearLayout) holder.getView(R.id.ll_img)).getLayoutParams().height=width/3;
        BaseUtil.loadBitmap(list.get(position).getU_image_link(),R.mipmap.ic_launcher, (ImageView) holder.getView(R.id.iv_head));
        ((TextView) holder.getView(R.id.tv_name)).setText(list.get(position).getU_nickname());
        if("".equals(list.get(position).getC_content())){
            ((TextView) holder.getView(R.id.tv_content)).setText("用户好像忘记发表言论了~~");
        }else{

            ((TextView) holder.getView(R.id.tv_content)).setText(list.get(position).getC_content());
        }
        try {
            ((TextView) holder.getView(R.id.tv_date)).setText(BaseUtil.stampToDate(list.get(position).getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(list.get(position).getC_star()!=null && !"".equals(list.get(position).getC_star())){
            float temp=Float.valueOf(list.get(position).getC_star());
            ((RatingBar) holder.getView(R.id.rating_at)).setRating(temp);
        }
        String temp=list.get(position).getImages();
        if(temp!=null && !"".equals(temp)){
            holder.getView(R.id.ll_img).setVisibility(View.VISIBLE);
            if(temp.contains(",")){

                String[] imgs=temp.split(",");
                if(imgs.length==1){
                    BaseUtil.loadBitmap(context,imgs[0],0, (ImageView) holder.getView(R.id.iv_one),true);
                    holder.getView(R.id.iv_one).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_two).setVisibility(View.INVISIBLE);
                    holder.getView(R.id.iv_three).setVisibility(View.INVISIBLE);

                    ArrayList<String> mImgs = new ArrayList<>();
                    ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imgs[0]);
                    bigImgs.add(imgs[0]);
                    holder.getView(R.id.iv_one).setOnClickListener(new View.OnClickListener() {
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
                    BaseUtil.loadBitmap(context,imgs[0],0, (ImageView) holder.getView(R.id.iv_one),true);
                    BaseUtil.loadBitmap(context,imgs[1],0, (ImageView) holder.getView(R.id.iv_two),true);
                    holder.getView(R.id.iv_one).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_two).setVisibility(View.VISIBLE);
                    holder.getView(R.id.iv_three).setVisibility(View.INVISIBLE);
                    ArrayList<String> mImgs = new ArrayList<>();
                    ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imgs[0]);
                    bigImgs.add(imgs[0]);
                    mImgs.add(imgs[1]);
                    bigImgs.add(imgs[1]);
                    holder.getView(R.id.iv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 0);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                    holder.getView(R.id.iv_two).setOnClickListener(new View.OnClickListener() {
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
                    BaseUtil.loadBitmap(context,imgs[0],0, (ImageView) holder.getView(R.id.iv_one),true);
                    BaseUtil.loadBitmap(context,imgs[1],0,(ImageView) holder.getView(R.id.iv_two),true);
                    BaseUtil.loadBitmap(context,imgs[2],0,(ImageView) holder.getView(R.id.iv_three),true);
                    ( holder.getView(R.id.iv_one)).setVisibility(View.VISIBLE);
                    ( holder.getView(R.id.iv_two)).setVisibility(View.VISIBLE);
                    ( holder.getView(R.id.iv_three)).setVisibility(View.VISIBLE);
                    ArrayList<String> mImgs = new ArrayList<>();
                    ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imgs[0]);
                    bigImgs.add(imgs[0]);
                    mImgs.add(imgs[1]);
                    bigImgs.add(imgs[1]);
                    mImgs.add(imgs[2]);
                    bigImgs.add(imgs[2]);
                    ( holder.getView(R.id.iv_one)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 0);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                    ( holder.getView(R.id.iv_two)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(context, ShowLargePicActivity.class);
                            it.putExtra("position", 1);
                            it.putExtra("imagelist", mImgs);
                            it.putExtra("bigImageList", bigImgs);
                            context.startActivity(it);
                        }
                    });
                    ( holder.getView(R.id.iv_three)).setOnClickListener(new View.OnClickListener() {
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

                BaseUtil.loadBitmap(context,temp,0, (ImageView) holder.getView(R.id.iv_one),true);
                ( holder.getView(R.id.iv_one)).setVisibility(View.VISIBLE);
                ( holder.getView(R.id.iv_two)).setVisibility(View.INVISIBLE);
                ( holder.getView(R.id.iv_three)).setVisibility(View.INVISIBLE);
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                mImgs.add(temp);
                bigImgs.add(temp);
                ( holder.getView(R.id.iv_one)).setOnClickListener(new View.OnClickListener() {
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
        }else{
            holder.getView(R.id.ll_img).setVisibility(View.GONE);
            ( holder.getView(R.id.iv_one)).setVisibility(View.GONE);
            ( holder.getView(R.id.iv_two)).setVisibility(View.GONE);
            ( holder.getView(R.id.iv_three)).setVisibility(View.GONE);

        }
//        if(list.get(position).getImageLists()!=null && list.get(position).getImageLists().size()!=0){
//            holder.getView(R.id.ll_img).setVisibility(View.VISIBLE);
//            if(list.get(position).getImageLists().size()==1){
//                ( holder.getView(R.id.iv_one)).setVisibility(View.VISIBLE);
//                ( holder.getView(R.id.iv_two)).setVisibility(View.INVISIBLE);
//                ( holder.getView(R.id.iv_three)).setVisibility(View.INVISIBLE);
//                BaseUtil.loadBitmap(list.get(position).getImageLists().get(0).getImage_link(),0, (ImageView) holder.getView(R.id.iv_one));
//                holder.getView(R.id.iv_one).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ArrayList<String> mImgs = new ArrayList<>();
//                        ArrayList<String> bigImgs = new ArrayList<>();
//                        mImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        Intent it = new Intent(context, ShowLargePicActivity.class);
//                        it.putExtra("position", 0);
//                        it.putExtra("imagelist", mImgs);
//                        it.putExtra("bigImageList", bigImgs);
//                        context.startActivity(it);
//                    }
//                });
//            }else if(list.get(position).getImageLists().size()==2){
//                ( holder.getView(R.id.iv_one)).setVisibility(View.VISIBLE);
//                ( holder.getView(R.id.iv_two)).setVisibility(View.VISIBLE);
//                ( holder.getView(R.id.iv_three)).setVisibility(View.INVISIBLE);
//                BaseUtil.loadBitmap(list.get(position).getImageLists().get(0).getImage_link(),0, (ImageView) holder.getView(R.id.iv_one));
//                BaseUtil.loadBitmap(list.get(position).getImageLists().get(1).getImage_link(),0, (ImageView) holder.getView(R.id.iv_two));
//                holder.getView(R.id.iv_one).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ArrayList<String> mImgs = new ArrayList<>();
//                        ArrayList<String> bigImgs = new ArrayList<>();
//                        mImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        Intent it = new Intent(context, ShowLargePicActivity.class);
//                        it.putExtra("position", 0);
//                        it.putExtra("imagelist", mImgs);
//                        it.putExtra("bigImageList", bigImgs);
//                        context.startActivity(it);
//                    }
//                });
//                holder.getView(R.id.iv_two).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ArrayList<String> mImgs = new ArrayList<>();
//                        ArrayList<String> bigImgs = new ArrayList<>();
//                        mImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        Intent it = new Intent(context, ShowLargePicActivity.class);
//                        it.putExtra("position", 1);
//                        it.putExtra("imagelist", mImgs);
//                        it.putExtra("bigImageList", bigImgs);
//                        context.startActivity(it);
//                    }
//                });
//            }else if(list.get(position).getImageLists().size()>2){
//                ( holder.getView(R.id.iv_one)).setVisibility(View.VISIBLE);
//                ( holder.getView(R.id.iv_two)).setVisibility(View.VISIBLE);
//                ( holder.getView(R.id.iv_three)).setVisibility(View.VISIBLE);
//                BaseUtil.loadBitmap(list.get(position).getImageLists().get(0).getImage_link(),0, (ImageView) holder.getView(R.id.iv_one));
//                BaseUtil.loadBitmap(list.get(position).getImageLists().get(1).getImage_link(),0, (ImageView) holder.getView(R.id.iv_two));
//                BaseUtil.loadBitmap(list.get(position).getImageLists().get(2).getImage_link(),0, (ImageView) holder.getView(R.id.iv_three));
//
//
//                holder.getView(R.id.iv_one).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ArrayList<String> mImgs = new ArrayList<>();
//                        ArrayList<String> bigImgs = new ArrayList<>();
//                        mImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(2).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(2).getImage_link());
//                        Intent it = new Intent(context, ShowLargePicActivity.class);
//                        it.putExtra("position", 0);
//                        it.putExtra("imagelist", mImgs);
//                        it.putExtra("bigImageList", bigImgs);
//                        context.startActivity(it);
//                    }
//                });
//                holder.getView(R.id.iv_two).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ArrayList<String> mImgs = new ArrayList<>();
//                        ArrayList<String> bigImgs = new ArrayList<>();
//                        mImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(2).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(2).getImage_link());
//                        Intent it = new Intent(context, ShowLargePicActivity.class);
//                        it.putExtra("position", 1);
//                        it.putExtra("imagelist", mImgs);
//                        it.putExtra("bigImageList", bigImgs);
//                        context.startActivity(it);
//                    }
//                });
//                holder.getView(R.id.iv_three).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ArrayList<String> mImgs = new ArrayList<>();
//                        ArrayList<String> bigImgs = new ArrayList<>();
//                        mImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        mImgs.add(list.get(position).getImageLists().get(2).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(0).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(1).getImage_link());
//                        bigImgs.add(list.get(position).getImageLists().get(2).getImage_link());
//                        Intent it = new Intent(context, ShowLargePicActivity.class);
//                        it.putExtra("position", 2);
//                        it.putExtra("imagelist", mImgs);
//                        it.putExtra("bigImageList", bigImgs);
//                        context.startActivity(it);
//                    }
//                });
//
//
//
//
//            }
//        }else{
//            holder.getView(R.id.ll_img).setVisibility(View.GONE);
//            ( holder.getView(R.id.iv_one)).setVisibility(View.GONE);
//            ( holder.getView(R.id.iv_two)).setVisibility(View.GONE);
//            ( holder.getView(R.id.iv_three)).setVisibility(View.GONE);
//        }
        if(!"".equals(list.get(position).getReply()) && list.get(position).getReply()!=null ){
            holder.getView(R.id.ll_reply).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.tv_reply)).setText(list.get(position).getReply());
        }else{
            holder.getView(R.id.ll_reply).setVisibility(View.GONE);
        }



    }

    @Override
    public int getLayoutId() {
        return R.layout.item_value;
    }
}
