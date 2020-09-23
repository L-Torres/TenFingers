package com.hemaapp.hm_FrameWork.downtoclose;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Checkable;
import android.widget.TextView;


import com.hemaapp.hm_FrameWork.R;

import java.util.Arrays;
import java.util.Collections;

/**
 * 描述: View 扩展帮助类，不支持：前景、背景、渐变、边框、下拉关闭；
 * 作者: Torres
 * 时间: 2019/1/26
 * 版本: V1.0
 */
public class ViewHelperBlurBg<T extends View> {
    private final Canvas mCanvas = new Canvas();
    private final Matrix mMatrixScale = new Matrix();
    private final Matrix mMatrixScaleInv = new Matrix();
    private final int[] mLocationInWindow = new int[2];
    private final ViewTreeObserver.OnPreDrawListener mBlurOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            if (mView.getVisibility() == View.VISIBLE) {
                drawOffscreenBitmap();
            }
            return true;
        }
    };

    private T mView;
    private AttributeSet mAttrs;
    private int mDefStyleAttr;
    private int mDefStyleRes;

    private Paint mPaint = new Paint();
    private Path mClipPath = new Path();
    private RectF mLayerRect = new RectF();
    private Region mAreaRegion = new Region();

    private boolean isAreaPadding = true;
    private boolean isAreaClick = false;

    private boolean isCircle = false;
    private float[] mRadiusArray = new float[8];

    private Bitmap mBlurBitmap;
    private int mBlurRadius = 1;
    private float mBlurScale = 0.125f;

    private @ColorInt
    int mTextColorNormal;
    private @ColorInt
    int mTextColorPressed;
    private @ColorInt
    int mTextColorChecked;
    private @ColorInt
    int mTextColorUnable;

    private Path mPath = new Path();
    private RectF mAreas = new RectF();
    private Region mClip = new Region();
    private PointF mCenter = new PointF();
    private int mPadding = 0;

    private boolean mChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public void init(@NonNull T view) {
        init(view, null);
    }
    public void init(@NonNull T view, AttributeSet attrs) {
        init(view, attrs, 0);
    }
    public void init(@NonNull T view, AttributeSet attrs, int defStyleAttr) {
        init(view, attrs, defStyleAttr, 0);
    }
    public void init(@NonNull T view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mView = view;
        mAttrs = attrs;
        mDefStyleAttr = defStyleAttr;
        mDefStyleRes = defStyleRes;
        init();
    }
    public void init() {
        if (mView != null && mAttrs != null) {
            final TypedArray ta = mView.getContext().getTheme().obtainStyledAttributes(mAttrs, R.styleable.ViewHelper, mDefStyleAttr, mDefStyleRes);
            isAreaClick = ta.getBoolean(R.styleable.ViewHelper_vh_isAreaClick, false);
            isAreaPadding = ta.getBoolean(R.styleable.ViewHelper_vh_isAreaPadding, true);

            isCircle = ta.getBoolean(R.styleable.ViewHelper_vh_isCircle, false);
            float roundCorner = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadius, 0);
            float tl = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusTopLeft, roundCorner);
            float tr = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusTopRight, roundCorner);
            float bl = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusBottomLeft, roundCorner);
            float br = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusBottomRight, roundCorner);
            setCornersRadius(tl, tr, bl, br);

            mBlurScale = ta.getFloat(R.styleable.ViewHelper_vh_blurScale, 0);
            mBlurRadius = ta.getDimensionPixelSize(R.styleable.ViewHelper_vh_blurRadius, 0);
            setBlurScale(mBlurScale);
            setBlurRadius(mBlurRadius);

            int mTextColor = ta.getColor(R.styleable.ViewHelper_vh_textColor, Color.TRANSPARENT);
            mTextColorNormal = ta.getColor(R.styleable.ViewHelper_vh_textColorNormal, mTextColor);
            mTextColorPressed = ta.getColor(R.styleable.ViewHelper_vh_textColorPressed, mTextColorNormal);
            mTextColorChecked = ta.getColor(R.styleable.ViewHelper_vh_textColorChecked, mTextColorNormal);
            mTextColorUnable = ta.getColor(R.styleable.ViewHelper_vh_textColorUnable, mTextColorNormal);

            ta.recycle();
        }

        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }


    public void setIsAreaPadding(boolean isAreaPadding) {
        this.isAreaPadding = isAreaPadding;
    }

    public void setIsAreaClick(boolean isAreaClick) {
        this.isAreaClick = isAreaClick;
    }

    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
        refreshRegion();
    }

    public void setCornersRadius(float radius) {
        if (radius >= 0) {
            setCornersRadius(radius, radius, radius, radius);
        }
    }
    public void setCornersRadius(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        if (topLeftRadius >= 0 && topRightRadius >= 0 && bottomLeftRadius >= 0 && bottomRightRadius >= 0) {
            mRadiusArray[0] = mRadiusArray[1] = topLeftRadius;
            mRadiusArray[2] = mRadiusArray[3] = topRightRadius;
            mRadiusArray[4] = mRadiusArray[5] = bottomRightRadius;
            mRadiusArray[6] = mRadiusArray[7] = bottomLeftRadius;
            refreshRegion();
        }
    }

    public void setTextColor(@ColorInt int color) {
        mTextColorNormal = mTextColorPressed = mTextColorChecked = mTextColorUnable = color;
        drawableStateChanged();
    }
    public void setTextColorNormal(@ColorInt int color) {
        mTextColorNormal = color;
        drawableStateChanged();
    }
    public void setTextColorPressed(@ColorInt int color) {
        mTextColorPressed = color;
        drawableStateChanged();
    }
    public void setTextColorChecked(@ColorInt int color) {
        mTextColorChecked = color;
        drawableStateChanged();
    }
    public void setTextColorUnable(@ColorInt int color) {
        mTextColorUnable = color;
        drawableStateChanged();
    }

    public void setBlurRadius(float radius) {
        if (radius >= 0) {
            mBlurRadius = Math.round(radius);
        } else {
            mBlurRadius = 0;
        }
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setBlurScale(float scale) {
        if (scale >= 0) {
            mBlurScale = Math.min(1f, scale);
        } else {
            mBlurScale = 0;
        }
        if (mView != null) {
            mView.invalidate();
        }
    }



    public void onAttachedToWindow(T view) {
        mView = view;
        mView.getViewTreeObserver().addOnPreDrawListener(mBlurOnPreDrawListener);
    }

    public void onDetachedFromWindow() {
        mView.getViewTreeObserver().removeOnPreDrawListener(mBlurOnPreDrawListener);
        mView = null;
    }

    public void onSizeChanged(int w, int h) {
        mLayerRect.set(0, 0, w, h);
        refreshRegion();
    }

    public void saveLayer(Canvas canvas) {
        canvas.saveLayer(mLayerRect, null, Canvas.ALL_SAVE_FLAG);
    }

    public void trySaveLayer(Canvas canvas) {
        if (mView.getBackground() == null) {
            canvas.saveLayer(mLayerRect, null, Canvas.ALL_SAVE_FLAG);
        }
    }

    public void onClipDraw(Canvas canvas) {
        if (mBlurRadius > 0 && mBlurScale > 0) {
            if (isOffscreenCanvas(canvas)) {
                applyBlur();
            } else {
                drawToCanvas(canvas);
            }
        }

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mPadding);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawPath(mClipPath, mPaint);
        } else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPath.reset();
            mPath.addRect(0, 0, (int) mLayerRect.width(), (int) mLayerRect.height(), Path.Direction.CW);
            mPath.op(mClipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(mPath, mPaint);
        }

        canvas.restore();
    }

    private void refreshRegion() {
        if (mView != null) {
            float w = mLayerRect.width();
            float h = mLayerRect.height();

            if (isAreaPadding) {
                mAreas.left = 0;
                mAreas.top = 0;
                mAreas.right = w;
                mAreas.bottom = h;
            } else {
                mAreas.left = mView.getPaddingLeft();
                mAreas.top = mView.getPaddingTop();
                mAreas.right = w - mView.getPaddingRight();
                mAreas.bottom = h - mView.getPaddingBottom();
            }

            mPadding = Collections.max(Arrays.asList(mView.getPaddingLeft(), mView.getPaddingTop(), mView.getPaddingRight(), mView.getPaddingBottom()));

            mClipPath.reset();
            if (isCircle) {
                float d = Math.min(mAreas.width(), mAreas.height());
                float r = d / 2;
                mCenter.set(mAreas.width() / 2 + mAreas.left, mAreas.height() / 2 + mAreas.top);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                    mClipPath.addCircle(mCenter.x, mCenter.y, r, Path.Direction.CW);
                    mClipPath.moveTo(0, 0);
                    mClipPath.moveTo(w, h);
                } else {
                    float y = h / 2 - r;
                    mClipPath.moveTo(mAreas.left, y);
                    mClipPath.addCircle(mCenter.x, y + r, r, Path.Direction.CW);
                }
            } else {
                mClipPath.addRoundRect(mAreas, mRadiusArray, Path.Direction.CW);
            }
            mClip.set((int) mAreas.left, (int) mAreas.top, (int) mAreas.right, (int) mAreas.bottom);
            mAreaRegion.setPath(mClipPath, mClip);

            mView.invalidate();
        }
    }

    // 抓取屏幕截屏
    private void drawOffscreenBitmap() {
        if (mView != null && mBlurScale > 0 && mBlurScale <= 1) {
            // 计算比例, 离屏 位图宽高
            int width = Math.round(mView.getWidth() * mBlurScale);
            int height = Math.round(mView.getHeight() * mBlurScale);
            // 宽高必须 > 0
            width = Math.max(width, 1);
            height = Math.max(height, 1);
            // 只在需要时分配新的离屏位图
            if (mBlurBitmap == null || mBlurBitmap.getWidth() != width || mBlurBitmap.getHeight() != height) {
                mBlurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

                mMatrixScale.setScale((float) width / mView.getWidth(), (float) height / mView.getHeight());
                mMatrixScale.invert(mMatrixScaleInv);
            }

            mView.getLocationInWindow(mLocationInWindow);
            // 将 canvas 还原为初始状态
            mCanvas.restoreToCount(1);
            mCanvas.setBitmap(mBlurBitmap);
            // 使用比例矩阵, 使绘制调用匹配；调整大小尺寸
            mCanvas.setMatrix(mMatrixScale);
            mCanvas.translate(-mLocationInWindow[0], -mLocationInWindow[1]);
            mCanvas.save();

            mView.getRootView().draw(mCanvas);
        }
    }

    private boolean isOffscreenCanvas(Canvas canvas) {
        return canvas == mCanvas;
    }

    // 绘制模糊的背景图像并继续传递到子视图
    private void drawToCanvas(Canvas canvas) {
        if (mBlurBitmap != null) {
            canvas.drawBitmap(mBlurBitmap, mMatrixScaleInv, null);
        }
    }

    private void applyBlur() {
        if (mBlurRadius > 0) {
            Bitmap bitmap = fastBlur(mBlurBitmap, mBlurRadius);
            if (bitmap != null) {
                mBlurBitmap = bitmap;
            }
        }
    }

    private Bitmap fastBlur(Bitmap bitmap, int radius) {
        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int[] r = new int[wh];
        int[] g = new int[wh];
        int[] b = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int[] vmin = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int[] dv = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }



    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN && isAreaClick && mAreaRegion.contains((int) ev.getX(), (int) ev.getY())) {
            return false;
        }
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
            mView.refreshDrawableState();
        } else if (action == MotionEvent.ACTION_CANCEL) {
            mView.setPressed(false);
            mView.refreshDrawableState();
        }
        return true;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            mView.refreshDrawableState();
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(mView, mChecked);
            }
        }
    }

    public void drawableStateChanged() {
        if (mView instanceof TextView && mTextColorNormal != Color.TRANSPARENT) {
            ((TextView) mView).setTextColor(mTextColorNormal);
        }
        if (mView instanceof Checkable) {
            if (((Checkable) mView).isChecked()) {
                if (mView instanceof TextView && mTextColorChecked != Color.TRANSPARENT) {
                    ((TextView) mView).setTextColor(mTextColorChecked);
                }
            }
            if (mView.isEnabled()) {
                if (mView.isPressed() || mView.isFocused()) {
                    if (mView instanceof TextView && mTextColorPressed != Color.TRANSPARENT) {
                        ((TextView) mView).setTextColor(mTextColorPressed);
                    }
                }
            } else {
                if (mView instanceof TextView && mTextColorUnable != Color.TRANSPARENT) {
                    ((TextView) mView).setTextColor(mTextColorUnable);
                }
            }
        }
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setOnCheckedChangeListener(ViewHelperBlurBg.OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean isChecked);
    }
}
