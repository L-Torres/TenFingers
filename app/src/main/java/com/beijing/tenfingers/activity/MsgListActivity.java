package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.FuwuAdapter;
import com.beijing.tenfingers.adapter.MsgListAdapter;
import com.beijing.tenfingers.bean.MessageList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;

/**
 * 消息列表
 */
public class MsgListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_right;
    private XRecyclerView rcy_list;
    private MsgListAdapter adapter;
    private Integer page = 1;
    private ArrayList<MessageList> lists=new ArrayList<>();
    private PopTipDialog allDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
        getNetWorker().notify(MyApplication.getInstance().getUser().getToken(),"1","20");
    }
    public void  read(String id){
        getNetWorker().notify_read(MyApplication.getInstance().getUser().getToken(),id);
//        allDialog=new PopTipDialog(mContext);
//        allDialog.setCancelable(true);
//        allDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
//            @Override
//            public void onLeftButtonClick(PopTipDialog dialog) {
//                dialog.cancel();
//            }
//
//            @Override
//            public void onRightButtonClick(PopTipDialog dialog) {
//                dialog.cancel();
//
//            }
//        });
//        allDialog.show();

    }
    public void  delete(String id){
        getNetWorker().notify_delete(MyApplication.getInstance().getUser().getToken(),id);
//        allDialog=new PopTipDialog(mContext);
//        allDialog.setCancelable(true);
//        allDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
//            @Override
//            public void onLeftButtonClick(PopTipDialog dialog) {
//                dialog.cancel();
//            }
//
//            @Override
//            public void onRightButtonClick(PopTipDialog dialog) {
//                dialog.cancel();
//
//            }
//        });
//        allDialog.show();

    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case NOTIFY_DELETE:
            case NOTIFY_READ:
            case NOTIFY_CLEAR:
            case NOTIFY:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case NOTIFY_DELETE:
            case NOTIFY_READ:
            case NOTIFY_CLEAR:
            case NOTIFY:
               cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case NOTIFY_CLEAR:
            case NOTIFY_DELETE:
            case NOTIFY_READ:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.MSG_UPDATE));
                getNetWorker().notify(MyApplication.getInstance().getUser().getToken(),"1","20");
                break;
            case NOTIFY:
                HemaArrayResult<MessageList> mResult= (HemaArrayResult<MessageList>) baseResult;
                ArrayList<MessageList> temp=new ArrayList<>();
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
                    adapter =new MsgListAdapter(mContext,lists,"");
                    adapter.setLists(lists);
                    rcy_list.setAdapter(adapter);
                }else {
                    adapter.setLists(lists);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case NOTIFY:

                break;
        }
    }

    @Override
    protected void findView() {

        tv_right=findViewById(R.id.tv_right);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        rcy_list=findViewById(R.id.rcy_list);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        tv_right.setText("全部已读");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("我的消息");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.setPullRefreshEnabled(true);
        rcy_list.setLoadingMoreEnabled(true);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().notify(MyApplication.getInstance().getUser().getToken(), page.toString(), "20");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().notify(MyApplication.getInstance().getUser().getToken(), page.toString(), "20");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                if(lists.size()>0){
                    allDialog=new PopTipDialog(mContext);
                    allDialog.setCancelable(true);
                    allDialog.setTip("全部置为已读？");
                    allDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
                        @Override
                        public void onLeftButtonClick(PopTipDialog dialog) {
                            dialog.cancel();
                        }

                        @Override
                        public void onRightButtonClick(PopTipDialog dialog) {
                            dialog.cancel();
                            getNetWorker().notify_clear(MyApplication.getInstance().getUser().getToken());
                        }
                    });
                    allDialog.show();
                }else{

                }
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }

}
