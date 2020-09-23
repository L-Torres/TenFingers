package com.beijing.tenfingers.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beijing.tenfingers.fragment.MyOrderFragment;

public class MyOrderPagerAdapter extends FragmentPagerAdapter {
    ////0:全部 3：待支付 4：待收货 3：已支付 5：退货单
    private String tabTitles[] = new String[]{"全部", "待支付", "待服务","进行中","待评价", "已完成","已取消","售后"};
    private boolean IsGroup;
    private boolean IsMerchant;

    public MyOrderPagerAdapter(FragmentManager fm, boolean IsGroup, boolean IsMerchant) {
        super(fm);
        this.IsGroup = IsGroup;
        this.IsMerchant = IsMerchant;
    }

    @Override
    public Fragment getItem(int position) {
        return MyOrderFragment.getInstance(position);
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
