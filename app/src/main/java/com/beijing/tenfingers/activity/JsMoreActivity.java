package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.JsMorePagerAdapter;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

/**
 * 技师更多列表
 */
public class JsMoreActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private JsMorePagerAdapter adapter;
    private boolean IsGroup = false;//标记是否是团购订单
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jsmore);
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
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        adapter = new JsMorePagerAdapter(getSupportFragmentManager(), IsGroup, false);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，可以滚动
        tv_title.setText("技师列表");
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finishAc(mContext);
                break;

        }
    }
}
