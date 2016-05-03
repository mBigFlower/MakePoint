package com.flowerfat.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 明明大美女 on 2016/4/27.
 */
public class NPNView extends View {

    // 动画时长 ms
    private final int ANIM_TIME = 2000;
    // 线条的颜色
    private final int PAINT_COLOR = 0xFFFFFFFF/*白色*/;
    // 背景的颜色
    private final int BG_COLOR = 0x00FFFFFF/*透明*/;
    // 控件默认尺寸
    private final int DEFAULT_WIDGET_SIZE = 480;
    // 线的宽度
    private final int LINE_WIDTH = 5;

    private Paint mPaint;
    private Path mResPath1 = new Path();
    private Path mResPath2 = new Path();
    private Path mResPath3 = new Path();
    private Path mResPath4 = new Path();
    private Path mResPath5 = new Path();
    private Path mResPath6 = new Path();
    private Path mOutputPath = new Path();
    private DrawCtrl mDrawCtrl;

    public NPNView(Context context) {
        super(context);
    }

    public NPNView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(BG_COLOR);
        init();
    }

    public NPNView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(PAINT_COLOR);
        mPaint.setStrokeWidth(LINE_WIDTH);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mDrawCtrl = new DrawCtrl(getWidth());

            animTopBottomLine();
            refreshDriver();
            animRes(mResPath1, mResPath2, ANIM_TIME*3/8, ANIM_TIME/4, mDrawCtrl.res1Height, mDrawCtrl.halfSize / 2, 0);
            animRes(mResPath3, mResPath4, ANIM_TIME/4, ANIM_TIME*9/16, mDrawCtrl.res2Height, mDrawCtrl.halfSize * 5 / 4, 0);
            animRes(mResPath5, mResPath6, ANIM_TIME/4, ANIM_TIME*9/16, mDrawCtrl.res3Height, mDrawCtrl.halfSize * 5 / 4, mDrawCtrl.halfSize * 5 / 4);
            animNPN();
            animOutput();
        }
    }

    private void refreshDriver() {
        ValueAnimator timeAnimator = ValueAnimator.ofInt(0, ANIM_TIME);
        timeAnimator.setDuration(ANIM_TIME);
        timeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
            }
        });
        timeAnimator.start();
    }

    //////////////////////////////////////////////
    // 下面是属性动画
    //////////////////////////////////////////////
    float lineLength;

    public void animTopBottomLine() {
        ValueAnimator lineAnim = ValueAnimator.ofFloat(0, mDrawCtrl.size);
        lineAnim.setDuration(ANIM_TIME);
        lineAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lineLength = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        lineAnim.start();
    }

    /**
     * 画三极管
     */
    float npnLength;

    private void animNPN() {
        ValueAnimator npnAnim = ValueAnimator.ofFloat(0, mDrawCtrl.npnHalfHeight);
        npnAnim.setDuration(ANIM_TIME/8).setStartDelay(ANIM_TIME/2);
        npnAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                npnLength = (float) animation.getAnimatedValue();
            }
        });
        npnAnim.start();
    }

    private void animOutput(){
        PathAnim.Builder builderUp = new PathAnim.Builder();
        builderUp.setAnimDuration(ANIM_TIME*7/16).setStartDelay(ANIM_TIME*9/16)
                .addLine(mDrawCtrl.outputX, mDrawCtrl.outputY, mDrawCtrl.size, mDrawCtrl.outputY)
                .bindPath(mOutputPath).onStart();
    }

    /**
     * /**
     * 画电阻
     * 一个电阻由两个path构成
     *
     * @param path1      第一个Path
     * @param path2      第二个Path
     * @param duration   动画执行时间
     * @param startDelay 动画开始的延时
     * @param resHeight  电阻的高度
     * @param beginX     电阻的位置：上面的引脚的X坐标值
     * @param beginY     电阻的位置：上面的引脚的Y坐标值
     */
    private void animRes(Path path1, Path path2, int duration, int startDelay, float resHeight, int beginX, int beginY) {
        // 根据高宽，来计算各个点位
        float resHeightDiv4 = resHeight / 4;
        // 实行动画
        PathAnim.Builder builderUp = new PathAnim.Builder();
        builderUp.setAnimDuration(duration).setStartDelay(startDelay)
                .addLine(beginX, beginY + resHeight, beginX, beginY + resHeightDiv4 * 3)
                .toRelX(-mDrawCtrl.resWidthDiv2)
                .toRelY(-resHeightDiv4 * 2)
                .toRelX(mDrawCtrl.resWidthDiv2)
                .bindPath(path1).onStart();
        PathAnim.Builder builderDown = new PathAnim.Builder();
        builderDown.setAnimDuration(duration).setStartDelay(startDelay)
                .addLine(beginX, beginY, beginX, beginY + resHeightDiv4)
                .toRelX(mDrawCtrl.resWidthDiv2)
                .toRelY(resHeightDiv4 * 2)
                .toRelX(-mDrawCtrl.resWidthDiv2)
                .bindPath(path2).onStart();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画最顶部的那跟线
        canvas.drawLine(0, mDrawCtrl.topLineY, lineLength, mDrawCtrl.topLineY, mPaint);
        // 画最底部的那跟线
        canvas.drawLine(mDrawCtrl.size-lineLength, mDrawCtrl.bottomLineY, mDrawCtrl.size, mDrawCtrl.bottomLineY, mPaint);
        // 画信号输入的线
        onDrawInputLine(canvas);
        // 画信号输出的线
        onDrawOutputLine(canvas);
        // 画电阻
        onDrawRes(canvas);
        // 画三极管
        onDrawNPNLine(canvas);

    }

    private void onDrawInputLine(Canvas canvas) {
        if (lineLength <= mDrawCtrl.halfSize) {
            canvas.drawLine(0, mDrawCtrl.halfSize, lineLength, mDrawCtrl.halfSize, mPaint);
        } else {
            canvas.drawLine(0, mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize, mPaint);
        }
    }

    private void onDrawOutputLine(Canvas canvas) {
        canvas.drawPath(mOutputPath, mPaint);
    }

    /**
     * 画NPN三极管，没有箭头
     *
     * @param canvas
     */
    private void onDrawNPNLine(Canvas canvas) {
        // 输入向上的半截直线
        canvas.drawLine(mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize - npnLength, mPaint);
        // 输入向下的半截直线
        canvas.drawLine(mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize + npnLength, mPaint);
        // 输入斜向上的半截直线
        canvas.drawLine(mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize + npnLength, mDrawCtrl.halfSize - npnLength, mPaint);
        // 输入斜向下的半截直线
        canvas.drawLine(mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize + npnLength, mDrawCtrl.halfSize + npnLength, mPaint);
    }

    private void onDrawRes(Canvas canvas) {
        canvas.drawPath(mResPath1, mPaint);
        canvas.drawPath(mResPath2, mPaint);
        canvas.drawPath(mResPath3, mPaint);
        canvas.drawPath(mResPath4, mPaint);
        canvas.drawPath(mResPath5, mPaint);
        canvas.drawPath(mResPath6, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDGET_SIZE, r.getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDGET_SIZE, r.getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    // >>2为除以4 这样效率高
    public class DrawCtrl {
        private int size;
        private int halfSize;
        private int topLineY;
        private int bottomLineY;
        private float outputX;
        private float outputY;

        public DrawCtrl(int size) {
            this.size = size;
            halfSize = size >> 1;
            topLineY = LINE_WIDTH / 2;
            bottomLineY = size - topLineY;
            outputX = halfSize + halfSize / 4;
            outputY = halfSize * 11 / 16;
            jisuanNPN();
            jisuanRes();
        }

        private int npnHeight;
        private int npnHalfHeight;

        private void jisuanNPN() {
            npnHeight = size >> 2;
            npnHalfHeight = (npnHeight >> 1) + 2;
        }

        private int res1Height;
        private float resWidthDiv2;
        private float res2Height;
        private float res3Height;

        // 电阻包括长方形和两个引脚
        private void jisuanRes() {
            resWidthDiv2 = halfSize / 16;
            res1Height = halfSize;
            res2Height = res3Height = halfSize * 3 / 4;
        }
    }
}
