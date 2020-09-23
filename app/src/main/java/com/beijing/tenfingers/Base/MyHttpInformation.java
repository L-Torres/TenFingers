package com.beijing.tenfingers.Base;

import com.hemaapp.HemaConfig;
import com.hemaapp.hm_FrameWork.HemaHttpInfomation;

/**
 * Created by Torres on 2016/11/22.
 */

public enum MyHttpInformation implements HemaHttpInfomation {
    /**
     * 登录
     */
    CLIENT_LOGIN(HemaConfig.ID_LOGIN, "auth/login", "登录", false),
    // 注意登录接口id必须为HemaConfig.ID_LOGIN

    // 注意第三方登录接口id必须为HemaConfig.ID_THIRDSAVE
    THIRD_SAVE(HemaConfig.ID_THIRDSAVE, "third_save", "第三方登录", false),

    /**
     * 后台服务接口根路径
     */
    SYS_ROOT(0, MyConfig.SYS_ROOT, "后台服务接口根路径", true),
    /**
     * 系统初始化     index.php/Webservice2/Index/init
     */
    INIT(1, "/index.php/webservice/index/init", "系统初始化", false),
    /**
     * 验证用户名是否合法
     */
    CLIENT_VERIFY(2, "v1/client_verify", "验证用户名是否合法", false),
    /**
     * 申请随机验证码
     */
    CODE_GET(3, "sms/send", "申请随机验证码", false),
    /**
     * 验证随机码
     */
    CODE_VERIFY(4, "v1/sms/check", "验证随机码", false),
    /**
     * 用户注册
     */
    CLIENT_ADD(5, "auth/register", "用户注册", false),
    /**
     * 首页-服务项目
     */
    SERVICE_PRODUCT(6, "index/product", "首页-服务项目", false),
    /**
     * 首页-技师
     */
    TECH_NICIAN(7, "index/technician", "首页-技师", false),

    /**
     * 退出登录
     */
    CLIENT_LOGINOUT(8, "auth/logout", "退出登录", false),
    /**
     * 我的数据 需登录
     */
    MY_DATA(9, "my", "我的数据", false),
    /**
     * 店铺
     */
    INDEX_SHOP(10, "index/shop", "店铺", false),
    /**
     * 项目详情
     */
    PRODUCT_DETAIL(11, "index/product/detail", "项目详情", false),
    /**
     * vip数据
     */
    MY_VIP(12, "my/vip", "vip数据", false),
    /**
     * 获取用户个人资料
     */
    CLIENT_GET(13, "my/info", "获取用户个人资料", false),
    /**
     * 消息列表
     */
    NOTIFY(14, "notify", "消息列表", false),
    /**
     * 修改昵称 必须登录
     */
    UPDATE_NICKNAME(15, "v1/my/info/nickname", "修改昵称", false),
    /**
     * 茶友列表 需登录
     */
    MOBILE_SECRET(16, "common/mobile/secret", "茶友列表", false),
    /**
     * 两端通用隐私手机号
     */
    COMMON_PHONE(17, "common/mobile/secret/get", "两端通用隐私手机号", false),
    /**
     * 修改性别 必须登录
     */
    UPDATE_SEX(18, "v1/my/info/sex", "修改性别", false),
    /**
     * 修改手机号 必须登录
     */
    UPDATE_MOBILE(19, "v1/my/info/mobile", "修改手机号", false),
    /**
     * 取消订单
     */
    ORDER_CANCEL(19, "order/cancel", "取消订单", false),
    /**
     * 添加不喜欢原因
     */
    DISLIKE_CREATE(20, "v1/order/unlike/create", "添加不喜欢原因", false),
    /**
     * 申请退款
     */
    ORDER_APPLY(21, "v1/order/apply", "申请退款", false),

    /**
     * 删除订单
     */
    ORDER_DELETE(22, "order/delete", " 删除订单", false),
    /**
     * 修改密码
     */
    CHANGE_PWD(23, "auth/password", "修改密码", false),
    /**
     * 上传图片 评论
     */
    VALUE_PIC(24, "v1/order/comment/image", "上传图片 评论", false),
    /**
     * 问题页面 需要登录
     */
    QUESTION_LIST(25, "v1/my/hobby", "问题页面需要登录", false),

