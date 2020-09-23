package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.TrendsAdapter;
import com.beijing.tenfingers.adapter.ValueAdapter;
import com.beijing.tenfingers.bean.CommonList;
import com.beijing.tenfingers.bean.CountList;
import com.beijing.tenfingers.bean.TechDetail;
import com.beijing.tenfingers.bean.TechImage;
import com.beijing.tenfingers.bean.TrendsList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.interf.ZanClickListener;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayerManager;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.PopVideoDialog;
import com.beijing.tenfingers.view.ShareDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.showlargepic.ShowLargePicActivity;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

//从项目到选择技师 进入详情 形成订单页面
public class ArtificerActivity extends BaseActivity implements View.OnClickListener , ZanClickListener {
    private ImageView iv_back, iv_personal;
    private FrameLayout fl_info;
    private LinearLayout ll_bottom, ll_video, ll_photo, ll_dt;
//    private RadarView radarview;
    private RecyclerView rcy_value;
    private ValueAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private TechDetail detail;
    private String id;
    private RoundedImageView iv_head;
    private ImageView  iv_king, iv_book, iv_share;
    private TextView tv_name, tv_e_name, tv_dt, tv_photo, tv_collect, tv_hot, tv_info, tv_book, tv_v_more, tv_t_more, tv_p_more,tv_week,tv_month;
    private ArrayList<CountList> countLists = new ArrayList<>();
    private ArrayList<TrendsList> trendsLists = new ArrayList<>();
    private ArrayList<TechImage> imageList = new ArrayList<>();
    private ArrayList<TechImage> richList = new ArrayList<>();
    private CommonList commonList;
    private ArrayList<CommonList.Children> children = new ArrayList<>();
    private String technicianId = "";
    private String sid = "";
    private View v_pic, v_video, v_trend,v_first,v_mid,v_last;
    private RoundedImageView iv_v_one, iv_v_two, iv_v_three, iv_p_one, iv_p_two, iv_p_three;
    private ImageView v_empty;
    private TrendsAdapter trendsAdapter;
    private RecyclerView rcy_trends;
    private String pro_id;
    private TextView tv_zizhi;
//    private PercentPieView percentPieView2 ;
    private  PopVideoDialog videoDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_artificer);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().tech_detail(id);
        getNetWorker().common_list(id, "1", "20");
    }

    @Override
    protected void onDestroy() {
        if(JCVideoPlayerManager.getCurrentJcvd()!=null){
            JCVideoPlayerManager.getCurrentJcvd().release();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case  MyEventBusConfig.DIAN_ZAN:
//                getNetWorker().tech_detail(id);

                break;
            case MyEventBusConfig.CLOSE_VIDEO:
                videoDialog.cancel();

                break;
        }
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case TECHICIAN_DETAIL:

                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case TECHICIAN_DETAIL:

                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case COLLECT_TECHNICIAN:
            case COLLECT_CANCEL:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.COLLECT));
                break;
            case TECHICIAN_COMMENTS:
                HemaArrayResult<CommonList> cResult = (HemaArrayResult<CommonList>) baseResult;
                commonList = cResult.getObjects().get(0);
                children = commonList.getChildren();
                adapter = new ValueAdapter(mContext, children);
                trendsAdapter.setListener(this);
                rcy_value.setAdapter(adapter);
                break;
            case TECHICIAN_DETAIL:
                HemaArrayResult<TechDetail> tResult = (HemaArrayResult<TechDetail>) baseResult;
                detail = tResult.getObjects().get(0);
                countLists = detail.getCountLists();
                trendsLists = detail.getTrendsLists();
                imageList = detail.getTechImages();
                richList = detail.getRichList();
                setData();
                v_pic.setVisibility(View.VISIBLE);
                v_video.setVisibility(View.GONE);
                v_trend.setVisibility(View.GONE);
                break;
        }
    }

    private void setPicData() {
        if (imageList.size() > 0) {
            v_pic.setVisibility(View.VISIBLE);
            v_empty.setVisibility(View.GONE);
            tv_p_more.setVisibility(View.VISIBLE);
            if (imageList.size() == 1) {
                BaseUtil.loadBitmap(imageList.get(0).getImage(), 0, iv_p_one);

                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imageList.get(0).getImage());
                    bigImgs.add(imageList.get(0).getImage());
                iv_p_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(mContext, ShowLargePicActivity.class);
                        it.putExtra("position", 0);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        startActivity(it);
                    }
                });

            } else if (imageList.size() == 2) {
                BaseUtil.loadBitmap(imageList.get(0).getImage(), 0, iv_p_one);
                BaseUtil.loadBitmap(imageList.get(1).getImage(), 0, iv_p_two);
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(imageList.get(0).getImage());
                    bigImgs.add(imageList.get(0).getImage());
                    mImgs.add(imageList.get(1).getImage());
                    bigImgs.add(imageList.get(1).getImage());
                iv_p_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(mContext, ShowLargePicActivity.class);
                        it.putExtra("position", 0);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        startActivity(it);
                    }
                });
                iv_p_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(mContext, ShowLargePicActivity.class);
                        it.putExtra("position", 1);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        startActivity(it);
                    }
                });
            } else if (imageList.size() == 3) {
                BaseUtil.loadBitmap(imageList.get(0).getImage(), 0, iv_p_one);
                BaseUtil.loadBitmap(imageList.get(1).getImage(), 0, iv_p_two);
                BaseUtil.loadBitmap(imageList.get(2).getImage(), 0, iv_p_three);
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                mImgs.add(imageList.get(0).getImage());
                bigImgs.add(imageList.get(0).getImage());
                mImgs.add(imageList.get(1).getImage());
                bigImgs.add(imageList.get(1).getImage());
                mImgs.add(imageList.get(2).getImage());
                bigImgs.add(imageList.get(2).getImage());
                iv_p_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(mContext, ShowLargePicActivity.class);
                        it.putExtra("position", 0);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        startActivity(it);
                    }
                });
                iv_p_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(mContext, ShowLargePicActivity.class);
                        it.putExtra("position", 1);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        startActivity(it);
                    }
                });
                iv_p_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(mContext, ShowLargePicActivity.class);
                        it.putExtra("position", 2);
                        it.putExtra("imagelist", mImgs);
                        it.putExtra("bigImageList", bigImgs);
                        startActivity(it);
                    }
                });
            }
        } else {
            v_pic.setVisibility(View.GONE);
            v_empty.setVisibility(View.VISIBLE);
            tv_p_more.setVisibility(View.GONE);
            iv_p_one.setVisibility(View.GONE);
            iv_p_two.setVisibility(View.GONE);
            iv_p_three.setVisibility(View.GONE);
        }
    }

    private void setVideoData() {

        if (richList.size() > 0) {
            v_video.setVisibility(View.VISIBLE);
            v_empty.setVisibility(View.GONE);
            tv_v_more.setVisibility(View.VISIBLE);
            if (richList.size() == 1) {
                BaseUtil.loadBitmap(richList.get(0).getImage(), 0, iv_v_one);
                iv_v_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         videoDialog=new PopVideoDialog(mContext,richList.get(0).getImage(),richList.get(0).getUrl());
                        videoDialog.setCancelable(true);
                        videoDialog.show();
                    }
                });
            } else if (richList.size() == 2) {
                BaseUtil.loadBitmap(richList.get(0).getImage(), 0, iv_v_one);
                BaseUtil.loadBitmap(richList.get(1).getImage(), 0, iv_v_two);
                iv_v_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         videoDialog=new PopVideoDialog(mContext,richList.get(0).getImage(),richList.get(0).getUrl());
                        videoDialog.setCancelable(true);
                        videoDialog.show();
                    }
                });
                iv_v_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         videoDialog=new PopVideoDialog(mContext,richList.get(1).getImage(),richList.get(1).getUrl());
                        videoDialog.setCancelable(true);
                        videoDialog.show();
                    }
                });
            } else if (richList.size() == 3) {
                BaseUtil.loadBitmap(richList.get(0).getImage(), 0, iv_v_one);
                BaseUtil.loadBitmap(richList.get(1).getImage(), 0, iv_v_two);
                BaseUtil.loadBitmap(richList.get(2).getImage(), 0, iv_v_three);
                iv_v_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         videoDialog=new PopVideoDialog(mContext,richList.get(0).getImage(),richList.get(0).getUrl());
                        videoDialog.setCancelable(true);
                        videoDialog.show();
                    }
                });
                iv_v_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         videoDialog=new PopVideoDialog(mContext,richList.get(1).getImage(),richList.get(1).getUrl());
                        videoDialog.setCancelable(true);
                        videoDialog.show();
                    }
                });
                iv_v_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         videoDialog=new PopVideoDialog(mContext,richList.get(2).getImage(),richList.get(2).getUrl());
                        videoDialog.setCancelable(true);
                        videoDialog.show();
                    }
                });
            }
        } else {
            tv_v_more.setVisibility(View.GONE);
            v_video.setVisibility(View.GONE);
            v_empty.setVisibility(View.VISIBLE);
        }
    }

    private void setTrendsData() {
        if(trendsLists.size()>0){
            rcy_trends.setVisibility(View.VISIBLE);
            v_empty.setVisibility(View.GONE);
            tv_t_more.setVisibility(View.VISIBLE);
        }else{
            rcy_trends.setVisibility(View.GONE);
            v_empty.setVisibility(View.VISIBLE);
            tv_t_more.setVisibility(View.GONE);
        }
        BaseUtil.initRecyleVertical(mContext,rcy_trends);
        if (trendsAdapter == null) {
            trendsAdapter = new TrendsAdapter(mContext, trendsLists,1);
            rcy_trends.setAdapter(trendsAdapter);
        } else {
            trendsAdapter.notifyDataSetChanged();
        }
    }




    private void setData() {



        BaseUtil.loadBitmap(detail.getT_cover_link(),R.mipmap.girl,iv_personal);
        if("1".equals(detail.getIs_vip())){
            iv_king.setVisibility(View.VISIBLE);
        }else{
            iv_king.setVisibility(View.INVISIBLE);
        }
        setPicData();
        setVideoData();
        setTrendsData();
        if(countLists.size()>0){
            int[] data2 = new int[countLists.size()];
            String[] str = new String[countLists.size()];
            for (int i = 0; i < countLists.size(); i++) {
                data2[i]=Integer.valueOf(countLists.get(i).getTotal());
                str[i]=countLists.get(i).getP_name();
            }
        }

        switch (detail.getIs_week()){
            //周统计荣誉 0 无 1 冠军 2 亚军 3 季军
            case "1":
               tv_week.setText("周冠军");
                break;
            case "2":
                tv_week.setText("周亚军");
                break;
            case "3":
                tv_week.setText("周季军");
                break;
            default:
                tv_week.setText("暂无排名");
                break;
        }
        if("1".equals(detail.getIs_month())){
            tv_month.setText("月度十佳技师");
           tv_month.setVisibility(View.VISIBLE);
        }else {
            tv_month.setText("暂无排名");

        }



        tv_hot.setText(detail.getT_hot_value());
        tv_dt.setText(detail.getT_dynamic_count());
        tv_photo.setText(detail.getT_media_count());
        iv_head.setCornerRadius(3);
        BaseUtil.loadBitmap(detail.getT_image_link(), R.mipmap.girl_head, iv_head);
        tv_name.setText(detail.getT_name());
        tv_e_name.setText(detail.getT_english_name());
        tv_collect.setText(detail.getT_collect_count());
        tv_info.setText("技师简介："+detail.getT_desc());
        if (BaseUtil.IsLogin()) {
            if ("1".equals(detail.getIs_collect())) {
                iv_book.setImageResource(R.mipmap.iv_collected);
            } else {
                iv_book.setImageResource(R.mipmap.person_add);
            }
        } else {
            iv_book.setImageResource(R.mipmap.person_add);
        }


    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        ToastUtils.show(baseResult.getError_message());
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_week=findViewById(R.id.tv_week);
        tv_month=findViewById(R.id.tv_month);
//        percentPieView2 = (PercentPieView) findViewById(R.id.percentPieView2);
        tv_zizhi=findViewById(R.id.tv_skill);
        v_first= findViewById(R.id.v_first);
        v_mid= findViewById(R.id.v_mid);
        v_last= findViewById(R.id.v_last);
        tv_t_more = findViewById(R.id.tv_t_more);
        tv_p_more = findViewById(R.id.tv_p_more);
        rcy_trends = findViewById(R.id.rcy_trends);
        v_empty = findViewById(R.id.v_empty);
        v_trend = findViewById(R.id.v_trend);
        iv_v_one = findViewById(R.id.iv_v_one);
        iv_v_two = findViewById(R.id.iv_v_two);
        iv_v_three = findViewById(R.id.iv_v_three);
        iv_p_one = findViewById(R.id.iv_p_one);
        iv_p_two = findViewById(R.id.iv_p_two);
        iv_p_three = findViewById(R.id.iv_p_three);
        v_pic = findViewById(R.id.v_pic);
        v_video = findViewById(R.id.v_video);
        tv_v_more = findViewById(R.id.tv_v_more);
        iv_share = findViewById(R.id.iv_share);
        iv_book = findViewById(R.id.iv_book);
        tv_book = findViewById(R.id.tv_book);
        ll_video = findViewById(R.id.ll_video);
        ll_dt = findViewById(R.id.ll_dt);
        ll_photo = findViewById(R.id.ll_photo);
        tv_info = findViewById(R.id.tv_info);
        tv_hot = findViewById(R.id.tv_hot);
        tv_collect = findViewById(R.id.tv_collect);
        tv_photo = findViewById(R.id.tv_photo);
        tv_dt = findViewById(R.id.tv_dt);
        tv_e_name = findViewById(R.id.tv_e_name);
        iv_king = findViewById(R.id.iv_king);
        tv_name = findViewById(R.id.tv_name);
        iv_head = findViewById(R.id.iv_head);
//        radarview = findViewById(R.id.radarview);
        ll_bottom = findViewById(R.id.ll_bottom);
        iv_back = findViewById(R.id.iv_back);
        iv_personal = findViewById(R.id.iv_personal);
        fl_info = findViewById(R.id.fl_info);
        rcy_value = findViewById(R.id.rcy_value);
        ll_video = findViewById(R.id.ll_video);
    }

    @Override
    protected void getExras() {
        id = mIntent.getStringExtra("id");
        technicianId = mIntent.getStringExtra("technicianId");
        sid = mIntent.getStringExtra("sid");
        pro_id=mIntent.getStringExtra("pro_id");
    }

    @Override
    protected void setListener() {
        tv_zizhi.setOnClickListener(this);
        tv_t_more.setOnClickListener(this);
        tv_p_more.setOnClickListener(this);
        tv_v_more.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_book.setOnClickListener(this);
        tv_book.setOnClickListener(this);
        ll_dt.setOnClickListener(this);
        ll_video.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_photo.setOnClickListener(this);
        int h = (int) (BaseUtil.getScreenWidth(mContext) * 1.33);
        iv_personal.getLayoutParams().height = h;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fl_info.getLayoutParams();
        params.setMargins(0, BaseUtil.getScreenWidth(mContext) + 10, 0, 0);// 通过自定义坐标来放置你的控件
        fl_info.setLayoutParams(params);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ll_bottom.getLayoutParams();
        layoutParams.setMargins(0, h, 0, 0);// 通过自定义坐标来放置你的控件
        ll_bottom.setLayoutParams(layoutParams);
        BaseUtil.initRecyleVertical(mContext, rcy_value);
        ll_video.setOnClickListener(this);
        iv_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch (v.getId()) {
            case R.id.iv_head:
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                    mImgs.add(detail.getT_image_link());
                    bigImgs.add(detail.getT_image_link());

                 it = new Intent(mContext, ShowLargePicActivity.class);
                it.putExtra("position", 0);
                it.putExtra("imagelist", mImgs);
                it.putExtra("bigImageList", bigImgs);
                startActivity(it);

                break;
            case R.id.tv_skill:
                it=new Intent(mContext,SkillActivity.class);
                it.putExtra("health",detail.getT_health_link());
                it.putExtra("skill",detail.getT_skill_link());
                startActivity(it);
                changeAc();
                break;
            case R.id.tv_p_more://图片
                if(BaseUtil.IsLogin()){
                    it = new Intent(mContext, PhotoListActivity.class);
                    it.putExtra("id", id);
                    startActivity(it);
                    changeAc();
                }else{
                    BaseUtil.toLogin(mContext,"1");
                }

                break;
            case R.id.tv_t_more://动态
                if(BaseUtil.IsLogin()){
                    it = new Intent(mContext, TrendsActivity.class);
                    it.putExtra("id", id);
                    it.putExtra("is_vip",MyApplication.getInstance().getUser().getViptype());
                    startActivity(it);
                    changeAc();
                }else{
                    BaseUtil.toLogin(mContext,"1");
                }
                break;
            case R.id.tv_v_more://视频
                if(BaseUtil.IsLogin()){
                    it = new Intent(mContext, VideoListActivity.class);
                    it.putExtra("id", id);
                    startActivity(it);
                    changeAc();
                }else{
                    BaseUtil.toLogin(mContext,"1");
                }

                break;
            case R.id.iv_share:
//                String path = MyConfig.WEB_ROOT;
                String path = "http://10zhijian.com/down.html?t_id="+id;
                ShareDialog shareDialog = new ShareDialog(mContext, path, "十指间", "十指间",
                        detail.getT_image_link(), "0",path);
                shareDialog.show();
                break;
            case R.id.iv_book:
                if (BaseUtil.IsLogin()) {
//                    if (detail.getIs_collect().equals("1")) {
//                        iv_book.setImageResource(R.mipmap.person_add);
//                        getNetWorker().collect_cancel(MyApplication.getInstance().getUser().getToken(), id);
//                    } else {
                        iv_book.setImageResource(R.mipmap.iv_collected);
                        getNetWorker().collect_tech(MyApplication.getInstance().getUser().getToken(), id);
//                    }
                } else {
                    BaseUtil.toLogin(mContext,"1");
                }
                break;
            case R.id.tv_book://立即预约
                if(BaseUtil.IsLogin()){
                    it = new Intent(mContext, ConfirmOrderActivity.class);
                    it.putExtra("id",pro_id );
                    it.putExtra("technicianId", id);
                    it.putExtra("sid", sid);
                    startActivity(it);
                    changeAc();
                }else{
                    BaseUtil.toLogin(mContext,"1");
                }

                break;
            case R.id.ll_dt:
               v_trend.setVisibility(View.VISIBLE);
               v_video.setVisibility(View.GONE);
               v_pic.setVisibility(View.GONE);
               v_first.setVisibility(View.INVISIBLE);
               v_mid.setVisibility(View.INVISIBLE);
               v_last.setVisibility(View.VISIBLE);
                tv_v_more.setVisibility(View.GONE);
                tv_p_more.setVisibility(View.GONE);
                setTrendsData();
                break;
            case R.id.ll_photo:
                v_pic.setVisibility(View.VISIBLE);
                v_video.setVisibility(View.GONE);
                v_trend.setVisibility(View.GONE);
                v_first.setVisibility(View.VISIBLE);
                v_mid.setVisibility(View.INVISIBLE);
                v_last.setVisibility(View.INVISIBLE);
                tv_v_more.setVisibility(View.GONE);
                tv_t_more.setVisibility(View.GONE);
                setPicData();
                break;
            case R.id.ll_video:

                v_video.setVisibility(View.VISIBLE);
                v_pic.setVisibility(View.GONE);
                v_trend.setVisibility(View.GONE);
                v_first.setVisibility(View.INVISIBLE);
                v_mid.setVisibility(View.VISIBLE);
                v_last.setVisibility(View.INVISIBLE);
                tv_p_more.setVisibility(View.GONE);
                tv_t_more.setVisibility(View.GONE);
                setVideoData();
                break;
            case R.id.iv_back:
                finishAc(mContext);
                break;
        }
    }

    @Override
    public void thumb(String id, int type) {
        if(type==0){
            getNetWorker().thumb("2",id);
        }else{
            getNetWorker().thumb("1",id);
        }
    }

    @Override
    public void collect(String id) {

    }

    @Override
    public void addToCart(String id) {

    }
}
