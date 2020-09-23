package com.beijing.tenfingers.Base;

/**
 * Created by Torres on 2016/11/8.
 */

public class MyConfig {

    /**
     * 是否打印信息开关
     */
     public static final boolean DEBUG = true;
//     public static final boolean DEBUG = false;

    /**
     * 是否启用友盟统计
     */
    public static final boolean UMENG_ENABLE = false;
    /**
     * 后台服务接口根路径
     */
//  public static final String SYS_ROOT = " https://notify.10zhijian.com/";   //正式服务器
    public static final String SYS_ROOT = "http://182.92.159.115:9001/";
    public static final String SYS_ROOT_V2 = "https://uat.api.chajisong.com/v2/";
    public static final String WEB_ROOT="http://192.168.13.82:8080";

    /**
     * 图片压缩的最大宽度
     */
    public static final int IMAGE_WIDTH = 640;
    /**
     * 图片压缩的最大宽度
     */
    public static final int IMAGE_SIZE = 2080;
    /**
     * 图片压缩的最大高度
     */
    public static final int IMAGE_HEIGHT = 800;
    /**
     * 是否使用MD5加密密码
     */
    public static final boolean USE_MD5 = true;
    /**
     * 图片压缩的失真率
     */
    public static final int IMAGE_QUALITY = 100;
    /**
     * 银联支付环境--"00"生产环境,"01"测试环境
     */
    public static final String UNIONPAY_TESTMODE = "00";
    /**
     * 微信appid
     */
    public static final String APPID_WEIXIN = "wx3f43c7eaec6bb9be";
    /**
     * 加密公钥
     */
    public static final String DATA_KEY = "SiMHGuLosUYGacw";

}
