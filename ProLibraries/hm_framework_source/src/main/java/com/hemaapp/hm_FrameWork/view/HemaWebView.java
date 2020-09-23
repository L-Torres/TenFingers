package com.hemaapp.hm_FrameWork.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class HemaWebView extends WebView {

    public HemaWebView(Context context) {
        this(context, null);
    }

    public HemaWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        set();
    }

    public HemaWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        set();
    }


    @SuppressLint("NewApi")
    private void set() {
//		有的手机不能播放视频原因以及解决方案
//		http://blog.csdn.net/qq_16472137/article/details/54346078
        try {
            if (Build.VERSION.SDK_INT >Build.VERSION_CODES.KITKAT) {
                getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        getSettings().setAllowFileAccess(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setBlockNetworkImage(false); // 解决图片不显示
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setPluginState(PluginState.ON);
        setWebViewClient(new HemaWebViewClient());
        setWebChromeClient(new WebChromeClient());

    }

    private class HemaWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                new URL(url);// 检查是否是合法的URL
                view.loadUrl(url);
                return true;
            } catch (MalformedURLException e) {
                return false;
            }

        }
    }

}
