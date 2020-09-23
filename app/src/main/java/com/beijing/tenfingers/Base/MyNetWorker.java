package com.beijing.tenfingers.Base;

import android.content.Context;
import android.content.pm.PackageManager;

import com.beijing.tenfingers.bean.AboutUs;
import com.beijing.tenfingers.bean.AddressList;
import com.beijing.tenfingers.bean.CarFee;
import com.beijing.tenfingers.bean.CityChildren;
import com.beijing.tenfingers.bean.ClientGet;
import com.beijing.tenfingers.bean.CommonList;
import com.beijing.tenfingers.bean.Count;
import com.beijing.tenfingers.bean.CouponList;
import com.beijing.tenfingers.bean.FileUploadResult;
import com.beijing.tenfingers.bean.GetCode;
import com.beijing.tenfingers.bean.History;
import com.beijing.tenfingers.bean.Hobby;
import com.beijing.tenfingers.bean.MediaList;
import com.beijing.tenfingers.bean.MessageList;
import com.beijing.tenfingers.bean.MyData;
import com.beijing.tenfingers.bean.MyHistory;
import com.beijing.tenfingers.bean.MyVip;
import com.beijing.tenfingers.bean.Order;
import com.beijing.tenfingers.bean.OrderDetail;
import com.beijing.tenfingers.bean.OrderSettle;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.bean.Price;
import com.beijing.tenfingers.bean.Product;
import com.beijing.tenfingers.bean.ProductDetail;
import com.beijing.tenfingers.bean.ProductList;
import com.beijing.tenfingers.bean.SMobile;
import com.beijing.tenfingers.bean.ServiceList;
import com.beijing.tenfingers.bean.ShopDetail;
import com.beijing.tenfingers.bean.ShopList;
import com.beijing.tenfingers.bean.TechDetail;
import com.beijing.tenfingers.bean.Technician;
import com.beijing.tenfingers.bean.Technicians;
import com.beijing.tenfingers.bean.TimePoint;
import com.beijing.tenfingers.bean.TrendsList;
import com.beijing.tenfingers.bean.User;
import com.beijing.tenfingers.bean.ValueList;
import com.beijing.tenfingers.bean.VersionModle;
import com.beijing.tenfingers.bean.WeixinTrade;
import com.beijing.tenfingers.nettask.CommonNetTask;
import com.beijing.tenfingers.nettask.CommonPageNetTask;
import com.beijing.tenfingers.nettask.CurrentTask;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetWorker;
import com.hemaapp.hm_FrameWork.HemaUtil;

import java.util.HashMap;

import xtom.frame.util.XtomSharedPreferencesUtil;


/**
 * 网络请求工具类
 * Created by Torres on 2016/11/8.
 */

public class MyNetWorker extends HemaNetWorker {


    /**
     * 实例化网络请求工具类
     *
     * @param mContext
     */
    private Context mContext;

    public MyNetWorker(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public void clientLogin() {
        MyHttpInformation information = MyHttpInformation.CLIENT_LOGIN;
        HashMap<String, String> params = new HashMap<String, String>();
        String username = XtomSharedPreferencesUtil.get(mContext, "username");
        params.put("mobile", username);// 用户登录名 手机号或邮箱
        String password = XtomSharedPreferencesUtil.get(mContext, "password");
        params.put("password", HemaUtil.getMD5String(password)); // 登陆密码 服务器端存储的是32位的MD5加密串
        MyNetTask task = new CommonNetTask<User>(information, params, User.class);
        executeTask(task);
    }

    @Override
    public boolean thirdSave() {
        if (HemaUtil.isThirdSave(mContext)) {
            MyHttpInformation information = MyHttpInformation.THIRD_SAVE;
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("devicetype", "2"); // 用户登录所用手机类型 1：苹果 2：安卓（方便服务器运维统计）
            String version = HemaUtil.getAppVersionForSever(mContext);
            params.put("lastloginversion", version);// 登陆所用的系统版本号,记录用户的登录版本，方便服务器运维统计
            String thirdtype = XtomSharedPreferencesUtil.get(mContext,
                    "thirdtype");
            params.put("thirdtype", thirdtype);// 平台类型 1：微信 2：QQ 3：微博
            String thirduid = XtomSharedPreferencesUtil.get(mContext,
                    "thirduid");
            params.put("thirduid", thirduid);// 平台用户id 该平台唯一的id
            String avatar = XtomSharedPreferencesUtil.get(mContext, "avatar");
            params.put("avatar", avatar);// 平台用户头像 图片地址
            String nickname = XtomSharedPreferencesUtil.get(mContext,
                    "nickname");
            params.put("nickname", nickname);// 平台用户昵称
            String sex = XtomSharedPreferencesUtil.get(mContext, "sex");
            params.put("sex", sex);// 姓名 "男"或"女"
            String age = XtomSharedPreferencesUtil.get(mContext, "age");
            params.put("age", age);// 年龄

            MyNetTask task = new CommonNetTask<User>(information, params, User.class);
            executeTask(task);

            return true;
        } else {
            return false;
        }
    }
//
    /**
     * 登录
     */
    public void clientLogin(String username, String password) {
        MyHttpInformation information = MyHttpInformation.CLIENT_LOGIN;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", username);// 用户登录名 手机号或邮箱
        params.put("password", password); // 密码 需要md5加密
        MyNetTask task = new CommonNetTask<User>(information, params, User.class);
        executeTask(task);
    }
//
//    /**
//     * 第三方登录
//     */
//    public void thirdSave(String thirdtype, String thirduid, String avatar,
//                          String nickname, String sex, String age) {
//        MyHttpInformation information = MyHttpInformation.THIRD_SAVE;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("devicetype", "2"); // 用户登录所用手机类型 1：苹果 2：安卓（方便服务器运维统计）
//        String version = HemaUtil.getAppVersionForSever(mContext);
//        params.put("lastloginversion", version);// 登陆所用的系统版本号,记录用户的登录版本，方便服务器运维统计
//        params.put("thirdtype", thirdtype);// 平台类型 1：微信 2：QQ 3：微博
//        params.put("thirduid", thirduid);// 平台用户id 该平台唯一的id
//        params.put("avatar", avatar);// 平台用户头像 图片地址
//        params.put("nickname", nickname);// 平台用户昵称
//        params.put("sex", sex);// 姓名 "男"或"女"
//        params.put("age", age);// 年龄
//
//        MyNetTask task = new CommonNetTask<User>(information, params, User.class);
//        executeTask(task);
//    }
//
//    /**
//     * 系统初始化
//     */
//    public void init() {
//        MyHttpInformation information = MyHttpInformation.INIT;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("devicetype", "2");
//        params.put("lastloginversion", HemaUtil.getAppVersionForSever(mContext));// 版本号码(默认：1.0.0)
//        params.put("device_sn", XtomDeviceUuidFactory.get(mContext));// 客户端硬件串号
//        params.put("device_mac", "");
//        MyNetTask task = new CommonNetTask<SysInitInfo>(information, params, SysInitInfo.class);
//        executeTask(task);
//    }
//
//
    /**
     * 首页-服务项目
     */
    public void get_product(String isMore, String page, String rows,String longitude,String latitude,String key,String isVip) {
        MyHttpInformation information = MyHttpInformation.SERVICE_PRODUCT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("isMore", isMore);
        params.put("page", page);
        params.put("rows", rows);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("key", key);
        params.put("isVip", isVip);
        MyNetTask task = new CommonNetTask<Product>(information, params, Product.class);
        executeTask(task);
    }
    /**
     * 首页-服务项目
     */
    public void get_service(String isMore, String page, String rows,String longitude,String latitude,String key,String isVip) {
        MyHttpInformation information = MyHttpInformation.SERVICE_PRODUCT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("isMore", isMore);
        params.put("page", page);
        params.put("rows", rows);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("key", key);
        params.put("isVip", isVip);
        MyNetTask task = new CommonNetTask<ServiceList>(information, params, ServiceList.class);
        executeTask(task);
    }
    /**
     * 首页-技师
     */
    public void get_teacher(String isMore, String page, String rows,String longitude,String latitude,String orderType) {
        MyHttpInformation information = MyHttpInformation.TECH_NICIAN;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("isMore", isMore);
        params.put("page", page);
        params.put("rows", rows);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("orderType", orderType);
        if(BaseUtil.IsLogin()){
            params.put("token", MyApplication.getInstance().getUser().getToken());
        }
        MyNetTask task = new CommonNetTask<Technician>(information, params, Technician.class);
        executeTask(task);
    }
   /**
     * 首页-店铺
     */
    public void get_shops(String isMore, String page, String rows,String longitude,String latitude) {
        MyHttpInformation information = MyHttpInformation.INDEX_SHOP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("isMore", isMore);
        params.put("page", page);
        params.put("rows", rows);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        MyNetTask task = new CommonNetTask<ShopList>(information, params, ShopList.class);
        executeTask(task);
    }

    /**
     *虚拟手机号
     */
    public void mobile_tec(String token,String tid) {
        MyHttpInformation information = MyHttpInformation.MOBILE_SECRET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 用户登录名 手机号或邮箱
        params.put("tid", tid);// 6位随机号码 测试阶段固定向服务器提交“123456”
        MyNetTask task = new CommonNetTask<>(information, params, SMobile.class);
        executeTask(task);
    }
//
    /**
     * 用户注册
     */
    public void clientAdd(String mobile, String password, String again_password, String code, String sign) {
        MyHttpInformation information = MyHttpInformation.CLIENT_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);//手机号
        params.put("password", password);// 验证码
        params.put("again_password", again_password);// 短信类型, 注册传递register
        params.put("code", code);// 推荐人手机号
        params.put("sign", sign);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * PREPAY_SHOP
     */
    public void prepay_shop(String token, String pid,String sid, String payType,String price,String count,String couponId,
                            String serviceType,String redFee,String remark) {
        MyHttpInformation information = MyHttpInformation.PREPAY_SHOP;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token); //
        params.put("pid", pid);
        params.put("sid", sid);
        params.put("payType", payType);
        params.put("price", price);
        params.put("count", count);
        params.put("couponId", couponId);
        params.put("serviceType", serviceType);
        params.put("redFee", redFee);
        params.put("remark", remark);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 短信发送
     */
    public void code_get(String mobile, String type) {
        MyHttpInformation information = MyHttpInformation.CODE_GET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);//
        params.put("type", type);
        MyNetTask task = new CommonNetTask<>(information, params, GetCode.class);
        executeTask(task);
    }
//
    /**
     * 申请退款
     * v112
     */
    public void order_apply(String token, String id,String reason) {
        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_APPLY;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);//
        params.put("id", id);
        params.put("reason", reason);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
    /**
     * 删除订单
     */
    public void order_delete(String token, String id) {
        MyHttpInformation information = MyHttpInformation.ORDER_DELETE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }


