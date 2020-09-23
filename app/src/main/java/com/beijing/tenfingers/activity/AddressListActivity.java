package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.AddressAdapter;
import com.beijing.tenfingers.bean.Address;
import com.beijing.tenfingers.bean.AddressList;
import com.beijing.tenfingers.bean.Technician;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class AddressListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView      tv_title,tv_add;
    private XRecyclerView rcy_list;
    private AddressAdapter addressAdapter;
    private ArrayList<Address> list=new ArrayList<>();
    private Integer page=1;
    private String type="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_addresslist);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().addressList(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");

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
            case MyEventBusConfig.ADDRESS_ADD:
                getNetWorker().addressList(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
                break;
        }
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_LIST:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_LIST:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_LIST:
                HemaArrayResult<AddressList> mResult= (HemaArrayResult<AddressList>) baseResult;
                AddressList addressList=mResult.getObjects().get(0);
                ArrayList<Address> temp=new ArrayList<>();
                temp=addressList.getChildren();
                if ("1".equals(page.toString())) {
                    rcy_list.refreshSuccess();
                    list.clear();
                    list.addAll(temp);
                    if (temp.size() < 20) {
                        rcy_list.setLoadingMoreEnabled(false);
                    } else {
                        rcy_list.setLoadingMoreEnabled(true);
                    }
                } else {
                    rcy_list.loadMoreComplete();
                    if (temp.size() > 0) {
                        list.addAll(temp);
                    } else {
                        rcy_list.setLoadingMoreEnabled(false);
                        ToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }

                if(addressAdapter==null){
                    addressAdapter=new AddressAdapter(mContext,list,type);
                    rcy_list.setAdapter(addressAdapter);
                }else{
                    addressAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case DELIVERY_ADDRESS_LIST:

                break;
        }
    }

    @Override
    protected void findView() {
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);
        tv_add=findViewById(R.id.tv_add);
    }

    @Override
    protected void getExras() {
        type=mIntent.getStringExtra("type");
        if(isNull(type)){
            type="0";
        }

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_title.setText("地址管理");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNetWorker().addressList(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().addressList(MyApplication.getInstance().getUser().getToken(),page.toString(),"20");

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_add:
                it=new Intent(mContext,AddAddressActivity.class);
                startActivity(it);
                changeAc();

                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