    /**
     * 统一下单需登录
     */
    ORDER_PREPAY(26, "order/prepay", "统一下单需登录", false),
    /**
     * 添加评论
     */
    ORDER_COMMENT_CREATE(27, "order/comment", "添加评论", false),

    /**
     * 获取支付宝交易签名串
     */
    ALIPAY(28, "OnlinePay/Unionpay/alipaysign_get", "获取支付宝交易签名串", false),
    /**
     * 获取银联交易签名串
     */

    UNIONPAY(29, "OnlinePay/Unionpay/unionpay_get.php", "获取银联交易签名串", false),
    /**
     * 收藏删除
     */
    LOVE_REMOVE(30, "v1/love_remove", "收藏删除", false),
    /**
     * 意见反馈
     */
    ADVICE_ADD(31, "v1/advice_add", "意见反馈", false),
    /**
     * 活动-列表数据
     */
    ACTIVITY(32, "v2/activity", "活动-列表数据", false),

    /**
     *商家技师
     */
    SHOP_TEACHER(33, "index/shop/shop_technician", "商家技师", false),
    /**
     * 商铺详情
     */
    SHOP_DETAIL(34, "index/shop/shop_detail", "商铺详情", false),
    /**
     * 到店服务下单
     */
    PREPAY_SHOP(35, "order/prepayToShop", "到店服务下单", false),
    /**
     *
     * 获取商铺相关项目
     */
    SHOP_PRODUCT(36, "index/shop/shop_product", "获取商铺相关项目", false),
    /**
     * 获取微信预支付交易会话标识
     */
    WEIXINPAY(37, "v1/OnlinePay/Weixinpay/weixinpay_get.php", "获取微信预支付交易会话标识",
            false),
    /**
     * 未读通知数
     */
    NOTICE_UNREAD(38, "notify/unread", "未读通知数", false),
    /**
     * 商品列表
     */
    GOODS_LIST(39, "v1/goods_list", "商品列表", false),

    /**
     * 获取购物车列表
     */
    SHOPPING_CART_LIST(40, "v1/shopping_cart_list", "获取购物车列表", false),

    /**
     * 购物车操作
     */
    OPER_SHOPPING_CART(41, "v1/oper_shopping_cart", "购物车操作", false),
    /**
     * 取消收藏
     */
    COLLECT_CANCEL(42, "collect/technician/cancel", "取消收藏", false),
    /**
     * 收藏 需登录
     */
    COLLECT_ADD(43, "v1/index/vidio/collect", "收藏 ", false),
    /**
     * 批量取消收藏
     */
    COLLECT_CANCEL_MANY(44, "v1/my/data/collects/cancel", "批量取消收藏", false),
    /**
     * 点赞操作
     */
    THUMB_OPERATE(45, "technician/trends/operateLike", "取消点赞", false),
    /**
     * 收藏技师
     */
    COLLECT_TECHNICIAN(46, "collect/technician", "收藏技师", false),
    /**
     * 订单查看
     */
    ORDER_GET(47, "order/detail", "订单查看", false),
    /**
     * 足迹列表
     */
    SCAN_LIST(48, "v1/my/data/slots", "足迹列表", false),
    /**
     * 点赞列表
     */
    THUMB_LIST(49, "v1/my/data/thumbs", "点赞列表", false),
    /**
     * 上传头像 必须登录
     */
    UPLOAD_HEADER(50, "v1/my/info/photo", "上传头像", false),

