package com.flowerfat.path.cool;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.flowerfat.path.PathAnim;

/**
 * Created by 明明大美女 on 2016/7/8.
 */
public class HeartJumpAnimView extends View {

    /**
     * 默认宽高
     */
    private static final int DEFAULT_DIAMETER_SIZE = 120;
    // 线条的颜色
    private final int LINE_COLOR = Color.RED;
    // 线条的颜色
    private final int BG_COLOR = Color.TRANSPARENT;
    // 线宽
    private final float LINE_WIDTH = 5;

    // 动画时间
    private int ANIM_TIME = 1000;
    // 心跳线的paint
    private Paint linePaint = new Paint();
    // 覆盖用的Paint
    private Paint maskPaint = new Paint();
    // 心跳的path
    private Path linePath = new Path();
    // 覆盖的path
    private Path maskPath = new Path();
    // 动画是否在进行中
    private boolean isAniming;

    private int width;
    private int height;
    int step;
    int firstX, lastX;
    int startX, centerY;

    public HeartJumpAnimView(Context context) {
        super(context);
        init();
    }

    public HeartJumpAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartJumpAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setBackgroundColor(BG_COLOR);

        linePaint = new Paint();
        linePaint.setColor(LINE_COLOR);
        linePaint.setStrokeWidth(LINE_WIDTH);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        maskPaint.setColor(BG_COLOR);
        maskPaint.setStrokeWidth(LINE_WIDTH + 1); // 不加1的话，会有细线
        maskPaint.setAntiAlias(true);
        maskPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getTheParams();
            Log.e("onLayout", "the layout is changed " + width + " " + height);

            animStart();
        }
    }


    /**
     * 计算所需要的值
     */
    private void getTheParams() {
        width = getWidth();
        height = getHeight();
        centerY = height / 2;
        Log.i("getTheParams", width + " " + height);
        // 我们需要一个宽高比为2:1的形状，为了方便后面的计算？
        if (height > width / 2) {
            height = width / 2;
        } else {
            startX = width / 2 - height;
            width = height * 2;
        }
        Log.i("getTheParams", width + " " + height);
        step = height / 12;
        firstX = height - step * 3;
        lastX = height - step * 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2 * DEFAULT_DIAMETER_SIZE, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_DIAMETER_SIZE, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void animStart() {
        if (!isAniming) {
            isAniming = true;
            animLine();
        }
    }

    private void animLine() {
        PathAnim.Builder heartJumpBuilder = new PathAnim.Builder();
        heartJumpBuilder.setAnimDuration(ANIM_TIME)
                .addLine(startX, centerY, startX + firstX, centerY)
                .toRelWhere(step, -2 * step)
                .toRelWhere(step, 4 * step)
                .toRelWhere(step, -7 * step)
                .toRelWhere(step, 8 * step)
                .toRelWhere(step, -3 * step)
                .toRelX(lastX)
                .bindPath(linePath).onStart();

        // 这是一个时间轴
        ValueAnimator mainAnim = ValueAnimator.ofInt(0, ANIM_TIME);
        mainAnim.setDuration(ANIM_TIME * 2).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        mainAnim.start();

        // 这个时覆盖的
        PathAnim.Builder heartJumpBuilderMask = new PathAnim.Builder();
        heartJumpBuilderMask.setAnimDuration(ANIM_TIME).setStartDelay(400)
                .addLine(0, centerY, firstX, centerY)
                .toRelWhere(step, -2 * step)
                .toRelWhere(step, 4 * step)
                .toRelWhere(step, -7 * step)
                .toRelWhere(step, 8 * step)
                .toRelWhere(step, -3 * step)
                .toRelX(lastX)
                .bindPath(maskPath).onStart();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(linePath, linePaint);
//        canvas.drawPath(maskPath, maskPaint);
    }
}
