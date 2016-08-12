package com.flowerfat.path;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

/**
 * Created by 明明大美女 on 2016/8/10.
 */
public class PathAnimUtil {

    private ValueAnimator valueAnimator;
    private boolean isDisappear;

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDrawPath;
    private float mLength;
    private float pathStop;
    private float pathStart;

    public PathAnimUtil(Paint paint) {
        this(null, paint, false);
    }
    public PathAnimUtil(Path path, Paint paint) {
        this(path, paint, false);
    }

    public PathAnimUtil(Path path, Paint paint, boolean isDisappear) {
        mPath = path;
        mPaint = paint;
        this.isDisappear = isDisappear;

        mDrawPath = new Path();
        mPathMeasure = new PathMeasure();
        initPathMeasure(path);

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
    }

    private void initPathMeasure(Path path){
        mPathMeasure.setPath(path, false);
        mLength = mPathMeasure.getLength();
    }

    public void anim(final View view) {
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                pathStop = mLength * mAnimatorValue;
                view.invalidate();
            }
        });
        valueAnimator.start();
    }

    public void onDraw(Canvas canvas) {
        mDrawPath.reset();
        // 硬件加速的BUG
        mDrawPath.lineTo(0, 0);
        if (isDisappear) {
            pathStart = (float) (pathStop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
            mPathMeasure.getSegment(pathStart, pathStop, mDrawPath, true);
        } else {
            mPathMeasure.getSegment(0, pathStop, mDrawPath, true);
        }
        canvas.drawPath(mDrawPath, mPaint);
    }

    public ValueAnimator getAnimator() {
        return valueAnimator;
    }

    public void setPath(Path path) {
        this.mPath = path;
        initPathMeasure(path);
    }

}