    /**
     * 添加反馈
     */
    FEED_BACK(51, "my/feedback/create", " 添加反馈", false),
    /**
     * 批量取消点赞
     */
    MANY_THUMBS_CANCEL(52, "v1/my/data/thumbs/cancel", "删除收货地址", false),
    /**
     * 个人信息-更新
     */
    MY_INFO_UPDATE(53, "my/info/update", "个人信息-更新", false),
    /**
     * 产品详情信息
     */
    TEA_DETAIL(54, "v1/index/tea/detail", "产品详情信息", false),
    /**
     * 上传图片
     */
    FILE_UPLOAD(55, "common/upload", "上传图片", false),
    /**
     * 收藏列表
     */
    TEACHER_COLLECT(56, "collect/technician/list", "技师收藏列表", false),
    /**
     * 待处理订单
     */
    ORDER_WAIT(57, "technician/order/pending", " 待处理订单", false),
    /**
     * 订单列表
     */
    ORDER_LIST(58, "order", " 订单列表", false),
    /**
     * 订单操作
     */
    ORDER_OPERATE(61, "v1/order_operate", "订单操作", false),
    /**
     * 收货地址列表
     */
    DELIVERY_ADDRESS_LIST(62, "my/address", "收货地址列表", false),
    /**
     * 新增收货地址
     */
    DELIVERY_ADDRESS_SAVE(63, "my/address/create", "新增收货地址", false),
    /**
     * 地址三级联动数据
     */
    DISTRICT_LIST(64, "common/areas", "地址三级联动数据", false),

    /**
     * 订单提交
     */
    ORDER_ADD(65, "v1/order_add", "订单提交", false),

    /**
     * 编辑收货地址
     */
    DELIVERY_ADDRESS_EDIT(66, "my/address/edit", "编辑收货地址", false),
    /**
     * 删除地址
     */
    DELIVERY_ADDRESS_DEL(67, "my/address/delete", "删除地址", false),

    /**
     * 用户/项目评价列表
     */
    COMMENTLIST(68, "commentList", "用户/项目评价列表", false),

    /**
     * 未读消息数
     */
    UNREAD_GET(69, "v1/unread_get", "未读消息数", false),
    /**
     * 提醒操作
     */
    REMIND_OPER(67, "v1/remind_oper", "提醒操作", false),
    /**
     * 根据技师id获取项目
     */
    GET_PRO_TEACH(68, "index/technician/products", "根据技师id获取项目", false),

    /**
     * 根据项目id获取技师
     */
    GET_TEACH_PRO(69, "index/product/technicians", " 根据项目id获取技师", false),
    /**
     * 充值接口
     */
    MY_INVEST(70, "my/invest", "充值接口", false),
    /**
     * 删除
     */
    CART_DEL(71, "v1/shop/delete", "删除 ", false),
    /**
     * 收藏列表
     */
    COLLECTION_LIST(72, "v1/my/data/collects", "收藏列表", false),
    /**
     * 订单数量
     */
    ORDER_COUNT(73, "v1/order_count", "订单数量", false),
    /**
     * 编辑收藏
     */
    DEL_COLLECTION(74, "v1/del_collection", " 编辑收藏", false),

    /**
     * 历史列表
     */
    MY_HISTORY(75, "my/history", "历史列表", false),
    /**
     * 申请退货
     */
    ORDER_OPER(76, "v1/order_oper", "申请退货", false),

    /**
     * 售后列表
     */
    AFTER_SALES_LIST(77, "v1/after_sales_list", "售后列表", false),
    /**
     * 余额支付
     */
    BALANCE_PAY(78, "v1/yuepay", "余额支付", false),
    /**
     * 提交售后信息
     */
    AFTER_SALES_ADD(79, "v1/after_sales_add", "提交售后信息", false),
    /**
     * 售后详情
     */
    AFTER_SALES_GET(80, "v1/after_sales_get", "提交售后信息", false),
    /**
     * 入驻 不同类型入驻时候参数必填，可为空字符串
     */
    MY_JOIN(81, "my/join", "入驻 不同类型入驻时候参数必填，可为空字符串", false),
    /**
     * 聊天对话操作
     */
    DEL_MESSAGE(82, "v1/del_message", "聊天对话操作", false),
    /**
     * 热门词汇
     */
    HOT_WORD(83, "v1/hot_word", "热门词汇", false),
    /**
     * 学习园地详情
     */
    LEARN_GET(84, "v1/learn_get", "学习园地详情", false),

    /**
     * 退款列表
     */
    REFUND_LIST(85, "v1/refund_list", "退款列表", false),
    /**
     * 知识库列表
     */
    WORD_LIST(86, "v1/word_list", "知识库列表", false),
    /**
     * 标签列表
     */
    TAG_LIST(87, "v1/tag_list", "标签列表", false),
    /**
     * 标签保存
     */
    TAG_SAVE(88, "v1/tag_save", "标签保存", false),

