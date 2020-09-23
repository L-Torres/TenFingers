package com.beijing.tenfingers.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beijing.tenfingers.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RecycleView适配器基类 用来处理没有数据时的emptyview
 * created by Torres
 * 2019年8月7日09:41:22
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleAdapter.BaseViewHolder> {

    protected List<T> datas;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_EMPTY = 0;//空布局

    private int ratio = 1;//当v1/my前屏幕高度的比例(1/2)

    private Context mContext;
    private int resId = R.mipmap.wushuju_;

    private String hint = "";//提示文本信息

    protected OnItemClickListener onItemClickListener;

    public BaseRecycleAdapter(List<T> datas) {
        this.datas = datas;
    }

    public BaseRecycleAdapter(Context mContext, List<T> datas) {
        this.datas = datas;
        this.mContext = mContext;
    }

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return datas == null || datas.size() == 0;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    @Override
    public int getItemCount() {
        return isEmpty() ? 1 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return isEmpty() ? TYPE_EMPTY : TYPE_NORMAL;
    }

    //可根据项目需求设置空布局的图片或布局
    public void setEmptyResourceId(int resId) {
        this.resId = resId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_common_view, parent, false);
            return new BaseViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
            return new BaseViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(BaseRecycleAdapter.BaseViewHolder holder, final int position) {

       /* *//**
         * 设置控件的点击事件
         *//*
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClick(position);
                }
            }
        });*/
        if (TYPE_NORMAL == getItemViewType(position)) {
            bindData(holder, position);
        } else {
//            TextView mTvEmpty = ((TextView) holder.getView(R.id.hint_tv));
//            mTvEmpty.setText(getHint());
//            if (mContext != null) {
//                BaseUtil.showTopDrawable(mContext, mTvEmpty, resId, mTvEmpty.getText().toString());
//            }
        }
    }

    /**
     * 更换提示语
     * @return
     */
    public String getHint() {
        if (TextUtils.isEmpty(hint)) {
            hint = "暂无搜索结果";
        }
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        Log.d("44444444", "refresh11:=== " + datas.size());
        this.datas.clear();
        this.datas.addAll(datas);
        Log.d("44444444", "refresh:=== " + this.datas.size());
        notifyDataSetChanged();
    }


    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    protected abstract void bindData(BaseViewHolder holder, int position);

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer, View> viewMap;

        public BaseViewHolder(View itemView) {
            super(itemView);
            viewMap = new HashMap<>();
        }

        /**
         * 获取设置的view
         *
         * @param id
         * @return
         */
        public View getView(int id) {
            View view = viewMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewMap.put(id, view);
            }
            return view;
        }
    }

    /**
     * 获取子item
     *
     * @return
     */
    public abstract int getLayoutId();


    public interface OnItemClickListener {
        void onClick(int position);
    }

    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
