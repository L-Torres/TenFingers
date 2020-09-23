package com.beijing.tenfingers.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Copyright (C), 2018-, 山东河马信息技术有限公司
 * FileName: PushNotificationReceiver
 * Author: wangyuxia
 * Date: 2020/4/14 17:24
 * Description: 推送消息的接收
 */
public class PushNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals("com.example.administrator.gotodetail")) {
            //处理点击事件
        }

    }
}
