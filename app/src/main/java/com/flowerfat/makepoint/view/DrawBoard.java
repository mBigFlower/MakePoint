package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2015/9/19.
 */
public class DrawBoard extends View implements View.OnTouchListener{

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private List<Path> pathList = new ArrayList<Path>();

    public DrawBoard(Context context) {
        super(context);
    }

    public DrawBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setOnTouchListener(this);
    }

    public DrawBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        setBackgroundColor(0x1444);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                pathList.add(mPath);
                break;
        }

        return true;
    }

    /**
     * 撤销功能
     */
    public void toLastPath(){
        int length = pathList.size() - 1 ;
        Log.i("xixixi", length + "  ");
        if(length >= 0){
            clear();
            pathList.remove(length--);
            mPath = pathList.get(length);
            invalidate();
        }
    }

    /**
     * 清屏
     */
    public void clear(){
        mPath.reset();
        invalidate();
    }
}
