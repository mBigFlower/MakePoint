package com.flowerfat.path.cool;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.flowerfat.path.PathAnimUtil;

/**
 * Created by 明明大美女 on 2016/7/8.
 */
public class HeartJumpAnimView extends View {

    /**
     * 默认宽高
     */
    private static final int DEFAULT_DIAMETER_SIZE = 120;
    // 线条的颜色
    private final int LINE_COLOR = Color.RED;
    // 背景的颜色
    private final int BG_COLOR = Color.TRANSPARENT;
    // 线宽
    private final float LINE_WIDTH = 5;

    // 动画时间
    private int ANIM_TIME = 1000;
    // 动画是否在进行中
    private boolean isAniming;

    private Paint linePaint;

    private int width;
    private int height;
    int step;
    int firstX, lastX;
    int startX, centerY;

    PathAnimUtil mPathAnimUtil;

    public HeartJumpAnimView(Context context) {
        super(context);
        init();
    }

    public HeartJumpAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartJumpAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setBackgroundColor(BG_COLOR);

        linePaint = new Paint();
        linePaint.setColor(LINE_COLOR);
        linePaint.setStrokeWidth(LINE_WIDTH);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        mPathAnimUtil = new PathAnimUtil(linePaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getTheParams();
            Log.e("onLayout", "the layout is changed " + width + " " + height);
            mPathAnimUtil.setPath(getHeartJumpPath());
            animStart();
        }
    }


    /**
     * 计算所需要的值
     */
    private void getTheParams() {
        width = getWidth();
        height = getHeight();
        centerY = height / 2;
        Log.i("getTheParams", width + " " + height);
        // 我们需要一个宽高比为2:1的形状，为了方便后面的计算？
        if (height > width / 2) {
            height = width / 2;
        } else {
            startX = width / 2 - height;
            width = height * 2;
        }
        Log.i("getTheParams", width + " " + height);
        step = height / 12;
        firstX = height - step * 3;
        lastX = height - step * 2;
    }

    private Path getHeartJumpPath(){
        Path path = new Path();
        path.moveTo(startX, centerY);
        path.lineTo(firstX, centerY);
        path.rLineTo(step, -2 * step);
        path.rLineTo(step, 4 * step);
        path.rLineTo(step, -7 * step);
        path.rLineTo(step, 8 * step);
        path.rLineTo(step, -3 * step);
        path.rLineTo(lastX, 0);
        return path ;
    }

    public void animStart() {
        if (!isAniming) {
            isAniming = true;
            mPathAnimUtil.anim(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPathAnimUtil.onDraw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2 * DEFAULT_DIAMETER_SIZE, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DIAMETER_SIZE, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
