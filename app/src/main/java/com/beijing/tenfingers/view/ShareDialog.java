package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.until.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import xtom.frame.XtomObject;

import static com.beijing.tenfingers.Base.MyConfig.IMAGE_SIZE;

/**
 * 分享
 * Created by Torres on 2016/12/26.
 */

public class ShareDialog extends XtomObject implements View.OnClickListener, PlatformActionListener {

    private Context context;
    private Dialog mDialog;
    private Button btnCancel;
    private BottomThreeDialog.OnButtonListener buttonListener;
    private ImageView imgWx;
    private ImageView imgFriend;
    private ImageView imgQq;
    private ImageView imgQqZone;
    private ImageView imgWb;
    private Platform.ShareParams sp;

    private OnekeyShare oks;

    private String TitleUrl;
    private String Title;
    private String Content;
    private TextView tv_cancel;
    private LinearLayout ll_zone, ll_qq, ll_pyq, ll_wx;
    private String pic;
    private String path="";
    public ShareDialog(Context context, String TitleUrl, String Title, String Content, String pic, String type,String path) {
        this.context = context;
        this.Title = Title;
        this.TitleUrl = TitleUrl;
        this.Content = Content;
        this.pic = pic;
        this.path=path;


        wxAPI = WXAPIFactory.createWXAPI(context,"wx3f43c7eaec6bb9be",true);
        wxAPI.registerApp("wx3f43c7eaec6bb9be");


//        ShareSDK.initSDK(this.context);//一定需要，否则无法分享  ShareSDK.initSDK
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.share_dialog, null);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        imgWx = (ImageView) view.findViewById(R.id.imgWx);
        imgFriend = (ImageView) view.findViewById(R.id.imgPyq);
        imgQq = (ImageView) view.findViewById(R.id.imgQq);
        ll_zone = (LinearLayout) view.findViewById(R.id.ll_zone);
        ll_qq = (LinearLayout) view.findViewById(R.id.ll_qq);
        ll_pyq = (LinearLayout) view.findViewById(R.id.ll_pyq);
        ll_wx = (LinearLayout) view.findViewById(R.id.ll_wx);
        imgQqZone = (ImageView) view.findViewById(R.id.imgQqZone);
        imgWb = (ImageView) view.findViewById(R.id.imgWb);
        imgFriend.setOnClickListener(this);
        imgWx.setOnClickListener(this);
        imgFriend.setOnClickListener(this);
        imgQq.setOnClickListener(this);
        imgQqZone.setOnClickListener(this);
        imgWb.setOnClickListener(this);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        mDialog.setCancelable(true);
        mDialog.setContentView(view);


        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);

        if ("1".equals(type)) {//分享文章
            ll_zone.setVisibility(View.GONE);
            ll_pyq.setVisibility(View.GONE);
        } else {

        }
    }

    /**
     * 设置是否可以取消
     *
     * @param cancelable
     */
    public void setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
    }


    public void show() {
        mDialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        mDialog.getWindow().setAttributes(lp);
    }

    public void cancel() {
        mDialog.cancel();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgWx:
                mDialog.cancel();
                share(false,path);
//                showShare(Wechat.NAME);
                break;
            case R.id.imgPyq:
                mDialog.cancel();
//                showShare(WechatMoments.NAME);
                share(true,path);
                break;
            case R.id.imgQq:
                mDialog.cancel();
                showShare(QQ.NAME);
                break;
            case R.id.imgQqZone:
                mDialog.cancel();
                showShare(QZone.NAME);
                break;
            case R.id.imgWb:
                mDialog.cancel();
                ToastUtil.showLongToast(context, "暂未开通该功能！");
                break;

        }

    }
    private IWXAPI wxAPI;
    /**
     * 微信分享
     * @param friendsCircle  是否分享到朋友圈
     */
    public void share(boolean friendsCircle,String  path){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl =path;//分享url
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "十指间";
        msg.description = "十指间";
        msg.thumbData =getThumbData();//封面图片byte数组

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = friendsCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        wxAPI.sendReq(req);
    }

    /**
     * 获取分享封面byte数组 我们这边取的是软件启动icon
     * @return
     */
    private  byte[] getThumbData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;
        final Bitmap[] bitmap = {BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher, options)};
//        Bitmap bitmap=BaseUtil.getBitmap(pic)
        Glide.with(context).asBitmap().load(pic).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmap[0] =resource;
            }
        });
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap[0].compress(Bitmap.CompressFormat.JPEG, 100, output);
        int quality = 100;
        while (output.toByteArray().length > IMAGE_SIZE && quality != 10) {
            output.reset(); // 清空baos
            bitmap[0].compress(Bitmap.CompressFormat.JPEG, quality, output);// 这里压缩options%，把压缩后的数据存放到baos中
            quality -= 10;
        }
        bitmap[0].recycle();
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtil.showShortToast(this.context, "分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtil.showShortToast(this.context, "分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
    }

    private void showShare(String platform) {

        Platform.ShareParams wtx = new Platform.ShareParams();
        wtx.setTitle(Title);
        wtx.setShareType(Platform.SHARE_WEBPAGE);
        wtx.setText(Content);
        wtx.setImageUrl(pic);
        // wt.setSite("发布分享的网站名称");
        if (platform.equals("Wechat") || platform.equals("WechatMoments")) {
            wtx.setUrl(TitleUrl);
        } else {
            wtx.setTitleUrl(TitleUrl);
        }
        Platform wechatq = ShareSDK.getPlatform(platform);

// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        wechatq.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                Log.d("ShareSDK", "onError ---->  分享失败");
                Log.d("ShareSDK", "onError ---->  分享失败" + arg2.toString());
            }

            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
                Log.d("ShareSDK", "onComplete ---->  成功");
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                Log.d("ShareSDK", "onCancel ---->  取消");
            }
        });
// 执行图文分享
        wechatq.share(wtx);


    }


    /**
     * 分享小程序
     */
    private  void sharePrograme(Context mContext){
        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl="http://www.qq.com";//自定义
        miniProgram.userName="xxxxxxxxx";//小程序端提供参数
        miniProgram.path="pages/entry";//小程序端提供参数
        WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
        mediaMessage.title = "cgw miniProgram";//自定义
        mediaMessage.description = "this is miniProgram's description";//自定义
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher);
        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);
        bitmap.recycle();
        mediaMessage.thumbData = Util.bmpToByteArray(sendBitmap,true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "";
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.message = mediaMessage;
        wxAPI.sendReq(req);
    }


}
