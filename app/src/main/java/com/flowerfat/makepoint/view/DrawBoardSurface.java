package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2015/9/19.
 *
 * 画板，用 SurfaceView 这个靠谱点
 *
 * 但是撤销效果不好，暂时先不弄了
 */
public class DrawBoardSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {


    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    // 保存路径
    private List<Path> pathList = new ArrayList<Path>();
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
     * 撤销功能
     */
    public void toLastPath() {
        int lastIndex = pathList.size() - 1;
        if (lastIndex > 0) {
            final Path path = pathList.get(lastIndex - 1);
            clearDrawBoard();
            pathList.remove(lastIndex);

            String pathStr = new Gson().toJson(path);
            mPath = new Gson().fromJson(pathStr, Path.class);
            draw();
        }
    }

    /**
     * 清屏
     */
    public void clear() {
        clearDrawBoard();
        pathList.clear();
    }

    private void clearDrawBoard(){
        mPath.reset();
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        getHolder().unlockCanvasAndPost(canvas);
    }

    public boolean isDrawed() {
        return !pathList.isEmpty();
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
                pathList.add(new Path(mPath));
                break;
        }
        return true;
    }


    public void release() {
        getHolder().removeCallback(this);
    }
}
