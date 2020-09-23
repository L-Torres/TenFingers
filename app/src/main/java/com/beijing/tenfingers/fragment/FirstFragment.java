package com.beijing.tenfingers.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.AddUsActivity;
import com.beijing.tenfingers.activity.JsMoreActivity;
import com.beijing.tenfingers.activity.MsgListActivity;
import com.beijing.tenfingers.activity.RecommendShopActivity;
import com.beijing.tenfingers.activity.ShopDetailActivity;
import com.beijing.tenfingers.adapter.DianpuAdapter;
import com.beijing.tenfingers.adapter.FirstFuwuAdapter;
import com.beijing.tenfingers.adapter.FuwuAdapter;
import com.beijing.tenfingers.adapter.JishiAdapter;
import com.beijing.tenfingers.bean.Count;
import com.beijing.tenfingers.bean.Jishi;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ShopChild;
import com.beijing.tenfingers.bean.ShopList;
import com.beijing.tenfingers.bean.Technician;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.MyRefreshLoadmoreLayout;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

public class FirstFragment extends MyBaseFragment implements View.OnClickListener {

    private RecyclerView rcy_service,rcy_jishi,rcy_dianpu;
    private FirstFuwuAdapter adapter;
    private JishiAdapter jishiAdapter;
    private DianpuAdapter dianpuAdapter;
    private ArrayList<String> list=new ArrayList<>();
    private TextView tv_one,tv_shop,tv_addus,tv_xm,tv_js,tv_book;
    private ArrayList<Product> products=new ArrayList<>();
    private ArrayList<Technicians> teachers=new ArrayList<>();
    private ShopList shopList;
    private ArrayList<ShopChild> children=new ArrayList<>();
    private ImageView iv_msg;
    private View v_red;
    private MyRefreshLoadmoreLayout my_refresh;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_first);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initData();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initData(){
        String token= XtomSharedPreferencesUtil.get(getActivity(),"token");
        if(BaseUtil.IsLogin()){
            getNetWorker().unread_msg(MyApplication.getInstance().getUser().getToken());
        }
        getNetWorker().get_product("0","","",BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),"","");
        getNetWorker().get_teacher("0","","",BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()),"0");
        getNetWorker().get_shops("0","","",BaseUtil.getLng(getActivity()),BaseUtil.getLat(getActivity()));
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.MSG_UPDATE:
                String token=XtomSharedPreferencesUtil.get(getActivity(),"token");
                if(!isNull(token)){
                    getNetWorker().unread_msg(token);
                }
                break;
        }
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case SERVICE_PRODUCT:
//                showProgressDialog("");
                break;
        }


    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case SERVICE_PRODUCT:
//                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        my_refresh.refreshSuccess();
        switch (information){
            case NOTICE_UNREAD:
                HemaArrayResult<Count> cResult= (HemaArrayResult<Count>) baseResult;
                Count c=cResult.getObjects().get(0);
                String  count=c.getCount();
                if(!isNull(count)){
                    if("0".equals(count)){
                        v_red.setVisibility(View.INVISIBLE);
                    }else{
                        v_red.setVisibility(View.VISIBLE);
                    }
                }else{
                    v_red.setVisibility(View.INVISIBLE);
                }

                break;
            case INDEX_SHOP:
                HemaArrayResult<ShopList> sResult= (HemaArrayResult<ShopList>) baseResult;
                shopList=sResult.getObjects().get(0);
                if(shopList!=null){
                    children=shopList.getChildren();
                        if(dianpuAdapter==null){
                            dianpuAdapter=new DianpuAdapter(getActivity(),children);
                            rcy_dianpu.setAdapter(dianpuAdapter);
                        }else{
                            dianpuAdapter.notifyDataSetChanged();
                        }
                }
                break;
            case SERVICE_PRODUCT:
                HemaArrayResult<Product> pResult= (HemaArrayResult<Product>) baseResult;
                products=pResult.getObjects();
//                if(adapter==null){
                    adapter=new FirstFuwuAdapter(getActivity(),products);
                    rcy_service.setAdapter(adapter);
//                }else{
//                    adapter.notifyDataSetChanged();
//                }
                break;
            case TECH_NICIAN:
                HemaArrayResult<Technician> tResult= (HemaArrayResult<Technician>) baseResult;
                teachers=tResult.getObjects().get(0).getList();
//                if(jishiAdapter==null){
                    jishiAdapter=new JishiAdapter(getActivity(),teachers);
                    rcy_jishi.setAdapter(jishiAdapter);
//                }else{
//                    jishiAdapter.notifyDataSetChanged();
//                }
                break;
        }

    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        my_refresh.refreshFailed();
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case SERVICE_PRODUCT:

                break;
        }
    }

    @Override
    protected void findView() {
        my_refresh= (MyRefreshLoadmoreLayout) findViewById(R.id.my_refresh);
        tv_book= (TextView) findViewById(R.id.tv_book);
        v_red=findViewById(R.id.v_red);
        tv_js= (TextView) findViewById(R.id.tv_js);
        tv_xm= (TextView) findViewById(R.id.tv_xm);
        rcy_service= (RecyclerView) findViewById(R.id.rcy_service);
        rcy_jishi= (RecyclerView) findViewById(R.id.rcy_jishi);
        rcy_dianpu= (RecyclerView) findViewById(R.id.rcy_dianpu);
        BaseUtil.initRecyleVertical(getActivity(),rcy_dianpu);
        BaseUtil.initRecyleVertical(getActivity(),rcy_jishi);
        BaseUtil.initRecyleVertical(getActivity(),rcy_service);
        tv_one= (TextView) findViewById(R.id.tv_one);
        tv_shop= (TextView) findViewById(R.id.tv_shop);
        tv_addus= (TextView) findViewById(R.id.tv_addus);
        iv_msg= (ImageView) findViewById(R.id.iv_msg);
    }

    @Override
    protected void setListener() {
        tv_one.setOnClickListener(this);
        tv_shop.setOnClickListener(this);
        tv_addus.setOnClickListener(this);
        tv_xm.setOnClickListener(this);
        tv_js.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        tv_book.setOnClickListener(this);

        my_refresh.setOnStartListener(new XtomRefreshLoadmoreLayout.OnStartListener() {
            @Override
            public void onStartRefresh(XtomRefreshLoadmoreLayout xtomRefreshLoadmoreLayout) {
                initData();
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout xtomRefreshLoadmoreLayout) {
            xtomRefreshLoadmoreLayout.setLoadmoreable(false);
            }
        });
        my_refresh.setLoadmoreable(false);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.tv_xm://项目
            case R.id.tv_book:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.GO_TO_BOOK));
                break;
            case R.id.iv_msg:
                if(BaseUtil.IsLogin()){
                    it=new Intent(getActivity(), MsgListActivity.class);
                    startActivity(it);
                    changeAc(getActivity());
                }else{
                    BaseUtil.toLogin(getActivity(),"1");
                }
                break;
            case R.id.tv_js://技师
                it=new Intent(getActivity(), JsMoreActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.tv_addus:
                it=new Intent(getActivity(), AddUsActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.tv_shop:
                it=new Intent(getActivity(), RecommendShopActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
            case R.id.tv_one:
                it=new Intent(getActivity(), ShopDetailActivity.class);
                startActivity(it);
                changeAc(getActivity());
                break;
        }
    }
}
