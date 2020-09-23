package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.JsxmAdapter;
import com.beijing.tenfingers.adapter.ShopAdapter;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * 技师对应项目列表
 */
public class JsXmActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private ArrayList<Product> list=new ArrayList<>();
    private JsxmAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopteachers);
        super.onCreate(savedInstanceState);
        list.add(new Product());
        list.add(new Product());
        list.add(new Product());
        adapter=new JsxmAdapter(mContext,list);
        rcy_list.setAdapter(adapter);

    }
    @Override
    public void onEventMainThread(EventBusModel event) {



    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){

        }
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
        rcy_list=findViewById(R.id.rcy_list);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("服务项目");
        BaseUtil.initRecyleVertical(mContext,rcy_list);

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
