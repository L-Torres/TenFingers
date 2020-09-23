package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ArtificerActivity;
import com.beijing.tenfingers.activity.ConfirmOrderActivity;
import com.beijing.tenfingers.activity.JsXmActivity;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.beijing.tenfingers.view.RoundAutoImageView;

import java.util.ArrayList;

import xtom.frame.util.XtomWindowSize;

/**
 * 根据想项目选择技师
 */
public class SelectJsAdapter extends BaseRecycleAdapter<Technicians> {

    private ArrayList<Technicians> list = new ArrayList<>();
    private Context context;
    private String type="";//0 服务项目到技师 1技师到服务项目
    private  String id="";//项目id
    private String technicianId="";
    public SelectJsAdapter(Context contex, ArrayList<Technicians> datas,String type,String id) {
        super(datas);
        this.context = contex;
        this.list = datas;
        this.type=type;
        this.id=id;
        this.technicianId=technicianId;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        Technicians t=list.get(position);
        int width = (int) (XtomWindowSize.getWidth(context)*0.36)- BaseUtil.dip2px(context,30);
        holder.getView(R.id.iv_pic).getLayoutParams().width=width;
        if("1".equals(t.getIs_vip())){
            holder.getView(R.id.iv_king).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.iv_king).setVisibility(View.INVISIBLE);
        }
        if(t.getT_image_link()!=null){
            BaseUtil.loadBitmap(t.getT_image_link(),0, (RoundAutoImageView) holder.getView(R.id.iv_pic));
        }
        ((TextView)  holder.getView(R.id.tv_name)).setText(t.getT_name());
        ((TextView) holder.getView(R.id.tv_e_name)).setText(list.get(position).getT_english_name());
        if(!"".equals(list.get(position).getT_distance()) && list.get(position).getT_distance()!=null){
            double temp=Double.valueOf(list.get(position).getU_distance());
//            temp=temp/1000;
//            ((TextView) holder.getView(R.id.tv_distance)).setText("服务距离:"+list.get(position).getT_distance()+"km,距您"+BaseUtil.format2(temp)+"km");
            ((TextView) holder.getView(R.id.tv_distance)).setText("距您"+BaseUtil.format2(temp)+"km");

        }
        ((TextView)  holder.getView(R.id.tv_collect)).setText("收藏"+t.getT_collect_count());
        ((TextView) holder.getView(R.id.tv_history)).setText("历史最高值:"+list.get(position).getT_high_value());
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
//        int width = (int) (XtomWindowSize.getWidth(context)) - BaseUtil.dip2px(context, 46);
        ((TextView) holder.getView(R.id.tv_hot)).setText(list.get(position).getT_hot_value());
        holder.getView(R.id.fl_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, ArtificerActivity.class);
                it.putExtra("id",t.getId());
                it.putExtra("pro_id",id);
                it.putExtra("sid",t.getS_id());
                context.startActivity(it);
            }
        });
      holder.getView(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent it=new Intent(context, ArtificerActivity.class);
              it.putExtra("id",t.getId());
              it.putExtra("pro_id",id);
              it.putExtra("sid",t.getS_id());
              context.startActivity(it);
          }
      });
        holder.getView(R.id.fl_book).setTag(t);
      //立即预约
      holder.getView(R.id.fl_book).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Technicians t= (Technicians) v.getTag();
              if("0".equals(type)){
                  if(BaseUtil.IsLogin()){

                      Intent it=new Intent(context, ConfirmOrderActivity.class);
                      it.putExtra("id",id);
                      it.putExtra("technicianId",t.getId());
                      it.putExtra("sid",t.getS_id());
                      context.startActivity(it);
                  }else{
                      BaseUtil.toLogin(context,"1");
                  }

              }else{
                  Intent it=new Intent(context, JsXmActivity.class);
                  context.startActivity(it);
              }

          }
      });
        if("1".equals(list.get(position).getT_sex())){
            ((ImageView)holder.getView(R.id.iv_collect)).setImageResource(R.mipmap.man);
        }else{
            ((ImageView)holder.getView(R.id.iv_collect)).setImageResource(R.mipmap.female);
        }
        if(list.get(position).getIs_week()!=null){

            switch (list.get(position).getIs_week()){
                //周统计荣誉 0 无 1 冠军 2 亚军 3 季军
                case "1":
                    ((TextView) holder.getView(R.id.tv_week)).setText("周冠军");
                    break;
                case "2":
                    ((TextView) holder.getView(R.id.tv_week)).setText("周亚军");
                    break;
                case "3":
                    ((TextView) holder.getView(R.id.tv_week)).setText("周季军");
                    break;
                default:
                    ((TextView) holder.getView(R.id.tv_week)).setText("暂无排名");
                    break;
            }
            if("1".equals(list.get(position).getIs_month())){
                ((TextView) holder.getView(R.id.tv_month)).setVisibility(View.VISIBLE);
                ((TextView) holder.getView(R.id.tv_month)).setText("月度十佳技师");
            }else {
                ((TextView) holder.getView(R.id.tv_month)).setVisibility(View.VISIBLE);
                ((TextView) holder.getView(R.id.tv_month)).setText("暂无排名");

            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_shop_teacher;
    }
}
