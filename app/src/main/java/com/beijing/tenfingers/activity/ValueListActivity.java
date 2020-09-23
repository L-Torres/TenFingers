package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ValueListAdapter;
import com.beijing.tenfingers.bean.ValueList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class ValueListActivity extends BaseActivity {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private ValueListAdapter adapter;
    private String id="";
    private Integer page=1;
    private ArrayList<ValueList> lists=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
        getNetWorker().commlist(id,page.toString(),"20","1");
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
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case COMMENTLIST:
                HemaArrayResult<ValueList> mResult= (HemaArrayResult<ValueList>) baseResult;
                ArrayList<ValueList> temp=new ArrayList<>();
                temp=mResult.getObjects();
                if ("1".equals(page.toString())) {
                    rcy_list.refreshSuccess();
                    lists.clear();
                    lists.addAll(temp);
                    if (temp.size() < 20) {
                        rcy_list.setLoadingMoreEnabled(false);
                    } else {
                        rcy_list.setLoadingMoreEnabled(true);
                    }
                } else {
                    rcy_list.loadMoreComplete();
                    if (temp.size() > 0) {
                        lists.addAll(temp);
                    } else {
                        rcy_list.setLoadingMoreEnabled(false);
                        ToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }
                if(adapter==null){
                    adapter =new ValueListAdapter(mContext,lists,"1");
                    rcy_list.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);
        BaseUtil.initRecyleVertical(mContext,rcy_list);

    }

    @Override
    protected void getExras() {
            id=mIntent.getStringExtra("id");
    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAc(mContext);
            }
        });
        tv_title.setText("评论列表");
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNetWorker().commlist(id,page.toString(),"20","1");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().commlist(id,page.toString(),"20","1");
            }
        });
    }
}
