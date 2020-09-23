package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ServiceListAdapter;
import com.beijing.tenfingers.adapter.ShopFwAdapter;
import com.beijing.tenfingers.adapter.ShopTeachersAdapter;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ShopDetail;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * 店内技师
 */
public class ShopTeachersActivity  extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private ArrayList<Technicians> list=new ArrayList<>();
    private ShopTeachersAdapter adapter;
    private String shop_id="";
    private ArrayList<Technicians> technicians=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopteachers);
        super.onCreate(savedInstanceState);
        getNetWorker().shop_teacher(shop_id,BaseUtil.getLng(mContext),BaseUtil.getLat(mContext));
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
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        rcy_list.refreshSuccess();
        switch (information){
            case SHOP_TEACHER:
                HemaArrayResult<Technicians> tResult= (HemaArrayResult<Technicians>) baseResult;
                technicians=tResult.getObjects();
                if(adapter==null){
                    adapter=new ShopTeachersAdapter(mContext,technicians,"1","");
                    rcy_list.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
                break;

        }

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();

        switch (information){
            case SHOP_TEACHER:
                rcy_list.refreshSuccess();
                break;

        }

    }

    @Override
    protected void findView() {
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);

    }

    @Override
    protected void getExras() {
        shop_id=mIntent.getStringExtra("shop_id");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("店内技师");
        BaseUtil.initRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getNetWorker().shop_teacher(shop_id,BaseUtil.getLng(mContext),BaseUtil.getLat(mContext));
            }

            @Override
            public void onLoadMore() {

            }
        });
        rcy_list.setLoadingMoreEnabled(false);

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
