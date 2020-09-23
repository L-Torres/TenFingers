package com.hemaapp.hm_FrameWork.downtoclose;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;


import com.hemaapp.hm_FrameWork.R;

import java.util.Arrays;
import java.util.Collections;

/**
 * 描述: View 扩展帮助类，设置范围是否包含Padding，默认true；是否只有设置范围内点击生效，默认false；是否是圆形，默认false；圆角设置；
 *          文本颜色设置；背景颜色、图设置；前景颜色、图设置；渐变色设置；边框设置；同时这些属性可以设置不同状态的值（默认、按下、选中、不可点击）；
 *          下拉关闭（用于ViewGroup，可实现微信朋友圈的图片查看器）；
 * 作者: Torres
 * 时间: 2019/1/26
 * 版本: V1.0
 */
public class ViewHelper<T extends View> {
    private T mView;
    private AttributeSet mAttrs;
    private int mDefStyleAttr;
    private int mDefStyleRes;

    private int mPaddingMax = 0;                                // View 的最大 padding 值
    private int mPaddingStart = 0;
    private int mPaddingTop = 0;
    private int mViewWidth = 0;
    private int mViewHeight = 0;

    private Paint mPaint = new Paint();
    private Path mClipPath = new Path();                        // 剪裁区域路径
    private RectF mLayerRect = new RectF();                     // 画布图层大小
    private Region mAreaRegion = new Region();                  // 内容区域

    private boolean isAreaPadding = true;                       // 是否区域包含padding值
    private boolean isAreaClick = false;                        // 是否范围内点击有效

    private boolean isCircle = false;                           // 是否是圆形
    private float[] mRadiusArray = new float[8];                // 圆角数组

    private @ColorInt int mTextColorNormal;                     // 文本颜色
    private @ColorInt int mTextColorPressed;
    private @ColorInt int mTextColorChecked;
    private @ColorInt int mTextColorUnable;

    // 背景图
    private Bitmap mBgBitmap, mBgBitmapNormal, mBgBitmapPressed, mBgBitmapChecked, mBgBitmapUnable;

    private @ColorInt int mBgColor = Color.TRANSPARENT;         // 背景颜色
    private @ColorInt int mBgColorNormal = Color.TRANSPARENT;
    private @ColorInt int mBgColorPressed = Color.TRANSPARENT;
    private @ColorInt int mBgColorChecked = Color.TRANSPARENT;
    private @ColorInt int mBgColorUnable = Color.TRANSPARENT;

    // 前景图
    private Bitmap mProspectBitmap, mProspectBitmapNormal, mProspectBitmapPressed, mProspectBitmapChecked, mProspectBitmapUnable;

    private @ColorInt int mProspectColor = Color.TRANSPARENT;   // 前景颜色
    private @ColorInt int mProspectColorNormal = Color.TRANSPARENT;
    private @ColorInt int mProspectColorPressed = Color.TRANSPARENT;
    private @ColorInt int mProspectColorChecked = Color.TRANSPARENT;
    private @ColorInt int mProspectColorUnable = Color.TRANSPARENT;

    // 渐变的着色器
    private Shader mGradient, mGradientNormal, mGradientPressed,  mGradientChecked, mGradientUnable;
    private GradientInfo mGradientInfo = new GradientInfo();


    private DashPathEffect mBorderDashPathEffect;               // 虚线参数设置

    private float mBorderWidth = 0;                             // 边框宽度
    private float mBorderWidthNormal = 0;
    private float mBorderWidthPressed = 0;
    private float mBorderWidthChecked = 0;
    private float mBorderWidthUnable = 0;

    private @ColorInt int mBorderColor = Color.TRANSPARENT;     // 边框颜色
    private @ColorInt int mBorderColorNormal = Color.TRANSPARENT;
    private @ColorInt int mBorderColorPressed = Color.TRANSPARENT;
    private @ColorInt int mBorderColorChecked = Color.TRANSPARENT;
    private @ColorInt int mBorderColorUnable = Color.TRANSPARENT;

    private Path mPath = new Path();                            // onClipDraw 使用的临时变量
    private RectF mAreas = new RectF();                         // 绘制范围
    private Region mClip = new Region();                        // refreshRegion 使用的临时变量
    private PointF mCenter = new PointF();                      // refreshRegion 使用的临时变量

    private boolean mChecked;                                   // Selector 变量，是否选中
    private OnCheckedChangeListener mOnCheckedChangeListener;   // Selector 变量，回调

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
            isDropdownClose = ta.getBoolean(R.styleable.ViewHelper_vh_isDropdownClose, false);

