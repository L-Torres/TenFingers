package com.beijing.tenfingers.eventbus;

/**
 * 为EventBus提供的类型静态类
 * Created by Torres on 2018/1/3.
 */
public class MyEventBusConfig {
    /**
     * 预购
     */
    public static final int GO_TO_BOOK = 0;
    /**
     * 刷新头像
     */
    public static final int UPDATE_HEAD = 1;
    /**
     * 去加入我们选择页面
     */
    public static final int GO_TO_JOIN = 2;

    /**
     * 去服务列表页面
     */
    public static final int GO_TO_SERVICE = 3;
    /**
     * 更新动态
     */
    public static final int TRENDS_UPDATE = 4;
    /**
     * 刷新订单
     */
    public static final int UPDATE_ORDER = 5;

    /**
     * 启动推送
     */
    public static final int START_PUSH = 6;
    /**
     * 申请退款
     */
    public static final int APPLY_FOR_RETURN = 7;
    /**
     * 消息操作
     */
    public static final int APPLY_REASON = 8;

    /**
     * 标签删除
     */
    public static final int TAG_DEL = 9;
    /**
     * 标签保存
     */
    public static final int TAG_SAVE = 10;
    /**
     * 选择标签
     */
    public static final int TAG_SELECT = 11;
    /**
     * 话术保存
     */
    public static final int WORD_SAVE = 12;
    /**
     * 意见反馈
     */
    public static final int FEED_BACK = 13;
    /**
     * 客户标签
     */
    public static final int CUSTOMER_TAG_SELECTED = 14;
    /**
     * 更新客户资料
     */
    public static final int UP_DATE_CUSTOMER = 15;
    /**
     *聊天更新
     */
    public static final int DIAN_ZAN= 16;
    /**
     * 更新系统消息
     */
    public static final int UPDATE_SYS= 17;
    /**
     * 搜索选中客户
     */
    public static final int SEARCH_CLIENT_GET= 18;

    /**
     * 订单删除
     */
    public static final int ORDER_DEL= 19;
    /**
     * 购物车删除
     */
    public static final int CART_DEL= 20;
    /**
     * 支付成功
     */
    public static final int PAY_SUCCESS= 21;
    /**
     * 选择收货地址
     */
    public static final int  CHARGE= 22;
    /**
     * 新增收货地址
     */
    public static final int ADDRESS_ADD= 23;
    /**
     * 编辑收货地址
     */
    public static final int EDIT_ADDRESS= 24;
    /**
     * 刷新好友
     */
    public static final int CUSTOMER_FRESH= 25;
    /**
     * 发送浏览足迹的商品
     */
    public static final int SEND_SCAN_GOODS= 26;
    /**
     * 刷新客户列表
     */
    public static final int FRESH_CUSTOMER_LIST= 27;
    /**
     * 刷新商品详情页
     */
    public static final int UP_DATE_GOOD_DETAIL= 28;
    /**
     * 刷新订单地址
     */
    public static final int UPDATE_OREDER_ADDRESS= 29;
    /**
     * 立即购买
     */
    public static final int CLIENT_LOGIN= 30;
    /**
     * 加入购物车
     */
    public static final int ADD_CART= 31;
    /**
     * 去聊天
     */
    public static final int GO_CHAT= 32;
    /**
     * 退出全屏
     */
    public static final int QUIT_FULL_SREEN= 33;
    /**
     * 全屏
     */
    public static final int FULL_SCREEN= 34;
    /**
     * 发送数据
     */
    public static final int UP_DATE= 35;
    /**
     * 点赞
     */
    public static final int THUMB= 36;

    /**
     * 收藏
     */
    public static final int COLLECT= 37;
    /**
     * 点赞操作后的处理
     */
    public static final int THUMB_AFTER= 38;
    /**
     * 收藏操作后的处理
     */
    public static final int COLLECT_AFTER= 39;
    /**
     * 批量操作 更新
     */
    public static final int MANY_OPERATE= 40;

    /**
     * 隐藏所爱
     */
    public static final int HIDE_SUOAI= 41;

    /**
     * 提交投诉
     */
    public static final int CUSTOMER_COMPLAIN= 42;

    /**
     * 更新购物车数量
     */
    public static final int UPDATE_CART= 43;

    /**
     * 去客服聊天页面
     */
    public static final int TO_CUSTOMER= 44;

    /**
     * 关闭弹窗
     */
    public static final int CLOSE_DIALOG= 45;
    /**
     * 推送
     */
    public static final int PSUH_SUCCESS= 46;

    /**
     * 优惠券选中
     */
    public static final int COUPON_SELECTED= 47;
    /**
     * 优惠券领取
     */
    public static final int COUPON_GET= 48;
    /**
     * 优惠券首页弹窗
     */
    public static final int COUPON_GET_FIRST= 49;
    /**
     * 开启优惠券弹窗和倒计时弹窗
     */
    public static final int ALERT_OPEN= 50;
    /**
     * 软件升级
     */
    public static final int SOFTE_UPGRADE= 51;
    /**
     *有聊天新消息
     */
    public static final int CHAT_NEWS= 52;
    /**
     * 新的活动
     */
    public static final int NEW_ACTIVITY= 53;
    /**
     * 预购到货
     */
    public static final int NEW_PURCHASE= 54;
    /**
     * 商品选择
     */
    public static final int GOOD_SELECT= 55;
    /**
     * 关闭视频
     */
    public static final int CLOSE_VIDEO= 56;
    /**
     * 取消支付
     */
    public static final int PAY_CANCEL= 57;
    /**
     * 支付异常
     */
    public static final int PAY_ERROR= 58;

    /**
     * 关闭web页面
     */
    public static final int UPDATE_DIALOG= 59;
    /**
     * 支付方式
     */
    public static final int PAY_TYPE= 60;
    /**
     * 出行方式
     */
    public static final int ARRIVE_TYPE= 61;
    /**
     * 预约时间
     */
    public static final int TIME_POINT= 62;
    /**
     * 预约时间1
     */
    public static final int TIME_POINT_ONE= 63;
    /**
     * 预约时间
     */
    public static final int TIME_POINT_TWO= 64;
    /**
     * 预约时间
     */
    public static final int TIME_POINT_THREE= 65;

    /**
     * 更新消息
     */
    public static final int MSG_UPDATE= 66;
    /**
     * 退出登录
     */
    public static final int LOGIN_OUT= 67;
    /**
     * 登录
     */
    public static final int LOGIN_IN= 68;

}
