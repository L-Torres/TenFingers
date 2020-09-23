package com.beijing.tenfingers.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beijing.tenfingers.fragment.JsMoreFragment;

public class JsMorePagerAdapter extends FragmentPagerAdapter {
    ////0:全部 3：待支付 4：待收货 3：已支付 5：退货单
    private String tabTitles[] = new String[]{"热度", "距离", "收藏数量"};
    private boolean IsGroup;
    private boolean IsMerchant;
    private String orderType="";
    public JsMorePagerAdapter(FragmentManager fm, boolean IsGroup, boolean IsMerchant) {
        super(fm);
        this.IsGroup = IsGroup;
        this.IsMerchant = IsMerchant;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                orderType="0";
                break;
            case 1:
                orderType="2";
                break;
            case 2:
                orderType="1";
                break;
        }
        return JsMoreFragment.getInstance(position,orderType);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public Parcelable saveState() {
        return null;
    }

}
