package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.flowerfat.makepoint.utils.SpInstance;
import com.google.gson.Gson;

/**
 * Created by 明明大美女 on 2015/9/19.
 *
 * 画板，用 SurfaceView 这个靠谱点
 *
 * 但是撤销效果不好，暂时先不弄了
 */
public class DrawBoardSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {


    private Paint mPaint = new Paint();
    private PathPlus mPath = new PathPlus();
    // 保存路径
    private int backgroundColor;

    public DrawBoardSurface(Context context) {
        super(context);
    }

    public DrawBoardSurface(Context context, AttributeSet attrs) {
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

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawPath(mPath, mPaint);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("有点乱", "surfaceCreated");
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    /**
     * 清屏
     */
    public void clear() {
        clearDrawBoard();
    }

    private void clearDrawBoard(){
        mPath.reset();
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        getHolder().unlockCanvasAndPost(canvas);
    }

    /**
     * 外部调用，设置board的背景色
     *
     * @param color
     */
    public void setBoardColor(int color) {
        backgroundColor = color;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                draw();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    public String save(){
//        if (mPath == null || mPath.isEmpty()) {
//            return "还是画点什么吧~";
//        }
//        Point point = Utils.color2Point(backgroundColor);
//        point.setImgPath(mPath);
//        point.update();

        SpInstance.get().pString("xixi22", new Gson().toJson(mPath));
        return "保存成功";
    }
    public void release() {
        getHolder().removeCallback(this);
    }
}
