package com.beijing.tenfingers.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyConfig;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import de.greenrobot.event.EventBus;

/**
 * 网页通用页面
 * Created by Torres on 2016/12/1.
 */

public class WebViewActivity extends BaseActivity {
    private String Baidu = "https://www.baidu.com/";
    private WebView webview;
    private ImageView imageQuitActivity;
    private TextView txtTitle;
    private TextView tvRight;
    private String Title;
    private String URL;
    private ProgressBar pg1;
    private LinearLayout ll_back;

    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public ValueCallback<Uri> mUploadMessage;
    public final static int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 2;
    private final static int FILE_CHOOSER_RESULT_CODE = 1;// 表单的结果回调
    private String type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview_h);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if ((Build.VERSION.SDK_INT > 27)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        initWebView();
        webview.setWebChromeClient(new WebChromeClient() {
            //Android < 5.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            //Android => 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                onenFileChooseImpleForAndroid(uploadMsg);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });

        if (isNull(URL))
            webview.loadUrl(Baidu);
        else
            webview.loadUrl(URL);

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask,
                                            HemaBaseResult baseResult) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask,
                                           HemaBaseResult baseResult) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void findView() {
        pg1 = findViewById(R.id.progressBar1);
        webview = (WebView) findViewById(R.id.webview);
        imageQuitActivity = (ImageView) findViewById(R.id.iv_back);
        txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText(Title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ll_back = findViewById(R.id.ll_back);

        if (isCanGoNext()) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText("去购买");
        } else {
            tvRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void getExras() {
        Title = mIntent.getStringExtra("Title");
        URL = mIntent.getStringExtra("URL");
        type_id = mIntent.getStringExtra("type_id");
    }

    private boolean isCanGoNext() {
        if (isNull(type_id)) {
            return false;
        } else if ("绿茶".equals(Title) || "红茶".equals(Title)
                || "青茶".equals(Title) || "白茶".equals(Title)
                || "黄茶".equals(Title) || "黑茶".equals(Title)
                || "花茶".equals(Title) || "再加工茶".equals(Title)) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void setListener() {
        imageQuitActivity.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                finish(R.anim.none, R.anim.right_out);
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish(R.anim.none, R.anim.right_out);
            }
        });

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected boolean onKeyBack() {
        finish(R.anim.none, R.anim.right_out);
        return super.onKeyBack();
    }

    @Override
    public void onEventMainThread(EventBusModel event) {
        if (!event.getState()) {
            return;
        }
        switch (event.getType()) {
        }
    }

    @Override
    protected void onResume() {
        webview.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        webview.onPause();
        super.onPause();
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        // 支持javascript
        webview.getSettings().setJavaScriptEnabled(true);
//        webview.addJavascriptInterface(new JavaScriptinterface(this),
//                "closeWeb");
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(false);
        // 扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        // 取消显示滚动条
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.getSettings().setAllowFileAccess(true);//设置在WebView内部是否允许访问文件
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                webview.loadUrl(url);
                return true;
            }
        });
        webview.getSettings().setSavePassword(true);
        webview.getSettings().setSaveFormData(true);
        webview.getSettings().setGeolocationEnabled(true);
        webview.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage
        webview.getSettings().setDomStorageEnabled(true);
        webview.requestFocus();
    }



    /**
     * android 5.0 以下开启图片选择（原生）
     * 可以自己改图片选择框架。
     */
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"),
                FILE_CHOOSER_RESULT_CODE);
    }

    /**
     * android 5.0(含) 以上开启图片选择（原生）
     * 可以自己改图片选择框架。
     */
    private void onenFileChooseImpleForAndroid(ValueCallback<Uri[]> filePathCallback) {
        mUploadMessageForAndroid5 = filePathCallback;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Uri result = (intent == null || resultCode != Activity.RESULT_OK) ? null : intent.getData();
        switch (requestCode) {
            case FILE_CHOOSER_RESULT_CODE:  //android 5.0以下 选择图片回调
                if (null == mUploadMessage)
                    return;
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
                break;
            case FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5:  //android 5.0(含) 以上 选择图片回调
                if (null == mUploadMessageForAndroid5)
                    return;
                if (result != null) {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                } else {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
                }
                mUploadMessageForAndroid5 = null;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}
