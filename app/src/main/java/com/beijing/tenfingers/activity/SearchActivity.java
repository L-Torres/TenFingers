package com.beijing.tenfingers.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.SearchEmptyAdapter;
import com.beijing.tenfingers.db.SearchDBClient_C;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.PowerfulEditText;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import cn.lankton.flowlayout.FlowLayout;


/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private PowerfulEditText edit;
    private TextView tv_right;

    private ImageView img_back;
    private XRecyclerView recyclerView;

    private View header;
    private TextView tv_close;
    private ImageView iv_clear;
    private FlowLayout fl_tag;
    private TextView tv_none;

    private SearchDBClient_C mClient;
    private ArrayList<String> search_strs = new ArrayList<String>();
    private PopTipDialog popTipDialog;

    private int flag = 0; //用来控制flowLayout展示的行数

    private String key="";
    private SearchEmptyAdapter adapter;
    private ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        initData();
        adapter=new SearchEmptyAdapter(mContext,list);
        recyclerView.setAdapter(adapter);
    }

    private void initData(){
        if(!isNull(key)){
            edit.setText(key);
            edit.setSelection(key.length());
            edit.requestFocus();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInputMethodManager.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
                }
            }, 100);
        }
    }

    //热销榜

    @Override
    protected void onResume() {
        mClient = SearchDBClient_C.get(mContext);
        getHistoryList();
        super.onResume();
    }

    /**
     * 加载搜索历史
     */
    private void getHistoryList() {
        new LoadDBTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finishAc(mContext);
                break;
            case R.id.tv_right:
                String str = edit.getText().toString().trim();
                if (isNull(str)) {
                    ToastUtil.showShortToast(mContext, "请输入您要搜索的服务");
                    return;
                }
                mInputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(),
                        0);
                select_search_str(str, true);
                edit.setText("");
                break;
            case R.id.iv_clear:
                if(tv_none.getVisibility() == View.VISIBLE){
                    return;
                }
                showPop();
                break;
        }
    }

    private void showPop(){
        if (popTipDialog == null) {
            popTipDialog = new PopTipDialog(mContext);
            popTipDialog.setCancelable(true);
            popTipDialog.setTip("确定删除所有记录？");
            popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
                @Override
                public void onLeftButtonClick(PopTipDialog dialog) {
                    dialog.cancel();
                }

                @Override
                public void onRightButtonClick(PopTipDialog dialog) {
                    dialog.cancel();
                    Clear_HistoryList();
                }
            });
        }
        popTipDialog.show();
    }

    /**
     * 清除搜索历史
     */
    private void Clear_HistoryList() {
        if (mClient != null) {

            mClient.clear();
        }
        if (search_strs.size() > 0) {
            search_strs.clear();
            freshData();
        }
        fl_tag.setVisibility(View.INVISIBLE);
        tv_none.setVisibility(View.VISIBLE);
    }

    private class LoadDBTask extends AsyncTask<Void, Void, Void> {
        private ArrayList<String> strs;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            strs = mClient.select();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            getHistoryList_done(strs);
        }
    }

    /**
     * 完成
     *
     * @param strs
     */
    private void getHistoryList_done(ArrayList<String> strs) {
        if (strs == null || strs.size() == 0) {
            fl_tag.setVisibility(View.INVISIBLE);
            tv_none.setVisibility(View.VISIBLE);
            return;
        } else {
            fl_tag.setVisibility(View.VISIBLE);
            tv_none.setVisibility(View.INVISIBLE);
            search_strs.clear();
            search_strs.addAll(strs);
            freshData();
        }
    }

    private void freshData() {
        int size = search_strs.size();
        String[] tags;
        if(size > 30){
            size = 30;
            tags = new String[30];
        }else{
            tags = new String[size];
        }
        for (int i = 0; i < size; i++) {
            tags[i] = search_strs.get(i);
        }
        if(flag == 0){
            fl_tag.specifyLines(2);
        }
        addTags(tags, fl_tag);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
//            case SEARCH_RANK:
//                showProgressDialog("加载中...");
//                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
//            case SEARCH_RANK:
//                cancelProgressDialog();
//                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
//            case SEARCH_RANK:
//                HemaArrayResult<SearchRankInfor> tResult = (HemaArrayResult<SearchRankInfor>) baseResult;
//                infor = tResult.getObjects().get(0);
//                adapter.setInfor(infor);
//                adapter.notifyDataSetChanged();
//                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        img_back = findViewById(R.id.iv_back);
        edit = findViewById(R.id.edit);
        tv_right = findViewById(R.id.tv_right);
        recyclerView = findViewById(R.id.xrecyclerview);
        header = LayoutInflater.from(mContext).inflate(R.layout.item_search_top, null);
        tv_close = header.findViewById(R.id.tv_history);
        iv_clear = header.findViewById(R.id.iv_clear);
        tv_none = header.findViewById(R.id.tv_none);
        fl_tag = header.findViewById(R.id.fl_tag);
        recyclerView.addHeaderView(header);
    }

    @Override
    protected void getExras() {
        key=getIntent().getStringExtra("key");
    }

    @Override
    protected void setListener() {
        img_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        edit.setOnRightClickListener(new PowerfulEditText.OnRightClickListener() {
            @Override
            public void onClick(EditText editText) {
                String str = edit.getText().toString().trim();
                if (isNull(str))
                    return;
                edit.setText("");
                mInputMethodManager.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
            }
        });
        edit.setOnLeftClickListener(new PowerfulEditText.OnLeftClickListener() {
            @Override
            public void onClick(EditText editText) {
                String str = edit.getText().toString().trim();
                if (isNull(str))
                    return;
                mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),
                        0);
                fl_tag.setVisibility(View.VISIBLE);
                tv_none.setVisibility(View.INVISIBLE);
                select_search_str(str, true);
            }
        });
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String str = edit.getText().toString().trim();
                    if (isNull(str)) {
                        return false;
                    }
                    mInputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(),
                            0);
                    select_search_str(str, true);
                    edit.setText("");
                    return true;
                }
                return false;
            }
        });
        iv_clear.setOnClickListener(this);

        BaseUtil.initRecyleVertical(mContext, recyclerView);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
    }

    /**
     * 搜索
     *
     * @param str
     * @param addhistory
     */
    public void select_search_str(String str, boolean addhistory) {
        Intent it = new Intent(mContext, ListActivity.class);
        it.putExtra("key", str);
        startActivity(it);
        mInputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(),
                0);

        if (addhistory) {
            boolean found = false;
            if (search_strs != null && search_strs.size() > 0) {
                for (String hstr : search_strs) {
                    if (hstr.equals(str)) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                if (search_strs == null)
                    search_strs = new ArrayList<String>();
                search_strs.add(0, str);
                mClient.insert(str);
//                freshData();
                getHistoryList();
            }
        }

    }

    private void addTags(String[] temp, FlowLayout flowLayout) {
        flowLayout.removeAllViews();
        for (int i = 0; i < temp.length; i++) {
            int ranHeight = dip2px(mContext, 30);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
            lp.setMargins(dip2px(mContext, 15), 0, dip2px(mContext, 15), 0);
            TextView tv = new TextView(mContext);
            tv.setPadding(dip2px(mContext, 10), 0, dip2px(mContext, 10), 0);
            tv.setTextColor(0xff333333);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setText(temp[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setLines(1);
            tv.setBackgroundResource(R.drawable.bg_tag_grey_bck_search);
            tv.setTag(temp[i]);
            flowLayout.addView(tv, lp);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = (String) v.getTag();
                    Intent it = new Intent(mContext, ListActivity.class);
                    it.putExtra("key", str);
                    startActivity(it);

                    mInputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(),
                            0);
                }
            });
        }

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
