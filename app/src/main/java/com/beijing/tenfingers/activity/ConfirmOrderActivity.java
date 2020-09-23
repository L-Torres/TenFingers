package com.beijing.tenfingers.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.beijing.tenfingers.Alipay.PayResult;
import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.Address;
import com.beijing.tenfingers.bean.CarFee;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.bean.OrderSettle;
import com.beijing.tenfingers.bean.TechDetail;
import com.beijing.tenfingers.bean.TimeData;
import com.beijing.tenfingers.bean.TimePoint;
import com.beijing.tenfingers.bean.WeixinTrade;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BottomPayDialog;
import com.beijing.tenfingers.view.BottomTimeDialog;
import com.beijing.tenfingers.view.BottomWayOutDialog;
import com.beijing.tenfingers.view.CouponBottomDialog;
import com.beijing.tenfingers.view.PopTipDialog;
import com.beijing.tenfingers.view.ToastUtils;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.util.DateUtil;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import de.greenrobot.event.EventBus;

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener, TimePicker.OnTimeSelectListener {


    private TimePicker mTimePicker;
    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("MM月dd日  E", Locale.CHINA);
    private final int REQUEST_LOC = 0x400;//地址选择成功
    private Address address;
    private String pro_id = "";//项目id
    private OrderSettle orderSettle;
    private ImageView iv_pro, iv_select;
    private EditText ed_content, ed_dashang;
    private LinearLayout ll_back, ll_service_time, ll_address, ll_empty, ll_pay, ll_go, ll_coupon;
    private TextView tv_title, tv_name, tv_address, tv_pro_name, tv_pro_price,
            tv_pro_content, tv_pro_time, tv_pay, tv_time, tv_total, tv_book, tv_go, tv_coupon,tv_member,tv_rule;
    private BottomPayDialog payDialog;
    private String pay_type = "";
    private BottomWayOutDialog wayOutDialog;
    private String tripType = "";
    private BottomTimeDialog timeDialog;
    private ArrayList<String> list = new ArrayList<>();
    private String technicianId = "";
    private String sid = "";
    private ArrayList<TimePoint> timePoints = new ArrayList<>();
    private String addrId = "";
    private String timeId = "";
    private String couponId = "0";
    private TimeData timeData = null;
    private String price = "";
    private String remark = "";
    private CouponBottomDialog couponBottomDialog;
    private ArrayList<CouponList> couponLists = new ArrayList<>();
    private int agree = 1;
    private ArrayList<CouponList> temp = new ArrayList<>();
    private String date = "";//选中的日期
    private double redFee = 0;
    private CarFee carFee;
    private double coupon = 0;
    private String lat = "";
    private String lng = "";
    private RoundedImageView iv_teach;
    private TextView tv_teach_name;
    private TechDetail detail;
    private String distance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirmorder);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getNetWorker().order_settle(MyApplication.getInstance().getUser().getToken(), pro_id);
        getNetWorker().my_coupon_available(MyApplication.getInstance().getUser().getToken());
        getNetWorker().tech_detail(technicianId);
        list = getDateList(3);
    }

    private ArrayList<String> getDateList(int day) {
        //创建集合储存日期
        ArrayList<String> dateList = new ArrayList<>();
        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date().getTime());
        //此处是我项目的需求，需要保存当前日期在第一位，你也可以根据自己的需求自行决定
        dateList.add(format);
        for (int i = 0; i < day; i++) {
            // 将当前的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
            Date date = sdf.parse(format, new ParsePosition(0));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // add方法的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天，你可根据自己的需求自行决定,
            //如果项目中需要多次调用，你也可把这个参数，通过方法动态传入
            calendar.add(Calendar.DATE, +1);
            Date date1 = calendar.getTime();
            format = sdf.format(date1);
            dateList.add(format);
        }
        return dateList;
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void setData() {
        if (orderSettle != null) {
            addrId = orderSettle.getAddr_id();
            if (orderSettle.getUsername() != null && !isNull(orderSettle.getMobile()) && !isNull(orderSettle.getAddress())) {
                tv_name.setText(orderSettle.getUsername() + " " + HemaUtil.hide(orderSettle.getMobile(), "1"));
                tv_address.setText(orderSettle.getAddress()+orderSettle.getAddr_detail());
                ll_empty.setVisibility(View.GONE);
                ll_address.setVisibility(View.VISIBLE);
            } else {
                ll_address.setVisibility(View.GONE);
                ll_empty.setVisibility(View.VISIBLE);
            }
        }
        if(isNull(orderSettle.getVip_discount_price())){
         tv_member.setText("0");
        }else{
            tv_member.setText(orderSettle.getVip_discount_price());
        }
        BaseUtil.loadBitmap(orderSettle.getP_image_link(), R.mipmap.order_pic, iv_pro);
        tv_pro_name.setText(orderSettle.getP_name());
        tv_pro_content.setText(orderSettle.getP_desc());
        tv_pro_price.setText("¥" + orderSettle.getP_price());
        tv_pro_time.setText(orderSettle.getP_service_time());
        if ("1".equals(orderSettle.getIs_discount())) {
            tv_total.setText("¥" + orderSettle.getDiscount_price());
        } else {
            tv_total.setText("¥" + orderSettle.getP_price());
        }
        price = orderSettle.getP_price();
        lat = orderSettle.getLatitude();
        lng = orderSettle.getLongitude();
        getNetWorker().car_fee(lng, lat, technicianId);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
            case MyEventBusConfig.PAY_SUCCESS:
            case MyEventBusConfig.PAY_ERROR:
            case MyEventBusConfig.PAY_CANCEL:
                finishAc(mContext);
                break;
            case MyEventBusConfig.PAY_TYPE:
                pay_type = event.getContent();
                setPayType(pay_type);
                break;
            case MyEventBusConfig.ARRIVE_TYPE:
                tripType = event.getContent();
                if ("1".equals(tripType)) {
                    if (carFee.getTotal() != null) {
                        double fee = Double.valueOf(carFee.getTotal());
//                        fee = fee * 2;
                        tv_go.setText("打车,预计车费" + fee + "元");
                    }

                } else if ("2".equals(tripType)) {
                    tv_go.setText("绿色出行");
                }
                calculate();
                break;
            case MyEventBusConfig.TIME_POINT_ONE:
            case MyEventBusConfig.TIME_POINT_TWO:
            case MyEventBusConfig.TIME_POINT_THREE:
                String time = event.getContent();
                date = time;
                if (list != null) {
                    getNetWorker().time_point(MyApplication.getInstance().getUser().getToken(),
                            technicianId, time, "3");
                }
                break;
            case MyEventBusConfig.TIME_POINT:
                timeData = (TimeData) event.getObject();
                timeId = timeData.getTimeId();
                tv_time.setText(timeData.getAdvance());
                if (BaseUtil.isCurrentInTimeScope(20, 0, 8, 0, dateToLong(timeData.getAdvanceTime()))) {
                    ToastUtils.show("当前为夜间服务时间，出行方式只能选择打车，感谢您的配合");
                    tripType = "1";
                    if (carFee.getTotal() != null) {
                        double fee = Double.valueOf(carFee.getTotal());
//                        fee = fee * 2;
                        tv_go.setText("打车,预计车费" + fee + "元");
                    }
                    calculate();
                } else {


                }

                break;
        }
    }

    private void setPayType(String type) {
        switch (type) {
            case "1":
                tv_pay.setText("微信支付");
                break;
            case "2":
                tv_pay.setText("支付宝支付");
                break;
            case "0":
                tv_pay.setText("余额支付");
                break;
            default:

                break;
        }

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case ORDER_PREPAY:
            case ORDER_SETTLE:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case ORDER_PREPAY:
            case ORDER_SETTLE:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case TECHICIAN_DETAIL:
                HemaArrayResult<TechDetail> aResult = (HemaArrayResult<TechDetail>) baseResult;
                detail = aResult.getObjects().get(0);
                BaseUtil.loadCircleBitmap(mContext, detail.getT_image_link(), R.mipmap.head_pic, iv_teach);
                tv_teach_name.setText(detail.getT_name());
                break;
            case COUPON_AVAILABLE:
                HemaArrayResult<CouponList> cResult = (HemaArrayResult<CouponList>) baseResult;
                temp = cResult.getObjects();
                couponBottomDialog = new CouponBottomDialog(mContext, temp);
                break;
            case ORDER_PREPAY:
                if ("0".equals(pay_type)) {
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_SUCCESS));
                    ToastUtils.show("下单成功！");
                    finishAc(mContext);
                } else if ("1".equals(pay_type)) {
                    HemaArrayResult<WeixinTrade> wResult = (HemaArrayResult<WeixinTrade>) baseResult;
                    WeixinTrade wTrade = wResult.getObjects().get(0);
                    if (BaseUtil.isWeixinAvilible(mContext)) {
                        goWeixin(wTrade);
                    } else {
                        ToastUtil.showLongToast(mContext, "请先安装微信！");
                        return;
                    }
                } else {
                    HemaArrayResult<String> wResult = (HemaArrayResult<String>) baseResult;
                    String orderInfo = wResult.getObjects().get(0).toString();
                    new AlipayThread(orderInfo).start();
                }
                break;
            case CAR_FEE:
                HemaArrayResult<CarFee> carResult = (HemaArrayResult<CarFee>) baseResult;
                carFee = carResult.getObjects().get(0);
                distance = carResult.getObjects().get(0).getDistance();
                calculate();
                break;
            case ORDER_SETTLE:
                HemaArrayResult<OrderSettle> oResult = (HemaArrayResult<OrderSettle>) baseResult;
                orderSettle = oResult.getObjects().get(0);
                setData();
                if (orderSettle != null) {
                    if(!isNull(orderSettle.getLatitude())&&!"0".equals(orderSettle.getLatitude())){
                        getNetWorker().car_fee(orderSettle.getLongitude(), orderSettle.getLatitude(), technicianId);
                    }
                }
                calculate();
                break;
            case TIME_POINT_LIST:
                HemaArrayResult<TimePoint> tResult = (HemaArrayResult<TimePoint>) baseResult;
                timePoints = tResult.getObjects();
                if (timeDialog == null) {
                    timeDialog = new BottomTimeDialog(mContext, timePoints);
                    timeDialog.setCancelable(true);
                    timeDialog.setAdpter(timePoints);
                } else {
                    timeDialog.setAdpter(timePoints);
                }
                timeDialog.show();
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case ORDER_SETTLE:

                break;
        }
    }

    @Override
    protected void findView() {
        tv_rule=findViewById(R.id.tv_rule);
        tv_member=findViewById(R.id.tv_member);
        iv_teach = findViewById(R.id.iv_teach);
        tv_teach_name = findViewById(R.id.tv_teach_name);
        ed_dashang = findViewById(R.id.ed_dashang);
        iv_select = findViewById(R.id.iv_select);
        tv_coupon = findViewById(R.id.tv_coupon);
        tv_go = findViewById(R.id.tv_go);
        tv_book = findViewById(R.id.tv_book);
        tv_total = findViewById(R.id.tv_total);
        ed_content = findViewById(R.id.ed_content);
        ll_coupon = findViewById(R.id.ll_coupon);
        tv_time = findViewById(R.id.tv_time);
        tv_pay = findViewById(R.id.tv_pay);
        ll_go = findViewById(R.id.ll_go);
        ll_pay = findViewById(R.id.ll_pay);
        tv_pro_time = findViewById(R.id.tv_pro_time);
        tv_pro_content = findViewById(R.id.tv_pro_content);
        tv_pro_price = findViewById(R.id.tv_pro_price);
        tv_pro_name = findViewById(R.id.tv_pro_name);
        iv_pro = findViewById(R.id.iv_pro);
        tv_address = findViewById(R.id.tv_address);
        tv_name = findViewById(R.id.tv_name);
        ll_address = findViewById(R.id.ll_address);
        ll_empty = findViewById(R.id.ll_empty);
        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        ll_service_time = findViewById(R.id.ll_service_time);
    }

    @Override
    protected void getExras() {
        sid = mIntent.getStringExtra("sid");
        technicianId = mIntent.getStringExtra("technicianId");
        pro_id = mIntent.getStringExtra("id");

    }

    @Override
    protected void setListener() {
        tv_rule.setOnClickListener(this);
        iv_select.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        ll_service_time.setOnClickListener(this);
        tv_title.setText("确认订单");
        ll_empty.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        ll_go.setOnClickListener(this);
        tv_book.setOnClickListener(this);
        ed_dashang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                double total= Double.parseDouble(tv_total.getText().toString());
                if (s.toString().trim() != null && !"".equals(s.toString().trim())) {
                    double c = Double.valueOf(s.toString());
                    redFee = c;
//                    tv_total.setText(String.valueOf(total+c));
                } else {
                    redFee = 0;
//                    tv_total.setText(String.valueOf(total-redFee));
                }
                calculate();

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch (v.getId()) {
            case R.id.tv_rule:

            it = new Intent(mContext, WebViewActivity.class);
            it.putExtra("Title", "预约须知");
            it.putExtra("URL", "https://10zhijian.com/purchase.html");
            startActivity(it);
            changeAc();
            break;
            case R.id.iv_select:
                if (agree == 1) {
                    iv_select.setImageResource(R.mipmap.select_n);
                    agree = 0;
                } else {
                    iv_select.setImageResource(R.mipmap.select_y_green);
                    agree = 1;
                }

                break;
            case R.id.ll_coupon:
                if (couponBottomDialog == null) {
                    couponBottomDialog = new CouponBottomDialog(mContext, temp);
                } else {
                    couponBottomDialog.showDialog(orderSettle.getCoupon_id());
                }
                break;
            case R.id.tv_book:
                remark = ed_content.getText().toString().trim();
                if (isNull(lat)) {
                    ToastUtils.show("请选择服务地址！");
                    return;
                }
                if (isNull(pay_type)) {
                    ToastUtils.show("请选择支付方式");
                    return;
                }
                if (isNull(tripType)) {
                    ToastUtils.show("请选择出行方式");
                    return;
                }
                if (isNull(timeId)) {
                    ToastUtils.show("请选择服务时间");
                    return;
                }
                if (agree == 0) {
                    ToastUtils.show("请阅读并同意退款细则");
                    return;
                }
                PopTipDialog popTipDialog=new PopTipDialog(mContext);
                popTipDialog.setTip("确定支付？");
                popTipDialog.setButtonListener(new PopTipDialog.OnButtonListener() {
                    @Override
                    public void onLeftButtonClick(PopTipDialog dialog) {
                        dialog.cancel();
                    }

                    @Override
                    public void onRightButtonClick(PopTipDialog dialog) {
                        if (carFee.getTotal() != null && tripType.equals("1")) {
                            double fee = Double.valueOf(carFee.getTotal());
                            tv_go.setText("打车,预计车费" + fee + "元");
                            getNetWorker().order_prepay(MyApplication.getInstance().getUser().getToken(), pro_id, sid, technicianId,
                                    pay_type, price, addrId, timeData.getAdvance(),
                                    timeData.getAdvanceTime(), "1", couponId, tripType, "1", timeData.getTimeId(),
                                    remark, String.valueOf(redFee), String.valueOf(fee), distance);
                        } else {
                            getNetWorker().order_prepay(MyApplication.getInstance().getUser().getToken(), pro_id, sid, technicianId,
                                    pay_type, price, addrId, timeData.getAdvance(),
                                    timeData.getAdvanceTime(), "1", couponId, tripType, "1", timeData.getTimeId(),
                                    remark, String.valueOf(redFee), "0", distance);
                        }
                        dialog.cancel();
                    }
                });
                popTipDialog.show();
                break;
            case R.id.ll_go:
                if (isNull(lat) || "0".equals(lat)) {
                    ToastUtils.show("请选择服务地址");
                    return;
                }
                if (timeData != null && BaseUtil.isCurrentInTimeScope(20, 0, 8, 0, dateToLong(timeData.getAdvanceTime()))) {
                    ToastUtils.show("当前所选时间，只支持打车出行哦～");
                    return;
                }
                if (wayOutDialog == null) {
                    wayOutDialog = new BottomWayOutDialog(mContext);
                    wayOutDialog.setCancelable(true);
                }
                wayOutDialog.setType(tripType);
                wayOutDialog.setDistance(Double.valueOf(distance));
                wayOutDialog.show();
                break;
            case R.id.ll_pay:
                if (payDialog == null) {
                    payDialog = new BottomPayDialog(mContext);
                    payDialog.setCancelable(true);
                }
                payDialog.show();
                break;
            case R.id.ll_address:
            case R.id.ll_empty:
                it = new Intent(mContext, AddressListActivity.class);
                it.putExtra("type", "1");
                startActivityForResult(it, REQUEST_LOC);
                break;
            case R.id.ll_service_time:

                if (list != null) {
                    if (isNull(date)) {
                        date = list.get(0);
                    }
                    getNetWorker().time_point(MyApplication.getInstance().getUser().getToken(),
                            technicianId, date, "3");
//                    getNetWorker().time_point(MyApplication.getInstance().getUser().getToken(),
//                            technicianId,list.get(0),"3");
                } else {
                    ToastUtils.show("当前技师暂无可服务时间点提供");
                }
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14  16:09:00"）返回时间戳
     */
    public static long dateToLong(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }


    private void showTime() {
        // 设置CenterDecoration
        final DefaultCenterDecoration decoration = new DefaultCenterDecoration(mContext);
        decoration.setLineColor(Color.TRANSPARENT);
        mTimePicker =
                new TimePicker.Builder(this, TimePicker.TYPE_MIXED_DATE | TimePicker.TYPE_MIXED_TIME,
                        this)
                        // 设置不包含超出的结束时间<=
                        .setContainsEndDate(false)
                        // 设置时间间隔为30分钟
                        .setTimeMinuteOffset(30).setRangDate(1587104819000L, 1609430399000L)
                        .setFormatter(new TimePicker.DefaultFormatter() {
                            @Override
                            public CharSequence format(TimePicker picker, int type, int position, long value) {
                                if (type == TimePicker.TYPE_MIXED_DATE) {
                                    CharSequence text;
                                    int dayOffset = DateUtil.getDayOffset(value, System.currentTimeMillis());
                                    if (dayOffset == 0) {
                                        text = "今天";
                                    } else if (dayOffset == 1) {
                                        text = "明天";
                                    } else { // xx月xx日 星期 x
                                        text = mDateFormat.format(value);
                                    }
                                    return text;
                                }
                                return super.format(picker, type, position, value);
                            }
                        })
                        .create();

        Dialog pickerDialog = mTimePicker.getPickerDialog();
        pickerDialog.setCanceledOnTouchOutside(true);
        mTimePicker.getTopBar().getTitleView().setText("请选择时间");
//        try {
//            Date date = sSimpleDateFormat.parse(mBtnShow.getText().toString());
//            mTimePicker.setSelectedDate(date.getTime());
//        } catch (ParseException e) {
        mTimePicker.setSelectedDate(System.currentTimeMillis());
//            e.printStackTrace();
//        }
        mTimePicker.show();
    }

    @Override
    public void onTimeSelect(TimePicker picker, Date date) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case REQUEST_LOC:
                address = (Address) data.getSerializableExtra("address");
                ll_empty.setVisibility(View.GONE);
                tv_name.setText(address.getUsername() + "  " + address.getMobile());
                tv_address.setText(address.getAddr_detail());
                ll_address.setVisibility(View.VISIBLE);
                addrId = address.getId();
                lng = address.getLongitude();
                lat = address.getLatitude();
                getNetWorker().car_fee(lng, lat, technicianId);

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static ArrayList<String> getSevendate() {
        ArrayList<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        for (int i = 0; i < 3; i++) {
            String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            if (Integer.valueOf(mMonth) < 10) {
                mMonth = "0" + mMonth;
            }
            String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            if (Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (i + 1))) {
                mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (i + 1)));
            }
            if (Integer.valueOf(mDay) < 10) {
                mDay = "0" + mDay;
            }
            String date = mYear + "-" + mMonth + "-" + mDay;

            dates.add(date);
        }
        return dates;
    }

    /**
     * 得到当年当月的最大日期
     **/
    public static int MaxDayFromDay_OF_MONTH(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0                 
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }


    /*支付宝支付*/
    private class AlipayThread extends Thread {
        String orderInfo;
        AlipayHandler alipayHandler;

        public AlipayThread(String orderInfo) {
            this.orderInfo = orderInfo;
            alipayHandler = new AlipayHandler(ConfirmOrderActivity.this);
        }

        @Override
        public void run() {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(ConfirmOrderActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            log_i("result = " + result);
            Message msg = new Message();
            msg.obj = result;
            alipayHandler.sendMessage(msg);
        }
    }

    private class AlipayHandler extends Handler {
        ConfirmOrderActivity activity;

        public AlipayHandler(ConfirmOrderActivity activity) {
            this.activity = activity;
        }

        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            switch (resultStatus) {
                case "9000":
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_SUCCESS));
                    break;
                default:
                    EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.PAY_CANCEL));
                    break;
            }
        }
    }

    public void dealCouponSelected(CouponList selectedInfor) {
        couponBottomDialog.hide();
        couponId = selectedInfor.getId();
        tv_coupon.setText("-¥" + selectedInfor.getC_price());
        double total = Double.valueOf(orderSettle.getP_price());
        coupon = Double.valueOf(selectedInfor.getC_price());
        calculate();
    }

    //计算费用
    private void calculate() {
        double carfee = 0.00;
        double total = 0;
        double p_price = 0;
        double t_distance=0;
        if(!isNull(distance)){
            t_distance=Double.valueOf(distance);
        }
        if(t_distance>5){
            ToastUtils.show("当前服务地点超过5KM，只支持打车出行");
            tripType="1";
            carfee = Double.valueOf(carFee.getTotal());
            tv_go.setText("打车，预计车费" + carFee.getTotal());
            if (!isNull(ed_dashang.getText().toString().trim())) {
                redFee = Double.valueOf(ed_dashang.getText().toString().trim());
            }
            if (orderSettle != null) {
                if ("1".equals(orderSettle.getIs_discount())) {
                    p_price = Double.parseDouble(orderSettle.getDiscount_price());
                } else {
                    p_price = Double.parseDouble(orderSettle.getP_price());
                }
            }
            total = p_price + carfee + redFee - coupon;
            tv_total.setText("¥" + BaseUtil.format2(total));
        }else{
            if (carFee != null && "1".equals(tripType)) {
                carfee = Double.valueOf(carFee.getTotal());
                tv_go.setText("打车，预计车费" + carFee.getTotal());
            } else {
                carfee = 0;
            }
            if (!isNull(ed_dashang.getText().toString().trim())) {
                redFee = Double.valueOf(ed_dashang.getText().toString().trim());
            }
            if (orderSettle != null) {
                if ("1".equals(orderSettle.getIs_discount())) {
                    p_price = Double.parseDouble(orderSettle.getDiscount_price());
                } else {
                    p_price = Double.parseDouble(orderSettle.getP_price());
                }
            }
            total = p_price + carfee + redFee - coupon;
            tv_total.setText("¥" + BaseUtil.format2(total));
        }
    }


    /*微信支付开始*/

    IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    private void goWeixin(WeixinTrade trade) {
//        IWXAPI api = WXAPIFactory.createWXAPI(this, trade.getAppid());
//        //data  根据服务器返回的json数据创建的实体类对象
//        PayReq req = new PayReq(); req.appId = trade.getAppid();
//        req.partnerId = trade.getPartnerid();
//        req.prepayId = trade.getPrepayid();
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = trade.getNoncestr();
//        req.timeStamp = trade.getTimestamp();
//        req.sign = trade.getSign();
//        api.registerApp(trade.getAppid());
//        //发起请求
//        api.sendReq(req);

        msgApi.registerApp(trade.getAppid());
        PayReq request = new PayReq();
        request.appId = trade.getAppid();
        request.partnerId = trade.getPartnerid().trim();
        request.prepayId = trade.getPrepayid();
        request.packageValue = trade.getPackageValue();
        request.nonceStr = trade.getNoncestr();
        request.timeStamp = trade.getTimestamp();
        request.sign = trade.getSign();
        msgApi.sendReq(request);
    }

}