    /**
     * 获取未读消息数量
     */
    UNREAD_NOTICE_COUNT_GET(89, "v1/unread_notice_count_get", " 获取未读消息数量", false),

    /**
     * 标签删除
     */
    TAG_DEL(90, "v1/tag_del", "标签删除", false),

    /**
     * 知识库保存
     */
    WORD_SAVE(91, "v1/word_save", " 知识库保存", false),
    /**
     * 通联支付（确认）
     */
    TONGLIAN_PAY(92, "v1/tonglian_pay", " 通联支付（确认）", false),
    /**
     * 设置/修改支付密码
     */
    SET_PAY_PWD(93, "v1/set_pay_password", "设置/修改支付密码", false),
    /**
     * 退款审核详情
     */
    AUDIT_GET(94, "v1/audit_get", "退款审核详情", false),

    /**
     * 退款审核
     */
    AUDIT_SAVE(95, "v1/audit_save", "退款审核", false),
    /**
     * 健康方案
     */
    HEALTH_PLAN_LIST(96, "v1/health_program_list", "健康方案", false),
    /**
     * 活动/资料列表
     */
    ARTICLE_LIST(97, "v1/article_list", "活动/资料列表", false),
    /**
     * 搜索文章
     */
    SEARCH_ATICAL(98, "v1/search_get", "搜索文章", false),
    /**
     * 搜索列表
     */
    SEARCH_AB(99, "v1/search_list", "搜索列表", false),
    /**
     * 排行榜
     */
    RANKING_LIST(100, "v1/ranking_list", " 排行榜", false),
    /**
     * 品牌列表
     */
    BRAND_LIST(101, "common/areas", "品牌列表", false),
    /**
     * 关于我们
     */
    ABOUT_US(102, "my/setting/about", "关于我们", false),

    /**
     * 用户信息
     */
    USER_GET(103, "v1/user_get", "用户信息", false),
    /**
     * 客户标签保存
     */
    CUSTOMER_TAG_SAVE(104, "v1/customer_tag_save", "客户标签保存", false),

    /**
     * 订单删除
     */
    ORDER_DEL(105, "v1/order_del", "订单删除", false),

    /**
     * 标签客户列表
     */
    TAG_CUSTOMERS(106, "v1/tag_customers", "标签客户列表", false),

    /**
     * 消息读取
     */
    NOTIFY_READ(107, "notify/read", "消息读取", false),
    /**
     * 搜索 分类二级页面接口
     */
    SEARCH_LIST(108, "v1/index/data/result", " 搜索 分类二级页面接口", false),

    /**
     * 确认收货
     */
    ORDER_CONFIRM(109, "v1/order/confirm", "确认收货", false),
    /**
     * 添加反馈 需登录
     */
    ADD_FEEDBACK(110, "v1/more/feedback/create", "添加反馈", false),

    /**
     * 营业额统计
     */
    TURN_OVER(111, "v1/turnover", "营业额统计", false),
    /**
     * 客户列表
     */
    CUSTOMER_ALL(112, "v1/customer_all", "客户列表", false),
    /**
     * 订单修改地址
     */
    UPDATE_ORDER_ADDRESS(113, "v1/update_order_address", "订单修改地址", false),

    /**
     * 客服访问时间
     */
    GET_CUSTOMERMSG_TIME(114, "v1/get_customermsg_time", "客服访问时间", false),

    /**
     * 反馈列表 V2 需登录
     */
    All(115, "v2/more/feedback", "反馈列表", false),

    /**
     * 消息删除
     */
    NOTIFY_DELETE(116, "notify/delete", "消息删除", false),

    /**
     * 未读数量
     */
    NOTIFY_UNREAD(117, "notify/unread", "未读数量", false),
    /**
     * 删除反馈
     */
    FEED_BACK_DEL(118, "v1/more/feedback/delete", "删除反馈", false),
    /**
     * 预购茶叶
     */
    ADVANCE_CREATE(119, "v2/wiki/advanced/create", "预购茶叶", false),
    /**
     * 活动-弹出窗口
     */
    ACTIVITY_ALERT(120, "v2/activity/alert", "活动-弹出窗口", false),
    /**
     * 品茶师/评茶师-详情
     */
    ADMIN_DETAIL(121, "v2/index/admin/detail", " 品茶师/评茶师-详情", false),
    /**
     * 预购-预购列表
     */
    WIKI_ADVANCED(122, "v2/wiki/advanced", "预购-预购列表", false),
    /**
     * 产区-产区列表
     */
    PRODUCT_AREA(123, "v2/index/areas", "产区-产区列表", false),

