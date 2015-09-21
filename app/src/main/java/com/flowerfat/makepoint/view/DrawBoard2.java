package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2015/9/19.
 */
public class DrawBoard2 extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {


    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private List<Path> pathList = new ArrayList<Path>();

    public DrawBoard2(Context context) {
        super(context);
    }

    public DrawBoard2(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        setZOrderOnTop(true);//设置画布  背景透明
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public DrawBoard2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawPath(mPath, mPaint);

        getHolder().unlockCanvasAndPost(canvas);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    /**
     * 撤销功能
     */
    public void toLastPath() {
//        int lastIndex = pathList.size() - 1;
//        if (lastIndex >=  0) {
//            clear();
//            pathList.remove(lastIndex);
//            lastIndex = lastIndex - 1 ;
//            mPath = pathList.get(lastIndex);
//            draw();
//        }
    }

    /**
     * 清屏
     */
    public void clear() {
        mPath.reset();
        draw();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                draw();
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                draw();
                break;
            case MotionEvent.ACTION_UP:
                pathList.add(mPath);
                Log.i("xixixi", pathList.size() +"--数组的大小");
                break;
        }
        return true;
    }

}
