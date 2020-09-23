package com.hemaapp.hm_FrameWork.downtoclose;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.TextView;


import com.hemaapp.hm_FrameWork.R;

import static android.view.View.LAYER_TYPE_SOFTWARE;

/**
 * 描述: View 扩展帮助类，不支持：前景图、背景图、边框、下拉关闭；；由于阴影功能关闭了 ParentView 的硬件加速，所以 ParentView 之上的 ParentView 设置 setClipChildren(false) 将失效
 * 作者: Torres
 * 时间: 2019/1/26
 * 版本: V1.0
 */
public class ViewHelperShadow<T extends View> {
    private T mView;
    private AttributeSet mAttrs;
    private int mDefStyleAttr;
    private int mDefStyleRes;

    private int mPaddingStart = 0;
    private int mPaddingTop = 0;
    private int mViewWidth = 0;
    private int mViewHeight = 0;

    private Paint mPaint = new Paint();
    private Path mClipPath = new Path();
    private RectF mLayerRect = new RectF();
    private Region mAreaRegion = new Region();

    private boolean isAreaPadding = true;
    private boolean isAreaClick = false;

    private boolean isCircle = false;
    private float[] mRadiusArray = new float[8];

    private float mShadowRadius = 0;
    private float mShadowRadiusNormal = 0;
    private float mShadowRadiusPressed = 0;
    private float mShadowRadiusChecked = 0;
    private float mShadowRadiusUnable = 0;
    private float mShadowOffsetX = 0;
    private float mShadowOffsetXNormal = 0;
    private float mShadowOffsetXPressed = 0;
    private float mShadowOffsetXChecked = 0;
    private float mShadowOffsetXUnable = 0;
    private float mShadowOffsetY = 0;
    private float mShadowOffsetYNormal = 0;
    private float mShadowOffsetYPressed = 0;
    private float mShadowOffsetYChecked = 0;
    private float mShadowOffsetYUnable = 0;

    private @ColorInt int mShadowColor = Color.TRANSPARENT;
    private @ColorInt int mShadowColorNormal = Color.TRANSPARENT;
    private @ColorInt int mShadowColorPressed = Color.TRANSPARENT;
    private @ColorInt int mShadowColorChecked = Color.TRANSPARENT;
    private @ColorInt int mShadowColorUnable = Color.TRANSPARENT;

    private @ColorInt int mTextColorNormal;
    private @ColorInt int mTextColorPressed;
    private @ColorInt int mTextColorChecked;
    private @ColorInt int mTextColorUnable;

    private @ColorInt int mProspectColor = Color.TRANSPARENT;                     // 前景颜色
    private @ColorInt int mProspectColorNormal = Color.TRANSPARENT;
    private @ColorInt int mProspectColorPressed = Color.TRANSPARENT;
    private @ColorInt int mProspectColorChecked = Color.TRANSPARENT;
    private @ColorInt int mProspectColorUnable = Color.TRANSPARENT;

    private @ColorInt int mBgColor = Color.TRANSPARENT;
    private @ColorInt int mBgColorNormal = Color.TRANSPARENT;
    private @ColorInt int mBgColorPressed = Color.TRANSPARENT;
    private @ColorInt int mBgColorChecked = Color.TRANSPARENT;
    private @ColorInt int mBgColorUnable = Color.TRANSPARENT;

    private Shader mGradient, mGradientNormal, mGradientPressed, mGradientChecked, mGradientUnable;
    private ViewHelper.GradientInfo mGradientInfo = new ViewHelper.GradientInfo();


    private RectF mAreas = new RectF();
    private Region mClip = new Region();
    private PointF mCenter = new PointF();

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

            mShadowRadius = ta.getDimension(R.styleable.ViewHelper_vh_shadowRadius, 0);
            mShadowRadiusNormal = ta.getDimension(R.styleable.ViewHelper_vh_shadowRadiusNormal, mShadowRadius);
            mShadowRadius = mShadowRadiusNormal;
            mShadowRadiusPressed = ta.getDimension(R.styleable.ViewHelper_vh_shadowRadiusPressed, mShadowRadiusNormal);
            mShadowRadiusChecked = ta.getDimension(R.styleable.ViewHelper_vh_shadowRadiusChecked, mShadowRadiusNormal);
            mShadowRadiusUnable = ta.getDimension(R.styleable.ViewHelper_vh_shadowRadiusUnable, mShadowRadiusNormal);