    /**
     * 预购-清空预购
     */
    ADVANCE_DELETE(124, "v2/wiki/advanced/delete", "预购-清空预购", false),
    /**
     * 消息全部读取
     */
    NOTIFY_CLEAR(125, "notify/clear", "消息全部读取", false),

    /**
     * 忘记原密码 重设密码
     */
    RESET_PWD(126, "v1/auth/forget", "忘记原密码 重设密码", false),

    /**
     * 相似茶
     */
    TEA_LIKE(127, "v1/index/tea/like", "相似茶", false),

    /**
     * 充值价格
     */
    CHARGE_PRICE(128, "my/price", "充值价格", false),

    /**
     * 客服-客服详情
     */
    SERVICE_CUSTOMER(129, "v2/csc", "客服-客服详情", false),

    /**
     * 客服-投诉客服
     */
    CUSTOMER_COMPLAIN(130, "v2/csc/complaint", "客服-投诉客服", false),

    /**
     * 全部评论
     */
    COMMENT_LIST(131, "v1/comment", "全部评论", false),


    /**
     * 茶厂数据
     */
    TEA_FACTORY(132, "v2/index/factory", "茶厂数据", false),

    /**
     * 预购-删除
     */
    ADVANCED_INVALID(133, "v2/wiki/advanced/invalid", "预购-删除", false),

    /**
     * 订单-提醒发货
     */
    ORDER_REMIND(134, "v1/order/remind", "订单-提醒发货", false),

    /**
     * 头条-数据
     */
    TOU_TIAO(135, "v2/news", "头条-数据", false),

    /**
     * 商品详情-详情
     */
    URL_DETAIL(136, "v1/index/tea/details", "商品详情-详情", false),

    /**
     * 版本升级接口
     */
    VERSION_COMPARE(137, "common/version", "版本升级接口", false),
    /**
     * 推送信息接口
     */
    PUSH_ID(138,"common/push","推送信息接口",false),
    /**
     * 弹框-优惠券 需登录
     */
    COUPON_ALERT(139,"v2/activity/coupon","弹框-优惠券",false),
    /**
     * 所有优惠券
     */
    COUPON_LIST(140,"my/coupon/list","所有优惠券",false),
    /**
     * 结算页面数据
     */
    ORDER_SETTLE(141,"order/settle","结算页面数据",false),
    /**
     * 优惠券一键领取
     */
    COUPON_GET(142,"my/coupon/receive","优惠券一键领取",false),

    /********新版本接口1.0.4********/
    MY_COUPON(143,"my/coupon/mine","我的优惠券",false),

    /**
     * 技师详情
     */
    TECHICIAN_DETAIL(145,"index/technician/detail","客服-刷新用户token",false),
    /**
     * 获取时间点列表
     */
    TIME_POINT_LIST(6, "technician/servicetime/listPoint", "获取时间点列表", false),

    /**
     * 技师评论
     * */
    TECHICIAN_COMMENTS(147, "index/technician/comments", "技师评论", false),

    /**
     *访客
     */
    VISIT_INIT(147, "v2/visit/init", "访客", false),
    /**
     * 订单整合后列表
     */
    VERSION_2_ORDER_LIST(148,"v2/order","订单整合后列表",false),
    /**
     * 订单-预下单
     */
    VERSION_2_ORDER_PREPAY(149,"order/prepay","订单-预下单",false),
    /**
     * 订单-订单详情
     */
    VERSION_2_ORDER_DETAIL(150,"v2/order/detail","订单-订单详情",false),

    /**
     * 订单-申请退款
     */
    VERSION_2_ORDER_APPLY(151,"order/refund/apply","订单-申请退款",false),
    /**
     * 订单-确认收货
     */
    VERSION_2_ORDER_CONFIRM(152,"v2/order/confirm","订单-确认收货",false),

