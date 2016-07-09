package com.flowerfat.makepoint.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 明明大美女 on 2016/7/7.
 */
public class MaskingView extends View {

    /**
     * 默认宽高
     */
    private static final int DEFAULT_DIAMETER_SIZE = 120;

    // for test
    int circleX;
    Paint mPaint = new Paint();
    int width, height;
    private PorterDuffXfermode xfermode;
    // for test over

    public MaskingView(Context context) {
        this(context, null);
    }

    public MaskingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // do something init
        initRes();
    }

    private void initRes() {
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DIAMETER_SIZE, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DIAMETER_SIZE, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed) {
            width = getWidth();
            height = getHeight();

            moveCircle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas){
        canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.argb(240, 0, 0, 0));
        canvas.drawRect(0, 0, width, height, mPaint);

        mPaint.setXfermode(xfermode);
        mPaint.setColor(Color.argb(200, 0, 0, 0));
//        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(circleX, height / 2, 200, mPaint);
        //最后将画笔去除Xfermode
        mPaint.setXfermode(null);
        canvas.restore();
    }

    public void moveCircle(){
        ValueAnimator moveAnim = ValueAnimator.ofInt(200, width-200);
        moveAnim.setInterpolator(new LinearInterpolator());
        moveAnim.setDuration(1000);
        moveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleX = Integer.parseInt(animation.getAnimatedValue().toString());
                Log.e("onLayout",circleX+"");
                postInvalidate();
            }
        });
        moveAnim.start();
    }

}
