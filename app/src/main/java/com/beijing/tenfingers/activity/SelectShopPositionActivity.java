package com.beijing.tenfingers.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.PoiSearchAdapter;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.until.LocationUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.ArrayList;

import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomListView;

/**
 * Created by wangyuxia on 2018/7/27.
 * 申请开店 -- 选择地址
 */
public class SelectShopPositionActivity extends BaseActivity implements LocationSource, TextView.OnEditorActionListener,
        AMapLocationListener, AMap.OnMapClickListener, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    private EditText editText;
    private ImageView img_clear_input;
    private TextView tv_button;
    private String keyword;

    private LinearLayout addressLayout = null;//地址
    private XtomListView listView = null;//

    private ImageView img_back;
    private ImageView img_location;
    private LinearLayout layout_content;
    private TextView tv_detailaddress;
    private MapView mapView;
    private AMap aMap;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private GeocodeSearch geocoderSearch;
    private LatLonPoint latLonPoint; //点击地点
    private LocationSource.OnLocationChangedListener mListener;
    private LatLng latlng;
    private String map_title;//省市区县 + 详细地址
    private String address = "";//省市区县
    private String addressDetail = "";//省市区县

    private String lng, lat;
    private Marker marker;
    private String province="";
    private String city="";
    private String distinct="";
    private String cityCode = "";//城市编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_map_selector_addr);
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .reset()
                .statusBarColor(R.color.green_55)
                .statusBarDarkFont(false)
                .init();
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        checkLocation();
    }

    private void checkLocation() {
        boolean flag = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED);
        if (flag) {//判断是否拥有定位权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    3);
        } else {
            init();
        }
    }

    //处理权限申请回调(写在Activity中)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权被允许
                    init();
                } else {
                    // 授权被拒绝
                }
                return;
            }
        }
    }

    private void init() {
        prepareLocation();
        if (aMap == null) {
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
            myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
            myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
            aMap = mapView.getMap();
            aMap.setMyLocationStyle(myLocationStyle);
            registerListener();
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            geocoderSearch = new GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(this);
        }
    }

    private void prepareLocation() {
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        // 设置定位参数
        locationOption.setOnceLocation(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(false);
    }

    /**
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(this);
        aMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    protected void callBeforeDataBack(HemaNetTask hemaNetTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask hemaNetTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask hemaNetTask, HemaBaseResult hemaBaseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask hemaNetTask, int i) {

    }

    @Override
    protected void findView() {
        editText = (EditText) findViewById(R.id.edit_search);
        img_clear_input = (ImageView) findViewById(R.id.img_title_clear);
        tv_button = (TextView) findViewById(R.id.button);
        img_back = (ImageView) findViewById(R.id.title_btn_left);
        mapView = (MapView) findViewById(R.id.bmapView);
        img_location = (ImageView) findViewById(R.id.img_map_location);
        layout_content = (LinearLayout) findViewById(R.id.layout_content);
        tv_detailaddress = (TextView) findViewById(R.id.textview);
        addressLayout = (LinearLayout) findViewById(R.id.address_layout);
        listView = (XtomListView) findViewById(R.id.address);
    }

    @Override
    protected void getExras() {
    }

    @Override
    protected void setListener() {
        img_back.setOnClickListener(this);
        img_location.setOnClickListener(this);
        layout_content.setOnClickListener(this);
        img_clear_input.setOnClickListener(this);
        editText.setOnEditorActionListener(this);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    tv_button.setText("搜索");
                    img_clear_input.setVisibility(View.VISIBLE);
                } else {
                    tv_button.setText("取消");
                    img_clear_input.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tv_button.setText("取消");
        tv_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str = ((TextView) v).getText().toString();
                if ("搜索".equals(str)) {
                    onEditorAction(editText, EditorInfo.IME_ACTION_SEARCH, null);
                } else
                    finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.img_map_location:
                startLocation();
                break;
            case R.id.img_title_clear:
                editText.setText("");
                break;
            case R.id.layout_content:
                Intent intent = new Intent();
                intent.putExtra("lng", lng);
                intent.putExtra("lat", lat);
                intent.putExtra("address", address);
                intent.putExtra("detail", addressDetail);
                intent.putExtra("province", province);
                intent.putExtra("city", city);
                intent.putExtra("distinct", distinct);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (mListener != null && location != null) {
//            mListener.onLocationChanged(location);// 显示系统小蓝点
            float bearing = aMap.getCameraPosition().bearing;
//            aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
            Message msg = mHandler.obtainMessage();
            msg.obj = location;
            msg.what = LocationUtils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
            cityCode = location.getCity();
            latlng = new LatLng(location.getLatitude(), location.getLongitude());
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
            goSearchNearByPoi("", latlng, cityCode);
        }
        deactivate();
    }

    //周边搜索
    public void goSearchNearByPoi(String keyWord, LatLng point, String cityCode) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|" +
                "生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|" +
                "科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施|商务住宅|政府机构及社会团体|" +
                "科教文化服务|公共设施|通行设施", cityCode);
//        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", cityCode);

        // keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
        //共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        if(isNull(keyWord)){
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(point.latitude,
                    point.longitude), 50000000));//设置周边搜索的中心点以及区域
        }
        poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();

        log_d("---" + "cityCode");
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                //开始定位
                case LocationUtils.MSG_LOCATION_START:
                    break;
                // 定位完成
                case LocationUtils.MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation) msg.obj;
                    lng = String.valueOf(loc.getLongitude());
                    lat = String.valueOf(loc.getLatitude());

                    latLonPoint = new LatLonPoint(loc.getLatitude(), loc.getLongitude());

                    latLonPoint = new LatLonPoint(Double.parseDouble(lat), Double.parseDouble(lng));
                    latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                            GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng,
                            15);
                    aMap.moveCamera(update);
                    break;
                //停止定位
                case LocationUtils.MSG_LOCATION_STOP:
                    break;
            }
        }
    };

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        startLocation();
    }

    private void startLocation() {
        locationClient = new AMapLocationClient(getApplicationContext());
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(this);
        // 启动定位
        locationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                RegeocodeAddress ad = result.getRegeocodeAddress();
                StreetNumber streetNumber = ad.getStreetNumber();
                address = ad.getProvince() + ad.getCity() + ad.getDistrict();
                province=ad.getProvince();
                city=ad.getCity();
                distinct=ad.getDistrict();
                addressDetail = BaseUtil.handleResultFilterNull(ad.getTownship())
                        + BaseUtil.handleResultFilterNull(ad.getNeighborhood())
                        + (streetNumber == null ? "" : ad.getStreetNumber().getStreet())
                        + (streetNumber == null ? "" : ad.getStreetNumber().getNumber());
                map_title = isNull(ad.getBuilding()) ? ad.getFormatAddress() : ad.getBuilding();
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                tv_detailaddress.setText(map_title);

                if (marker != null)
                    marker.remove();
                marker = aMap.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title(map_title)
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(BitmapFactory.
                                        decodeResource(getResources(), R.mipmap.img_map_endposition))));

                Message msg = new Message();
                if (handler != null) {
                    handler.sendMessage(msg);
                }

                cityCode = result.getRegeocodeAddress().getCity();
                goSearchNearByPoi("", latlng, cityCode);
            } else {
                XtomToastUtil.showShortToast(mContext, "抱歉，没有找到符合的结果");
            }
        } else if (rCode == 27) {
            XtomToastUtil.showShortToast(mContext, "网络出现问题,请重新检查");
        } else if (rCode == 32) {
            XtomToastUtil.showShortToast(mContext, "应用key值,请重新检查");
        } else {
            XtomToastUtil.showShortToast(mContext, "出现其他类型的问题,请重新检查");
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            tv_detailaddress.setText(map_title);
        }
    };

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onMapClick(LatLng latlng) {
        this.latlng = latlng;
        lng = String.valueOf(latlng.longitude);
        lat = String.valueOf(latlng.latitude);
        latLonPoint = new LatLonPoint(latlng.latitude, latlng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_GO) {
            keyword = v.getText().toString();
            if (isNull(keyword)) {
                return true;
            }

            if (keyword.length() > 0)
                tv_button.setText("搜索");
            else {
                tv_button.setText("取消");
            }
            mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            goSearch();
            return true;
        }
        return false;
    }

    //搜索位置
    public void goSearch() {
        if (latlng == null || isNull(cityCode)) {
            return;
        }
        goSearchNearByPoi(keyword, latlng, cityCode);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        PoiSearchAdapter poiSearchAdapter = null;
        if (i == 1000) {
            if (poiResult != null && poiResult.getPois() != null && poiResult.getPois().size() > 0) {
                ArrayList<PoiItem> data = poiResult.getPois();
                cameraMove(data.get(0).getLatLonPoint().getLatitude(),data.get(0).getLatLonPoint().getLongitude());
                poiSearchAdapter = new PoiSearchAdapter(mContext, data);
                poiSearchAdapter.setMarkFirstPoi(true);


            } else {
                poiSearchAdapter = null;
            }
        } else {
            poiSearchAdapter = null;
        }
        listView.setAdapter(poiSearchAdapter);
        BaseUtil.setListViewHeight(listView, addressLayout, 6);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    /**
     * 移动地图
     * @param lat
     * @param lng
     */
    private void cameraMove(double lat,double lng) {

        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, 18, 0, 0));
        if (marker != null)
            marker.remove();
        marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(map_title)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(BitmapFactory.
                                decodeResource(getResources(), R.mipmap.img_map_endposition))));
        aMap.moveCamera(camera);

    }
}
