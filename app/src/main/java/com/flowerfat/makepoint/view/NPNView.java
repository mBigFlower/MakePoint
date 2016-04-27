package com.flowerfat.makepoint.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 明明大美女 on 2016/4/27.
 */
public class NPNView extends View {

    // 线条的颜色
    private final int PAINT_COLOR = 0xFFFFFFFF/*白色*/;
    // 背景的颜色
    private final int BG_COLOR = 0x00FFFFFF/*透明*/;
    // 控件默认尺寸
    private final int DEFAULT_WIDGET_SIZE = 240;
    // 线的宽度
    private final int LINE_WIDTH = 5;


    private Paint mPaint;

    public NPNView(Context context) {
        super(context);
    }

    public NPNView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(BG_COLOR);
        init();
    }

    public NPNView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(PAINT_COLOR);
        mPaint.setStrokeWidth(LINE_WIDTH);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        drawLine();
    }

    //////////////////////////////////////////////
    // 下面是属性动画
    //////////////////////////////////////////////
    float lineLength;

    public void drawLine() {
        ValueAnimator lineAnim = ValueAnimator.ofFloat(0, DEFAULT_WIDGET_SIZE);
        lineAnim.setDuration(10000);
        lineAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lineLength = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        lineAnim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画最顶上的那跟线
        canvas.drawLine(0, 0, DEFAULT_WIDGET_SIZE, 0, mPaint);
        drawNPN(canvas, DEFAULT_WIDGET_SIZE/4);
    }

    /**
     * 画电阻
     */
    private void drawR(){

    }

    /**
     * 画三极管
     * @param canvas
     * @param height
     */
    private void drawNPN(Canvas canvas, int height) {
        int halfSize = height / 2;
        canvas.drawLine(0, halfSize, 0, 0, mPaint);
        canvas.drawLine(0, halfSize, 0, height, mPaint);
        canvas.drawLine(0, halfSize, halfSize, 0, mPaint);
        canvas.drawLine(0, halfSize, halfSize, height, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDGET_SIZE, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDGET_SIZE, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public class timeCtrl {
        private int totalTime = 2000;

        public timeCtrl() {
        }

    }
}
