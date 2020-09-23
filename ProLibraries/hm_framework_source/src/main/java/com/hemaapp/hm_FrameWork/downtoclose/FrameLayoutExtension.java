package com.hemaapp.hm_FrameWork.downtoclose;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * 描述: 扩展的 FrameLayout，设置范围是否包含Padding，默认true；是否只有设置范围内点击生效，默认false；是否是圆形，默认false；圆角设置；
 *           背景颜色、图设置；前景颜色、图设置；渐变色设置；边框设置；同时这些属性可以设置不同状态的值（默认、按下、选中、不可点击）；
 *           下拉关闭（用于ViewGroup，可实现微信朋友圈的图片查看器）；
 * 作者: Torres
 * 时间: 2019/5/26
 * 版本:
 */
public class FrameLayoutExtension extends FrameLayout implements Checkable {
    private ViewHelper<FrameLayoutExtension> mHelper = new ViewHelper<>();

    public FrameLayoutExtension(Context context) {
        super(context);
        mHelper.init(this);
    }

    public FrameLayoutExtension(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper.init(this, attrs);
    }

    public FrameLayoutExtension(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper.init(this, attrs, defStyleAttr);
    }

    public ViewHelper<FrameLayoutExtension> getHelper() {
        return mHelper;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHelper.onAttachedToWindow(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHelper.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mHelper.saveLayer(canvas);
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mHelper.trySaveLayer(canvas);
        super.dispatchDraw(canvas);
        mHelper.onClipDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mHelper.dispatchTouchEvent(ev) && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        mHelper.drawableStateChanged();
    }

    @Override
    public boolean isChecked() {
        return mHelper.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        mHelper.setChecked(checked);
    }

    @Override
    public void toggle() {
        setChecked(!mHelper.isChecked());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.onInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mHelper.onTouchEvent(event) || super.onTouchEvent(event);
    }
}
