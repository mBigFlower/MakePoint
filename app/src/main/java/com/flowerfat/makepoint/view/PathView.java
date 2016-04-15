package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 明明大美女 on 2016/4/8.
 */
public class PathView extends View {

    private Path mPath;
    private Paint mPaint = new Paint();

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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

    public void setmPath(Path path) {
//        this.mPath = path;
        if(path != null) {
            Matrix matrix = new Matrix();
            matrix.setScale(200, 200);
//        matrix.postScale(getWidth(), getWidth());
            path.transform(matrix, mPath);
            invalidate();
        }
    }
}
