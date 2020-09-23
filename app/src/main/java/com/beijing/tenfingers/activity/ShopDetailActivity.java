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
import com.beijing.tenfingers.adapter.ShopFwAdapter;
import com.beijing.tenfingers.adapter.ValueAdapter;
import com.beijing.tenfingers.bean.CommonList;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ShopDetail;
import com.beijing.tenfingers.bean.ShopImage;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.shop.ShopProListActivity;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.RatingBar;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.ArrayList;

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back,ll_top,ll_more,ll_tech;
    private TextView tv_title;
    private ImageView iv_pic,iv_tech,iv_value,iv_collect;
    private RecyclerView rcy_service,rcy_value;
    private ShopFwAdapter adapter;
    private ArrayList<Product> list=new ArrayList<>();
    private ValueAdapter valueAdapter;
    private ArrayList<String> values=new ArrayList<>();

    private ShopDetail detail;
    private String id="";
    private TextView tv_name,tv_buy,tv_focus,tv_address,tv_plus,tv_more,tv_score,tv_value_more;
    private FrameLayout fl_more;
    private ImageView iv_one,iv_two,iv_three,iv_four;
    private ArrayList<ShopImage> images=new ArrayList<>();
    private ArrayList<Technicians> technicians=new ArrayList<>();
    private ArrayList<Product> products=new ArrayList<>();
    private RatingBar rating_at;
    private CommonList commonList;
    private ArrayList<CommonList.Children> children = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopdetail);
        super.onCreate(savedInstanceState);
        getNetWorker().shop_detail(id);
        getNetWorker().shop_teacher(id,BaseUtil.getLng(mContext),BaseUtil.getLat(mContext));
        getNetWorker().shop_product(id);
        getNetWorker().shop_common(id, "1", "20");
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
            case SHOP_COLLECT:
                iv_collect.setImageResource(R.mipmap.heart_added);

                break;
            case SHOP_COMMON:
                HemaArrayResult<CommonList> cResult = (HemaArrayResult<CommonList>) baseResult;
                commonList = cResult.getObjects().get(0);
                children = commonList.getChildren();
                if(children.size()>0){
                    rcy_value.setVisibility(View.VISIBLE);
                    tv_value_more.setVisibility(View.VISIBLE);
                    iv_value.setVisibility(View.GONE);
                    tv_value_more.setVisibility(View.VISIBLE);
                    valueAdapter = new ValueAdapter(mContext, children);
                    rcy_value.setAdapter(valueAdapter);
                }else{
                    rcy_value.setVisibility(View.GONE);
                    tv_value_more.setVisibility(View.GONE);
                    iv_value.setVisibility(View.VISIBLE);
                }

                break;
            case SHOP_PRODUCT:
                HemaArrayResult<Product> pResult= (HemaArrayResult<Product>) baseResult;
                products=pResult.getObjects();
                adapter=new ShopFwAdapter(mContext,products);
                rcy_service.setAdapter(adapter);
                break;
            case SHOP_TEACHER:
                HemaArrayResult<Technicians> tResult= (HemaArrayResult<Technicians>) baseResult;
                technicians=tResult.getObjects();
                setTechData();
                break;
            case SHOP_DETAIL:
                HemaArrayResult<ShopDetail> sResult= (HemaArrayResult<ShopDetail>) baseResult;
                detail=sResult.getObjects().get(0);
                setData();
                break;

        }
    }

    private void setTechData(){
        iv_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(mContext, FArtificerActivity.class);
                it.putExtra("id",technicians.get(0).getId());
                startActivity(it);
            }
        });
        iv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(mContext, FArtificerActivity.class);
                it.putExtra("id",technicians.get(1).getId());
                startActivity(it);
            }
        });
        iv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(mContext, FArtificerActivity.class);
                it.putExtra("id",technicians.get(2).getId());
                startActivity(it);
            }
        });
        iv_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(mContext, FArtificerActivity.class);
                it.putExtra("id",technicians.get(3).getId());
                startActivity(it);
            }
        });
        if(technicians.size()>0){
            ll_tech.setVisibility(View.VISIBLE);
            iv_tech.setVisibility(View.GONE);
            switch (technicians.size()){
                case 1:
                    BaseUtil.loadBitmap(technicians.get(0).getT_image_link(),R.mipmap.ic_launcher,iv_one);
                    iv_two.setVisibility(View.INVISIBLE);
                    iv_three.setVisibility(View.INVISIBLE);
                    iv_four.setVisibility(View.INVISIBLE);
                    fl_more.setVisibility(View.INVISIBLE);

                    break;
                case 2:
                    BaseUtil.loadBitmap(technicians.get(0).getT_image_link(),R.mipmap.ic_launcher,iv_one);
                    BaseUtil.loadBitmap(technicians.get(1).getT_image_link(),R.mipmap.ic_launcher,iv_two);
                    iv_three.setVisibility(View.INVISIBLE);
                    iv_four.setVisibility(View.INVISIBLE);
//                    fl_more.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    BaseUtil.loadBitmap(technicians.get(0).getT_image_link(),R.mipmap.ic_launcher,iv_one);
                    BaseUtil.loadBitmap(technicians.get(1).getT_image_link(),R.mipmap.ic_launcher,iv_two);
                    BaseUtil.loadBitmap(technicians.get(2).getT_image_link(),R.mipmap.ic_launcher,iv_three);
                    iv_four.setVisibility(View.INVISIBLE);
                    fl_more.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    BaseUtil.loadBitmap(technicians.get(0).getT_image_link(),R.mipmap.ic_launcher,iv_one);
                    BaseUtil.loadBitmap(technicians.get(1).getT_image_link(),R.mipmap.ic_launcher,iv_two);
                    BaseUtil.loadBitmap(technicians.get(2).getT_image_link(),R.mipmap.ic_launcher,iv_three);
                    BaseUtil.loadBitmap(technicians.get(3).getT_image_link(),R.mipmap.ic_launcher,iv_four);
                    fl_more.setVisibility(View.INVISIBLE);
                    break;
            }
        }else{
            ll_tech.setVisibility(View.GONE);
            iv_tech.setVisibility(View.VISIBLE);
        }

        if(technicians.size()>4){
            int size=technicians.size()-4;
            ll_more.setVisibility(View.VISIBLE);
            tv_plus.setText("+"+size);
            ll_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it=new Intent(mContext,ShopTeachersActivity.class);
                    it.putExtra("shop_id",id);
                    startActivity(it);
                    changeAc();

                }
            });
        }
    }

    private void setData(){
        if("1".equals(detail.getIs_collect())){
         iv_collect.setImageResource(R.mipmap.heart_added);
        }else{
            iv_collect.setImageResource(R.mipmap.heart_add);
        }
        BaseUtil.loadBitmap(detail.getS_image_link(),0,iv_pic);
        tv_name.setText(detail.getS_name());
        tv_address.setText(detail.getAddr_name());
        images=detail.getImgs();
        tv_buy.setText(detail.getFinish_count()+"人消费");
        tv_focus.setText(detail.getCollect_count()+"人关注");
        BaseUtil.setMyRatingBarHeight(mContext, R.mipmap.star_y,rating_at);
        if(!isNull(detail.getShop_score())){
            float temp=Float.valueOf(detail.getShop_score());
            rating_at.setStar(temp);
        }
        tv_score.setText(detail.getShop_score()+"分");
        tv_title.setText(detail.getS_name());
    }


    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case SHOP_COLLECT:
                iv_collect.setImageResource(R.mipmap.heart_add);
                break;
        }

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        iv_collect=findViewById(R.id.iv_collect);
        iv_value=findViewById(R.id.iv_value);
        tv_value_more=findViewById(R.id.tv_value_more);
        ll_tech=findViewById(R.id.ll_tech);
        iv_tech=findViewById(R.id.iv_tech);
        tv_score=findViewById(R.id.tv_score);
        rating_at=findViewById(R.id.rating_at);
        fl_more=findViewById(R.id.fl_more);
        tv_more=findViewById(R.id.tv_more);
        tv_plus=findViewById(R.id.tv_plus);
        iv_one=findViewById(R.id.iv_one);
        iv_two=findViewById(R.id.iv_two);
        iv_three=findViewById(R.id.iv_three);
        iv_four=findViewById(R.id.iv_four);
        tv_name=findViewById(R.id.tv_name);
        tv_buy=findViewById(R.id.tv_buy);
        tv_focus=findViewById(R.id.tv_focus);
        tv_address=findViewById(R.id.tv_address);
        ll_more=findViewById(R.id.ll_more);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        iv_pic=findViewById(R.id.iv_pic);
        ll_top=findViewById(R.id.ll_top);
        rcy_value=findViewById(R.id.rcy_value);
        rcy_service=findViewById(R.id.rcy_service);
        BaseUtil.initRecyleVertical(mContext,rcy_service);
        BaseUtil.initRecyleVertical(mContext,rcy_value);
    }

    @Override
    protected void getExras() {
        id=mIntent.getStringExtra("id");

    }

    @Override
    protected void setListener() {
        iv_collect.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        fl_more.setOnClickListener(this);
        tv_title.setText("商铺名称");
        int h= (int) ((BaseUtil.getScreenWidth(mContext)-BaseUtil.dip2px(mContext,48))*0.563);
        iv_pic.getLayoutParams().height=h;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_top.getLayoutParams();
        params.setMargins(0, h-10, 0, 0);// 通过自定义坐标来放置你的控件
        ll_top.setLayoutParams(params);



    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.iv_collect:
                if(BaseUtil.IsLogin()){
                    getNetWorker().shop_collect(MyApplication.getInstance().getUser().getToken(),id);
                }else{
                    BaseUtil.toLogin(mContext,"1");
                }

                break;
            case R.id.tv_more:
                it=new Intent(mContext, ShopProListActivity.class);
                it.putExtra("id",id);
                startActivity(it);
                changeAc();

                break;
            case R.id.fl_more:
            case R.id.ll_more://店内技师
                it=new Intent(mContext,ShopTeachersActivity.class);
                it.putExtra("shop_id",id);
                startActivity(it);
                changeAc();
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
