package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.FArtificerActivity;
import com.beijing.tenfingers.activity.ServiceListActivity;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter;
import com.beijing.tenfingers.view.RoundAutoImageView;

import java.util.ArrayList;

import xtom.frame.util.XtomWindowSize;

/**
 * 技师查看更多
 */
public class JsMoreAdapter  extends BaseRecycleAdapter<Technicians> {

    private ArrayList<Technicians> list = new ArrayList<>();
    private Context context;

    public JsMoreAdapter(Context contex, ArrayList<Technicians> datas) {
        super(datas);
        this.context = contex;
        this.list = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
//            ((ImageView) holder.getView(R.id.iv_play)).setVisibility(View.INVISIBLE);
//        ((TextView) holder.getView(R.id.tv_tag)).setVisibility(View.GONE);
        int width = (int) (XtomWindowSize.getWidth(context)*0.36)- BaseUtil.dip2px(context,30);
//        int height= (int) (width*1.32);
        holder.getView(R.id.iv_pic).getLayoutParams().width=width;
//        holder.getView(R.id.iv_pic).getLayoutParams().height=height;
        BaseUtil.loadBitmap(list.get(position).getT_image_link(),0, (RoundAutoImageView) holder.getView(R.id.iv_pic));
        ((TextView) holder.getView(R.id.tv_name)).setText(list.get(position).getT_name());
        ((TextView) holder.getView(R.id.tv_e_name)).setText(list.get(position).getT_english_name());
        if(!"".equals(list.get(position).getT_distance()) && list.get(position).getT_distance()!=null){
            double temp=Double.valueOf(list.get(position).getU_distance());
//            ((TextView) holder.getView(R.id.tv_distance)).setText("服务距离:"+list.get(position).getT_distance()+"km,距您"+BaseUtil.format2(temp)+"km");
            ((TextView) holder.getView(R.id.tv_distance)).setText("距您"+BaseUtil.format2(temp)+"km");
        }
        if("1".equals(list.get(position).getT_sex())){
            ((ImageView)holder.getView(R.id.iv_collect)).setImageResource(R.mipmap.man);
        }else{
            ((ImageView)holder.getView(R.id.iv_collect)).setImageResource(R.mipmap.female);

        }
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
        if("1".equals(list.get(position).getIs_vip())){
            ((ImageView) holder.getView(R.id.iv_vip)).setVisibility(View.VISIBLE);
        }else {
            ((ImageView) holder.getView(R.id.iv_vip)).setVisibility(View.GONE);

        }
        ((TextView) holder.getView(R.id.tv_hot)).setText(list.get(position).getT_hot_value());
        ((TextView) holder.getView(R.id.tv_collect)).setText("收藏："+list.get(position).getT_collect_count());
        ((TextView) holder.getView(R.id.tv_history)).setText("历史最高值："+list.get(position).getT_high_value());

        ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
        ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Technicians t= (Technicians) v.getTag();
                if(t.getIs_vip().equals("0")){

                    Intent it=new Intent(context, FArtificerActivity.class);
                    it.putExtra("id",t.getId());
                    context.startActivity(it);
                }else{
                    if("1".equals(BaseUtil.getVip(context))){
                        Intent it=new Intent(context, FArtificerActivity.class);
                        it.putExtra("id",t.getId());
                        context.startActivity(it);
                    }else{
                        BaseUtil.showVip(context);
                    }
                }
            }
        });

//        ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
//        ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Technicians t= (Technicians) v.getTag();
//                Intent it=new Intent(context, ServiceListActivity.class);
//                it.putExtra("tid",t.getId());
//                context.startActivity(it);
//            }
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tech;
    }
}
