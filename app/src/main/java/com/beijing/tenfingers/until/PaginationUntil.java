package com.beijing.tenfingers.until;

import android.content.Context;

import com.hemaapp.hm_FrameWork.ToastUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * 分页工具类
 * Created by Torres on 2017/9/27.
 */

public class PaginationUntil<T> {

    private ArrayList<T> first = new ArrayList();
    private ArrayList<T> second = new ArrayList();
    private XRecyclerView xRecyclerView;

    public PaginationUntil(ArrayList<T> first, ArrayList<T> second, XRecyclerView xRecyclerView) {
        this.first = first;
        this.second = second;
        this.xRecyclerView = xRecyclerView;
    }

    //返回数据
    public ArrayList<T> pagination(Context mContext, String page) {

            if ("1".equals(page.toString())) {
                xRecyclerView.refreshSuccess();
                this.first.clear();
                this.first.addAll(second);
                if (second.size() < 20) {
                    xRecyclerView.setLoadingMoreEnabled(false);
                } else {
                    xRecyclerView.setLoadingMoreEnabled(true);
                }
            } else {
                xRecyclerView.loadMoreComplete();
                if (this.second.size() > 0) {
                    this.first.addAll(second);
                } else {
                    xRecyclerView.setLoadingMoreEnabled(false);
                    ToastUtil.showShortToast(mContext, "已经到最后啦");
                }
            }
        return first;
    }
}
