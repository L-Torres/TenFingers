package com.beijing.tenfingers.interf;

/**
 * 列表 点赞 收藏
 */
public interface ZanClickListener {
    public void thumb(String id,int type);
    public void collect(String id);
    public void addToCart(String id);
}
