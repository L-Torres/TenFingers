package com.beijing.tenfingers.push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.bean.User;

import java.util.List;

/**
 * 推送消息处理工具
 */
public class NotifyMessageUtil {

    public static void handleFunction(Context context,PushModel pushModel){
        Intent it=null;
        if (isAppRunning(context)) {//已经运行
            MyApplication application = MyApplication.getInstance();
//            application.setPushModel(pushModel);//在application中保存推送数据
            User user=MyApplication.getInstance().getUser();
            switch (pushModel.getKeyType()) {
                case "1"://系统维护
                    break;
                case "4"://商品详情
                case "2"://2.活动通知 跳转活动详情
//                it=new Intent(context,GoodDetailActivity.class);
//                it=new Intent(context, GoodDetailActivity.class);
//                    it.putExtra("id",pushModel.getKeyId());
                    break;
                case "5"://5.订单详情页
                case "3"://3.订单待付款列表
//                      it=new Intent(context,OrderDetailActivity.class);
//                     it.putExtra("id",pushModel.getKeyId());
                    break;
                case "6"://  6.茶类百科
//                    it=new Intent(context,BaiKeCommonActivity.class);
//                    it.putExtra("title",pushModel.getMsg());
//                    it.putExtra("type_id",pushModel.getKeyId());
                    break;
                case "7"://售后订单详情
//                    it=new Intent(context,RefundDetailActivity.class);
//                    it.putExtra("id", pushModel.getKeyId());
//                    it.putExtra("type","0");
                    break;
                case "8"://更换客服

                    break;

                case "14":
                case "15":

                    break;

            }
        } else {//未运行
            it = context.getPackageManager().getLaunchIntentForPackage(context.getApplicationContext().getPackageName());
            it.putExtra("PushModel", pushModel);
        }
    }

    /**
     * 判断是否运行
     *
     * @param context
     * @return
     */
    private static boolean isAppRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) || info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