            isCircle = ta.getBoolean(R.styleable.ViewHelper_vh_isCircle, false);
            float roundCorner = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadius, 0);
            float tl = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusTopLeft, roundCorner);
            float tr = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusTopRight, roundCorner);
            float bl = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusBottomLeft, roundCorner);
            float br = ta.getDimension(R.styleable.ViewHelper_vh_cornersRadiusBottomRight, roundCorner);
            setCornersRadius(tl, tr, bl, br);

            int mTextColor = ta.getColor(R.styleable.ViewHelper_vh_textColor, Color.TRANSPARENT);
            mTextColorNormal = ta.getColor(R.styleable.ViewHelper_vh_textColorNormal, mTextColor);
            mTextColorPressed = ta.getColor(R.styleable.ViewHelper_vh_textColorPressed, mTextColorNormal);
            mTextColorChecked = ta.getColor(R.styleable.ViewHelper_vh_textColorChecked, mTextColorNormal);
            mTextColorUnable = ta.getColor(R.styleable.ViewHelper_vh_textColorUnable, mTextColorNormal);


            mProspectColor = ta.getColor(R.styleable.ViewHelper_vh_prospectColor, Color.TRANSPARENT);
            mProspectColorNormal = ta.getColor(R.styleable.ViewHelper_vh_prospectColorNormal, mProspectColor);
            mProspectColor = mProspectColorNormal;
            mProspectColorPressed = ta.getColor(R.styleable.ViewHelper_vh_prospectColorPressed, mBgColorNormal);
            mProspectColorChecked = ta.getColor(R.styleable.ViewHelper_vh_prospectColorChecked, mBgColorNormal);
            mProspectColorUnable = ta.getColor(R.styleable.ViewHelper_vh_prospectColorUnable, mBgColorNormal);

            Drawable prospectDrawable = ta.getDrawable(R.styleable.ViewHelper_vh_prospectDrawable);
            Drawable prospectDrawableNormal = ta.getDrawable(R.styleable.ViewHelper_vh_prospectDrawableNormal);
            if (prospectDrawableNormal == null) {
                prospectDrawableNormal = prospectDrawable;
            }
            Drawable prospectDrawablePressed = ta.getDrawable(R.styleable.ViewHelper_vh_prospectDrawablePressed);
            Drawable prospectDrawableChecked = ta.getDrawable(R.styleable.ViewHelper_vh_prospectDrawableChecked);
            Drawable prospectDrawableUnable = ta.getDrawable(R.styleable.ViewHelper_vh_prospectDrawableUnable);
            if (prospectDrawableNormal != null) {
                mProspectBitmap = mProspectBitmapNormal = drawableToBitmap(prospectDrawableNormal);
            }
            if (prospectDrawablePressed == null || prospectDrawablePressed == prospectDrawableNormal) {
                mProspectBitmapPressed = mProspectBitmapNormal;
            } else {
                mProspectBitmapPressed = drawableToBitmap(prospectDrawablePressed);
            }
            if (prospectDrawableChecked == null || prospectDrawableChecked == prospectDrawableNormal) {
                mProspectBitmapChecked = mProspectBitmapNormal;
            } else {
                mProspectBitmapChecked = drawableToBitmap(prospectDrawableChecked);
            }
            if (prospectDrawableUnable == null || prospectDrawableUnable == prospectDrawableNormal) {
                mProspectBitmapUnable = mProspectBitmapNormal;
            } else {
                mProspectBitmapUnable = drawableToBitmap(prospectDrawableUnable);
            }

            mBgColor = ta.getColor(R.styleable.ViewHelper_vh_backgroundColor, Color.TRANSPARENT);
            mBgColorNormal = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorNormal, mBgColor);
            mBgColor = mBgColorNormal;
            mBgColorPressed = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorPressed, mBgColorNormal);
            mBgColorChecked = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorChecked, mBgColorNormal);
            mBgColorUnable = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorUnable, mBgColorNormal);

            Drawable bgDrawable = ta.getDrawable(R.styleable.ViewHelper_vh_backgroundDrawable);
            Drawable bgDrawableNormal = ta.getDrawable(R.styleable.ViewHelper_vh_backgroundDrawableNormal);
            if (bgDrawableNormal == null) {
                bgDrawableNormal = bgDrawable;
            }
            Drawable bgDrawablePressed = ta.getDrawable(R.styleable.ViewHelper_vh_backgroundDrawablePressed);
            Drawable bgDrawableChecked = ta.getDrawable(R.styleable.ViewHelper_vh_backgroundDrawableChecked);
            Drawable bgDrawableUnable = ta.getDrawable(R.styleable.ViewHelper_vh_backgroundDrawableUnable);
            if (bgDrawableNormal != null) {
                mBgBitmap = mBgBitmapNormal = drawableToBitmap(bgDrawableNormal);
            }
            if (bgDrawablePressed == null || bgDrawablePressed == bgDrawableNormal) {
                mBgBitmapPressed = mBgBitmapNormal;
            } else {
                mBgBitmapPressed = drawableToBitmap(bgDrawablePressed);
            }
            if (bgDrawableChecked == null || bgDrawableChecked == bgDrawableNormal) {
                mBgBitmapChecked = mBgBitmapNormal;
            } else {
                mBgBitmapChecked = drawableToBitmap(bgDrawableChecked);
            }
            if (bgDrawableUnable == null || bgDrawableUnable == bgDrawableNormal) {
                mBgBitmapUnable = mBgBitmapNormal;
            } else {
                mBgBitmapUnable = drawableToBitmap(bgDrawableUnable);
            }

            mGradientInfo.setGradientType(ta.getInt(R.styleable.ViewHelper_vh_gradientType, -1));
            if (mGradientInfo.mTypeNormal >= 0) {
                int gColorStart = ta.getColor(R.styleable.ViewHelper_vh_gradientColorStart, Color.TRANSPARENT);
                int gColorStartNormal = ta.getColor(R.styleable.ViewHelper_vh_gradientColorStartNormal, gColorStart);
                int gColorStartPressed = ta.getColor(R.styleable.ViewHelper_vh_gradientColorStartPressed, gColorStartNormal);
                int gColorStartChecked = ta.getColor(R.styleable.ViewHelper_vh_gradientColorStartChecked, gColorStartNormal);
                int gColorStartUnable = ta.getColor(R.styleable.ViewHelper_vh_gradientColorStartUnable, gColorStartNormal);
                int gColorCenter = ta.getColor(R.styleable.ViewHelper_vh_gradientColorCenter, Color.TRANSPARENT);
                int gColorCenterNormal = ta.getColor(R.styleable.ViewHelper_vh_gradientColorCenterNormal, gColorCenter);
                int gColorCenterPressed = ta.getColor(R.styleable.ViewHelper_vh_gradientColorCenterPressed, gColorCenterNormal);
                int gColorCenterChecked = ta.getColor(R.styleable.ViewHelper_vh_gradientColorCenterChecked, gColorCenterNormal);
                int gColorCenterUnable = ta.getColor(R.styleable.ViewHelper_vh_gradientColorCenterUnable, gColorCenterNormal);
                int gColorEnd = ta.getColor(R.styleable.ViewHelper_vh_gradientColorEnd, Color.TRANSPARENT);
                int gColorEndNormal = ta.getColor(R.styleable.ViewHelper_vh_gradientColorEndNormal, gColorEnd);
                int gColorEndPressed = ta.getColor(R.styleable.ViewHelper_vh_gradientColorEndPressed, gColorEndNormal);
                int gColorEndChecked = ta.getColor(R.styleable.ViewHelper_vh_gradientColorEndChecked, gColorEndNormal);
                int gColorEndUnable = ta.getColor(R.styleable.ViewHelper_vh_gradientColorEndUnable, gColorEndNormal);
                if (!(gColorStartNormal == Color.TRANSPARENT && gColorCenterNormal == Color.TRANSPARENT && gColorEndNormal == Color.TRANSPARENT)) {
                    if ((gColorStartNormal == Color.TRANSPARENT && gColorCenterNormal == Color.TRANSPARENT) || (gColorCenterNormal == Color.TRANSPARENT && gColorEndNormal == Color.TRANSPARENT)) {
                        mGradientInfo.mColorArrayNormal = new int[]{gColorStartNormal, gColorEndNormal};
                        mGradientInfo.mColorArrayPressed = new int[]{gColorStartPressed, gColorEndPressed};
                        mGradientInfo.mColorArrayChecked = new int[]{gColorStartChecked, gColorEndChecked};
                        mGradientInfo.mColorArrayUnable = new int[]{gColorStartUnable, gColorEndUnable};
                    } else {
                        mGradientInfo.mColorArrayNormal = new int[]{gColorStartNormal, gColorCenterNormal, gColorEndNormal};
                        mGradientInfo.mColorArrayPressed = new int[]{gColorStartPressed, gColorCenterPressed, gColorEndPressed};
                        mGradientInfo.mColorArrayChecked = new int[]{gColorStartChecked, gColorCenterChecked, gColorEndChecked};
                        mGradientInfo.mColorArrayUnable = new int[]{gColorStartUnable, gColorCenterUnable, gColorEndUnable};
                    }
                } else {
                    mGradientInfo.mColorArrayNormal = getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayNormal, getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArray, null));
                    mGradientInfo.mColorArrayPressed = getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayPressed, mGradientInfo.mColorArrayNormal);
                    mGradientInfo.mColorArrayChecked = getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayChecked, mGradientInfo.mColorArrayNormal);
                    mGradientInfo.mColorArrayUnable = getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayUnable, mGradientInfo.mColorArrayNormal);
                }

                mGradientInfo.mPositionsNormal = getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayNormal, getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArray, null));
                mGradientInfo.mPositionsPressed = getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayPressed, mGradientInfo.mPositionsNormal);
                mGradientInfo.mPositionsChecked = getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayChecked, mGradientInfo.mPositionsNormal);
                mGradientInfo.mPositionsUnable = getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayUnable, mGradientInfo.mPositionsNormal);

                mGradientInfo.setGradientMode(ta.getInt(R.styleable.ViewHelper_vh_gradientMode, 0));
                mGradientInfo.setGradientCenterX(ta.getFloat(R.styleable.ViewHelper_vh_gradientCenterX, 0.5f));
                mGradientInfo.setGradientCenterY(ta.getFloat(R.styleable.ViewHelper_vh_gradientCenterY, 0.5f));
                mGradientInfo.setGradientAngle(ta.getFloat(R.styleable.ViewHelper_vh_gradientAngle, 0));
                mGradientInfo.setGradientRadius(ta.getDimension(R.styleable.ViewHelper_vh_gradientRadius, 0));
                mView.post(new Runnable() {
                    @Override
                    public void run() {
                        newGradient(mGradientInfo);
                    }
                });
            }

            mBorderWidth = ta.getDimension(R.styleable.ViewHelper_vh_borderWidth, 0);
            mBorderWidthNormal = ta.getDimension(R.styleable.ViewHelper_vh_borderWidthNormal, mBorderWidth);
            mBorderWidth = mBorderWidthNormal;
            mBorderWidthPressed = ta.getDimension(R.styleable.ViewHelper_vh_borderWidthPressed, mBorderWidthNormal);
            mBorderWidthChecked = ta.getDimension(R.styleable.ViewHelper_vh_borderWidthChecked, mBorderWidthNormal);
            mBorderWidthUnable = ta.getDimension(R.styleable.ViewHelper_vh_borderWidthUnable, mBorderWidthNormal);

            mBorderColor = ta.getColor(R.styleable.ViewHelper_vh_borderColor, Color.TRANSPARENT);
            mBorderColorNormal = ta.getColor(R.styleable.ViewHelper_vh_borderColorNormal, mBorderColor);
            mBorderColor = mBorderColorNormal;
            mBorderColorPressed = ta.getColor(R.styleable.ViewHelper_vh_borderColorPressed, mBorderColorNormal);
            mBorderColorChecked = ta.getColor(R.styleable.ViewHelper_vh_borderColorChecked, mBorderColorNormal);
            mBorderColorUnable = ta.getColor(R.styleable.ViewHelper_vh_borderColorUnable, mBorderColorNormal);

            float borderDashGap = ta.getDimension(R.styleable.ViewHelper_vh_borderDashGap, 0);
            float borderDashWidth = ta.getDimension(R.styleable.ViewHelper_vh_borderDashWidth, 0);
            if (borderDashGap > 0 && borderDashWidth > 0) {
                setBorderDash(borderDashWidth, borderDashGap);
            } else {
                float[] fArray = getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_borderDashArray, null);
                if (fArray != null && fArray.length > 1) {
                    setBorderDash(fArray);
                }
            }
            ta.recycle();
        }

        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }


    // 公开接口
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

    public void setProspect(@ColorInt int color) {
        mProspectColorNormal = mProspectColorPressed = mProspectColorChecked = mProspectColorUnable = color;
        drawableStateChanged();
    }
    public void setProspectNormal(@ColorInt int color) {
        mProspectColorNormal = color;
        drawableStateChanged();
    }
    public void setProspectPressed(@ColorInt int color) {
        mProspectColorPressed = color;
        drawableStateChanged();
    }
    public void setProspectChecked(@ColorInt int color) {
        mProspectColorChecked = color;
        drawableStateChanged();
    }
    public void setProspectUnable(@ColorInt int color) {
        mProspectColorUnable = color;
        drawableStateChanged();
    }

    public void setProspect(Bitmap bitmap) {
        mProspectBitmapNormal = mProspectBitmapPressed = mProspectBitmapChecked = mProspectBitmapUnable = bitmap;
        drawableStateChanged();
    }
    public void setProspectNormal(Bitmap bitmap) {
        mProspectBitmapNormal = bitmap;
        drawableStateChanged();
    }
    public void setProspectPressed(Bitmap bitmap) {
        mProspectBitmapPressed = bitmap;
        drawableStateChanged();
    }
    public void setProspectChecked(Bitmap bitmap) {
        mProspectBitmapChecked = bitmap;
        drawableStateChanged();
    }
    public void setProspectUnable(Bitmap bitmap) {
        mProspectBitmapUnable = bitmap;
        drawableStateChanged();
    }

    public void setBackground(@ColorInt int color) {
        mBgColorNormal = mBgColorPressed = mBgColorChecked = mBgColorUnable = color;
        drawableStateChanged();
    }
    public void setBackgroundNormal(@ColorInt int color) {
        mBgColorNormal = color;
        drawableStateChanged();
    }
    public void setBackgroundPressed(@ColorInt int color) {
        mBgColorPressed = color;
        drawableStateChanged();
    }
    public void setBackgroundChecked(@ColorInt int color) {
        mBgColorChecked = color;
        drawableStateChanged();
    }
    public void setBackgroundUnable(@ColorInt int color) {
        mBgColorUnable = color;
        drawableStateChanged();
    }

    public void setBackground(Bitmap bitmap) {
        mBgBitmapNormal = mBgBitmapPressed = mBgBitmapChecked = mBgBitmapUnable = bitmap;
        drawableStateChanged();
    }
    public void setBackgroundNormal(Bitmap bitmap) {
        mBgBitmapNormal = bitmap;
        drawableStateChanged();
    }
    public void setBackgroundPressed(Bitmap bitmap) {
        mBgBitmapPressed = bitmap;
        drawableStateChanged();
    }
    public void setBackgroundChecked(Bitmap bitmap) {
        mBgBitmapChecked = bitmap;
        drawableStateChanged();
    }
    public void setBackgroundUnable(Bitmap bitmap) {
        mBgBitmapUnable = bitmap;
        drawableStateChanged();
    }

    public void setGradientLinear(float angle, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.setGradientType(0);
        mGradientInfo.setGradientMode(mode);
        mGradientInfo.setGradientAngle(angle);
        mGradientInfo.setGradientColorArray(colors);
        mGradientInfo.setGradientPositions(positions);

        newGradient(mGradientInfo);
    }
    public void setGradientLinearNormal(float angle, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypeNormal = 0;
        mGradientInfo.setGradientModeNormal(mode);
        mGradientInfo.mAngleNormal = angle;
        mGradientInfo.mColorArrayNormal = colors;
        mGradientInfo.mPositionsNormal = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientLinearPressed(float angle, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypePressed = 0;
        mGradientInfo.setGradientModePressed(mode);
        mGradientInfo.mAnglePressed = angle;
        mGradientInfo.mColorArrayPressed = colors;
        mGradientInfo.mPositionsPressed = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientLinearChecked(float angle, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypeChecked = 0;
        mGradientInfo.setGradientModeChecked(mode);
        mGradientInfo.mAngleChecked = angle;
        mGradientInfo.mColorArrayChecked = colors;
        mGradientInfo.mPositionsChecked = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientLinearUnable(float angle, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypeUnable = 0;
        mGradientInfo.setGradientModeUnable(mode);
        mGradientInfo.mAngleUnable = angle;
        mGradientInfo.mColorArrayUnable = colors;
        mGradientInfo.mPositionsUnable = positions;

        newGradient(mGradientInfo);
    }

    public void setGradientRadial(float x, float y, float radius, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.setGradientType(1);
        mGradientInfo.setGradientMode(mode);
        mGradientInfo.setGradientCenterX(x);
        mGradientInfo.setGradientCenterY(y);
        mGradientInfo.setGradientRadius(radius);
        mGradientInfo.setGradientColorArray(colors);
        mGradientInfo.setGradientPositions(positions);

        newGradient(mGradientInfo);
    }
    public void setGradientRadialNormal(float x, float y, float radius, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypeNormal = 1;
        mGradientInfo.setGradientModeNormal(mode);
        mGradientInfo.mCenterXNormal = x;
        mGradientInfo.mCenterYNormal = y;
        mGradientInfo.mRadiusNormal = radius;
        mGradientInfo.mColorArrayNormal = colors;
        mGradientInfo.mPositionsNormal = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientRadialPressed(float x, float y, float radius, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypePressed = 1;
        mGradientInfo.setGradientModePressed(mode);
        mGradientInfo.mCenterXPressed = x;
        mGradientInfo.mCenterYPressed = y;
        mGradientInfo.mRadiusPressed = radius;
        mGradientInfo.mColorArrayPressed = colors;
        mGradientInfo.mPositionsPressed = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientRadialChecked(float x, float y, float radius, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypeChecked = 1;
        mGradientInfo.setGradientModeChecked(mode);
        mGradientInfo.mCenterXChecked = x;
        mGradientInfo.mCenterYChecked = y;
        mGradientInfo.mRadiusChecked = radius;
        mGradientInfo.mColorArrayChecked = colors;
        mGradientInfo.mPositionsChecked = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientRadialUnable(float x, float y, float radius, int[] colors, float[] positions, Shader.TileMode mode) {
        mGradientInfo.mTypeUnable = 1;
        mGradientInfo.setGradientModeUnable(mode);
        mGradientInfo.mCenterXUnable = x;
        mGradientInfo.mCenterYUnable = y;
        mGradientInfo.mRadiusUnable = radius;
        mGradientInfo.mColorArrayUnable = colors;
        mGradientInfo.mPositionsUnable = positions;

        newGradient(mGradientInfo);
    }

    public void setGradientSweep(float x, float y, float angle, int[] colors, float[] positions) {
        mGradientInfo.setGradientType(2);
        mGradientInfo.setGradientCenterX(x);
        mGradientInfo.setGradientCenterY(y);
        mGradientInfo.setGradientAngle(angle);
        mGradientInfo.setGradientColorArray(colors);
        mGradientInfo.setGradientPositions(positions);

        newGradient(mGradientInfo);
    }
    public void setGradientSweepNormal(float x, float y, float angle, int[] colors, float[] positions) {
        mGradientInfo.mTypeNormal = 2;
        mGradientInfo.mCenterXNormal = x;
        mGradientInfo.mCenterYNormal = y;
        mGradientInfo.mAngleNormal = angle;
        mGradientInfo.mColorArrayNormal = colors;
        mGradientInfo.mPositionsNormal = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientSweepPressed(float x, float y, float angle, int[] colors, float[] positions) {
        mGradientInfo.mTypePressed = 2;
        mGradientInfo.mCenterXPressed = x;
        mGradientInfo.mCenterYPressed = y;
        mGradientInfo.mAnglePressed = angle;
        mGradientInfo.mColorArrayPressed = colors;
        mGradientInfo.mPositionsPressed = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientSweepChecked(float x, float y, float angle, int[] colors, float[] positions) {
        mGradientInfo.mTypeChecked = 2;
        mGradientInfo.mCenterXChecked = x;
        mGradientInfo.mCenterYChecked = y;
        mGradientInfo.mAngleChecked = angle;
        mGradientInfo.mColorArrayChecked = colors;
        mGradientInfo.mPositionsChecked = positions;

        newGradient(mGradientInfo);
    }
    public void setGradientSweepUnable(float x, float y, float angle, int[] colors, float[] positions) {
        mGradientInfo.mTypeUnable = 2;
        mGradientInfo.mCenterXUnable = x;
        mGradientInfo.mCenterYUnable = y;
        mGradientInfo.mAngleUnable = angle;
        mGradientInfo.mColorArrayUnable = colors;
        mGradientInfo.mPositionsUnable = positions;

        newGradient(mGradientInfo);
    }

    public void setBorderWidth(float borderWidth) {
        if (borderWidth >= 0) {
            mBorderWidthNormal = mBorderWidthPressed = mBorderWidthChecked = mBorderWidthUnable = borderWidth;
            drawableStateChanged();
        }
    }
    public void setBorderWidthNormal(float borderWidth) {
        if (borderWidth >= 0) {
            mBorderWidthNormal = borderWidth;
            drawableStateChanged();
        }
    }
    public void setBorderWidthPressed(float borderWidth) {
        if (borderWidth >= 0) {
            mBorderWidthPressed = borderWidth;
            drawableStateChanged();
        }
    }
    public void setBorderWidthChecked(float borderWidth) {
        if (borderWidth >= 0) {
            mBorderWidthChecked = borderWidth;
            drawableStateChanged();
        }
    }
    public void setBorderWidthUnable(float borderWidth) {
        if (borderWidth >= 0) {
            mBorderWidthUnable = borderWidth;
            drawableStateChanged();
        }
    }

    public void setBorderColor(@ColorInt int borderColor) {
        mBorderColorNormal = mBorderColorPressed = mBorderColorChecked = mBorderColorUnable = borderColor;
        drawableStateChanged();
    }
    public void setBorderColorNormal(@ColorInt int borderColor) {
        mBorderColorNormal = borderColor;
        drawableStateChanged();
    }
    public void setBorderColorPressed(@ColorInt int borderColor) {
        mBorderColorPressed = borderColor;
        drawableStateChanged();
    }
    public void setBorderColorChecked(@ColorInt int borderColor) {
        mBorderColorChecked = borderColor;
        drawableStateChanged();
    }
    public void setBorderColorUnable(@ColorInt int borderColor) {
        mBorderColorUnable = borderColor;
        drawableStateChanged();
    }

    public void setBorderDash(float... dash) {
        if (dash.length > 1 && dash[0] > 0 && dash[1] > 0) {
            mBorderDashPathEffect = new DashPathEffect(dash, 0);
        } else {
            mBorderDashPathEffect = null;
        }
        if (mView != null) {
            mView.invalidate();
        }
    }


    // View 支持
    public void onAttachedToWindow(T view) {
        mView = view;
    }

    public void onDetachedFromWindow() {
        mView = null;
    }

    public void onSizeChanged(int w, int h) {
        mLayerRect.set(0, 0, w, h);
        refreshRegion();
    }

    public void saveLayer(Canvas canvas) {
        canvas.saveLayer(mLayerRect, null, Canvas.ALL_SAVE_FLAG);

        if (mBgColor != Color.TRANSPARENT) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawColor(mBgColor);
        } else if (mBgBitmap != null) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            canvas.drawBitmap(mBgBitmap, null, mAreas, mPaint);
        }

        if (mGradient != null) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setShader(mGradient);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mClipPath, mPaint);
            mPaint.setShader(null);
        }
    }

    public void trySaveLayer(Canvas canvas) {
        if (mView.getBackground() == null) {
            canvas.saveLayer(mLayerRect, null, Canvas.ALL_SAVE_FLAG);

            if (mBgColor != Color.TRANSPARENT) {
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                canvas.drawColor(mBgColor);
            } else if (mBgBitmap != null) {
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                canvas.drawBitmap(mBgBitmap, null, mAreas, mPaint);
            }

            if (mGradient != null) {
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                mPaint.setShader(mGradient);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawPath(mClipPath, mPaint);
                mPaint.setShader(null);
            }
        }
    }

    public void onClipDraw(Canvas canvas) {
        if (mBorderWidth > 0 && mBorderColor != Color.TRANSPARENT) {
            if (mBorderDashPathEffect != null) {
                mPaint.setPathEffect(mBorderDashPathEffect);
            }
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(mBorderWidth * 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setColor(mBorderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);

            mPaint.setPathEffect(null);
        }

        if (mProspectColor != Color.TRANSPARENT) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawColor(mProspectColor);
        } else if (mProspectBitmap != null) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            canvas.drawBitmap(mProspectBitmap, null, mAreas, mPaint);
        }

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mPaddingMax);
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
            mViewWidth = mView.getWidth();
            mViewHeight = mView.getHeight();

            mPaddingStart = mView.getPaddingLeft();
            mPaddingTop = mView.getPaddingTop();
            mPaddingMax = Collections.max(Arrays.asList(mPaddingStart, mPaddingTop, mView.getPaddingRight(), mView.getPaddingBottom()));

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

            newGradient(mGradientInfo);
        }
    }


    private void newGradient(GradientInfo info) {
        mGradientNormal = newGradient(info.mTypeNormal, info.mModeNormal, info.mCenterXNormal, info.mCenterYNormal, info.mRadiusNormal, info.mAngleNormal, info.mColorArrayNormal, info.mPositionsNormal, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        mGradientPressed = newGradient(info.mTypePressed, info.mModePressed, info.mCenterXPressed, info.mCenterYPressed, info.mRadiusPressed, info.mAnglePressed, info.mColorArrayPressed, info.mPositionsPressed, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        mGradientChecked = newGradient(info.mTypeChecked, info.mModeChecked, info.mCenterXChecked, info.mCenterYChecked, info.mRadiusChecked, info.mAngleChecked, info.mColorArrayChecked, info.mPositionsChecked, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        mGradientUnable = newGradient(info.mTypeUnable, info.mModeUnable, info.mCenterXUnable, info.mCenterYUnable, info.mRadiusUnable, info.mAngleUnable, info.mColorArrayUnable, info.mPositionsUnable, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        drawableStateChanged();
    }


    // Selector 支持
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
        mProspectColor = mProspectColorNormal;
        mProspectBitmap = mProspectBitmapNormal;
        mBgColor = mBgColorNormal;
        mBgBitmap = mBgBitmapNormal;
        mGradient = mGradientNormal;
        mBorderColor = mBorderColorNormal;
        mBorderWidth = mBorderWidthNormal;
        if (mView instanceof TextView && mTextColorNormal != Color.TRANSPARENT) {
            ((TextView) mView).setTextColor(mTextColorNormal);
        }
        if (mView instanceof Checkable) {
            if (((Checkable) mView).isChecked()) {
                mProspectColor = mProspectColorChecked;
                mProspectBitmap = mProspectBitmapChecked;
                mBgColor = mBgColorChecked;
                mBgBitmap = mBgBitmapChecked;
                mGradient = mGradientChecked;
                mBorderColor = mBorderColorChecked;
                mBorderWidth = mBorderWidthChecked;
                if (mView instanceof TextView && mTextColorChecked != Color.TRANSPARENT) {
                    ((TextView) mView).setTextColor(mTextColorChecked);
                }
            }
            if (mView.isEnabled()) {
                if (mView.isPressed() || mView.isFocused()) {
                    mProspectColor = mProspectColorPressed;
                    mProspectBitmap = mProspectBitmapPressed;
                    mBgColor = mBgColorPressed;
                    mBgBitmap = mBgBitmapPressed;
                    mGradient = mGradientPressed;
                    mBorderColor = mBorderColorPressed;
                    mBorderWidth = mBorderWidthPressed;
                    if (mView instanceof TextView && mTextColorPressed != Color.TRANSPARENT) {
                        ((TextView) mView).setTextColor(mTextColorPressed);
                    }
                }
            } else {
                mProspectColor = mProspectColorUnable;
                mProspectBitmap = mProspectBitmapUnable;
                mBgColor = mBgColorUnable;
                mBgBitmap = mBgBitmapUnable;
                mGradient = mGradientUnable;
                mBorderColor = mBorderColorUnable;
                mBorderWidth = mBorderWidthUnable;
                if (mView instanceof TextView && mTextColorUnable != Color.TRANSPARENT) {
                    ((TextView) mView).setTextColor(mTextColorUnable);
                }
            }
        }
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setOnCheckedChangeListener(ViewHelper.OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean isChecked);
    }



    // 下拉关闭支持
    private float mEndTranslationX = 0, mEndTranslationY = 0;
    private float mPreviousX, mPreviousY;
    private boolean isScrollingUp;
    private boolean isDropdownClose = false;
    private Drawable mDropdownCloseBackground;
    private DropdownCloseListener mDropdownCloseListener;

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isDropdownClose && ev.getPointerCount() == 1) {                                         // 判断几指操作，大于1时认为在对图片进行放大缩小操作，不拦截事件 交由下面控件处理
            float x = ev.getRawX();
            float y = ev.getRawY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPreviousX = x;
                    mPreviousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float diffX = x - mPreviousX;
                    float diffY = y - mPreviousY;
                    if (diffY > 0 && Math.abs(diffX) + 50 < Math.abs(diffY)) {                      // 当下拉时，当Y轴移动距离大于X轴50个单位时拦截事件，进入onTouchEvent开始处理下滑退出效果
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isDropdownClose) {
            float x = ev.getRawX();
            float y = ev.getRawY();

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPreviousX = x;
                    mPreviousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float diffX = x - mPreviousX;
                    float diffY = y - mPreviousY;
                    isScrollingUp = diffY <= 0;                                                     // 判断手指向上还是向下移动，关联手指抬起后的动画位移方向

                    mView.setTranslationX(diffX);
                    mView.setTranslationY(diffY);
                    if (diffY > 0) {
                        float scale = 1 - diffY / (mViewHeight - mPreviousY);
                        scale = scale > 0.5 ? scale : 0.5f;
                        scale = scale > 1 ? 1 : scale;
                        mView.setScaleX(scale);
                        mView.setScaleY(scale);
                    } else {
                        mView.setScaleX(1);
                        mView.setScaleY(1);
                    }

                    if (!isScrollingUp && mDropdownCloseBackground != null) {
                        int alpha = (int) ((255 * Math.abs(diffY)) / mViewHeight);                  // 透明度跟随手指的移动距离发生变化
                        mDropdownCloseBackground.setAlpha(255 - alpha);
                        mDropdownCloseListener.onAlphaChange(alpha / 255f);                         // 回调给外面做更多操作
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!isScrollingUp && Math.abs(mView.getTranslationY()) > (mViewHeight / 3f)) { // 滑动距离超过临界值才执行退出动画，临界值为控件高度1/3
                        layoutExitAnim();                                                           // 执行退出动画
                    } else {
                        layoutRecoverAnim();                                                        // 执行恢复动画
                    }
            }
            return true;
        }
        return false;
    }

    public void layoutExitAnim() {
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationX", mView.getTranslationX(), mEndTranslationX);
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationY", mView.getTranslationY(), mEndTranslationY);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("scaleX", mView.getScaleX(), 0.2f);
        PropertyValuesHolder p4 = PropertyValuesHolder.ofFloat("scaleY", mView.getScaleY(), 0.2f);
        PropertyValuesHolder p5 = PropertyValuesHolder.ofFloat("alpha", mView.getAlpha(), 0);
        ObjectAnimator exitAnim = ObjectAnimator.ofPropertyValuesHolder(mView, p1, p2, p3, p4, p5);


        final int alpha = mDropdownCloseBackground != null ? mDropdownCloseBackground.getAlpha() : 255;
        exitAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mDropdownCloseBackground != null) {                                             // 动画结束时将背景置为完全透明
                    mDropdownCloseBackground.setAlpha(0);
                }
                if (mDropdownCloseListener != null) {                                               // 执行回调，退出页面
                    mDropdownCloseListener.onClosed();
                }
            }
        });
        exitAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (mDropdownCloseBackground != null) {
                    mDropdownCloseBackground.setAlpha((int) (alpha * (1 - (animation.getCurrentPlayTime() - animation.getStartDelay()) * 1f / animation.getDuration())));
                }
            }
        });
        exitAnim.setDuration(300);
        exitAnim.start();
    }

    private void layoutRecoverAnim() {
        mView.animate().x(0).y(0).scaleX(1).scaleY(1).setDuration(100).start();                     // 从手指抬起的地方恢复到原点
        if (mDropdownCloseBackground != null) {
            mDropdownCloseBackground.setAlpha(255);                                                 // 将背景置为完全不透明
        }
        if (mDropdownCloseListener != null) {
            mDropdownCloseListener.onRecover();
        }
    }

    public void setDropdownCloseGradualBackground(Drawable drawable) {
        mDropdownCloseBackground = drawable;
    }

    public void setDropdownCloseListener(DropdownCloseListener listener) {
        mDropdownCloseListener = listener;
    }

    public void setIsDropdownClose(boolean isDropdownClose) {
        this.isDropdownClose = isDropdownClose;
    }

    public void setDropdownCloseTranslation(float x, float y) {
        mEndTranslationX = x - mViewWidth / 2f;
        mEndTranslationY = y - mViewHeight / 2f;
    }



    static Shader newGradient(int type, int mode, float centerX, float centerY, float radius, float angle, int[] colors, float[] positions, RectF areas, int width, int height, boolean isAreaPadding, int paddingStart, int paddingTop) {
        Shader shader = null;
        if (colors != null && colors.length > 1) {
            Shader.TileMode tileMode;
            switch (mode) {
                case 1:
                    tileMode = Shader.TileMode.REPEAT;
                    break;
                case 2:
                    tileMode = Shader.TileMode.MIRROR;
                    break;
                default:
                    tileMode = Shader.TileMode.CLAMP;
            }
            switch (type) {
                case 0:
                    float[] gLP = computeLineForAngle(areas.right - areas.left, areas.bottom - areas.top, angle, isAreaPadding, paddingStart, paddingTop);
                    shader = new LinearGradient(gLP[0], gLP[1], gLP[2], gLP[3], colors, positions, tileMode);
                    break;
                case 1:
                    if (radius > 0 && centerX >= 0 && centerX <= 1 && centerY >= 0 && centerY <= 1) {
                        shader = new RadialGradient(width * centerX, height * centerY, radius, colors, positions, tileMode);
                    }
                    break;
                case 2:
                    if (centerX >= 0 && centerX <= 1 && centerY >= 0 && centerY <= 1) {
                        shader = new SweepGradient(width * centerX, height * centerY, colors, positions);
                        if (angle % 360 != 0) {
                            Matrix matrix = new Matrix();
                            matrix.setRotate(angle, width * centerX, height * centerY);
                            shader.setLocalMatrix(matrix);
                        }
                    }
                    break;
            }
        }
        return shader;
    }

    static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    static int[] getColorArray(Context context, TypedArray ta, int attrId, int[] def) {
        int colorArrayID = ta.getResourceId(attrId, -1);
        if (colorArrayID != -1) {
            String typeName = context.getResources().getResourceTypeName(colorArrayID);
            if ("array".equals(typeName)) {
                String[] arrayStr = context.getResources().getStringArray(colorArrayID);
                int[] arrayInt = context.getResources().getIntArray(colorArrayID);
                int length = Math.min(arrayInt.length, arrayStr.length);
                if (length > 1) {
                    int[] colorArray = new int[length];
                    String strIndex;
                    int intIndex;
                    for (int i = 0; i < length; i++) {
                        strIndex = arrayStr[i];
                        intIndex = arrayInt[i];
                        colorArray[i] = !TextUtils.isEmpty(strIndex) ? Color.parseColor(strIndex) : intIndex;
                    }
                    return colorArray;
                }
            }
        }
        return def;
    }

    static float[] getFloatArray(Context context, TypedArray ta, int attrId, float[] def) {
        int floatArrayID = ta.getResourceId(attrId, -1);
        if (floatArrayID != -1) {
            String typeName = context.getResources().getResourceTypeName(floatArrayID);
            if ("array".equals(typeName)) {
                String[] arrayStr = context.getResources().getStringArray(floatArrayID);
                int[] arrayInt = context.getResources().getIntArray(floatArrayID);
                int length = Math.min(arrayInt.length, arrayStr.length);
                if (length > 1) {
                    float[] fArray = new float[length];
                    String strIndex;
                    int intIndex;
                    for (int i = 0; i < length; i++) {
                        strIndex = arrayStr[i];
                        intIndex = arrayInt[i];
                        fArray[i] = !TextUtils.isEmpty(strIndex) ? Float.parseFloat(strIndex) : intIndex;
                    }
                    return fArray;
                }
            }
        }
        return def;
    }

    static float[] computeLineForAngle(float width, float height, float angle, boolean isAreaPadding, int paddingStart, int paddingTop) {
        float x0 = 0;
        float y0 = 0;
        float x1 = 0;
        float y1 = 0;
        angle = angle % 360;
        if (angle >= 0 && angle <= 90) {
            if (angle == 0) {
                x0 = 0;
                x1 = width;
                y0 = height / 2;
                y1 = height / 2;
            } else if (angle == 90) {
                x0 = width / 2;
                x1 = width / 2;
                y0 = 0;
                y1 = height;
            } else {
                y0 = (float) (height / 2 - width / 2 * Math.tan(Math.toRadians(angle)));
                if (y0 >= 0) {
                    y1 = height - y0;
                    x0 = 0;
                    x1 = width;
                } else {
                    x0 = (float) (width / 2 - height / 2 * Math.tan(Math.toRadians(90 - angle)));
                    x1 = width - x0;
                    y0 = 0;
                    y1 = height;
                }
            }
        } else if (angle > 90 && angle <= 180) {
            if (angle == 180) {
                x0 = width;
                x1 = 0;
                y0 = height / 2;
                y1 = height / 2;
            } else {
                x0 = (float) (width / 2 + height / 2 * Math.tan(Math.toRadians(angle - 90)));
                if (x0 <= width) {
                    x1 = width - x0;
                    y0 = 0;
                    y1 = height;
                } else {
                    x0 = width;
                    x1 = 0;
                    y0 = (float) (height / 2 - width / 2 * Math.tan(Math.toRadians(180 - angle)));
                    y1 = height - y0;
                }
            }
        } else if (angle > 180 && angle <= 270) {
            if (angle == 270) {
                x0 = width / 2;
                x1 = width / 2;
                y0 = height;
                y1 = 0;
            } else {
                y0 = (float) (height / 2 + width / 2 * Math.tan(Math.toRadians(angle - 180)));
                if (y0 <= height) {
                    y1 = height - y0;
                    x0 = width;
                    x1 = 0;
                } else {
                    x0 = (float) (width / 2 + height / 2 * Math.tan(Math.toRadians(270 - angle)));
                    x1 = width - x0;
                    y0 = height;
                    y1 = 0;
                }
            }
        } else if (angle > 270 && angle < 360) {
            x0 = (float) (width / 2 - height / 2 * Math.tan(Math.toRadians(angle - 270)));
            if (x0 >= 0) {
                x1 = width - x0;
                y0 = height;
                y1 = 0;
            } else {
                x0 = 0;
                x1 = width;
                y0 = (float) (height / 2 + width / 2 * Math.tan(Math.toRadians(360 - angle)));
                y1 = height - y0;
            }
        }
        if (!isAreaPadding) {
            x0 += paddingStart;
            x1 += paddingStart;
            y0 += paddingTop;
            y1 += paddingTop;
        }
        return new float[]{x0, y0, x1, y1};
    }

    static class GradientInfo {
        int mTypeNormal = -1,mTypePressed = -1, mTypeChecked = -1, mTypeUnable = -1;
        int mModeNormal = 0, mModePressed = 0, mModeChecked = 0, mModeUnable = 0;
        float mCenterXNormal = 0.5f, mCenterXPressed = 0.5f, mCenterXChecked = 0.5f, mCenterXUnable = 0.5f;
        float mCenterYNormal = 0.5f, mCenterYPressed = 0.5f, mCenterYChecked = 0.5f, mCenterYUnable = 0.5f;
        float mRadiusNormal = 0, mRadiusPressed = 0, mRadiusChecked = 0, mRadiusUnable = 0;
        float mAngleNormal = 0, mAnglePressed = 0, mAngleChecked = 0, mAngleUnable = 0;
        int[] mColorArrayNormal, mColorArrayPressed, mColorArrayChecked, mColorArrayUnable;
        float[] mPositionsNormal, mPositionsPressed, mPositionsChecked, mPositionsUnable;

        void setGradientType(int type) {
            mTypeNormal = mTypePressed = mTypeChecked = mTypeUnable = type;
        }

        void setGradientMode(Shader.TileMode mode) {
            if (mode == Shader.TileMode.CLAMP) {
                setGradientMode(0);
            } else if (mode == Shader.TileMode.REPEAT) {
                setGradientMode(1);
            } else if (mode == Shader.TileMode.MIRROR) {
                setGradientMode(2);
            }
        }

        void setGradientModeNormal(Shader.TileMode mode) {
            if (mode == Shader.TileMode.CLAMP) {
                mModeNormal = 0;
            } else if (mode == Shader.TileMode.REPEAT) {
                mModeNormal = 1;
            } else if (mode == Shader.TileMode.MIRROR) {
                mModeNormal = 2;
            }
        }
        void setGradientModePressed(Shader.TileMode mode) {
            if (mode == Shader.TileMode.CLAMP) {
                mModePressed = 0;
            } else if (mode == Shader.TileMode.REPEAT) {
                mModePressed = 1;
            } else if (mode == Shader.TileMode.MIRROR) {
                mModePressed = 2;
            }
        }
        void setGradientModeChecked(Shader.TileMode mode) {
            if (mode == Shader.TileMode.CLAMP) {
                mModeChecked = 0;
            } else if (mode == Shader.TileMode.REPEAT) {
                mModeChecked = 1;
            } else if (mode == Shader.TileMode.MIRROR) {
                mModeChecked = 2;
            }
        }
        void setGradientModeUnable(Shader.TileMode mode) {
            if (mode == Shader.TileMode.CLAMP) {
                mCenterXUnable = 0;
            } else if (mode == Shader.TileMode.REPEAT) {
                mCenterXUnable = 1;
            } else if (mode == Shader.TileMode.MIRROR) {
                mCenterXUnable = 2;
            }
        }

        void setGradientMode(int mode) {
            mModeNormal = mModePressed = mModeChecked = mModeUnable = mode;
        }

        void setGradientCenterX(float x) {
            mCenterXNormal = mCenterXPressed = mCenterXChecked = mCenterXUnable = x;
        }

        void setGradientCenterY(float y) {
            mCenterYNormal = mCenterYPressed = mCenterYChecked = mCenterYUnable = y;
        }

        void setGradientRadius(float radius) {
            mRadiusNormal = mRadiusPressed = mRadiusChecked = mRadiusUnable = radius;
        }

        void setGradientAngle(float angle) {
            mAngleNormal = mAnglePressed = mAngleChecked = mAngleUnable = angle;
        }

        void setGradientColorArray(int[] colorArray) {
            mColorArrayNormal = mColorArrayPressed = mColorArrayChecked = mColorArrayUnable = colorArray;
        }

        void setGradientPositions(float[] positions) {
            mPositionsNormal = mPositionsPressed = mPositionsChecked = mPositionsUnable = positions;
        }
    }

    public interface DropdownCloseListener {
        // 正在滑动
        void onAlphaChange(float alpha);

        // 关闭布局
        void onClosed();

        // 恢复布局，滑动结束并且没有触发关闭
        void onRecover();
    }
}
