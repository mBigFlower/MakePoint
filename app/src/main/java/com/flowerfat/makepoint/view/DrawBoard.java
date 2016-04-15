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

import com.flowerfat.makepoint.entity.db.Point;
import com.flowerfat.makepoint.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2015/9/19.
 * <p/>
 * 画板 用 View ， 貌似没 SurfaceView 好。
 */
public class DrawBoard extends View implements View.OnTouchListener {

    private Context mContext;

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private List<Path> savePath = new ArrayList<Path>();


    private int backgroundColor;
    // 该画板是否可以绘制
    private boolean drawEnable = true;

    public DrawBoard(Context context) {
        super(context);
    }

    public DrawBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        setOnTouchListener(this);
    }

    public DrawBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        // 初始化画笔
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPath != null)
            canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!drawEnable) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("我是按下", "按下了啊" + event.getX());
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                Log.i("我是抬起", "抬起了啊" + event.getX());
                savePath.add(new Path(mPath));
                break;
        }

        return true;
    }


    /**
     * 外部调用，设置board的背景色
     *
     * @param color
     */
    public void setBoardColor(int color) {
        backgroundColor = color;
    }

    /**
     * 撤销功能
     */
    public void toLastPath() {
        int lastIndex = savePath.size() - 1;
        if (lastIndex == 0) {
            clear();
        } else if (lastIndex > 0) {
            final Path path = savePath.get(lastIndex - 1);
            mPath.reset();
            invalidate();
            savePath.remove(lastIndex);
            mPath = path;
            invalidate();
        }
    }

    public void setPath(Path path) {
        if (path != null)
            this.mPath = path;
    }

    /**
     * 清屏
     */
    public void clear() {
        mPath.reset();
        savePath.clear();
        invalidate();
    }

    /**
     * 是否可画
     *
     * @param enable
     */
    public void setDrawEnable(boolean enable) {
        drawEnable = enable;
    }


    /**
     * 获得是否可画
     *
     * @return
     */
    public boolean getDrawEnable() {
        return drawEnable;
    }


    public boolean isDrawed() {
        return !savePath.isEmpty();
    }

    /**
     * 保存所绘图形
     * 返回绘图文件的存储路径
     */
    public String saveBitmap() {
        if (mPath == null || mPath.isEmpty()) {
            return "还是画点什么吧~";
        }
        Point point = Utils.color2Point(backgroundColor);
        point.setImgPath(mPath);
        point.update();
        return "保存成功";
    }

    public void release() {

    }

}
