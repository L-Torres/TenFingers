package com.beijing.tenfingers.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.beijing.tenfingers.Alipay.PayResult;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.ConfirmOrderActivity;
import com.beijing.tenfingers.adapter.OrderAdpter;
import com.beijing.tenfingers.adapter.RefundAdapter;
import com.beijing.tenfingers.bean.Order;
import com.beijing.tenfingers.bean.WeixinTrade;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.until.PaginationUntil;
import com.beijing.tenfingers.view.CodeDialog;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.ToastDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class MyOrderFragment extends MyBaseFragment {

    private int position;
    private XRecyclerView rcy_list;
    private OrderAdpter adpter1;
    private OrderAdpter adpter2;
    private OrderAdpter adpter3;
    private OrderAdpter adpter4;
    private OrderAdpter adpter5;
    private OrderAdpter adpter6;
    private OrderAdpter adpter7;
    private RefundAdapter refundAdapter;
    private ArrayList<String> list = new ArrayList<>();

    public MyOrderFragment() {
        super();
    }

    private Integer page0 = 1;
    private Integer page1 = 1;
    private Integer page2 = 1;
    private Integer page3 = 1;
    private Integer page4 = 1;
    private Integer page5 = 1;
    private Integer page6 = 1;
    private Integer page7 = 1;
    private ArrayList<Order> allList = new ArrayList<>();
    private ArrayList<Order> fList = new ArrayList<>();
    private ArrayList<Order> hList = new ArrayList<>();
    private ArrayList<Order> dList = new ArrayList<>();
    private ArrayList<Order> pList = new ArrayList<>();
    private ArrayList<Order> cList = new ArrayList<>();
    private ArrayList<Order> xList = new ArrayList<>();
    private ArrayList<Order> rList = new ArrayList<>();
    private ImageView iv_empty;
    private PopTipDialog popTipDialog;
    private CodeDialog codeDialog;
    private String pay_type="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_list);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onResume() {
        initData();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //显示二维码
    public void show_code(String url) {
        codeDialog = new CodeDialog(getActivity(), url);
        codeDialog.setCancelable(true);
        codeDialog.show();

    }

    //立即支付
    public void order_pay(String id,String pay_type){
        this.pay_type=pay_type;
        getNetWorker().order_proceed(MyApplication.getInstance().getUser().getToken(),id);
    }
    //取消售后
    public void cancel_refund(String id){
        popTipDialog = new PopTipDialog(getActivity());
        popTipDialog.setCancelable(true);
        popTipDialog.setTip("确定取消售后？");
        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
            @Override
            public void onLeftButtonClick(PopTipDialog dialog) {

                dialog.cancel();

            }

            @Override
            public void onRightButtonClick(PopTipDialog dialog) {
                dialog.cancel();
                getNetWorker().order_refund_cancel_two(MyApplication.getInstance().getUser().getToken(), id);

            }
        });
        popTipDialog.show();
    }
    //取消订单
    public void cancel_order(String id) {
        popTipDialog = new PopTipDialog(getActivity());
        popTipDialog.setCancelable(true);
        popTipDialog.setTip("确定取消订单？");
        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
            @Override
            public void onLeftButtonClick(PopTipDialog dialog) {

                dialog.cancel();

            }

            @Override
            public void onRightButtonClick(PopTipDialog dialog) {
                dialog.cancel();
                getNetWorker().orderCancel(MyApplication.getInstance().getUser().getToken(), id);
            }
        });
        popTipDialog.show();
    }

    //删除订单
    public void delete_order(String id) {
        popTipDialog = new PopTipDialog(getActivity());
        popTipDialog.setCancelable(true);
        popTipDialog.setTip("确定删除订单？");
        popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
            @Override
            public void onLeftButtonClick(PopTipDialog dialog) {

                dialog.cancel();

            }

            @Override
            public void onRightButtonClick(PopTipDialog dialog) {
                dialog.cancel();
                getNetWorker().order_delete(MyApplication.getInstance().getUser().getToken(), id);
            }
        });
        popTipDialog.show();

    }

    //获取数据
    private void initData() {
        switch (position) {//0全部 1 未支付 2 待发货 3 待收货 4 已收货,待评价 5 售后
            case 0://全部
                page0 = 1;
                getOrderList(page0, "0");
                break;
            case 1://待付款
                page1 = 1;
                getOrderList(page1, "1");
                break;
            case 2://待服务
                page2 = 1;
                getOrderList(page2, "2");
                break;
            case 3://进行中
                page3 = 1;
                getOrderList(page3, "4");
                break;
            case 4://待评价
                page4 = 1;
                getOrderList(page4, "5");
                break;
            case 5://已完成
                page5 = 1;
                getOrderList(page5, "6");
                break;
            case 6://已取消
                page6 = 1;
                getOrderList(page6, "7");
                break;
            case 7:
                getOrderList(page7, "8");
                break;
        }
    }

    private void getOrderList(int page, String status) {
        getNetWorker().order_list(MyApplication.getInstance().getUser().getToken(), String.valueOf(page), "20", status);
    }

    @Override
    protected void findView() {
        iv_empty = (ImageView) findViewById(R.id.iv_empty);
        rcy_list = (XRecyclerView) findViewById(R.id.rcy_list);
    }

    @Override
    protected void setListener() {
        BaseUtil.initXRecyleVertical(getActivity(), rcy_list);
        rcy_list.setLoadingMoreEnabled(true);
        rcy_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                switch (position) {//订单状态 1 未支付 2 支付成功 待接单 3 已接单待服务 4 进行中 5 待评价 6 已完成 7 已取消
                    case 0://全部
                        page0++;
                        getOrderList(page0, "0");
                        break;
                    case 1://待付款
                        page1++;
                        getOrderList(page1, "1");
                        break;
                    case 2://待服务
                        page2++;
                        getOrderList(page2, "2");
                        break;
                    case 3://进行中
                        page3++;
                        getOrderList(page3, "4");
                        break;
                    case 4://待评价
                        page4++;
                        getOrderList(page4, "5");
                        break;
                    case 5://已完成
                        page5++;
                        getOrderList(page5, "6");
                        break;
                    case 6://已取消
                        page5++;
                        getOrderList(page5, "7");
                        break;
                    case 7://已取消
                        page6++;
                        getOrderList(page6, "8");
                        break;
                }
            }
        });

    }

    public static MyOrderFragment getInstance(int position) {
        MyOrderFragment fragment = new MyOrderFragment();
        fragment.position = position;
        return fragment;
    }


    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.CLIENT_LOGIN:
            case MyEventBusConfig.APPLY_FOR_RETURN:
            case MyEventBusConfig.PAY_CANCEL:
            case MyEventBusConfig.PAY_SUCCESS:
            case MyEventBusConfig.UPDATE_ORDER:
                initData();
                break;
        }

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case ORDER_DELETE:
            case ORDER_CANCEL:
            case ORDER_LIST:
