package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseFragmentActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ShopJoinPagerAdapter;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.fragment.ServiceProFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import de.greenrobot.event.EventBus;

public class ShopJoinActivity  extends BaseFragmentActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_title;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ShopJoinPagerAdapter adapter;
    private boolean IsGroup = false;//标记是否是团购订单
    private String position = "-1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopjoin);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarColor(R.color.white,0.07f)
                .statusBarDarkFont(true).init();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.GO_TO_JOIN://普通订单 跳转到待发货列表
                finishAc();
                break;
        }
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
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        adapter = new ShopJoinPagerAdapter(getSupportFragmentManager(), IsGroup, false);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，可以滚动
        tv_title.setText("商家入驻");
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finishAc();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ServiceProFragment fragment=new ServiceProFragment();
        fragment.onActivityResult(requestCode,resultCode,data);
    }
}