    /**
     * 退出登录
     */
    public void clientLoginout(String token) {
        MyHttpInformation information = MyHttpInformation.CLIENT_LOGINOUT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 修改昵称
     */
    public void order_proceed(String token, String id) {
        MyHttpInformation information = MyHttpInformation.ORDER_PROCEED;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("id", id);//
        MyNetTask task = new CommonNetTask<>(information, params,WeixinTrade.class);
        executeTask(task);
    }


    /**
     * 动态点赞/取消点赞
     */
    public void thumb(String type, String trendsId) {
        MyHttpInformation information = MyHttpInformation.THUMB_OPERATE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);//
        params.put("trendsId", trendsId);//
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
//
    /**
     * 个人信息-更新
     */
    public void myinfo_update(String token, String type, String nickname, String sex,
                              String birthday, String path) {
        MyHttpInformation information = MyHttpInformation.MY_INFO_UPDATE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("type", type);// 用户昵称
        params.put("nickname", nickname);
        params.put("sex", sex);
        params.put("birthday", birthday);
        params.put("path", path);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
    /**
     * 用户个人资料
     */
    public void ClientGet(String token) {

        MyHttpInformation information = MyHttpInformation.CLIENT_GET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<ClientGet>(information, params, ClientGet.class);
        executeTask(task);
    }
//
    /**
     * 我的数据 需登录
     */
    public void myData(String token) {

        MyHttpInformation information = MyHttpInformation.MY_DATA;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<MyData>(information, params, MyData.class);
        executeTask(task);
    }

    /**
     * 上传文件（图片，音频，视频）
     */
    public void fileUpload(String token,String temp_file) {
        MyHttpInformation information = MyHttpInformation.FILE_UPLOAD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token); //
        HashMap<String, String> files = new HashMap<String, String>();
        files.put("file", temp_file); //
        MyNetTask task = new CommonNetTask<FileUploadResult>(information, params, files,FileUploadResult.class);
        executeTask(task);
    }



//
//
//    /**
//     * 预告
//     */
//    public void tea_advance(String page, String rows, String key, String order, String is_order) {
//
//        MyHttpInformation information = MyHttpInformation.TEA_ADVANCE;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("page", page);
//        params.put("rows", rows);
//        params.put("key", key);
//        params.put("order", order);
//        params.put("is_order", is_order);
//        MyNetTask task = new CommonNetTask<TeaRecommand>(information, params, TeaRecommand.class);
//        executeTask(task);
//    }
//
//    /**
//     * 意见反馈
//     */
//    public void set_address_default(String token, String addr_id) {
//        MyHttpInformation information = MyHttpInformation.SET_ADDRESS_DEFAULT;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("addr_id", addr_id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//
//    }
//
//    /**
//     * 硬件注册保存
//     */
//    public void add_advice(String token, String content, String contact) {
//        MyHttpInformation information = MyHttpInformation.ADD_FEEDBACK;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);// 登陆令牌
//        params.put("content", content);
//        params.put("contact", contact);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 点赞列表 需登录
//     */
//
//    public void thumbList(String token, String page, String rows) {
//
//        MyHttpInformation information = MyHttpInformation.THUMB_LIST;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("page", page);
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<CommonList>(information, params, CommonList.class);
//        executeTask(task);
//    }
//
//    /**
//     * 足迹列表 需登录
//     */
//
//    public void scanList(String token, String page, String rows) {
//
//        MyHttpInformation information = MyHttpInformation.SCAN_LIST;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("page", page);
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<CommonList>(information, params, CommonList.class);
//        executeTask(task);
//    }
//
//    /**
//     * 收藏列表 需登录
//     */
//
//    public void collectList(String token, String page, String rows) {
//
//        MyHttpInformation information = MyHttpInformation.COLLECTION_LIST;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("page", page);
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<CommonList>(information, params, CommonList.class);
//        executeTask(task);
//    }
//    /**
//     * 预购收藏列表 需登录
//     */
//
//    public void rCollectList(String token, String page, String rows) {
//
//        MyHttpInformation information = MyHttpInformation.RESERVE_COLLECT;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("page", page);
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<AdColListParent>(information, params, AdColListParent.class);
//        executeTask(task);
//    }
    /**
     * 删除收货地址列表
     */
    public void addressDel(String token, String addr_id) {
        MyHttpInformation information = MyHttpInformation.DELIVERY_ADDRESS_DEL;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", addr_id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
////
////
//
    /**
     * 收货地址保存
     */
    public void AddressEdit(String id,String token, String province_id, String city_id,
                            String distinct_id, String addr_detail,
                            String username, String mobile, String is_default,
                            String province, String city, String distinct,
                            String longitude , String latitude   ) {
        MyHttpInformation information = MyHttpInformation.DELIVERY_ADDRESS_EDIT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("province_id", province_id);
        params.put("province", province);
        params.put("city_id", city_id);
        params.put("city", city);
        params.put("distinct_id", distinct_id);
        params.put("distinct", distinct);
        params.put("addr_detail", addr_detail);
        params.put("username", username);
        params.put("mobile", mobile);
        params.put("is_default", is_default);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
    /**
     * 收货地址保存
     */
    public void AddressSave(String token, String province_id, String city_id,
                            String distinct_id, String addr_detail,
                            String username, String mobile, String is_default,
                            String province, String city, String distinct,
                            String longitude , String latitude   ) {
        MyHttpInformation information = MyHttpInformation.DELIVERY_ADDRESS_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("province_id", province_id);
        params.put("province", province);
        params.put("city_id", city_id);
        params.put("city", city);
        params.put("distinct_id", distinct_id);
        params.put("distinct", distinct);
        params.put("addr_detail", addr_detail);
        params.put("username", username);
        params.put("mobile", mobile);
        params.put("is_default", is_default);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
    /**
     * 反馈列表
     */
    public void feed_back(String token, String reason, String advice) {
        MyHttpInformation information = MyHttpInformation.FEED_BACK;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("reason", reason);
        params.put("advice", advice);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 评论列表
     */
    public void commlist(String id, String page,String rows,String type) {
        MyHttpInformation information = MyHttpInformation.COMMENTLIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("page", page);
        params.put("rows", rows);
        params.put("type", type);
        MyNetTask task = new CommonNetTask<>(information, params, ValueList.class);
        executeTask(task);
    }
//
    /**
     * 删除所有消息记录
     */
    public void notify_read(String token,String id) {
        MyHttpInformation information = MyHttpInformation.NOTIFY_READ;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
    /**
     * 消息删除
     */
    public void notify_delete(String token,String id) {
        MyHttpInformation information = MyHttpInformation.NOTIFY_DELETE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 关于我们
     */
    public void about_us() {
        MyHttpInformation information = MyHttpInformation.ABOUT_US;
        HashMap<String, String> params = new HashMap<String, String>();
        MyNetTask task = new CommonNetTask<AboutUs>(information, params, AboutUs.class);
        executeTask(task);
    }
//
    /**
     * 添加评论
     */
    public void order_comment_create(String token, String id, String tid, String pid,
                                     String star, String content, String images,String serviceStar,
                                     String atitudeStar,String techStar) {
        MyHttpInformation information = MyHttpInformation.ORDER_COMMENT_CREATE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        params.put("tid", tid);
        params.put("pid", pid);
        params.put("star", star);
        params.put("content", content);
        params.put("images", images);
        params.put("serviceStar", serviceStar);
        params.put("atitudeStar", atitudeStar);
        params.put("techStar", techStar);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
    /**
     * 未读数量
     */
    public void unread_msg(String token) {
        MyHttpInformation information = MyHttpInformation.NOTICE_UNREAD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<>(information, params, Count.class);
        executeTask(task);
    }
////    /**
////     * 聊天对话置顶取消
////     */
////    public void untopMessage(String token, String customer_id) {
////        MyHttpInformation information = MyHttpInformation.UNTOP_MESSAGE;
////        HashMap<String, String> params = new HashMap<String, String>();
////        params.put("token", token);
////        params.put("customerId", customer_id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////    /**
////     * 客户列表
////     */
////    public void CustomerList(String token, String keyword, String page) {
////        MyHttpInformation information = MyHttpInformation.CUSTOMER_LIST;
////        HashMap<String, String> params = new HashMap<String, String>();
////        params.put("token", token);
////        params.put("keyword", keyword);
////        params.put("page", page);
////        MyNetTask task = new CommonPageNetTask<Customer>(information, params, Customer.class);
////        executeTask(task);
////    }
////    /**
////     * 获取所有城市接口
////     */
////    public void districtAllGet() {
////        MyHttpInformation information = MyHttpInformation.DISTRICT_ALL_GET;
////        HashMap<String, String> params = new HashMap<>();
////        MyNetTask task = new DistrictAllGetTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 备忘列表
////     */
////    public void MemoList(String token, String page, String customer_id) {
////        MyHttpInformation information = MyHttpInformation.MEMO_LIST;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("page", page);
////        params.put("customer_id", customer_id);
////        MyNetTask task = new CommonPageNetTask<Memo>(information, params, Memo.class);
////        executeTask(task);
////    }
//////
////
////    /**
////     * 完善用户信息
////     *
////     * @param token
////     */
////    public void customerSave(String token, String id, String star, String nickname,
////                             String phone, String age) {
////        MyHttpInformation information = MyHttpInformation.CUSTOMER_SAVE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        params.put("star", star);
////        params.put("nickname", nickname);
////        params.put("phone", phone);
////        params.put("age", age);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
//////
////
////    /**
////     * 客户详情
////     */
////    public void CustomerGet(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.CUSTOMER_GET;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        MyNetTask task = new CommonNetTask<Customer>(information, params, Customer.class);
////        executeTask(task);
////    }
////
////    /**
////     * 备忘保存
////     */
////    public void MemoSave(String token, String id, String content, String customer_id) {
////        MyHttpInformation information = MyHttpInformation.MEMO_SAVE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        params.put("content", content);
////        params.put("customer_id", customer_id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 备忘删除
////     */
////    public void memoDel(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.MEMO_DEL;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 提醒列表 1:待提醒=未到期 2：已完成=已处理 0：已过期=到期未处理 全部传空字符传或不传
////     */
////    public void remindList(String token, String keyword, String status, String customer_id, String page) {
////        MyHttpInformation information = MyHttpInformation.REMIND_LIST;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("keyword", keyword);
////        params.put("status",status);
////        params.put("customer_id", customer_id);
////        params.put("page", page);
////        MyNetTask task = new CommonPageNetTask<Remind>(information, params, Remind.class);
////        executeTask(task);
////    }
////
////    /**
////     * 提醒保存
////     */
////    public void remindSave(String token, String content, String id, String customer_id, String time) {
////        MyHttpInformation information = MyHttpInformation.REMIND_SAVE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("content", content);
////        params.put("id", id);
////        params.put("customer_id", customer_id);
////        params.put("time", time);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 提醒操作
////     */
////    public void remindOperate(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.REMIND_OPER;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id",id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 提醒删除
////     */
////    public void remindDel(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.REMIND_DEL;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id",id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 消息操作（调用当前接口把改客户的消息全部置为已读）
////     */
////    public void operateMsg(String token, String customer_id) {
////        MyHttpInformation information = MyHttpInformation.OPER_MESSAGE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("customer_id", customer_id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////    /**
////     * 订单搜索
////     */
////    public void orderSearch(String token, String keyword, String page) {
////        MyHttpInformation information = MyHttpInformation.ORDER_SEARCH;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("keyword", keyword);
////        params.put("page", page);
////        MyNetTask task = new CommonPageNetTask<OrderList>(information, params,OrderList.class);
////        executeTask(task);
////    }
////    /**
////     * 订单提交
////     */
////    public void orderAdd(String token, String goods_id, String customer_id, String count
////            , String address_id, String coupon_id, String from_type, String order_type,
////                         String buy_type, String pay_endtime, String pay_remindtime, String remark) {
////        MyHttpInformation information = MyHttpInformation.ORDER_ADD;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("goods_id", goods_id);
////        params.put("customer_id", customer_id);
////        params.put("count", count);
////        params.put("address_id", address_id);
////        params.put("coupon_id", coupon_id);
////        params.put("from_type", from_type);
////         params.put("order_type", order_type);
////        params.put("buy_type", buy_type);
////        params.put("pay_endtime", pay_endtime);
////        params.put("pay_remindtime", pay_remindtime);
////        params.put("remark", remark);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////    /**
////     * 聊天对话操作
////     */
////    public void delMessage(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.DEL_MESSAGE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////
////    /**
////     * 标签列表 1：客户标签 2：知识库标签
////     */
////    public void tagList(String token, String type, String customer_id) {
////        MyHttpInformation information = MyHttpInformation.TAG_LIST;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("type", type);
////        params.put("customer_id", customer_id);
////        MyNetTask task = new CommonPageNetTask<TagList>(information, params,TagList.class);
////        executeTask(task);
////    }
////    /**
////     *标签保存
////     */
////    public void tagSave(String token, String id, String name, String type) {
////        MyHttpInformation information = MyHttpInformation.TAG_SAVE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        params.put("name", name);
////        params.put("type", type);
////        //MyNetTask task = new CommonNetTask(information, params);
////        MyNetTask task = new CommonNetTask<TagList>(information, params, TagList.class);
////        executeTask(task);
////    }
////
////    /**
////     *指派专员
////     */
////    public void tagDel(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.TAG_DEL;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
//////    /**
//////     * 当前业绩
//////     */
//////    public void nowList(String token) {
//////        MyHttpInformation information = MyHttpInformation.NOW_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        MyNetTask task = new NowListNetTask(information, params);
//////        executeTask(task);
//////    }
//////    /**
//////     * 部门业务业绩排名
//////     */
//////    public void trackTwoList(String token,String month,String week,String keytype,String page) {
//////        MyHttpInformation information = MyHttpInformation.TRACK_TWO_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("month", month);
//////        params.put("week", week);
//////        params.put("keytype", keytype);
//////        params.put("page", page);
//////        MyNetTask task = new TrackTwoListNetTask(information, params);
//////        executeTask(task);
//////    }
//////    /**
//////     * 历史业绩上（业绩排行）
//////     */
//////    public void historyList(String token,String month,String week,String keyid,String keytype) {
//////        MyHttpInformation information = MyHttpInformation.HISTORY_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("month", month);
//////        params.put("week", week);
//////        params.put("keyid", keyid);
//////        params.put("keytype", keytype);
//////        MyNetTask task = new HistoryListNetTask(information, params);
//////        executeTask(task);
//////    }
//////    /**
//////     * 历史业绩下
//////     */
//////    public void historyTwoList(String token,String keytype,String keyid) {
//////        MyHttpInformation information = MyHttpInformation.HISTORY_TWO_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("keytype", keytype);
//////        params.put("keyid", keyid);
//////        MyNetTask task = new HistoryTwoListNetTask(information, params);
//////        executeTask(task);
//////    }
//////
//////    /**
//////     * 销售人员列表（不分页）
//////     */
//////    public void salesList(String token,String keyid) {
//////        MyHttpInformation information = MyHttpInformation.SALES_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("keyid", keyid);
//////        MyNetTask task = new SalesClientsListNetTask(information, params);
//////        executeTask(task);
//////    }
//////    /**
//////     * 业绩排名
//////     */
//////    public void trackGet(String token,String month,String week,String keytype) {
//////        MyHttpInformation information = MyHttpInformation.TRACK_GET;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("month", month);
//////        params.put("week", week);
//////        params.put("keytype", keytype);
//////        MyNetTask task = new TrackGetNetTask(information, params);
//////        executeTask(task);
//////    }
//////    /**
//////     * 我的收藏
//////     */
//////    public void loveList(String token,String page) {
//////        MyHttpInformation information = MyHttpInformation.LOVE_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("page", page);
//////        MyNetTask task = new LoveListNetTask(information, params);
//////        executeTask(task);
//////    }
//////    /**
//////     * 任务列表
//////     */
//////    public void taskList(String token,String keytype,String page) {
//////        MyHttpInformation information = MyHttpInformation.TASK_LIST;
//////        HashMap<String, String> params = new HashMap<>();
//////        params.put("token", token);
//////        params.put("keytype", keytype);
//////        params.put("page", page);
//////        MyNetTask task = new TaskListNetTask(information, params);
//////        executeTask(task);
//////    }
////    /**
////     * 购物车操作
////     */
////    public void operateCart(String token, String customer_id , String type, String goods_id, String count ) {
////        MyHttpInformation information = MyHttpInformation.OPER_SHOPPING_CART;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("customer_id", customer_id);
////        params.put("type", type);
////        params.put("goods_id", goods_id);
////        params.put("count", count);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
//////
////    /**
////     * 管理人员列表(总监端)
////     */
////    public void shoppingCartList(String token, String customer_id) {
////        MyHttpInformation information = MyHttpInformation.SHOPPING_CART_LIST;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("customer_id", customer_id);
////        MyNetTask task = new CommonPageNetTask<CartList>(information, params,CartList.class);
////        executeTask(task);
////    }
////    /**
////     * 商品列表
////     */
////    public void goodList(String keyword, String goods_type, String page) {
////        MyHttpInformation information = MyHttpInformation.GOODS_LIST;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("keyword", keyword);
////        params.put("goods_type", goods_type);
////        params.put("page", page);
////        MyNetTask task = new CommonPageNetTask<GoodList>(information, params,GoodList.class);
////        executeTask(task);
////    }
////    /**
////     * 商品分类
////     */
////    public void goodsType() {
////        MyHttpInformation information = MyHttpInformation.GOODS_TYPE;
////        HashMap<String, String> params = new HashMap<>();
////        MyNetTask task = new CommonPageNetTask<GoodsTypeParent>(information, params,GoodsTypeParent.class);
////        executeTask(task);
////    }
////
////    /**
////     * 系统通知列表
////     */
////    public void noticeList(String token, String page) {
////        MyHttpInformation information = MyHttpInformation.NOTICE_LIST;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("page", page);
////        MyNetTask task = new CommonPageNetTask<NoticeList>(information, params,NoticeList.class);
////        executeTask(task);
////    }
////    /**
////     *商品分类
////     */
////    public void orderLists() {
////        MyHttpInformation information = MyHttpInformation.GOODS_TYPE;
////        HashMap<String, String> params = new HashMap<>();
////        MyNetTask task = new CommonPageNetTask<GoodsTypeParent>(information, params,GoodsTypeParent.class);
////        executeTask(task);
////    }
    /**
     *订单详情
     */
    public void orderGet(String token, String id) {
        MyHttpInformation information = MyHttpInformation.ORDER_GET;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CommonNetTask<OrderDetail>(information, params,OrderDetail.class);
        executeTask(task);
    }
////    /**
////     *知识库保存
////     */
////    public void wordSave(String token, String id, String tag_id, String title, String content) {
////        MyHttpInformation information = MyHttpInformation.WORD_SAVE;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        params.put("tag_id", tag_id);
////        params.put("title", title);
////        params.put("content",content);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////    /**
////     * 订单操作
////     */
////    public void orderOperate(String token, String order_id, String price, String goods_ids, String count, String content,
////                             String imgs) {
////        MyHttpInformation information = MyHttpInformation.ORDER_OPER;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("order_id", order_id);
////        params.put("price", price);
////        params.put("goods_ids", goods_ids);
////        params.put("count", count);
////        params.put("content", content);
////        params.put("imgs", imgs);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
////    /**
////     * 知识库删除
////     */
////    public void wordDel(String token, String id) {
////        MyHttpInformation information = MyHttpInformation.WORD_DEL;
////        HashMap<String, String> params = new HashMap<>();
////        params.put("token", token);
////        params.put("id", id);
////        MyNetTask task = new CurrentTask(information, params);
////        executeTask(task);
////    }
    /**
     * 根据技师id获取项目
     */
    public void get_pro_teach(String tid,String page,String rows) {
        MyHttpInformation information = MyHttpInformation.GET_PRO_TEACH;
        HashMap<String, String> params = new HashMap<>();
        params.put("tid", tid);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<>(information, params,ServiceList.class);
        executeTask(task);
    }

    /**
     * 根据项目id获取技师
     */
    public void get_teach_pro(String pid, String page, String rows,String longitude,String latitude,String is_vip) {
        MyHttpInformation information = MyHttpInformation.GET_TEACH_PRO;
        HashMap<String, String> params = new HashMap<>();
        if(BaseUtil.IsLogin()){
            params.put("token", MyApplication.getInstance().getUser().getToken());
        }
        params.put("pid", pid);
        params.put("page", page);
        params.put("rows", rows);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("isVip", is_vip);
        MyNetTask task = new CommonNetTask<>(information, params,Technician.class);
        executeTask(task);
    }
//
    /**
     * 收藏列表
     */
    public void tech_collect(String token, String page,String rows) {
        MyHttpInformation information = MyHttpInformation.TEACHER_COLLECT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<Hobby>(information, params, Hobby.class);
        executeTask(task);
    }
    /**
     * 收藏店铺
     */
    public void shop_collect(String token, String shopId) {
        MyHttpInformation information = MyHttpInformation.SHOP_COLLECT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("shopId", shopId);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
    /**
     * 统一下单
     */
    public void order_prepay(String token,String pid, String sid, String tid,
                             String payType, String price, String addrId, String advance, String advanceTime,
                             String count,String couponId,String tripType,String serviceType,String timeId,String remark,
                             String redFee,String carFee,String  distance) {
        MyHttpInformation information = MyHttpInformation.ORDER_PREPAY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("pid", pid);
        params.put("sid", sid);
        params.put("tid", tid);
        params.put("payType", payType);
        params.put("price", price);
        params.put("addrId", addrId);
        params.put("advance", advance);
        params.put("advanceTime", advanceTime);
        params.put("count", count);
        params.put("couponId", couponId);
        params.put("tripType", tripType);
        params.put("serviceType", serviceType);
        params.put("timeId", timeId);
        params.put("remark", remark);
        params.put("redFee", redFee);
        params.put("carFee", carFee);
        params.put("distance", distance);
        if("2".equals(payType)){
            MyNetTask task = new CommonNetTask<String>(information, params, String.class);
            executeTask(task);
        }else{
            MyNetTask task = new CommonNetTask<WeixinTrade>(information, params, WeixinTrade.class);
            executeTask(task);

        }

    }
//
    /**
     * 统一下单
     */
    public void order_prepay_immediately(String token, String pid, String sid,String tid,String payType,String price,
                                         String addrId,String advance,String advanceTime,String count,String couponId,
                                         String tripType,String serviceType,String timeId) {
        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_PREPAY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("pid", pid);
        params.put("sid", sid);
        params.put("tid", tid);
        params.put("payType", payType);
        params.put("price", price);
        params.put("addrId", addrId);
        params.put("advance", advance);
        params.put("advanceTime", advanceTime);
        params.put("count", "1");
        params.put("couponId", couponId);
        params.put("tripType", tripType);
        params.put("serviceType", serviceType);
        params.put("timeId", timeId);
        MyNetTask task = new CommonNetTask<WeixinTrade>(information, params, WeixinTrade.class);
        executeTask(task);
    }
//
    /**
     * 充值
     */
    public void auth_charge(String token,String no,String password) {
        MyHttpInformation information = MyHttpInformation.AUTH_INVEST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("no", no);
        params.put("password", password);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 充值接口
     */
    public void my_invest(String id, String type) {
        MyHttpInformation information = MyHttpInformation.MY_INVEST;
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("type", type);
        if("2".equals(type)){
            MyNetTask task = new CommonNetTask<String>(information, params, String.class);
            executeTask(task);
        }else{
            MyNetTask task = new CommonNetTask<WeixinTrade>(information, params, WeixinTrade.class);
            executeTask(task);

        }

    }
//
//
//    /**
//     * .取消退款
//     */
//    public void order_refund_cancel(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.ORDER_REFUND_CANCEL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
    /**
     * 修改密码 String token,
     */
    public void change_pwd( String mobile, String password, String code,String sign) {
        MyHttpInformation information = MyHttpInformation.CHANGE_PWD;
        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("code", code);
        params.put("sign", sign);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
//    /**
//     * 忘记原密码 修改密码
//     */
//    public void reset_pwd(String token, String password, String sign, String password_confirmation, String mobile, String code) {
//        MyHttpInformation information = MyHttpInformation.RESET_PWD;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("password", password);
//        params.put("sign", sign);
//        params.put("password_confirmation", password_confirmation);
//        params.put("mobile", mobile);
//        params.put("code", code);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//
//    /**
//     * 单条删除 需登录
//     */
//    public void notify_del(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.NOTIFY_DEL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 确认收货
//     */
//    public void orderConfirm(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.ORDER_CONFIRM;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
    /**
     * 取消订单
     */
    public void orderCancel(String token, String id) {
        MyHttpInformation information = MyHttpInformation.ORDER_CANCEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
    /**
     * 取消收藏
     */
    public void collect_cancel(String token, String tid) {
        MyHttpInformation information = MyHttpInformation.COLLECT_CANCEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("tid", tid);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 收藏技师
     */
    public void collect_tech(String token, String tid) {
        MyHttpInformation information = MyHttpInformation.COLLECT_TECHNICIAN;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("tid", tid);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
//    /**
//     * 点赞取消
//     */
//    public void thumb_cancel(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.THUMB_CANCEL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 点赞 需登录
//     */
//    public void thumb_operate(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.THUMB_OPERATE;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
    /**
     * 订单详情 需登录
     */
    public void order_detail(String token, String id) {
        MyHttpInformation information = MyHttpInformation.ORDER_GET;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CommonNetTask<OrderDetail>(information, params, OrderDetail.class);
        executeTask(task);
    }

    /**
     * 地址三级联动数据
     */
    public void district_list(String token) {
        MyHttpInformation information = MyHttpInformation.DISTRICT_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<CityChildren>(information, params, CityChildren.class);
        executeTask(task);
    }
//
    /**
     * 收货地址列表
     */
    public void addressList(String token,String page,String rows) {
        MyHttpInformation information = MyHttpInformation.DELIVERY_ADDRESS_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<AddressList>(information, params, AddressList.class);
        executeTask(task);
    }
//
//    /**
//     * 删除
//     */
//    public void cart_del(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.CART_DEL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 购物车数量改变
//     */
//    public void cart_count(String token, String id, String operate) {
//        MyHttpInformation information = MyHttpInformation.CART_COUNT;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        params.put("operate", operate);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 购物车列表
//     */
//    public void cart_list(String token) {
//        MyHttpInformation information = MyHttpInformation.CART_LIST;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        MyNetTask task = new CommonNetTask<CartList>(information, params, CartList.class);
//        executeTask(task);
//    }
//
//    /**
//     * 加入购物车
//     */
//    public void add_cart(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.ADD_CART;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<CartCount>(information, params, CartCount.class);
//        executeTask(task);
//    }
////
//
//    /**
//     * 同款茶
//     */
//    public void tea_same(String id, String tea_no_same, String page, String rows) {
//        MyHttpInformation information = MyHttpInformation.INDEX_TEA_SAME;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("id", id);
//        params.put("tea_no_same", tea_no_same);
//        params.put("page", page);
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<TeaSame>(information, params, TeaSame.class);
//        executeTask(task);
//    }
//
//    /**
//     * 产品详情信息
//     */
//    public void tea_detail(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.TEA_DETAIL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<TeaDetail>(information, params, TeaDetail.class);
//        executeTask(task);
//    }
//
    /**
     * 历史
     */
    public void my_history(String token,String page,String rows) {
        MyHttpInformation information = MyHttpInformation.MY_HISTORY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<History>(information, params, History.class);
        executeTask(task);
    }
//
//
    /**
     * 订单列表
     */
    public void order_list(String token, String page, String rows,
                           String status) {
        MyHttpInformation information = MyHttpInformation.ORDER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        params.put("status", status);
        MyNetTask task = new CommonNetTask<Order>(information, params, Order.class);
        executeTask(task);
    }

    /**
     *入驻 不同类型入驻时候参数必填，可为空字符串
     */
    public void my_join(String s_name, String s_mobile,String source,String s_sex,String s_shop,String s_address
    ,String s_image_link,String s_products) {
        MyHttpInformation information = MyHttpInformation.MY_JOIN;
        HashMap<String, String> params = new HashMap<>();
        params.put("s_name", s_name);
        params.put("s_mobile", s_mobile);
        params.put("source", source);
        params.put("s_sex", s_sex);
        params.put("s_shop", s_shop);
        params.put("s_address", s_address);
        params.put("s_image_link", s_image_link);
        params.put("s_products", s_products );
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 茶友列表
     */
    public void notify_clear(String token) {
        MyHttpInformation information = MyHttpInformation.NOTIFY_CLEAR;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
////
//
    /**
     * 消息列表
     */
    public void notify(String token, String page, String rows) {
        MyHttpInformation information = MyHttpInformation.NOTIFY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<MessageList>(information, params, MessageList.class);
        executeTask(task);
    }
////
//
//    /**
//     * 搜索界面数据
//     */
//    public void index_search() {
//        MyHttpInformation information = MyHttpInformation.INDEX_SEARCH;
//        HashMap<String, String> params = new HashMap<>();
//        MyNetTask task = new CommonNetTask<ClassifyFather>(information, params, ClassifyFather.class);
//        executeTask(task);
//    }
//
    /**
     * 充值价格
     */
    public void charge_price(String token) {
        MyHttpInformation information = MyHttpInformation.CHARGE_PRICE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<Price>(information, params, Price.class);
        executeTask(task);
    }
    /**
     * my/vip
     */
    public void my_vip(String token) {
        MyHttpInformation information = MyHttpInformation.MY_VIP;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<MyVip>(information, params, MyVip.class);
        executeTask(task);
    }
//
//
//    /**
//     * 活动-列表数据
//     *
//     * @param page
//     * @param rows
//     */
//    public void getExercise(String page, String rows, String token) {
//        MyHttpInformation information = MyHttpInformation.ACTIVITY;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("page", page);
//        if (null!= token && !"".equals(token) && !"null".equals(token)) {
//            params.put("token", token);
//        }
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<Exercise>(information, params, Exercise.class);
//        executeTask(task);
//    }
//
//    /**
//     * 百科-数据列表
//     *
//     * @param type_id
//     * @param rows
//     * @param page
//     * @param first
//     */
//    public void getWiki(String type_id, String rows, String page, String first, String is_more) {
//        MyHttpInformation information = MyHttpInformation.GET_WIKI;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("type_id", type_id);
//        params.put("rows", rows);
//        params.put("page", page);
//        params.put("first", first);
//        params.put("is_more", is_more);
//        MyNetTask task = new CommonNetTask<Wiki>(information, params, Wiki.class);
//        executeTask(task);
//    }
//
//    /**
//     * 百科类型
//     *
//     * @param parent_id
//     */
//    public void getWikiType(String parent_id) {
//        MyHttpInformation information = MyHttpInformation.WIKI_TYPE;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("parent_id", parent_id);
//        MyNetTask task = new CommonNetTask<ClassifyChildren>(information, params, ClassifyChildren.class);
//        executeTask(task);
//    }
//
//    /**
//     * 产区数据
//     */
//    public void getAreaDara() {
//        MyHttpInformation information = MyHttpInformation.DATA_AREAS;
//        HashMap<String, String> params = new HashMap<>();
//        MyNetTask task = new CommonNetTask<AreaData>(information, params, AreaData.class);
//        executeTask(task);
//    }
//
//    /**
//     * 删除反馈
//     */
//    public void feed_back_del(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.FEED_BACK_DEL;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 预购茶叶
//     *
//     * @param token
//     * @param type_id 茶分类id
//     * @param name    茶类名
//     * @param needs   需求量
//     * @param remark  备注
//     */
//    public void advance_create(String token, String type_id, String name, String needs, String remark) {
//        MyHttpInformation information = MyHttpInformation.ADVANCE_CREATE;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("type_id", type_id);
//        params.put("name", name);
//        params.put("needs", needs);
//        params.put("remark", remark);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//
//    /**
//     * 活动-弹出窗口
//     *
//     * @param token
//     */
//    public void activity_alert(String token) {
//        MyHttpInformation information = MyHttpInformation.ACTIVITY_ALERT;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        MyNetTask task = new CommonNetTask<PopBean>(information, params, PopBean.class);
//        executeTask(task);
//    }
//
//
//    /**
//     * 品茶师/评茶师-详情
//     *
//     * @param id    评茶师/品茶师id
//     * @param first 是否首次
//     * @param page  页码
//     * @param rows  展示数量
//     * @param type  类型 tea_taster	| [string]  品茶师  ;  tea_valuator	| [string] 评茶师
//     */
//    public void get_teachers(String id, String first, String page, String rows, String type) {
//        MyHttpInformation information = MyHttpInformation.ADMIN_DETAIL;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("id", id);
//        params.put("first", first);
//        params.put("page", page);
//        params.put("rows", rows);
//        params.put("type", type);
//        MyNetTask task = new CommonNetTask<TeaTeacher>(information, params, TeaTeacher.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-预购列表
//     *
//     * @param token
//     * @param page
//     * @param rows
//     */
//    public void wiki_advanced(String token, String page, String rows) {
//        MyHttpInformation information = MyHttpInformation.WIKI_ADVANCED;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("page", page);
//        params.put("rows", rows);
//        MyNetTask task = new CommonNetTask<WikiAdvanced>(information, params, WikiAdvanced.class);
//        executeTask(task);
//    }
//
//    /**
//     * 产区-产区列表
//     *
//     * @param page  页码
//     * @param rows  展示数量
//     * @param first 首次访问 1 else 0
//     * @param id    产区id
//     */
//    public void product_area(String page, String rows, String first, String id) {
//        MyHttpInformation information = MyHttpInformation.PRODUCT_AREA;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("page", page);
//        params.put("rows", rows);
//        params.put("first", first);
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<Wiki>(information, params, Wiki.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-已读预购
//     *
//     * @param token
//     */
//    public void advance_read(String token) {
//        MyHttpInformation information = MyHttpInformation.ADVANCE_READ;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-清空预购
//     *
//     * @param token
//     */
//    public void advance_delete(String token) {
//        MyHttpInformation information = MyHttpInformation.ADVANCE_DELETE;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 相似茶
//     *
//     * @param rows
//     * @param page
//     * @param id   茶id
//     */
//    public void tea_like(String id, String page, String rows) {
//        MyHttpInformation information = MyHttpInformation.TEA_LIKE;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("rows", rows);
//        params.put("page", page);
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<TeaCommandResult>(information, params, TeaCommandResult.class);
//        executeTask(task);
//    }
//
//    /**
//     * 同款茶
//     *
//     * @param id          茶id
//     * @param tea_no_same 茶款号
//     * @param rows
//     * @param page
//     */
//    public void tea_same_list(String id, String tea_no_same, String rows, String page) {
//        MyHttpInformation information = MyHttpInformation.TEA_SAME;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("rows", rows);
//        params.put("page", page);
//        params.put("id", id);
//        params.put("tea_no_same", tea_no_same);
//        MyNetTask task = new CommonNetTask<TeaCommandResult>(information, params, TeaCommandResult.class);
//        executeTask(task);
//    }
//
//    /**
//     * 客服-客服详情
//     *
//     * @param token
//     */
//    public void customer_service(String token) {
//        MyHttpInformation information = MyHttpInformation.SERVICE_CUSTOMER;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        MyNetTask task = new CommonNetTask<CustomerService>(information, params, CustomerService.class);
//        executeTask(task);
//    }
//
//    /**
//     * 客服-投诉客服
//     *
//     * @param token
//     * @param csc_id  客服id
//     * @param content 投诉内容
//     */
//    public void customer_complain(String token, String csc_id, String content) {
//        MyHttpInformation information = MyHttpInformation.CUSTOMER_COMPLAIN;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("csc_id", csc_id);
//        params.put("content", content);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 全部评论
//     *
//     * @param key   类型
//     * @param first 首次访问传入 获取统计数据用
//     * @param page  页码
//     * @param id    茶id
//     * @param rows
//     */
//    public void value_list(String key, String first, String page, String id, String rows, String type) {
//        MyHttpInformation information = MyHttpInformation.COMMENT_LIST;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("key", key);
//        params.put("first", first);
//        params.put("page", page);
//        params.put("id", id);
//        params.put("rows", rows);
//        if(!isNull(type)){
//            params.put("reserve_type", type);
//        }
//
//        MyNetTask task = new CommonNetTask<ValueList>(information, params, ValueList.class);
//        executeTask(task);
//    }
//
//    /**
//     * 茶厂
//     *
//     * @param id
//     */
//    public void tea_factory(String id) {
//        MyHttpInformation information = MyHttpInformation.TEA_FACTORY;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<TeaFactory>(information, params, TeaFactory.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-删除(单条)
//     *
//     * @param id
//     */
//    public void advanced_invalid(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.ADVANCED_INVALID;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//
//    /**
//     * 订单-提醒发货
//     *
//     * @param order_id
//     */
//    public void order_remind(String token, String order_id) {
//        MyHttpInformation information = MyHttpInformation.ORDER_REMIND;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("order_id", order_id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//
//    /**
//     * 头条-数据
//     */
//    public void tou_tiao(String id, String reserve_type) {
//        MyHttpInformation information = MyHttpInformation.TOU_TIAO;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("id", id);
//        if(!isNull(reserve_type)){
//            params.put("reserve_type", reserve_type);
//        }
//        MyNetTask task = new CommonNetTask<Toutiao>(information, params, Toutiao.class);
//        executeTask(task);
//    }
//
//    /**
//     * 商品详情-详情
//     */
//    public void getUrl(String id) {
//        MyHttpInformation information = MyHttpInformation.URL_DETAIL;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("id", id);
//        params.put("type", "1");
//        MyNetTask task = new CommonNetTask<UrlDetail>(information, params, UrlDetail.class);
//        executeTask(task);
//    }
//
    /**
     * 版本检查
     *
     * @param type
     * @param version
     */
    public void checkVersion(String type, String version) {
        MyHttpInformation information = MyHttpInformation.VERSION_COMPARE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        params.put("current", version);
        MyNetTask task = new CommonNetTask<VersionModle>(information, params, VersionModle.class);
        executeTask(task);
    }
//
    /**
     * 推送信息接口
     */
    public void save_push_id(String token, String device_id) {
        MyHttpInformation information = MyHttpInformation.PUSH_ID;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("device_id", device_id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }
//
    /**
     * 项目详情
     */
    public void product_detail(String id) {
        MyHttpInformation information = MyHttpInformation.PRODUCT_DETAIL;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        MyNetTask task = new CommonNetTask<ProductDetail>(information, params, ProductDetail.class);
        executeTask(task);
    }

    /**
     * 优惠券列表 需登录
     */
    public void coupon_list(String token,String page,String rows) {
        MyHttpInformation information = MyHttpInformation.COUPON_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<CouponList>(information, params, CouponList.class);
        executeTask(task);
    }
    /**
     * 我的优惠券
     */
    public void my_coupon_available(String token) {
        MyHttpInformation information = MyHttpInformation.COUPON_AVAILABLE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        MyNetTask task = new CommonNetTask<CouponList>(information, params, CouponList.class);
        executeTask(task);
    }
    /**
     * 我的优惠券
     */
    public void my_coupon(String token,String page,String rows) {
        MyHttpInformation information = MyHttpInformation.MY_COUPON;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<CouponList>(information, params, CouponList.class);
        executeTask(task);
    }
//
    /**
     * 结算页面数据
     */
    public void order_settle(String token, String id) {
        MyHttpInformation information = MyHttpInformation.ORDER_SETTLE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CommonNetTask<OrderSettle>(information, params, OrderSettle.class);
        executeTask(task);
    }
    /**
     * 获取时间点列表
     */
    public void time_point(String token,String technicianId,String serviceDate,String type) {
        MyHttpInformation information = MyHttpInformation.TIME_POINT_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);//
        params.put("technicianId", technicianId);//
        params.put("serviceDate", serviceDate);//
        params.put("type", type);//
        MyNetTask task = new CommonNetTask<TimePoint>(information, params, TimePoint.class);
        executeTask(task);
    }

    /**
     * 优惠券获取
     *
     * @param token
     */
    public void coupon_get(String token,String id) {
        MyHttpInformation information = MyHttpInformation.COUPON_GET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     *
     * 技师详情
     *
     */
    public void tech_detail(String id) {
        MyHttpInformation information = MyHttpInformation.TECHICIAN_DETAIL;
        HashMap<String, String> params = new HashMap<String, String>();
        if(BaseUtil.IsLogin()){
            params.put("token", MyApplication.getInstance().getUser().getToken());
        }
        params.put("id", id);
        MyNetTask task = new CommonNetTask<TechDetail>(information, params, TechDetail.class);
        executeTask(task);
    }
//
    /**
     *
     * 技师评论列表
     *
    */
    public void common_list(String id, String page, String rows) {
        MyHttpInformation information = MyHttpInformation.TECHICIAN_COMMENTS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<CommonList>(information, params, CommonList.class);
        executeTask(task);
    }
    /**
     *
     * 店铺评论列表
     *
     */
    public void shop_common(String id, String page, String rows) {
        MyHttpInformation information = MyHttpInformation.SHOP_COMMON;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("shopId", id);
        params.put("page", page);
        params.put("rows", rows);
        MyNetTask task = new CommonNetTask<CommonList>(information, params, CommonList.class);
        executeTask(task);
    }
    /**
     * 图片/视频列表
     *
     * @param page
     * @param rows
     */
    public void media_list(String page, String rows, String type,String technicianId) {
        MyHttpInformation information = MyHttpInformation.MEDIA_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", rows);
        params.put("type", type);
        params.put("technicianId", technicianId);
        MyNetTask task = new CommonNetTask<MediaList>(information, params, MediaList.class);
        executeTask(task);
    }

    /**
     * 动态列表
     */
    public void trendsList( String page, String rows, String technicianId, String isVip) {
        MyHttpInformation information = MyHttpInformation.TRENDS_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);//手机号
        params.put("page", page);// 验证码
        params.put("rows", rows);// 短信类型, 注册传递register
        params.put("technicianId", technicianId);// 推荐人手机号
        params.put("isVip", isVip);
        MyNetTask task = new CommonNetTask<>(information, params, TrendsList.class);
        executeTask(task);
    }

    /**
     * 历史-删除
     *
     * @param
     */
    public void history_delete(String token,String id) {
        MyHttpInformation information = MyHttpInformation.HISTORY_DELETE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 弹窗接口
     */
    public void pop_common() {
        MyHttpInformation information = MyHttpInformation.COMMON_POPUP;
        HashMap<String, String> params = new HashMap<String, String>();
        MyNetTask task = new CommonNetTask<PopBean>(information, params, PopBean.class);
        executeTask(task);
    }


    /**
     * 获取金额 距离等
     * v112
     */
    public void car_fee(String longitude, String latitude, String tid) {
        MyHttpInformation information = MyHttpInformation.CAR_FEE;
        HashMap<String, String> params = new HashMap<>();
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("tid", tid);
        MyNetTask task = new CommonNetTask<CarFee>(information, params, CarFee.class);
        executeTask(task);
    }


    /**
     * .取消退款
     * v112
     */
    public void order_refund_cancel_two(String token, String id) {
        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_REFUND_CANCEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", id);
        MyNetTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 商铺详情
     * @param shopId
     */
    public void shop_detail(String shopId){
        MyHttpInformation information = MyHttpInformation.SHOP_DETAIL;
        HashMap<String, String> params = new HashMap<>();
        if(BaseUtil.IsLogin()){
            params.put("token", MyApplication.getInstance().getUser().getToken());
        }
        params.put("shopId", shopId);
        MyNetTask task = new CommonNetTask<ShopDetail>(information, params, ShopDetail.class);
        executeTask(task);
    }
    /**
     * 商铺技师
     * @param shopId
     */
    public void shop_teacher(String shopId,String longitude,String latitude){
        MyHttpInformation information = MyHttpInformation.SHOP_TEACHER;
        HashMap<String, String> params = new HashMap<>();
        params.put("shopId", shopId);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        MyNetTask task = new CommonNetTask<Technicians>(information, params, Technicians.class);
        executeTask(task);
    }
    /**
     * 商铺项目
     * @param shopId
     */
    public void shop_product(String shopId){
        MyHttpInformation information = MyHttpInformation.SHOP_PRODUCT;
        HashMap<String, String> params = new HashMap<>();
        params.put("shopId", shopId);
        MyNetTask task = new CommonNetTask<Product>(information, params, Product.class);
        executeTask(task);
    }
//    /**
//     * 订单-评价
//     * v112
//     */
//    public void order_comment_two(String token, String tea_id, String content, String order_id, String item_id, String images,
//                                  String is_anonymous, String is_attitude, String is_quality, String reserve_type, String task_id) {
//        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_COMMENT;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", token);
//        params.put("tea_id", tea_id);
//        params.put("content", content);
//        params.put("order_id", order_id);
//        params.put("item_id", item_id);
//        if ("".equals(images) || images == null) {
//        } else {
//            params.put("images", images);
//        }
//        params.put("is_anonymous", is_anonymous);
//        if (!isNull(is_attitude)) {
//            params.put("is_attitude", is_attitude);
//        }
//        if (!isNull(is_quality)) {
//            params.put("is_quality", is_quality);
//        }
//        if (!isNull(reserve_type)) {
//            params.put("reserve_type", reserve_type);
//        }
//        if (!isNull(task_id)) {
//            params.put("task_id", task_id);
//        }
//
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 取消订单
//     * V112
//     */
//    public void orderCancelTwo(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_CANCEL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 退款订单详情
//     * v112
//     */
//    public void refund_detail(String token, String id, String type) {
//        MyHttpInformation information = MyHttpInformation.REFUND_DETAIL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        params.put("type", type);
//        MyNetTask task = new CommonNetTask<Refund>(information, params, Refund.class);
//        executeTask(task);
//    }
//
//    /**
//     * 官方声明
//     */
//    public void protocalDetail(String type) {
//        MyHttpInformation information = MyHttpInformation.PROTOCAL_DETAIL;
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("type", type);
//        MyNetTask task = new CommonNetTask<ProtocalInfo>(information, params, ProtocalInfo.class);
//        executeTask(task);
//    }
//
//    /**
//     * 访客
//     */
//    public void visit_init() {
//        MyHttpInformation information = MyHttpInformation.VISIT_INIT;
//        HashMap<String, String> params = new HashMap<String, String>();
////        params.put("app_version", app_version);
////        params.put("device_id", device_id);
////        params.put("device_type", "Android");
////        params.put("device_model", device_model);
//        MyNetTask task = new CommonNetTask<VisitInit>(information, params, VisitInit.class);
//        executeTask(task);
//    }
//
//    //为每个接口的 params 增加固定参数
//    public HashMap<String, String> add_total(HashMap<String, String> params) {
//        try {
//            params.put("app_version", XtomBaseUtil.getAppVersionName(MyApplication.getInstance().getApplicationContext()));
//            params.put("device_id", BaseUtil.getDeviceId(MyApplication.getInstance().getApplicationContext()));
//            params.put("device_type", "Android");
//            String device_model = android.os.Build.BRAND + android.os.Build.MODEL;
//            params.put("device_model", device_model);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return params;
//    }
//
//    /**
//     * 订单列表
//     * v112
//     */
//    public void order_list_two(String token, String page, String rows,
//                               String status, String is_reserve) {
//        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_LIST;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("page", page);
//        params.put("rows", rows);
//        params.put("status", status);
//        if(!isNull(is_reserve)){
//            params.put("is_reserve", is_reserve);
//        }
//        MyNetTask task = new CommonNetTask<Order>(information, params, Order.class);
//        executeTask(task);
//    }
//
//    /**
//     * 订单-预下单
//     * v112
//     */
//    public void order_prepay_two(String token, String o_username, String o_mobile,
//                                 String o_addr_id, String trade_type, String type, String data, String o_remark,
//                                 String order_id) {
//        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_PREPAY;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("o_username", o_username);
//        params.put("o_mobile", o_mobile);
//        params.put("o_addr_id", o_addr_id);
//        params.put("trade_type", trade_type);
//        params.put("type", type);
//        params.put("data", data);
//        params.put("o_remark", o_remark);
//        params.put("order_id", order_id);
//        MyNetTask task = new CommonNetTask<WeixinTrade>(information, params, WeixinTrade.class);
//        executeTask(task);
//    }
//
//
//    /**
//     * 订单详情 需登录
//     * v112
//     */
//    public void order_detail_two(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_DETAIL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<OrderDetail>(information, params, OrderDetail.class);
//        executeTask(task);
//    }
//
//    /**
//     * 确认收货
//     * v112
//     */
//    public void orderConfirmTwo(String token, String id, String task_id, String reserve_type) {
//        MyHttpInformation information = MyHttpInformation.VERSION_2_ORDER_CONFIRM;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        if(!isNull(task_id)){
//            params.put("task_id", task_id);
//        }
//        if(!isNull(reserve_type)){
//            params.put("reserve_type", reserve_type);
//        }
//
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//
//    /**
//     * 配置-获取配置
//     */
//    public void setting(int type) {
//        MyHttpInformation information = MyHttpInformation.SETTING;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("type", String.valueOf(type));
//
//        MyNetTask task = new CommonNetTask<SettingInfor>(information, params, SettingInfor.class);
//        executeTask(task);
//    }
//
//
//
//    /**
//     * 修改生日
//     */
//    public void update_bir(String token, String birthday) {
//        MyHttpInformation information = MyHttpInformation.UPDATE_BIRTHDAY;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("birthday", birthday);
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
//    /**
//     * 预购-详情
//     */
//    public void reserve_detail(String token, String id) {
//        MyHttpInformation information = MyHttpInformation.RESERVE_DETAIL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        MyNetTask task = new CommonNetTask<AdvanceDetail>(information, params, AdvanceDetail.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-预购列表
//     */
//    public void reserve(int page, int row) {
//        MyHttpInformation information = MyHttpInformation.GOODS_ADVANCE;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("rows", String.valueOf(row));
//
//        MyNetTask task = new CommonNetTask<TeaAdvanceRecommand>(information, params, TeaAdvanceRecommand.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-搜索
//     * */
//    public void reserve_search(int page, String row, String key){
//        MyHttpInformation information = MyHttpInformation.RESERVE_SEARCH;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("rows", row);
//        params.put("key", key);
//
//        MyNetTask task = new CommonNetTask<TeaAdvanceRecommand>(information, params, TeaAdvanceRecommand.class);
//        executeTask(task);
//    }
//
//    /**
//     * 某分类下的全部预购商品
//     * */
//    public void getReserveWiki(String page, String rows, String type_id, String is_more){
//        MyHttpInformation information = MyHttpInformation.RESERVE_WIKI;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("rows", rows);
//        params.put("type_id", type_id);
//        params.put("is_more", is_more);
//
//        MyNetTask task = new CommonNetTask<TeaAdvanceRecommand>(information, params, TeaAdvanceRecommand.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-百科页面数据
//     * */
//    public void getReserveWiki_1(String page, String rows, String type_id, String is_more){
//        MyHttpInformation information = MyHttpInformation.RESERVE_WIKI;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("rows", rows);
//        params.put("type_id", type_id);
//        params.put("is_more", is_more);
//
//        MyNetTask task = new CommonNetTask<TeaAdvance>(information, params, TeaAdvance.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-定金预下单
//     * */
//    public void reservePrepay(String token, String data, String o_username, String o_mobile, String o_addr_id,
//                              String trade_type, String type, String reserve_type, String order_id, String item_id,
//                              String reserve_task_id, String o_remark){
//        MyHttpInformation information = MyHttpInformation.RESERVE_PREPAY;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        if(!isNull(data)){
//            params.put("data", data);
//        }
//        if(!isNull(o_username)){
//            params.put("o_username", o_username);
//        }
//        if(!isNull(o_mobile)){
//            params.put("o_mobile", o_mobile);
//        }
//        if(!isNull(o_addr_id)){
//            params.put("o_addr_id", o_addr_id);
//        }
//        if(!isNull(trade_type)){
//            params.put("trade_type", trade_type);
//        }
//        if(!isNull(type)){
//            params.put("type", type);
//        }
//        if(!isNull(reserve_type)){
//            params.put("reserve_type", reserve_type);
//        }
//        if(!isNull(order_id)){
//            params.put("order_id", order_id);
//        }
//        if(!isNull(item_id)){
//            params.put("item_id", item_id);
//        }
//        if(!isNull(reserve_task_id)){
//            params.put("reserve_task_id", reserve_task_id);
//        }
//        if(!isNull(o_remark)){
//            params.put("o_remark", o_remark);
//        }
//
//        MyNetTask task = new CommonNetTask<WeixinTrade>(information, params, WeixinTrade.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-尾款预下单
//     * type:1、直接支付，2、购物车，3、继续支付
//     * */
//    public void reserveRemainRepay(String token, String data, String o_username, String o_mobile, String o_addr_id,
//                                   String trade_type, String type, String reserve_type, String order_id, String item_id,
//                                   String reserve_task_id){
//        MyHttpInformation information = MyHttpInformation.RESERVE_REMAIN_REPAY;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        if(!isNull(data)){
//            params.put("data", data);
//        }
//        if(!isNull(o_username)){
//            params.put("o_username", o_username);
//        }
//        if(!isNull(o_mobile)){
//            params.put("o_mobile", o_mobile);
//        }
//        if(!isNull(o_addr_id)){
//            params.put("o_addr_id", o_addr_id);
//        }
//        if(!isNull(trade_type)){
//            params.put("trade_type", trade_type);
//        }
//        if(!isNull(type)){
//            params.put("type", type);
//        }
//        if(!isNull(reserve_type)){
//            params.put("reserve_type", reserve_type);
//        }
//        if(!isNull(order_id)){
//            params.put("order_id", order_id);
//        }
//        if(!isNull(item_id)){
//            params.put("item_id", item_id);
//        }
//        if(!isNull(reserve_task_id)){
//            params.put("reserve_task_id", reserve_task_id);
//        }
//
//        MyNetTask task = new CommonNetTask<WeixinTrade>(information, params, WeixinTrade.class);
//        executeTask(task);
//    }
//
//    /**
//     * 预购-取消预购
//     * */
//    public void reserveCancel(String token, String id, String task_id, String reason){
//        MyHttpInformation information = MyHttpInformation.RESERVE_CANCEL;
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
//        params.put("id", id);
//        params.put("task_id", task_id);
//        params.put("reason", reason);
//
//        MyNetTask task = new CurrentTask(information, params);
//        executeTask(task);
//    }
    /**
     * 两端通用隐私手机号
     *
     * */
    public void common_phone(String mobileA,String mobileB){
        MyHttpInformation information = MyHttpInformation.COMMON_PHONE  ;
        HashMap<String, String> params = new HashMap<>();
        params.put("mobileA", mobileA);
        params.put("mobileB", mobileB);
        MyNetTask task = new CommonNetTask<SMobile>(information, params,SMobile.class);
        executeTask(task);
    }
}
