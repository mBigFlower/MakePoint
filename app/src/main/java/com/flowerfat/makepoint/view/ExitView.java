package com.flowerfat.makepoint.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.flowerfat.makepoint.utils.Utils;

/**
 * Created by Bigflower on 2016/1/9.
 */
public class ExitView extends FrameLayout {

    private static final int ANIM_DURATION_LINE_IN = 1000;
    private static final int ANIM_DURATION_LINE_OUT = 500;

    private int leftMargin, topMargin, rightMargin, bottomMargin;

    private View hLine, vLine;

    public ExitView(Context context) {
        super(context);
        init();
    }

    public ExitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initParams();
        initVLine();
        initHLine();
    }

    private void initParams() {
        int[] screenSize = Utils.getScreenSize(getContext());
        leftMargin = screenSize[0] / 3;
        rightMargin = screenSize[0] / 6;
        topMargin = screenSize[1] / 8;
        bottomMargin = screenSize[1] / 4;
    }

    private void initVLine() {
        vLine = new View(getContext());
        vLine.setBackgroundColor(Color.WHITE);
        FrameLayout.LayoutParams vLineLP = new FrameLayout.LayoutParams(4, FrameLayout.LayoutParams.MATCH_PARENT);
        vLineLP.gravity = Gravity.CENTER_HORIZONTAL;
        vLineLP.topMargin = topMargin;
        vLineLP.bottomMargin = bottomMargin;
        this.addView(vLine, vLineLP);
    }

    private void initHLine() {
        hLine = new View(getContext());
        hLine.setBackgroundColor(Color.WHITE);
        FrameLayout.LayoutParams hLineLP = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 4);
        hLineLP.gravity = Gravity.CENTER_VERTICAL;
        hLineLP.leftMargin = leftMargin;
        hLineLP.rightMargin = rightMargin;
        this.addView(hLine, hLineLP);
    }

    public void animLineIn() {
        vLine.setTranslationY(-1500);
        hLine.setTranslationX(-1500);
        vLine.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(900)
                .setDuration(ANIM_DURATION_LINE_IN)
                .start();
        hLine.animate()
                .translationX(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(900)
                .setDuration(ANIM_DURATION_LINE_IN)
                .start();
    }

    public void animLineOut(OnAnimationEndListener mListener) {
        animLineLenth(0, 100, mListener);
    }

    public void animLineOutBack() {
        animLineLenth(100, 0, null);
    }

    private void animLineLenth(int start, int end, final OnAnimationEndListener mListener) {
        final int[] screenSize = Utils.getScreenSize(getContext());
        ValueAnimator lineAnimation = ValueAnimator.ofInt(start, end);
        lineAnimation.setDuration(ANIM_DURATION_LINE_OUT);
        lineAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                setPadding(screenSize[0] / 2 * value / 100, screenSize[1] / 2 * value / 100,
                        screenSize[0] / 2 * value / 100, screenSize[1] / 2 * value / 100);

                if (value == 100 && mListener != null) {
                    mListener.onAnimationEnd();
                }
            }
        });
        lineAnimation.start();
    }

    public interface OnAnimationEndListener {
        void onAnimationEnd();
    }
}
