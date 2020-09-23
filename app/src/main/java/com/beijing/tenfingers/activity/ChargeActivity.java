package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.FeeAdapter;
import com.beijing.tenfingers.bean.MyData;
import com.beijing.tenfingers.bean.MyVip;
import com.beijing.tenfingers.bean.Price;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.lang.reflect.Member;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ChargeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back,ll_level,ll_top;
    private TextView tv_title,tv_balance,tv_charge,tv_name,tv_id,tv_cur,tv_next,seek_text,tv_up,tv_right;
    private FrameLayout fl_vip;
    private RecyclerView rcy_list;
    private FeeAdapter adapter;
    private ArrayList<Price> price=new ArrayList<>();
    private RoundedImageView iv_head;
    private ImageView iv_king;
    private MyData myData;
    private String fee="0";
    private String id;
    private SeekBar seekbar;
    private MyVip myVip;
    private String priceLevelText="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().my_vip(MyApplication.getInstance().getUser().getToken());
    }

    @Override
    protected void onResume() {
        getNetWorker().my_vip(MyApplication.getInstance().getUser().getToken());
        getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
        getNetWorker().charge_price(MyApplication.getInstance().getUser().getToken());
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    public void setFee(String fee,String id){

        this.fee=fee;
        this.id=id;
    }
    private void setData(){
        BaseUtil.loadCircleBitmap(mContext,myData.getU_image_link(),R.mipmap.ic_launcher_round,iv_head);
        if("1".equals(myData.getIs_vip())){
            iv_king.setVisibility(View.VISIBLE);
        }else{
            iv_king.setVisibility(View.INVISIBLE);
        }
        tv_id.setText("ID:"+myData.getId());
        tv_name.setText(myData.getU_nickname());
        if(!isNull(myData.getU_account())){
            tv_balance.setText("您的账户余额："+BaseUtil.format2(Double.valueOf(myData.getU_account()))+"元");
        }else{
            tv_balance.setText("您的账户余额："+0+"元");
        }
    }
    private void showView(){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ll_top.measure(w, h);
        int height = ll_top.getMeasuredHeight();
        int width = ll_top.getMeasuredWidth();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fl_vip.getLayoutParams();
        params.setMargins(0, height- BaseUtil.dip2px(mContext,20), 0, 0);// 通过自定义坐标来放置你的控件
        fl_vip.setLayoutParams(params);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rcy_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.CHARGE:
                getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
                getNetWorker().charge_price(MyApplication.getInstance().getUser().getToken());
                break;
            default:
//                ToastUtils.show("充值失败");
                break;

        }


    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CHARGE_PRICE:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case CHARGE_PRICE:
               cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_VIP:
                HemaArrayResult<MyVip> vResult = (HemaArrayResult<MyVip>) baseResult;
                myVip=vResult.getObjects().get(0);
                priceLevelText=myVip.getPriceLevelText();
                tv_cur.setText("vip"+myVip.getCurrent_level());
                tv_next.setText("vip"+myVip.getNext_level());
                setSeekbar();
                break;
            case MY_DATA:
                HemaArrayResult<MyData> mResult = (HemaArrayResult<MyData>) baseResult;
                myData = mResult.getObjects().get(0);
                setData();
                break;
            case CHARGE_PRICE:
                HemaArrayResult<Price> pResut= (HemaArrayResult<Price>) baseResult;
                price=pResut.getObjects();
                adapter=new FeeAdapter(mContext,price);
                rcy_list.setAdapter(adapter);
                break;
        }
    }

    private void setSeekbar(){
        double next=0.0;
        double cur=0;
        if(!isNull(myVip.getNext_total())){
            next=Double.valueOf(myVip.getNext_total());
        }


        if(!isNull(myVip.getCurrent_total())){
            double cur_p=Double.valueOf(myVip.getCurrent_total());
            double next_p= Double.valueOf(myVip.getNext_total());
            int temp= (int) ((cur_p*100)/next_p);
            seekbar.setMax(100);
            seekbar.setProgress(temp);
            cur=Double.valueOf(myVip.getCurrent_total());
        }
        double temp=next-cur;
        tv_up.setText("还有"+BaseUtil.format2(temp)+"点升级");
        seek_text.setText(String.valueOf("当前积分："+myVip.getCurrent_total()));
//        seekbar.setMax(Integer.parseInt(myVip.getNext_total()));
//        seekbar.setProgress(Integer.parseInt(myVip.getCurrent_total()));
        seekbar.setEnabled(false);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度是从-50~50的,但是seekbar.getmin()有限制,所以这里用0~100 -50;
                int text = progress -50;
                //设置文本显示
                seek_text.setText(String.valueOf(myVip.getCurrent_total()));
                //获取文本宽度
                float textWidth = seek_text.getWidth();
                //获取seekbar最左端的x位置
                float left = seekBar.getLeft();
                //进度条的刻度值
                float max =Math.abs(seekBar.getMax());
                //这不叫thumb的宽度,叫seekbar距左边宽度,实验了一下，seekbar 不是顶格的，两头都存在一定空间，所以xml 需要用paddingStart 和 paddingEnd 来确定具体空了多少值,我这里设置15dp;
                float thumb = BaseUtil.dip2px(ChargeActivity.this,15);
                //每移动1个单位，text应该变化的距离 = (seekBar的宽度 - 两头空的空间) / 总的progress长度
                float average = (((float) seekBar.getWidth())-2*thumb)/max;
                //int to float
                float currentProgress = progress;
                //textview 应该所处的位置 = seekbar最左端 + seekbar左端空的空间 + 当前progress应该加的长度 - textview宽度的一半(保持居中作用)
                float pox = left - textWidth/2 +thumb + average * currentProgress;
                seek_text.setX(pox);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_right=findViewById(R.id.tv_right);
        tv_up=findViewById(R.id.tv_up);
        seek_text=findViewById(R.id.seek_text);
        seekbar=findViewById(R.id.seekbar);
        tv_cur=findViewById(R.id.tv_cur);
        tv_next=findViewById(R.id.tv_next);
        iv_head=findViewById(R.id.iv_head);
        tv_name=findViewById(R.id.tv_name);
        iv_king=findViewById(R.id.iv_king);
        tv_id=findViewById(R.id.tv_id);
        tv_charge=findViewById(R.id.tv_charge);
        tv_balance=findViewById(R.id.tv_balance);
        ll_top=findViewById(R.id.ll_top);
        rcy_list=findViewById(R.id.rcy_list);
        ll_level=findViewById(R.id.ll_level);
        fl_vip=findViewById(R.id.fl_vip);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        tv_right.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        tv_title.setText("会员中心");
        showView();
        tv_charge.setOnClickListener(this);
        tv_right.setText("会员规则");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextSize(13);

    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_right:
                it=new Intent(mContext, MemberRuleActivity.class);
                it.putExtra("priceLevelText",priceLevelText);
                startActivity(it);
                changeAc();
                break;
            case R.id.tv_charge:
                if("0".equals(fee)){
                    ToastUtils.show("请选择您要充值的金额！");
                    return;
                }
                it=new Intent(mContext,DoChargeActivity.class);
                it.putExtra("price",fee);
                it.putExtra("id",id);
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