    /**
     * 订单-取消退款
     */
    VERSION_2_ORDER_REFUND_CANCEL(153,"order/refund/cancel","订单-取消退款",false),
    /**
     * 订单-评价
     */
    VERSION_2_ORDER_COMMENT(154,"v2/order/comment/create","订单-评价",false),
    /**
     * 订单-取消订单
     */
    VERSION_2_ORDER_CANCEL(154,"v2/order/cancel","订单-取消订单",false),
    /**
     * 退款-订单详情
     */
    REFUND_DETAIL(155,"v2/order/detail","退款-订单详情",false),
    /**
     * 配置-获取配置
     * */
    SETTING(156, "v1/setting", "配置-获取配置", false),
    /**
     * 生日修改
     */
    UPDATE_BIRTHDAY(157,"v1/my/info/birthday","生日修改",false),

    /**
     * 订单页面可用优惠券
     */
    COUPON_AVAILABLE(158,"my/coupon/available","订单页面可用优惠券",false),
    /**
     * 预购-收藏列表
     */
    RESERVE_COLLECT(159,"v1/reserve/collects","预购-收藏列表",false),
    /**
     * 历史-删除
     * */
    HISTORY_DELETE(158, "my/history/delete", "历史-删除", false),
    /**
     * 弹窗接口
     * */
    COMMON_POPUP(159, "common/popup", "弹窗接口", false),
    /**
     * 获取金额 距离等
     * */
    CAR_FEE(160, "order/price", "获取金额 距离等", false),
    /**
     * 预购-定金预下单
     * */
    RESERVE_PREPAY(161, "v1/reserve/deposit/prepay", "预购-定金预下单", false),
    /**
     * 预购-尾款预下单
     * */
    RESERVE_REMAIN_REPAY(162, "v1/reserve/remain/prepay", "预购-尾款预下单", false),
    /**
     * 充值
     * */
    AUTH_INVEST(163, "auth/invest", "预购-取消预购", false),
    /**
     * 预购-取消原因
     * */
    RESERVE_REASON(164, "v1/reserve/reason", "预购-取消原因", false),
    /**
     * 图片/视频列表
     */
    MEDIA_LIST(165, "technician/trends/listMedia", "图片/视频列表", false),
    /**
     * 动态列表
     */
    TRENDS_LIST(166, "technician/trends/list", "动态列表", false),

    /**
     * 继续支付
     */
    ORDER_PROCEED(167, "order/proceed", "继续支付", false),
    /**
     * 店铺评价
     */
    SHOP_COMMON(168, "index/shop/shop_comment", "店铺评价", false),


    /**
     * 店铺收藏
     */
    SHOP_COLLECT(169, "index/shop/shop_collect", "店铺收藏", false),


    ;
    private int id;// 对应NetTask的id
    private String urlPath;// 请求地址
    private String description;// 请求描述
    private boolean isRootPath;// 是否是根路径

    private MyHttpInformation(int id, String urlPath, String description,
                              boolean isRootPath) {
        this.id = id;
        this.urlPath = urlPath;
        this.description = description;
        this.isRootPath = isRootPath;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUrlPath() {
//        if (isRootPath)
//            return urlPath;
//
//        String path = SYS_ROOT.urlPath + urlPath;
        String path = "";
//
//        if (this.equals(INIT))
//            return path;
//
//        MyApplication application = MyApplication.getInstance();
//        SysInitInfo info = application.getSysInitInfo();
        path = MyConfig.SYS_ROOT + urlPath;


//        if (this.equals(ALIPAY))
//            path = info.getSys_plugins() + urlPath;
//
//        if (this.equals(UNIONPAY))
//            path = info.getSys_plugins() + urlPath;
//
//        if (this.equals(WEIXINPAY))
//            path = info.getSys_plugins() + urlPath;
//        if (this.equals(TONGLIAN_WITHDRAWAPPLY))
//            path = info.getSys_plugins() + urlPath;

//        if (this.equals(TONGLIAN_PAY))
//            path = info.getSys_plugins() + urlPath;
        return path;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isRootPath() {
        return isRootPath;
    }
}
