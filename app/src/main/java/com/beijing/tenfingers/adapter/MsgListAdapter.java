package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.MsgListActivity;
import com.beijing.tenfingers.activity.OrderDetailActivity;
import com.beijing.tenfingers.bean.MessageList;
import com.beijing.tenfingers.swipereveallayout.SwipeRevealLayout;
import com.beijing.tenfingers.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

public class MsgListAdapter  extends RecyclerView.Adapter<MsgListAdapter.MyViewHolder> {

    private ArrayList<MessageList> lists = new ArrayList<>();
    private Context context;
    private ViewBinderHelper binderHelper = new ViewBinderHelper();

    public MsgListAdapter(Context context, ArrayList<MessageList> lists, String type) {
        this.context = context;
        this.lists = lists;
    }

    public void setLists(ArrayList<MessageList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view_choice = LayoutInflater.from(context).inflate(R.layout.item_msg, parent, false);
        MyViewHolder holder = new MyViewHolder(view_choice);
        return holder;
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MessageList m=lists.get(position);
        holder.tv_content.setText(m.getNotify_content());
        holder.tv_time.setText(m.getCreated_at());
        switch (m.getTarget_type()){
            case "order":
                holder.tv_name.setText("订单消息");
                break;
        }
        if("1".equals(m.getIs_read())){
            holder.v_red.setVisibility(View.INVISIBLE);
        }else{
            holder.v_red.setVisibility(View.VISIBLE);
        }
        holder.tv_read.setTag(m);
        holder.tv_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageList m= (MessageList) v.getTag();
                Activity activity= XtomActivityManager.getLastActivity();
                if(activity instanceof MsgListActivity){
                    ((MsgListActivity) activity).read(m.getId());
                }
            }
        });
        holder.tv_del.setTag(m);
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageList m= (MessageList) v.getTag();
                Activity activity= XtomActivityManager.getLastActivity();
                if(activity instanceof MsgListActivity){
                    ((MsgListActivity) activity).delete(m.getId());
                }
            }
        });

        holder.ll_all.setTag(m);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageList m= (MessageList) v.getTag();
                Intent it=null;
                switch (m.getTarget_type()){
                    case "order":
                        Activity activity= XtomActivityManager.getLastActivity();
                        if(activity instanceof MsgListActivity){
                            ((MsgListActivity) activity).read(m.getId());
                        }
                        if(m.getNotify_content().contains("退款")){
//                            it=new Intent(context, RefundDetailActivity.class);
                             it=new Intent(context, OrderDetailActivity.class);
                            it.putExtra("id",m.getNotify_target_id());
                            context.startActivity(it);
                        }else{
                            it=new Intent(context, OrderDetailActivity.class);
                            it.putExtra("id",m.getNotify_target_id());
                            context.startActivity(it);
                        }

                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        private View deleteLayout,v_red;
        private TextView tv_name, tv_time, tv_content,tv_read,tv_del;
        private LinearLayout ll_all;
        public MyViewHolder(View itemView) {
            super(itemView);
            ll_all=itemView.findViewById(R.id.ll_all);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            deleteLayout = itemView.findViewById(R.id.delete_layout);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_content=itemView.findViewById(R.id.tv_content);
            v_red=itemView.findViewById(R.id.v_red);
            tv_read=itemView.findViewById(R.id.tv_read);
            tv_del=itemView.findViewById(R.id.tv_del);
        }
    }
}
