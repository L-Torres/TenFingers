package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ProValueAdapter;
import com.beijing.tenfingers.adapter.ValueListAdapter;
import com.beijing.tenfingers.bean.ProductDetail;
import com.beijing.tenfingers.bean.ValueList;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.ImageBanner;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_title, tv_name, tv_price, tv_original, tv_info, tv_pro, tv_book,tv_time;
    private String title = "项目详情";
    private String id;
    private ProductDetail productDetail;
    private ImageView img_banner;
    private Integer page=1;
    private ArrayList<ValueList> lists=new ArrayList<>();
    private XRecyclerView rcy_list;
    private ProValueAdapter adapter;
    private ViewFlipper vf;
    private ArrayList<String> keys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_productdetail);
        super.onCreate(savedInstanceState);
        getNetWorker().product_detail(id);
        getNetWorker().commlist(id,page.toString(),"20","2");
        keys.add("183***1234刚购买了此次服务");
        keys.add("182***7877刚购买了此次服务");
        keys.add("181***7569刚购买了此次服务");
        keys.add("180***5696刚购买了此次服务");
        keys.add("189***3698刚购买了此次服务");
        setAdData();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case PRODUCT_DETAIL:
                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case PRODUCT_DETAIL:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case PRODUCT_DETAIL:
                HemaArrayResult<ProductDetail> pResult = (HemaArrayResult<ProductDetail>) baseResult;
                productDetail = pResult.getObjects().get(0);
                setData();
                break;
            case COMMENTLIST:
                HemaArrayResult<ValueList> mResult= (HemaArrayResult<ValueList>) baseResult;
                ArrayList<ValueList> temp=new ArrayList<>();
                temp=mResult.getObjects();
                if ("1".equals(page.toString())) {
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
                    adapter =new ProValueAdapter(mContext,lists);
                    rcy_list.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

                break;
        }
    }

    private void setData() {
        tv_title.setText(productDetail.getP_name());
        tv_time.setText(BaseUtil.toHour(productDetail.getP_service_time()));
        tv_name.setText(productDetail.getP_name());
        if ("1".equals(productDetail.getCan())) {
            tv_price.setText("¥" + productDetail.getActivity_price() + "次/人");
            tv_original.setText("¥" + productDetail.getP_price() + "次/人");
            BaseUtil.setLine(tv_original);
        } else {
            tv_original.setVisibility(View.INVISIBLE);
            tv_price.setText("¥" + productDetail.getP_price() + "次/人");
        }
        tv_info.setText(productDetail.getP_desc());
        BaseUtil.loadBitmap(productDetail.getP_image_link(),R.mipmap.icon_service,img_banner);
        tv_pro.setText(productDetail.getP_admits());
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case PRODUCT_DETAIL:

                break;
        }
    }

    @Override
    protected void findView() {
        vf = (ViewFlipper) findViewById(R.id.vf);
        tv_time=findViewById(R.id.tv_time);
        rcy_list=findViewById(R.id.rcy_list);
        BaseUtil.initRecyleVertical(mContext,rcy_list);
        tv_book = findViewById(R.id.tv_book);
        img_banner = findViewById(R.id.img_banner);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        tv_original = findViewById(R.id.tv_original);
        tv_info = findViewById(R.id.tv_info);
        tv_pro = findViewById(R.id.tv_pro);
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        img_banner.getLayoutParams().height = (int) (BaseUtil.getScreenWidth(mContext)*1.2);
    }
    /**
     * 设置滚动广告数据
     * 设置滚动头条
     */
    private void setAdData() {
        vf.removeAllViews();
        if (keys.size() > 0) {
            for (int i = 0; i < keys.size(); i++) {
                View v = LayoutInflater.from(mContext).inflate(
                        R.layout.header_textview, null);
                ViewHolder h = new ViewHolder(v);
                setData(h, keys.get(i));
                vf.addView(v);

            }
        } else {
            View v = LayoutInflater.from(mContext).inflate(
                    R.layout.header_textview, null);
            ViewHolder h = new ViewHolder(v);
            setData(h, "请输入您要搜索的茶叶");
            vf.addView(v);
        }
    }
    private class ViewHolder {
        private TextView tv_info;

        public ViewHolder(View v) {
            tv_info = (TextView) v.findViewById(R.id.tv_info);
        }
    }

    private void setData(ViewHolder h, String f) throws ParseException {
        h.tv_info.setText(f);
        h.tv_info.setTextColor(0xffc6c8c9);
        h.tv_info.setTag(f);
        h.tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = (String) v.getTag();
                Intent it = new Intent(mContext, SearchActivity.class);
                if (keys.size() > 0) {
                    it.putExtra("key", temp);
                }
                startActivity(it);
                changeAc();
            }
        });
    }
    @Override
    protected void getExras() {
        id = mIntent.getStringExtra("id");
        title = mIntent.getStringExtra("title");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText(title);
        tv_book.setOnClickListener(this);
        rcy_list.setPullRefreshEnabled(false);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNetWorker().commlist(id,page.toString(),"20","2");
            }

            @Override
            public void onLoadMore() {
                page++;
                getNetWorker().commlist(id,page.toString(),"20","2");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch (v.getId()) {
            case R.id.tv_book:
                if (BaseUtil.IsLogin()) {
                    if("1".equals(productDetail.getIs_vip())){
                        if("1".equals(BaseUtil.getVip(mContext))){
                            it = new Intent(mContext, SelectTecActivity.class);
                            it.putExtra("id", id);
                            it.putExtra("type", "0");
                            it.putExtra("sid", productDetail.getS_id());
                            it.putExtra("is_vip", productDetail.getIs_vip());
                            startActivity(it);
                            changeAc();
                        }else{
                            BaseUtil.showVip(mContext);
                        }
                    }else{

                        it = new Intent(mContext, SelectTecActivity.class);
                        it.putExtra("id", id);
                        it.putExtra("type", "0");
                        it.putExtra("sid", productDetail.getS_id());
                        it.putExtra("is_vip", productDetail.getIs_vip());
                        startActivity(it);
                        changeAc();
                    }
                } else {
                    BaseUtil.toLogin(mContext, "1");
                }

                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }

    }
}
