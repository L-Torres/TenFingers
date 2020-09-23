package com.beijing.tenfingers.activity;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.FuwuAdapter;
import com.beijing.tenfingers.adapter.FuwuVipAdapter;
import com.beijing.tenfingers.bean.MyData;
import com.beijing.tenfingers.bean.MyVip;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ServiceList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class VipActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title,tv_name,tv_id,tv_cur,tv_next,seek_text,tv_up;
    private XRecyclerView rcy_list;
    private ArrayList<String> list=new ArrayList<>();
    private FuwuVipAdapter adapter;
    private View header;
    private MyData myData;
    private ArrayList<Product> products=new ArrayList<>();
    private Integer page=1;
    private RoundedImageView  iv_head;
    private ImageView iv_king;
    private SeekBar seekbar;
    private MyVip myVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
        getNetWorker().myData(MyApplication.getInstance().getUser().getToken());
        getNetWorker().get_product("0","","",BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),"","1");
        getNetWorker().my_vip(MyApplication.getInstance().getUser().getToken());
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
            case MY_VIP:
                HemaArrayResult<MyVip> vResult = (HemaArrayResult<MyVip>) baseResult;
                myVip=vResult.getObjects().get(0);
                tv_cur.setText("vip"+myVip.getCurrent_level());
                tv_next.setText("vip"+myVip.getNext_level());
                setSeekbar();
                break;
            case MY_DATA:
                HemaArrayResult<MyData> mResult = (HemaArrayResult<MyData>) baseResult;
                myData = mResult.getObjects().get(0);
                setData();
                break;
            case SERVICE_PRODUCT:
                rcy_list.refreshSuccess();
                HemaArrayResult<Product> pResult= (HemaArrayResult<Product>) baseResult;
                products=pResult.getObjects();
                if(adapter==null){
                    adapter=new FuwuVipAdapter(mContext,products);
                    rcy_list.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
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
//        tv_up.setText("还有"+BaseUtil.format2(temp)+"点升级");
//        seek_text.setText(String.valueOf(myVip.getCurrent_total()));
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
                float thumb = BaseUtil.dip2px(VipActivity.this,15);

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


    private void setData(){
        BaseUtil.loadCircleBitmap(mContext,myData.getU_image_link(),R.mipmap.ic_launcher_round,iv_head);
        if("1".equals(myData.getIs_vip())){
            iv_king.setVisibility(View.VISIBLE);
        }else{
            iv_king.setVisibility(View.INVISIBLE);
        }
        tv_id.setText("ID:"+myData.getId());
        tv_name.setText(myData.getU_nickname());
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
       header= View.inflate(mContext, R.layout.header_vip, null);
        iv_head=header.findViewById(R.id.iv_head);
        tv_name=header.findViewById(R.id.tv_name);
        iv_king=header.findViewById(R.id.iv_king);
        tv_id=header.findViewById(R.id.tv_id);
        tv_cur=header.findViewById(R.id.tv_cur);
        tv_next=header.findViewById(R.id.tv_next);
        seek_text=header.findViewById(R.id.seek_text);
        seekbar=header.findViewById(R.id.seekbar);
        tv_up=header.findViewById(R.id.tv_up);
        ll_back.setOnClickListener(this);
        tv_title.setText("会员专区");
        BaseUtil.initXRecyleVertical(mContext,rcy_list);
        rcy_list.addHeaderView(header);
        rcy_list.setPullRefreshEnabled(true);
        rcy_list.setLoadingMoreEnabled(false);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorker().get_product("1",page.toString(),"20",
                        BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),"","1");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().get_product("1",page.toString(),"20",
                        BaseUtil.getLng(mContext),BaseUtil.getLat(mContext),"","1");
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
