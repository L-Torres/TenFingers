package com.beijing.tenfingers.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beijing.tenfingers.fragment.CityFragment;
import com.beijing.tenfingers.fragment.MyOrderFragment;
import com.beijing.tenfingers.fragment.NoSTFragment;
import com.beijing.tenfingers.fragment.ServiceProFragment;
import com.beijing.tenfingers.fragment.ShiTDFragment;

public class ShopJoinPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"有实体店", "无实体店", "服务用品供应","城市/地区合作商"};
    private boolean IsGroup;
    private boolean IsMerchant;

    public ShopJoinPagerAdapter(FragmentManager fm, boolean IsGroup, boolean IsMerchant) {
        super(fm);
        this.IsGroup = IsGroup;
        this.IsMerchant = IsMerchant;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ShiTDFragment.getInstance(position);
            case 1:
                return NoSTFragment.getInstance(position);
            case 2:
                return ServiceProFragment.getInstance(position);
            case 3:
                return CityFragment.getInstance(position);


        }
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
