package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.PhotoListAdapter;
import com.beijing.tenfingers.bean.MediaList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.SpacesItemDecoration;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import xtom.frame.util.XtomToastUtil;

/**
 * 照片列表 技师
 */
public class PhotoListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private ArrayList<MediaList> list=new ArrayList<>();
    private PhotoListAdapter adapter;
    private Integer page=0;
    private String id="";
    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopteachers);
        super.onCreate(savedInstanceState);

        getNetWorker().media_list(page.toString(),"20","2",id);
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
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){

        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MEDIA_LIST:
                HemaArrayResult<MediaList> mResult= (HemaArrayResult<MediaList>) baseResult;
                ArrayList<MediaList> temp=new ArrayList<>();
                temp=mResult.getObjects();

                if ("0".equals(page.toString())) {
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
                        XtomToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }
                adapter=new PhotoListAdapter(mContext,list,rootView);
                rcy_list.setAdapter(adapter);

                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        rootView = findViewById(R.id.father);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);

    }

    @Override
    protected void getExras() {
        id=mIntent.getStringExtra("id");
    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("照片");
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL,false);
        rcy_list.setLayoutManager(layoutManager);
        int space = 8;
        rcy_list.addItemDecoration(new SpacesItemDecoration(space));

        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=0;
                getNetWorker().media_list(page.toString(),"20","2",id);
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().media_list(page.toString(),"20","2",id);
            }
        });
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