//                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case ORDER_DELETE:
            case ORDER_CANCEL:
            case ORDER_LIST:
//                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case VERSION_2_ORDER_REFUND_CANCEL:
                ToastUtils.show("取消成功！");
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.APPLY_FOR_RETURN));
                break;
            case ORDER_PROCEED:
               if("1".equals(pay_type)){
                    HemaArrayResult<WeixinTrade> wResult = (HemaArrayResult<WeixinTrade>) baseResult;
                    WeixinTrade wTrade = wResult.getObjects().get(0);
                    if (BaseUtil.isWeixinAvilible(getActivity())) {
                        goWeixin(wTrade);
                    } else {
                        ToastUtil.showLongToast(getActivity(), "请先安装微信！");
                        return;
                    }
                }else{
                    HemaArrayResult<String>  wResult= (HemaArrayResult<String>) baseResult;
                    String orderInfo=wResult.getObjects().get(0).toString();
                    new AlipayThread(orderInfo).start();
//                    ToastUtils.show("下单成功！");
                }
                break;
            case ORDER_DELETE:
            case ORDER_CANCEL:
                initData();
                ToastUtils.show("操作成功！");
                break;
            case ORDER_LIST:
                switch (position) {
                    case 0://全部
                        HemaArrayResult<Order> aResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> allTemp = aResult.getObjects();
                        PaginationUntil<Order> pagination = new PaginationUntil<>(this.allList, allTemp, rcy_list);
                        allList = pagination.pagination(getActivity(), page0.toString());
                        if (adpter1 == null) {
                            adpter1 = new OrderAdpter(getActivity(), allList, this);
                            rcy_list.setAdapter(adpter1);
                        } else {
                            adpter1.freshData(allList);
                            adpter1.notifyDataSetChanged();
                        }
                        break;
                    case 1://待付款
                        HemaArrayResult<Order> fResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> fTemp = fResult.getObjects();
                        PaginationUntil<Order> pagination1 = new PaginationUntil<>(this.fList, fTemp, rcy_list);
                        fList = pagination1.pagination(getActivity(), page1.toString());
                        if (adpter2 == null) {
                            adpter2 = new OrderAdpter(getActivity(), fList, this);
                            rcy_list.setAdapter(adpter2);
                        } else {
                            adpter2.freshData(fList);
                            adpter2.notifyDataSetChanged();
                        }
                        break;
                    case 2://待发货
                        HemaArrayResult<Order> hResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> hTemp = hResult.getObjects();
                        PaginationUntil<Order> pagination2 = new PaginationUntil<>(this.hList, hTemp, rcy_list);
                        hList = pagination2.pagination(getActivity(), page2.toString());
                        if (adpter3 == null) {
                            adpter3 = new OrderAdpter(getActivity(), hList, this);
                            rcy_list.setAdapter(adpter3);
                        } else {
                            adpter3.freshData(hList);
                            adpter3.notifyDataSetChanged();
                        }
                        break;
                    case 3://待收货
                        HemaArrayResult<Order> dResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> dTemp = dResult.getObjects();
                        PaginationUntil<Order> pagination3 = new PaginationUntil<>(this.dList, dTemp, rcy_list);
                        dList = pagination3.pagination(getActivity(), page3.toString());
                        if (adpter4 == null) {
                            adpter4 = new OrderAdpter(getActivity(), dList, this);
                            rcy_list.setAdapter(adpter4);
                        } else {
                            adpter4.freshData(dList);
                            adpter4.notifyDataSetChanged();
                        }
                        break;
                    case 4://待评价
                        HemaArrayResult<Order> pResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> pTemp = pResult.getObjects();
                        PaginationUntil<Order> pagination4 = new PaginationUntil<>(this.pList, pTemp, rcy_list);
                        pList = pagination4.pagination(getActivity(), page4.toString());
                        if (adpter5 == null) {
                            adpter5 = new OrderAdpter(getActivity(), pList, this);
                            rcy_list.setAdapter(adpter5);
                        } else {
                            adpter5.freshData(pList);
                            adpter5.notifyDataSetChanged();
                        }
                        break;
                    case 5://
                        HemaArrayResult<Order> cResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> cTemp = cResult.getObjects();
                        PaginationUntil<Order> pagination5 = new PaginationUntil<>(this.cList, cTemp, rcy_list);
                        cList = pagination5.pagination(getActivity(), page5.toString());
                        if (adpter6 == null) {
                            adpter6 = new OrderAdpter(getActivity(), cList, this);
                            rcy_list.setAdapter(adpter6);
                        } else {
                            adpter6.freshData(cList);
                            adpter6.notifyDataSetChanged();
                        }
                        break;
                    case 6://
                        HemaArrayResult<Order> xResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> xTemp = xResult.getObjects();
                        PaginationUntil<Order> pagination6 = new PaginationUntil<>(this.xList, xTemp, rcy_list);
                        xList = pagination6.pagination(getActivity(), page6.toString());
                        if (adpter7 == null) {
                            adpter7 = new OrderAdpter(getActivity(), xList, this);
                            rcy_list.setAdapter(adpter7);
                        } else {
                            adpter7.freshData(xList);
                            adpter7.notifyDataSetChanged();
                        }
                        break;
                    case 7://
                        HemaArrayResult<Order> rResult = (HemaArrayResult<Order>) baseResult;
                        ArrayList<Order> rTemp = rResult.getObjects();
                        PaginationUntil<Order> pagination7 = new PaginationUntil<>(this.rList, rTemp, rcy_list);
                        rList = pagination7.pagination(getActivity(), page7.toString());
                        if (refundAdapter == null) {
                            refundAdapter = new RefundAdapter(getActivity(), rList, this);
                            rcy_list.setAdapter(refundAdapter);
                        } else {
                            refundAdapter.freshData(rList);
                            refundAdapter.notifyDataSetChanged();
                        }
                        break;
                }
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case ORDER_LIST:

                break;
        }
    }

    /*微信支付开始*/

    private void goWeixin(WeixinTrade trade) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this.getActivity(), trade.getAppid(), true);
        PayReq request = new PayReq();
        request.appId = trade.getAppid();
        request.partnerId = trade.getPartnerid();
        request.prepayId = trade.getPrepayid();
        request.packageValue = trade.getPackageValue();
        request.nonceStr = trade.getNoncestr();
        request.timeStamp = trade.getTimestamp();
        request.sign = trade.getSign();
        msgApi.sendReq(request);
    }

    /*支付宝支付*/
    private class AlipayThread extends Thread {
        String orderInfo;
        AlipayHandler alipayHandler;

        public AlipayThread(String orderInfo) {
            this.orderInfo = orderInfo;
            alipayHandler = new AlipayHandler();
        }

        @Override
        public void run() {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(getActivity());
            Map<String, String> result = alipay.payV2(orderInfo, true);
            log_i("result = " + result);
            Message msg = new Message();
            msg.obj = result;
            alipayHandler.sendMessage(msg);
        }
    }

    private class AlipayHandler extends Handler {

        public AlipayHandler() {


        }

        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            switch (resultStatus) {
                case "9000":
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_SUCCESS));
                    break;
                case "6001":
                    new ToastDialog.Builder(getActivity())
                            .setType(ToastDialog.Type.WARN)
                            .setMessage("支付已取消")
                            .show();
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_CANCEL));
                    break;
                default:
                    new ToastDialog.Builder(getActivity())
                            .setType(ToastDialog.Type.WARN)
                            .setMessage("支付异常，请选择其他支付方式！")
                            .show();
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_CANCEL));
                    break;
            }
        }
    }

}
