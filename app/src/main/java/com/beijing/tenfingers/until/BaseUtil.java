/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-27 上午11:29:09
 */
package com.beijing.tenfingers.until;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.activity.LoginActivity;
import com.beijing.tenfingers.bean.PopBean;
import com.beijing.tenfingers.view.PopVipDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomSharedPreferencesUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * 基础工具类
 */
public class BaseUtil {


   public static Bitmap bitmap=null;

    /**
     * 根据 图片URL 转换成 Bitmap
     * @param url
     * @return
     */
    public static Bitmap returnBitMap(final String url, final android.os.Handler handler){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                    Message message = handler.obtainMessage();
                    message.obj = bitmap;
                    message.arg1 = 1;
                    handler.sendMessage(message);

                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }


    public static String toHour(String time) {
        int temp = 0;
        if(time!=null && ! "".equals(time) ){
            if(!time.contains("h")){
                temp= Integer.valueOf(time);
            }else{
                return  time;
            }

        }

        int hours = (int) Math.floor(temp / 60);
        int minute = temp % 60;
        System.out.println(hours + "h" + minute + "min");
        return hours + "h" + minute + "min";
    }
    /**
     * 返回是不是会员标识
     * @param context
     * @return
     */

    public static String getVip(Context context){
        if(IsLogin()){

            String  huiyuan="";
            huiyuan=XtomSharedPreferencesUtil.get(context,"huiyuan");
            return huiyuan;
        }else{
            toLogin(context,"1");
        }
        return "0";
    }
    /**
     * 弹出VIP充值提示框
     * @param context
     */
    public static void showVip(Context context){
        PopVipDialog    vipDialog=new PopVipDialog(context);
        vipDialog.show();
    }
    private static SharedPreferences sp;

