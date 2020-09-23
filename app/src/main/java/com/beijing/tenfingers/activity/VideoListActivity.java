package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.VidoAdapter;
import com.beijing.tenfingers.bean.MediaList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayerManager;
import com.beijing.tenfingers.until.StaggeredDividerItemDecoration;
import com.beijing.tenfingers.view.PopVideoDialog;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import xtom.frame.util.XtomToastUtil;

/**
 * 视频列表
 * 瀑布流
 */
public class VideoListActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_back;
    private TextView tv_title;
    private XRecyclerView rcy_list;
    private VidoAdapter adapter;
    private ArrayList<MediaList> list = new ArrayList<>();
    private Integer page=0;
    private String id;
    private PopVideoDialog videoDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopteachers);
        super.onCreate(savedInstanceState);

        getNetWorker().media_list(page.toString(),"20","3",id);
    }
    @Override
    public void onEventMainThread(EventBusModel event) {
    }

    @Override
    protected void onDestroy() {
        if(JCVideoPlayerManager.getCurrentJcvd()!=null){
            JCVideoPlayerManager.getCurrentJcvd().release();
        }
        super.onDestroy();
    }

    public void showDialog(String img, String url){
        videoDialog=new PopVideoDialog(mContext,img,url);
        videoDialog.setCancelable(true);
        videoDialog.show();
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
        switch (information){
            case MEDIA_LIST:
                rcy_list.refreshSuccess();
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
                if(adapter==null){
                    adapter = new VidoAdapter();
                    adapter.replaceAll(list);
                    //设置Adapter
                    rcy_list.setAdapter(adapter);
                }else{
                    adapter.replaceAll(list);
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

    }

    @Override
    protected void getExras() {
        id=mIntent.getStringExtra("id");
    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("视频");
        rcy_list.setHasFixedSize(true);
        rcy_list.setItemAnimator(null);
        //垂直方向的2列
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //防止Item切换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rcy_list.setLayoutManager(layoutManager);
        final int spanCount = 2;
        rcy_list.addItemDecoration(new StaggeredDividerItemDecoration(this,5,spanCount));
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=0;
                getNetWorker().media_list(page.toString(),"20","3",id);
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().media_list(page.toString(),"20","3",id);
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