            mShadowOffsetX = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetX, 0);
            mShadowOffsetXNormal = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetXNormal, mShadowOffsetX);
            mShadowOffsetX = mShadowOffsetXNormal;
            mShadowOffsetXPressed = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetXPressed, mShadowOffsetXNormal);
            mShadowOffsetXChecked = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetXChecked, mShadowOffsetXNormal);
            mShadowOffsetXUnable = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetXUnable, mShadowOffsetXNormal);

            mShadowOffsetY = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetY, 0);
            mShadowOffsetYNormal = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetYNormal, mShadowOffsetY);
            mShadowOffsetY = mShadowOffsetYNormal;
            mShadowOffsetYPressed = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetYPressed, mShadowOffsetYNormal);
            mShadowOffsetYChecked = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetYChecked, mShadowOffsetYNormal);
            mShadowOffsetYUnable = ta.getDimension(R.styleable.ViewHelper_vh_shadowOffsetYUnable, mShadowOffsetYNormal);

            mShadowColor = ta.getColor(R.styleable.ViewHelper_vh_shadowColor, Color.TRANSPARENT);
            mShadowColorNormal = ta.getColor(R.styleable.ViewHelper_vh_shadowColorNormal, mShadowColor);
            mShadowColor = mShadowColorNormal;
            mShadowColorPressed = ta.getColor(R.styleable.ViewHelper_vh_shadowColorPressed, mShadowColorNormal);
            mShadowColorChecked = ta.getColor(R.styleable.ViewHelper_vh_shadowColorChecked, mShadowColorNormal);
            mShadowColorUnable = ta.getColor(R.styleable.ViewHelper_vh_shadowColorUnable, mShadowColorNormal);

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

            mBgColor = ta.getColor(R.styleable.ViewHelper_vh_backgroundColor, Color.TRANSPARENT);
            mBgColorNormal = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorNormal, mBgColor);
            mBgColor = mBgColorNormal;
            mBgColorPressed = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorPressed, mBgColorNormal);
            mBgColorChecked = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorChecked, mBgColorNormal);
            mBgColorUnable = ta.getColor(R.styleable.ViewHelper_vh_backgroundColorUnable, mBgColorNormal);


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
                    mGradientInfo.mColorArrayNormal = ViewHelper.getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayNormal, ViewHelper.getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArray, null));
                    mGradientInfo.mColorArrayPressed = ViewHelper.getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayPressed, mGradientInfo.mColorArrayNormal);
                    mGradientInfo.mColorArrayChecked = ViewHelper.getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayChecked, mGradientInfo.mColorArrayNormal);
                    mGradientInfo.mColorArrayUnable = ViewHelper.getColorArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorArrayUnable, mGradientInfo.mColorArrayNormal);
                }

                mGradientInfo.mPositionsNormal = ViewHelper.getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayNormal, ViewHelper.getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArray, null));
                mGradientInfo.mPositionsPressed = ViewHelper.getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayPressed, mGradientInfo.mPositionsNormal);
                mGradientInfo.mPositionsChecked = ViewHelper.getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayChecked, mGradientInfo.mPositionsNormal);
                mGradientInfo.mPositionsUnable = ViewHelper.getFloatArray(mView.getContext(), ta, R.styleable.ViewHelper_vh_gradientColorPositionArrayUnable, mGradientInfo.mPositionsNormal);

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

    public void setShadowColor(@ColorInt int shadowColor) {
        mShadowColorNormal = mShadowColorPressed = mShadowColorChecked = mShadowColorUnable = shadowColor;
        drawableStateChanged();
    }
    public void setShadowColorNormal(@ColorInt int shadowColor) {
        mShadowColorNormal = shadowColor;
        drawableStateChanged();
    }
    public void setShadowColorPressed(@ColorInt int shadowColor) {
        mShadowColorPressed = shadowColor;
        drawableStateChanged();
    }
    public void setShadowColorChecked(@ColorInt int shadowColor) {
        mShadowColorChecked = shadowColor;
        drawableStateChanged();
    }
    public void setShadowColorUnable(@ColorInt int shadowColor) {
        mShadowColorUnable = shadowColor;
        drawableStateChanged();
    }

    public void setShadowRadius(int shadowRadius) {
        if (shadowRadius >= 0) {
            mShadowRadiusNormal = mShadowRadiusPressed = mShadowRadiusChecked = mShadowRadiusUnable = shadowRadius;
            drawableStateChanged();
        }
    }
    public void setShadowRadiusNormal(int shadowRadius) {
        if (shadowRadius >= 0) {
            mShadowRadiusNormal = shadowRadius;
            drawableStateChanged();
        }
    }
    public void setShadowRadiusPressed(int shadowRadius) {
        if (shadowRadius >= 0) {
            mShadowRadiusPressed = shadowRadius;
            drawableStateChanged();
        }
    }
    public void setShadowRadiusChecked(int shadowRadius) {
        if (shadowRadius >= 0) {
            mShadowRadiusChecked = shadowRadius;
            drawableStateChanged();
        }
    }
    public void setShadowRadiusUnable(int shadowRadius) {
        if (shadowRadius >= 0) {
            mShadowRadiusUnable = shadowRadius;
            drawableStateChanged();
        }
    }

    public void setShadowOffsetX(float shadowOffsetX) {
        mShadowOffsetXNormal = mShadowOffsetXPressed = mShadowOffsetXChecked = mShadowOffsetXUnable = shadowOffsetX;
        drawableStateChanged();
    }
    public void setShadowOffsetXNormal(float shadowOffsetX) {
        mShadowOffsetXNormal = shadowOffsetX;
        drawableStateChanged();
    }
    public void setShadowOffsetXPressed(float shadowOffsetX) {
        mShadowOffsetXPressed = shadowOffsetX;
        drawableStateChanged();
    }
    public void setShadowOffsetXChecked(float shadowOffsetX) {
        mShadowOffsetXChecked = shadowOffsetX;
        drawableStateChanged();
    }
    public void setShadowOffsetXUnable(float shadowOffsetX) {
        mShadowOffsetXUnable = shadowOffsetX;
        drawableStateChanged();
    }

    public void setShadowOffsetY(float shadowOffsetY) {
        mShadowOffsetYNormal = mShadowOffsetYPressed = mShadowOffsetYChecked = mShadowOffsetYUnable = shadowOffsetY;
        drawableStateChanged();
    }
    public void setShadowOffsetYNormal(float shadowOffsetY) {
        mShadowOffsetYNormal = shadowOffsetY;
        drawableStateChanged();
    }
    public void setShadowOffsetYPressed(float shadowOffsetY) {
        mShadowOffsetYPressed = shadowOffsetY;
        drawableStateChanged();
    }
    public void setShadowOffsetYChecked(float shadowOffsetY) {
        mShadowOffsetYChecked = shadowOffsetY;
        drawableStateChanged();
    }
    public void setShadowOffsetYUnable(float shadowOffsetY) {
        mShadowOffsetYUnable = shadowOffsetY;
        drawableStateChanged();
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

    public void onClipDraw(Canvas canvas) {
        if (mShadowRadius > 0 && mShadowColor != Color.TRANSPARENT) {
            ((ViewGroup) mView.getParent()).setClipChildren(false);
            if (((ViewGroup) mView.getParent()).getLayerType() != LAYER_TYPE_SOFTWARE) {
                ((ViewGroup) mView.getParent()).setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
            mPaint.setShadowLayer(mShadowRadius, mShadowOffsetX, mShadowOffsetY, mShadowColor);
        } else {
            mPaint.setShadowLayer(0, 0, 0, mShadowColor);
        }

        mPaint.setStyle(Paint.Style.FILL);
        if (mBgColor != Color.TRANSPARENT) {
            mPaint.setColor(mBgColor);
            canvas.drawPath(mClipPath, mPaint);
        }

        if (mGradient != null) {
            mPaint.setShader(mGradient);
            canvas.drawPath(mClipPath, mPaint);
            mPaint.setShader(null);
        }
    }

    public void onClipDrawProspect(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        if (mBgColor != Color.TRANSPARENT) {
            mPaint.setColor(mProspectColor);
            canvas.drawPath(mClipPath, mPaint);
        }
    }

    private void refreshRegion() {
        if (mView != null) {
            mViewWidth = mView.getWidth();
            mViewHeight = mView.getHeight();

            mPaddingStart = mView.getPaddingLeft();
            mPaddingTop = mView.getPaddingTop();

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

    private void newGradient(ViewHelper.GradientInfo info) {
        mGradientNormal = ViewHelper.newGradient(info.mTypeNormal, info.mModeNormal, info.mCenterXNormal, info.mCenterYNormal, info.mRadiusNormal, info.mAngleNormal, info.mColorArrayNormal, info.mPositionsNormal, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        mGradientPressed = ViewHelper.newGradient(info.mTypePressed, info.mModePressed, info.mCenterXPressed, info.mCenterYPressed, info.mRadiusPressed, info.mAnglePressed, info.mColorArrayPressed, info.mPositionsPressed, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        mGradientChecked = ViewHelper.newGradient(info.mTypeChecked, info.mModeChecked, info.mCenterXChecked, info.mCenterYChecked, info.mRadiusChecked, info.mAngleChecked, info.mColorArrayChecked, info.mPositionsChecked, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        mGradientUnable = ViewHelper.newGradient(info.mTypeUnable, info.mModeUnable, info.mCenterXUnable, info.mCenterYUnable, info.mRadiusUnable, info.mAngleUnable, info.mColorArrayUnable, info.mPositionsUnable, mAreas, mViewWidth, mViewHeight, isAreaPadding, mPaddingStart, mPaddingTop);
        drawableStateChanged();
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
        mProspectColor = mProspectColorNormal;
        mBgColor = mBgColorNormal;
        mGradient = mGradientNormal;
        mShadowColor = mShadowColorNormal;
        mShadowRadius = mShadowRadiusNormal;
        mShadowOffsetX = mShadowOffsetXNormal;
        mShadowOffsetY = mShadowOffsetYNormal;
        if (mView instanceof TextView && mTextColorNormal != Color.TRANSPARENT) {
            ((TextView) mView).setTextColor(mTextColorNormal);
        }
        if (mView instanceof Checkable) {
            if (((Checkable) mView).isChecked()) {
                mProspectColor = mProspectColorChecked;
                mBgColor = mBgColorChecked;
                mGradient = mGradientChecked;
                mShadowColor = mShadowColorChecked;
                mShadowRadius = mShadowRadiusChecked;
                mShadowOffsetX = mShadowOffsetXChecked;
                mShadowOffsetY = mShadowOffsetYChecked;
                if (mView instanceof TextView && mTextColorChecked != Color.TRANSPARENT) {
                    ((TextView) mView).setTextColor(mTextColorChecked);
                }
            }
            if (mView.isEnabled()) {
                if (mView.isPressed() || mView.isFocused()) {
                    mProspectColor = mProspectColorPressed;
                    mBgColor = mBgColorPressed;
                    mGradient = mGradientPressed;
                    mShadowColor = mShadowColorPressed;
                    mShadowRadius = mShadowRadiusPressed;
                    mShadowOffsetX = mShadowOffsetXPressed;
                    mShadowOffsetY = mShadowOffsetYPressed;
                    if (mView instanceof TextView && mTextColorPressed != Color.TRANSPARENT) {
                        ((TextView) mView).setTextColor(mTextColorPressed);
                    }
                }
            } else {
                mProspectColor = mProspectColorUnable;
                mBgColor = mBgColorUnable;
                mGradient = mGradientUnable;
                mShadowColor = mShadowColorUnable;
                mShadowRadius = mShadowRadiusUnable;
                mShadowOffsetX = mShadowOffsetXUnable;
                mShadowOffsetY = mShadowOffsetYUnable;
                if (mView instanceof TextView && mTextColorUnable != Color.TRANSPARENT) {
                    ((TextView) mView).setTextColor(mTextColorUnable);
                }
            }
        }
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setOnCheckedChangeListener(ViewHelperShadow.OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean isChecked);
    }
}
