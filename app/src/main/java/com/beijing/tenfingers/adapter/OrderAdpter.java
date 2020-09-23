package com.beijing.tenfingers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ApplyRefundActivity;
import com.beijing.tenfingers.activity.ConfirmOrderActivity;
import com.beijing.tenfingers.activity.OrderDetailActivity;
import com.beijing.tenfingers.activity.ValueActivity;
import com.beijing.tenfingers.bean.Order;
import com.beijing.tenfingers.fragment.MyOrderFragment;
import com.beijing.tenfingers.shop.ShopOrderDetailActivity;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BaseRecycleAdapter_order;

import java.util.ArrayList;

public class OrderAdpter extends BaseRecycleAdapter_order<Order> {

    private ArrayList<Order> list = new ArrayList<>();
    private Context context;
    private MyOrderFragment fragment;
    public OrderAdpter(Context contex, ArrayList<Order> datas,MyOrderFragment fragment) {
        super(datas);
        this.context = contex;
        this.list = datas;
        this.fragment=fragment;
    }
    public void freshData(ArrayList<Order> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    protected void bindData(BaseViewHolder holder, int position) {

        BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.icon_service, (ImageView) holder.getView(R.id.iv_small_one_second));
        ((TextView)holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
        ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getO_price());
        ((TextView)holder.getView(R.id.tv_content)).setText(list.get(position).getP_desc());
        ((TextView)holder.getView(R.id.tv_time)).setText(list.get(position).getP_service_time());
        ((TextView)holder.getView(R.id.tv_shop)).setText(list.get(position).getShop_name());

        if(list.get(position).getO_refund_status().equals("0")){

            //	订单状态 1 未支付 2 支付成功 待接单 3 已接单待服务 4 进行中 5 待评价 6 已完成 7 已取消
            switch (list.get(position).getO_status()){
                case "1":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.to_pay);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("取消订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("立即支付");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消订单
                            Order order= (Order) v.getTag();
                            fragment.cancel_order(order.getId());
                        }
                    });
                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消订单
                            Order order= (Order) v.getTag();
                            fragment.order_pay(order.getId(),order.getPay_type());
                        }
                    });
                    break;
                case "3":
                case "2":
                case"8":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.daifuwu);
                    holder.getView(R.id.tv_one).setVisibility(View.INVISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("二维码");
                    ((TextView)holder.getView(R.id.tv_two)).setText("申请退款");
                    ((TextView)holder.getView(R.id.tv_one)).setTag(list.get(position));
                    ((TextView)holder.getView(R.id.tv_one)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            fragment.show_code(order.getO_code_url());
                        }
                    });
                    ((TextView)holder.getView(R.id.tv_two)).setTag(list.get(position));
                    ((TextView)holder.getView(R.id.tv_two)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=new Intent(context, ApplyRefundActivity.class);
                            it.putExtra("id",order.getId());
                            context.startActivity(it);
                        }
                    });
                    break;
                case "4":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.jinxingzhong);
                    holder.getView(R.id.tv_one).setVisibility(View.INVISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.INVISIBLE);
                    break;
                case "5":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.daipingjia);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("去评价");
                    ((TextView)holder.getView(R.id.tv_two)).setText("再来一单");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Order order= (Order) v.getTag();
                                    Intent it=new Intent(context, ValueActivity.class);
                                    it.putExtra("order_id",order.getId());
                                    context.startActivity(it);

                                }
                            });

                        }
                    });
                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=new Intent(context, ConfirmOrderActivity.class);
                            it.putExtra("sid",order.getS_id());
                            it.putExtra("technicianId",order.getT_id());
                            it.putExtra("id",order.getP_id());
                            context.startActivity(it);
                        }
                    });
                    break;
                case "6":
                    BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.icon_service, (ImageView) holder.getView(R.id.iv_small_one_second));
                    ((TextView)holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
                    ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getO_price());
                    ((TextView)holder.getView(R.id.tv_content)).setText(list.get(position).getP_desc());
                    ((TextView)holder.getView(R.id.tv_time)).setText(list.get(position).getP_service_time());
                    ((TextView)holder.getView(R.id.tv_shop)).setText(list.get(position).getShop_name());
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.yiwancheng);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("删除订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("再来一单");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            fragment.delete_order(order.getId());
                        }
                    });
                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=new Intent(context, ConfirmOrderActivity.class);
                            it.putExtra("id",order.getP_id());
                            it.putExtra("technicianId",order.getT_id());
                            it.putExtra("sid",order.getS_id());
                            context.startActivity(it);
                        }
                    });
                    break;
                case "7":
                    BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.icon_service, (ImageView) holder.getView(R.id.iv_small_one_second));
                    ((TextView)holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
                    ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getO_price());
                    ((TextView)holder.getView(R.id.tv_content)).setText(list.get(position).getP_desc());
                    ((TextView)holder.getView(R.id.tv_time)).setText(list.get(position).getP_service_time());
                    ((TextView)holder.getView(R.id.tv_shop)).setText(list.get(position).getShop_name());
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.tag_cancel);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("删除订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("再次购买");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=new Intent(context, ConfirmOrderActivity.class);
                            it.putExtra("id",order.getP_id());
                            it.putExtra("technicianId",order.getT_id());
                            it.putExtra("sid",order.getS_id());
                            context.startActivity(it);
                        }
                    });
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            fragment.delete_order(order.getId());
                        }
                    });
                    break;
                default:
                    ((ImageView) holder.getView(R.id.iv_flag)).setVisibility(View.INVISIBLE);
                    break;
            }


            ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
            ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order= (Order) v.getTag();
                    Intent it=null;
                    if("2".equals(order.getO_service_type())){
                        it=new Intent(context, ShopOrderDetailActivity.class);
                    }else{
                        it=new Intent(context, OrderDetailActivity.class);
                    }
                    it.putExtra("id",order.getId());
                    context.startActivity(it);
                }
            });
        }else{
            ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
            ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order= (Order) v.getTag();
                    Intent it=null;
                    if("2".equals(order.getO_service_type())){
                        it=new Intent(context, ShopOrderDetailActivity.class);
                    }else{
                        it=new Intent(context, OrderDetailActivity.class);
                    }

                    it.putExtra("id",order.getId());
                    context.startActivity(it);
                }
            });
            //	订单状态 1 未支付 2 支付成功 待接单 3 已接单待服务 4 进行中 5 待评价 6 已完成 7 已取消
            switch (list.get(position).getO_status()){
                case "1":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.to_pay);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("取消订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("立即支付");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消订单
                            Order order= (Order) v.getTag();
                            fragment.cancel_order(order.getId());
                        }
                    });
                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消订单
                            Order order= (Order) v.getTag();
                            fragment.order_pay(order.getId(),order.getPay_type());
                        }
                    });
                    break;
                case "2":
                case "3":

                    holder.getView(R.id.iv_flag).setVisibility(View.VISIBLE);
                    ((ImageView)holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.daifuwu);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.INVISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("取消售后");
                    ((TextView)holder.getView(R.id.tv_two)).setText("取消售后");
                    if(list.get(position).getO_refund_status().equals("3") ||
                            list.get(position).getO_refund_status().equals("2")
                            || list.get(position).getO_refund_status().equals("4")){
                        ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.INVISIBLE);
                    }
                    if(list.get(position).getO_refund_status().equals("3")){
                        ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                        ((TextView)holder.getView(R.id.tv_one)).setTag(list.get(position));
                        ((TextView)holder.getView(R.id.tv_one)).setText("删除订单");
                        holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Order order= (Order) v.getTag();
                                fragment.delete_order(order.getId());
                            }
                        });

                    }
                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消订单
                            Order order= (Order) v.getTag();
                            fragment.cancel_refund(order.getId());

                        }
                    });
                    ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
                    ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=null;
                            if("2".equals(order.getO_service_type())){
                                it=new Intent(context, ShopOrderDetailActivity.class);
                            }else{
                                it=new Intent(context, OrderDetailActivity.class);
                            }
                            it.putExtra("id",order.getId());
                            context.startActivity(it);
                        }
                    });
                    break;
                case"8":
                    holder.getView(R.id.iv_flag).setVisibility(View.VISIBLE);
                    ((ImageView)holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.daifuwu);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.INVISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.INVISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("删除订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("取消售后");
                    if(list.get(position).getO_refund_status().equals("3") ||
                    list.get(position).getO_refund_status().equals("2")
                    || list.get(position).getO_refund_status().equals("4")){
                        ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.INVISIBLE);
                    }
                    if(list.get(position).getO_refund_status().equals("3")){
                        ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    }

                    holder.getView(R.id.tv_two).setTag(list.get(position));
                    holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消订单
                            Order order= (Order) v.getTag();
                            fragment.cancel_refund(order.getId());

                        }
                    });
                    ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));
                    ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=null;
                            if("2".equals(order.getO_service_type())){
                                it=new Intent(context, ShopOrderDetailActivity.class);
                            }else{
                                it=new Intent(context, OrderDetailActivity.class);
                            }
                            it.putExtra("id",order.getId());
                            context.startActivity(it);
                        }
                    });
                    break;
                case "4":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.jinxingzhong);
                    holder.getView(R.id.tv_one).setVisibility(View.INVISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.INVISIBLE);
                    break;
                case "5":
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.daipingjia);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("去评价");
                    ((TextView)holder.getView(R.id.tv_two)).setText("再来一单");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=new Intent(context, ValueActivity.class);
                            it.putExtra("order_id",order.getId());
                            context.startActivity(it);
                        }
                    });
                    break;
                case "6":
                    BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.icon_service, (ImageView) holder.getView(R.id.iv_small_one_second));
                    ((TextView)holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
                    ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getO_price());
                    ((TextView)holder.getView(R.id.tv_content)).setText(list.get(position).getP_desc());
                    ((TextView)holder.getView(R.id.tv_time)).setText(list.get(position).getP_service_time());
                    ((TextView)holder.getView(R.id.tv_shop)).setText(list.get(position).getShop_name());
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.yiwancheng);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("删除订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("再来一单");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            fragment.delete_order(order.getId());
                        }
                    });
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            fragment.delete_order(order.getId());
                        }
                    });
                    break;
                case "7":
                    BaseUtil.loadBitmap(list.get(position).getP_image_link(),R.mipmap.icon_service, (ImageView) holder.getView(R.id.iv_small_one_second));
                    ((TextView)holder.getView(R.id.tv_name)).setText(list.get(position).getP_name());
                    ((TextView)holder.getView(R.id.tv_price)).setText("¥"+list.get(position).getO_price());
                    ((TextView)holder.getView(R.id.tv_content)).setText(list.get(position).getP_desc());
                    ((TextView)holder.getView(R.id.tv_time)).setText(list.get(position).getP_service_time());
                    ((TextView)holder.getView(R.id.tv_shop)).setText(list.get(position).getShop_name());
                    ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.tag_cancel);
                    ((TextView)holder.getView(R.id.tv_one)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_two)).setVisibility(View.VISIBLE);
                    ((TextView)holder.getView(R.id.tv_one)).setText("删除订单");
                    ((TextView)holder.getView(R.id.tv_two)).setText("再次购买");
                    holder.getView(R.id.tv_one).setTag(list.get(position));
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            Intent it=new Intent(context, ConfirmOrderActivity.class);
                            it.putExtra("id",order.getP_id());
                            it.putExtra("technicianId",order.getT_id());
                            it.putExtra("sid",order.getS_id());
                            context.startActivity(it);
                        }
                    });
                    holder.getView(R.id.tv_one).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Order order= (Order) v.getTag();
                            fragment.delete_order(order.getId());
                        }
                    });
                    break;
                default:
                    holder.getView(R.id.iv_flag).setVisibility(View.INVISIBLE);
                    break;
            }
            ((LinearLayout) holder.getView(R.id.ll_all)).setTag(list.get(position));

            if("3".equals(list.get(position).getO_refund_status())){
                ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.icon_ytk);
                holder.getView(R.id.iv_flag).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_two).setVisibility(View.VISIBLE);
                ((TextView)holder.getView(R.id.tv_two)).setText("再次购买");
                ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Order order= (Order) v.getTag();
                        Intent it=null;
                        if("2".equals(order.getO_service_type())){
                            it=new Intent(context, ShopOrderDetailActivity.class);
                        }else{
                            it=new Intent(context, OrderDetailActivity.class);
                        }
                        it.putExtra("id",order.getId());

                        context.startActivity(it);
                    }
                });
                holder.getView(R.id.tv_two).setTag(list.get(position));
                holder.getView(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Order order= (Order) v.getTag();
                        Intent it=new Intent(context, ConfirmOrderActivity.class);
                        it.putExtra("id",order.getP_id());
                        it.putExtra("technicianId",order.getT_id());
                        it.putExtra("sid",order.getS_id());
                        context.startActivity(it);
                    }
                });

            }else{
                ((ImageView) holder.getView(R.id.iv_flag)).setImageResource(R.mipmap.icon_tkz);
                holder.getView(R.id.iv_flag).setVisibility(View.VISIBLE);
                ((LinearLayout) holder.getView(R.id.ll_all)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Order order= (Order) v.getTag();
                        Intent it=null;
                        if("2".equals(order.getO_service_type())){
                            it=new Intent(context, ShopOrderDetailActivity.class);
                        }else{
                            it=new Intent(context, OrderDetailActivity.class);
                        }
                        it.putExtra("id",order.getId());
                        context.startActivity(it);
                    }
                });
            }


        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_order;
    }
}
