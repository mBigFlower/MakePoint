package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.flowerfat.makepoint.utils.Utils;

/**
 * Created by Administrator on 2016/1/7.
 */
public class DragLineView extends View implements View.OnTouchListener {

    private static final int CIRCLE_R = 5;
    private static final int LINE_WIDTH = Utils.dp2px(1);

    private int viewWidth, lineY;

    private boolean isCircle;
    private Paint mPaint, bigPaint;
    private float pressX = -1;

    public DragLineView(Context context) {
        super(context);
        init();
    }

    public DragLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(LINE_WIDTH);

        bigPaint = new Paint();
        bigPaint.setColor(Color.WHITE);
        bigPaint.setStrokeWidth(LINE_WIDTH*3);

        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isCircle)
            drawCircle(canvas);
        drawLine(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(pressX, lineY, lineY, mPaint);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, lineY, viewWidth, lineY, mPaint);
        canvas.drawLine(0, lineY, pressX, lineY, bigPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = this.getMeasuredWidth();
        lineY = this.getMeasuredHeight() / 2 ;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isCircle = true;
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            pressX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isCircle = false;
        }

        invalidate();

        return false;
    }
}
