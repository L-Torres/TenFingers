package com.beijing.tenfingers.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.MyOrderPagerAdapter;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class ThirdFragment extends MyBaseFragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyOrderPagerAdapter adapter;
    private boolean IsGroup = false;//标记是否是团购订单

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_third);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


    }

    @Override
    protected void setListener() {
        adapter = new MyOrderPagerAdapter(getChildFragmentManager(), IsGroup, false);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，可以滚动
    }
}
