package com.beijing.tenfingers.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayer;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayerManager;
import com.beijing.tenfingers.jcvideoplayer_lib.JCVideoPlayerStandard;
import com.beijing.tenfingers.until.BaseUtil;

import xtom.frame.XtomObject;

public class PopVideoDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private PopVideoDialog.OnButtonListener buttonListener;
    private JCVideoPlayerStandard videoPlayer;

    public PopVideoDialog(Context context, String img, String url) {
        this.context = context;
        mDialog = new Dialog(context, R.style.dialog_white);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_video, null);
        videoPlayer = view.findViewById(R.id.videoplayer);
        BaseUtil.loadBitmap(img, 0, videoPlayer.thumbImageView);

        videoPlayer.setUp(url
                , JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN, "");
        mDialog.setCancelable(true);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                    //进行你想要的操作
                if(JCVideoPlayerManager.getCurrentJcvd()!=null){
                    JCVideoPlayerManager.getCurrentJcvd().release();
                    videoPlayer.release();
                }
            }
        });
        if (mDialog.isShowing() && null != mDialog){
            //关键是这个判断，isShowing()判断是否有同一个对象的dialog正在show。
            //加上此句问题就解决了
            mDialog.dismiss();
        }

        mDialog.setContentView(view);
        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
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

        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }

    public void cancel() {
        mDialog.cancel();
    }


    public void setButtonListener(PopVideoDialog.OnButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface OnButtonListener {
        public void onLeftButtonClick(PopVideoDialog dialog);

        public void onRightButtonClick(PopVideoDialog dialog);
    }

}