    /**
     * 存储弹窗返回的
     */
    public static void putArray(Context ctx, List<PopBean> adAlerts) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(adAlerts);
        editor.putString("AD", json);
        editor.commit();
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     * <p>
     * beginHour 开始小时,例如22
     * beginMin  开始小时的分钟数,例如30
     * endHour   结束小时,例如 8
     * endMin    结束小时的分钟数,例如0
     * true表示在范围内, 否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin,long select) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;

        final long temp = System.currentTimeMillis();
        final long currentTimeMillis=select;
        Time now = new Time();
        now.set(currentTimeMillis);
        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;
        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;
        // 跨天的特殊情况(比如22:00-8:00)
        if (!startTime.before(endTime)) {
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
            //普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        return result;
    }



    /**
     * 去登录页面
     * purchase字段主要处理登录后是否需要停留在当前页面
     * 1 登录后停留在之前页面，空表示进入MainActivity
     */
    public static void toLogin(Context mContext, String purchase) {
            Intent it = new Intent(mContext, LoginActivity.class);
            it.putExtra("purchase", "1");
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(it);
    }

    /**
     * 验证是否登录
     **/
    public static boolean IsLogin() {
        boolean result = !(MyApplication.getInstance() == null
                || MyApplication.getInstance().getUser() == null
                || MyApplication.getInstance().getUser().getToken() == null);
        return result;
    }
    /**
     * 过滤 null
     *
     * @param result
     * @return
     */
    public static String handleResultFilterNull(String result) {
        if (TextUtils.isEmpty(result) || result == null || "null".equals(result)) {
            return "";
        }
        return result;
    }
    public static String getLat(Context context){
        String lat=XtomSharedPreferencesUtil.get(context,"lat");
        return lat;
    }
    public static String getLng(Context context){
        return XtomSharedPreferencesUtil.get(context,"lng");
    }

    /**
     * 使用glide加载图片
     *
     * @param url
     * @param defaultImage
     * @param imageView
     */
    public static void loadBitmap(String url, int defaultImage, ImageView imageView) {
//        if (url == null || url.equals("")) {
//            imageView.setImageResource(defaultImage);
//            return;
//        }
        Glide.with(MyApplication.getInstance())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(defaultImage)
                .into(imageView);
    }
    /**
     * 使用glide加载图片
     * 增加一个布尔类型参数 用来优化列表中加载数据的流畅度问题
     *
     * @param url
     * @param defaultImage
     * @param imageView
     * @param flag
     */
    public static void loadBitmap(Context context, String url, int defaultImage, ImageView imageView, boolean flag) {
//        if (context == null) {
//            return;
//        }
//        if (!isValidContextForGlide(context)) {
//            imageView.setImageResource(defaultImage);
//        }
//        if (url == null || url.equals("")) {
//            imageView.setImageResource(defaultImage);
//            return;
//        }
        if (flag) {
            Glide.with(context).resumeRequests();
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .placeholder(defaultImage)
                    .into(imageView);
        } else {
            Glide.with(context).pauseRequests();
        }
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param defaultImage
     * @param imageView
     */
    public static void loadCircleBitmap(Context context, String url, int defaultImage, ImageView imageView) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return;
            }
        }
        if (!isValidContextForGlide(context)) {
            imageView.setImageResource(defaultImage);
        }
        if (url == null || url.equals("")) {
            Glide.with(context).load("").circleCrop().placeholder(defaultImage).into(imageView);
            return;
        }
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .circleCrop()
                .placeholder(defaultImage)
                .into(imageView);
    }
    public static BitmapFactory.Options getBitmapOptions(String var0) {
        BitmapFactory.Options var1 = new BitmapFactory.Options();
        var1.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(var0, var1);
        return var1;
    }

    public static boolean isValidContextForGlide(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }
    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }




    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String time) throws ParseException{
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    /*
     * 获取时间戳
     * 将时间转换为时间戳
     */
    public static String dateToStamp() throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String res= simpleDateFormat.format(curDate);
        Date date = simpleDateFormat.parse(res);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    /**
     * 获取设备id
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei1 = (String) method.invoke(manager, 0);
            String imei2 = (String) method.invoke(manager, 1);
            if (TextUtils.isEmpty(imei2)) {
                return imei1;
            }
            if (!TextUtils.isEmpty(imei1)) {
                //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                String imei = "";
                if (imei1.compareTo(imei2) <= 0) {
                    imei = imei1;
                } else {
                    imei = imei2;
                }
                return imei;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return manager.getDeviceId();
        }
        return "";

    }




    public static String getScaledImage(Context context, String path) {
        File var2 = new File(path);
        if (!var2.exists()) {
            return path;
        } else {
            long length = var2.length();
            if (length <= 102400L) {
                return path;
            } else {
                BitmapFactory.Options option = getBitmapOptions(path);
                int h = option.outHeight;
                int w = option.outWidth;

                int r = h / w;
                int inSampleSize = 0;
                if (r > 3 && h > 2000) {
                    if (length <= 204800L) {
                        return path;
                    }
                    inSampleSize = Math.round((float) length / 204800);
                }

                if (inSampleSize == 0) {
                    inSampleSize = calculateInSampleSize(option, 1080, 1920);
                }

                option.inSampleSize = inSampleSize;
                option.inJustDecodeBounds = false;
                Bitmap newbmp = BitmapFactory.decodeFile(path, option);
                int degree = readPictureDegree(path);
                Bitmap var7 = null;
                if (newbmp != null && degree != 0) {
                    var7 = rotaingImageView(degree, newbmp);
                    newbmp.recycle();
                    newbmp = null;
                    return saveScaledImage(context, var7, path);
                } else {
                    return saveScaledImage(context, newbmp, path);
                }
            }
        }
    }

    public static String saveScaledImage(Context var0, Bitmap newbmp, String oldpath) {
        try {
            File file = File.createTempFile("image", ".jpg", var0.getFilesDir());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            newbmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (Exception var8) {
            var8.printStackTrace();
            return oldpath;
        }
    }

    public static Bitmap rotaingImageView(int var0, Bitmap var1) {
        Matrix var2 = new Matrix();
        var2.postRotate((float) var0);
        Bitmap var3 = Bitmap.createBitmap(var1, 0, 0, var1.getWidth(), var1.getHeight(), var2, true);
        return var3;
    }


    public static int calculateInSampleSize(BitmapFactory.Options var0, int var1, int var2) {
        int var3 = var0.outHeight;
        int var4 = var0.outWidth;
        int var5 = 1;
        if (var3 > var2 || var4 > var1) {
            int var6 = Math.round((float) var3 / (float) var2);
            int var7 = Math.round((float) var4 / (float) var1);
            var5 = var6 < var7 ? var6 : var7;
        }

        return var5;
    }

    public static int readPictureDegree(String var0) {
        short var1 = 0;
        try {
            ExifInterface var2 = new ExifInterface(var0);
            int var3 = var2.getAttributeInt("Orientation", 1);
            switch (var3) {
                case 3:
                    var1 = 180;
                    break;
                case 6:
                    var1 = 90;
                    break;
                case 8:
                    var1 = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return var1;
    }
    /**
     * 相差几天
     * @param startTime
     * @param endTime
     * @param format
     * @return
     */
    public static long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day >= 1) {
                return day;
            } else {
                if (day == 0) {
                    return 0;
                } else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 时间格式转换
     */
    public static String formatTime(String time, String style) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdFormat.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
//	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	String temp=sdf.format(inputFormat);
//	return temp;
    }

    /**
     * 判断是否是全面屏
     */
    private volatile static boolean mHasCheckAllScreen;
    private volatile static boolean mIsAllScreenDevice;

    public static boolean isAllScreenDevice(Context context) {
        if (mHasCheckAllScreen) {
            return mIsAllScreenDevice;
        }
        mHasCheckAllScreen = true;
        mIsAllScreenDevice = false;
        // 低于 API 21的，都不会是全面屏。。。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                mIsAllScreenDevice = true;
            }
        }
        return mIsAllScreenDevice;
    }

    /**
     * 判断全面屏是否启用虚拟键盘
     */

    private static final String NAVIGATION = "navigationBarBackground";

    public static boolean isNavigationBarExist(@NonNull Activity activity) {
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();

                if (vp.getChildAt(i).getId() != -1 && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 保留两位小数
     *
     * @param values
     * @return
     */
    public static double format2(double values) {
        return new BigDecimal(Double.toString(values)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     *
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeight(View view, boolean isHeight) {
        int result;
        if (view == null)
            return 0;
        if (isHeight) {
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(h, 0);
            result = view.getMeasuredHeight();
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(0, w);
            result = view.getMeasuredWidth();
        }
        return result;
    }

    /**
     * 初始化垂直布局的recycleview
     *
     * @param mContext
     * @param xrecylerview
     */
    public static void initXRecyleVertical(Context mContext, XRecyclerView xrecylerview) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecylerview.setLayoutManager(layoutManager);
    }

    /**
     * 初始化垂直布局的recycleview
     *
     * @param mContext
     * @param xrecylerview
     */
    public static void initRecyleVertical(Context mContext, RecyclerView xrecylerview) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);

        layoutManager.setAutoMeasureEnabled(true);
        xrecylerview.setHasFixedSize(true);
        xrecylerview.setNestedScrollingEnabled(false);
        xrecylerview.setLayoutManager(layoutManager);
    }

    /**
     * 退出软件
     *
     * @param context
     */
    public static void exit(Context context) {
        XtomActivityManager.finishAll();
    }

    private static final String TAG = "BaseUtil";
    public static final String TIME_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String TIME_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 使用sha-1方式进行加密
     *
     * @return
     */
    public static String digest(String content) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest msgDitest = MessageDigest.getInstance("SHA-1");
            msgDitest.update(content.getBytes());
            byte[] digests = msgDitest.digest();
            //将每个字节转为16进制
            for (int i = 0; i < digests.length; i++) {
                builder.append(Integer.toHexString(digests[i] & 0xff + 8));//+8为加盐操作
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 中间划线效果
     *
     * @param textView
     */
    public static void setLine(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
    }

    /**
     * 中间划线效果
     *
     * @param textView
     */
    public static void setBottomLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }

    public static String getCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String value = simpleDateFormat.format(date);
        return value;
    }

    /**
     * 处理价格。如果价格中含有.00，就将.00去掉
     * */
    public static String transPrice(String price){
        String value = "";
        if(price!=null){
            if(price.contains(".00")){
                value = price.substring(0, price.length() - 3);
            }else if(price.endsWith(".0")){
                value = price.substring(0, price.length() - 2);
            }else if(price.endsWith("0") && price.contains(".")){
                value = price.substring(0, price.length() - 1);
            }else{
                value = price;
            }
        }
        return value;
    }
    /**
     * 替换固定字符
     *
     * @param originalString 原始字符串
     * @param current
     * @return
     */
    public static String replaceAll(String originalString, String current, String future) {
        Pattern p = Pattern.compile(current);
        String instring = originalString;
//        System.out.println("initial String: " + instring);
        Matcher m = p.matcher(instring);
        String tmp = m.replaceAll(future);
//        System.out.println("String after replacing 1st Match: " + tmp);
        return tmp;
    }
    //最多显示ListView的item项数
    public static void setListViewHeight(ListView listView, LinearLayout container, int max_size) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int total_count = 0;
        total_count = listAdapter.getCount();
        max_size = total_count < max_size ? total_count : max_size;

        int totalHeight = 0;
        for (int i = 0, len = max_size; i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) container.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (max_size - 1));
        container.setLayoutParams(params);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    /**
     * @param mContext
     * @param resId
     * @param bar
     */
    public static void setRatingBarHeight(Context mContext, int resId, RatingBar bar) {
        int starHeight = 0;
        try {
            Bitmap bitmapStart = BitmapFactory.decodeResource(mContext.getResources(), resId);
            starHeight = bitmapStart.getHeight();
        } catch (Exception e) {
            starHeight = BaseUtil.dip2px(mContext, 18);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.height = starHeight;
        bar.setLayoutParams(params);
    }
    /**
     * @param mContext
     * @param resId
     * @param bar
     */
    public static void setMyRatingBarHeight(Context mContext, int resId, com.beijing.tenfingers.view.RatingBar bar) {
        int starHeight = 0;
        try {
            Bitmap bitmapStart = BitmapFactory.decodeResource(mContext.getResources(), resId);
            starHeight = bitmapStart.getHeight();
        } catch (Exception e) {
            starHeight = BaseUtil.dip2px(mContext, 18);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.height = starHeight;
        bar.setLayoutParams(params);
    }

    /**
     * 读取弹窗的list
     */
    public static List<PopBean> getArray(Context ctx) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = sp.getString("AD", null);
        Type type = new TypeToken<List<PopBean>>() {
        }.getType();
        if(json!=null){
            List<PopBean> arrayList = gson.fromJson(json, type);
            return arrayList;
        }
        return null;
    }
}