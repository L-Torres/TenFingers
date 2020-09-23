package com.beijing.tenfingers.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {

    //雷达填充颜色
    private int FILL_COLOR = 0x7f00574B;
    //雷达描点
    private int POINT_COLOR = 0xff008577;


    //控件大小
    private int width;
    private int height;
    //控件雷达最大边长
    private int maxRadius;
    private int margin = 70; //px，dp需要自己转换
    //N边形
    public static int borderCount = 6;
    //环线个数
    private int circleCount = 5;
    //an angle, in radians,与borderCount相关
    private double angle;

    private int titleTxSize = 28; //px，sp需要自己转换
    private int pointSize = 8; //px，dp需要自己转换
    //标签值
    public static String[] mTitles = {"a", "b", "c", "d", "e", "f"};
    //数据值，个数应该要和borderCount保持一致，不然要做兼容性处理
    public static int[] mData = new int[]{100, 60, 60, 60, 100, 70};
    //数据最大值
    private int maxValue = 100;

    //画笔
    private Paint paint;
    private Path path;


    public RadarView(Context context) {
        super(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        path = new Path();

        angle = 2 * Math.PI / borderCount;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        maxRadius = (Math.min(width, height) - margin) / 2;
        //坐标系移动到控件中心
        canvas.translate(width / 2, height / 2);
        //绘制雷达
        for (int j = 0; j < borderCount; j++) {
            canvas.rotate(360 / borderCount);
            canvas.drawLine(0, 0, maxRadius, 0, paint);
            for (int i = 1; i <= circleCount; i++) {
                int circleRadius = (int) (i * 1f / circleCount * maxRadius);
                float x = (float) (circleRadius * Math.cos(angle));
                float y = (float) (circleRadius * Math.sin(angle));
                canvas.drawLine(circleRadius, 0, x, y, paint);
            }
        }
        //绘制文字标签
        paint.setTextSize(titleTxSize);
        for (int i = 0; i < borderCount; i++) {
            int length = maxRadius + 20;
            int x = (int) (length * Math.cos(i * angle));
            int y = (int) (length * Math.sin(i * angle));
            canvas.drawText(mTitles[i], x, y, paint);
        }
        //绘制多边形并描点填充
        paint.setColor(POINT_COLOR);
        for (int i = 0; i < mData.length; i++) {
            float length = mData[i] * 1f / maxValue * maxRadius;
            int x = (int) (length * Math.cos(i * angle));
            int y = (int) (length * Math.sin(i * angle));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, pointSize, paint);
        }
//        path.close(); 填充模式下不close也不影响
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4);
        paint.setColor(FILL_COLOR);
        canvas.drawPath(path, paint);

    }
}
