package com.flowerfat.path.cool;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.flowerfat.path.PathAnimUtil;


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
    private PathAnimUtil mResPathAnim1;
    private PathAnimUtil mResPathAnim2;
    private PathAnimUtil mResPathAnim3;
    private PathAnimUtil mResPathAnim4;
    private PathAnimUtil mResPathAnim5;
    private PathAnimUtil mResPathAnim6;
    private PathAnimUtil mOutputPathAnim;
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
        Log.e("onLayout", changed + "");
        if (changed) {
            mDrawCtrl = new DrawCtrl(getWidth());

            animTopBottomLine();
            refreshDriver();
            mResPathAnim1 = animResUp(ANIM_TIME * 3 / 8, ANIM_TIME / 4, mDrawCtrl.res1Height, mDrawCtrl.halfSize / 2, 0);
            mResPathAnim2 = animResDown(ANIM_TIME * 3 / 8, ANIM_TIME / 4, mDrawCtrl.res1Height, mDrawCtrl.halfSize / 2, 0);
            mResPathAnim3 = animResUp(ANIM_TIME / 4, ANIM_TIME * 9 / 16, mDrawCtrl.res2Height, mDrawCtrl.halfSize * 5 / 4, 0);
            mResPathAnim4 = animResDown(ANIM_TIME / 4, ANIM_TIME * 9 / 16, mDrawCtrl.res2Height, mDrawCtrl.halfSize * 5 / 4, 0);
            mResPathAnim5 = animResUp(ANIM_TIME / 4, ANIM_TIME * 9 / 16, mDrawCtrl.res3Height, mDrawCtrl.halfSize * 5 / 4, mDrawCtrl.halfSize * 5 / 4);
            mResPathAnim6 = animResDown(ANIM_TIME / 4, ANIM_TIME * 9 / 16, mDrawCtrl.res3Height, mDrawCtrl.halfSize * 5 / 4, mDrawCtrl.halfSize * 5 / 4);
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
        npnAnim.setDuration(ANIM_TIME / 8).setStartDelay(ANIM_TIME / 2);
        npnAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                npnLength = (float) animation.getAnimatedValue();
            }
        });
        npnAnim.start();
    }

    private void animOutput() {
        Path path = new Path();
        path.moveTo(mDrawCtrl.outputX, mDrawCtrl.outputY);
        path.lineTo(mDrawCtrl.size, mDrawCtrl.outputY);
        mOutputPathAnim = new PathAnimUtil(path, mPaint);
        mOutputPathAnim.getAnimator().setDuration(ANIM_TIME*7/16).setStartDelay(ANIM_TIME*9/16);
        mOutputPathAnim.anim(this);
    }

    /**
     * /**
     * 画电阻
     * 一个电阻由两个path构成
     *
     * @param duration   动画执行时间
     * @param startDelay 动画开始的延时
     * @param resHeight  电阻的高度
     * @param beginX     电阻的位置：上面的引脚的X坐标值
     * @param beginY     电阻的位置：上面的引脚的Y坐标值
     */
    private PathAnimUtil animResUp(int duration, int startDelay, float resHeight, int beginX, int beginY) {
        // 根据高宽，来计算各个点位
        float resHeightDiv4 = resHeight / 4;
        Path path = new Path();
        path.moveTo(beginX, beginY + resHeight);
        path.lineTo(beginX, beginY + resHeightDiv4 * 3);
        path.rLineTo(-mDrawCtrl.resWidthDiv2, 0);
        path.rLineTo(0, -resHeightDiv4 * 2);
        path.rLineTo(mDrawCtrl.resWidthDiv2, 0);
        PathAnimUtil pathAnim = new PathAnimUtil(path, mPaint);
        pathAnim.getAnimator().setDuration(duration).setStartDelay(startDelay);
        pathAnim.anim(this);
        return pathAnim ;
    }

/**
     * /**
     * 画电阻
     * 一个电阻由两个path构成
     *
     * @param duration   动画执行时间
     * @param startDelay 动画开始的延时
     * @param resHeight  电阻的高度
     * @param beginX     电阻的位置：上面的引脚的X坐标值
     * @param beginY     电阻的位置：上面的引脚的Y坐标值
     */
    private PathAnimUtil animResDown(int duration, int startDelay, float resHeight, int beginX, int beginY) {
        // 根据高宽，来计算各个点位
        float resHeightDiv4 = resHeight / 4;

        Path path = new Path();
        path.moveTo(beginX, beginY);
        path.lineTo(beginX, beginY + resHeightDiv4);
        path.rLineTo(mDrawCtrl.resWidthDiv2, 0);
        path.rLineTo(0, resHeightDiv4 * 2);
        path.rLineTo(-mDrawCtrl.resWidthDiv2, 0);
        PathAnimUtil pathAnim = new PathAnimUtil(path, mPaint);
        pathAnim.getAnimator().setDuration(duration).setStartDelay(startDelay);
        pathAnim.anim(this);
        return pathAnim ;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画最顶部的那跟线
        canvas.drawLine(0, mDrawCtrl.topLineY, lineLength, mDrawCtrl.topLineY, mPaint);
        // 画最底部的那跟线
        canvas.drawLine(mDrawCtrl.size - lineLength, mDrawCtrl.bottomLineY, mDrawCtrl.size, mDrawCtrl.bottomLineY, mPaint);
        // 画信号输入的线
        onDrawInputLine(canvas);
        // 画信号输出的线
        onDrawOutputLine(canvas);
        // 画三极管
        onDrawNPNLine(canvas);
        // 画电阻
        onDrawRes(canvas);
    }

    private void onDrawInputLine(Canvas canvas) {
        if (lineLength <= mDrawCtrl.halfSize) {
            canvas.drawLine(0, mDrawCtrl.halfSize, lineLength, mDrawCtrl.halfSize, mPaint);
        } else {
            canvas.drawLine(0, mDrawCtrl.halfSize, mDrawCtrl.halfSize, mDrawCtrl.halfSize, mPaint);
        }
    }

    private void onDrawOutputLine(Canvas canvas) {
        mOutputPathAnim.onDraw(canvas);
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
        mResPathAnim1.onDraw(canvas);
        mResPathAnim2.onDraw(canvas);
        mResPathAnim3.onDraw(canvas);
        mResPathAnim4.onDraw(canvas);
        mResPathAnim5.onDraw(canvas);
        mResPathAnim6.onDraw(canvas);
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
