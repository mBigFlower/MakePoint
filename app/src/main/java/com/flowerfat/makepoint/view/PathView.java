package com.flowerfat.makepoint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.flowerfat.makepoint.utils.PathUtil;
import com.flowerfat.makepoint.utils.Utils;

/**
 * Created by 明明大美女 on 2016/4/8.
 */
public class PathView extends View {

    private final int PADDING = Utils.dp2px(56);

    private PathPlus mPath;
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


    public void setmPath(String pathStr) {
        if (pathStr != null) {
            mPath = new PathPlus(pathStr);
            mPath = (PathPlus)PathUtil.suitView(this, mPath, PADDING);
            invalidate();
        }
    }

}
