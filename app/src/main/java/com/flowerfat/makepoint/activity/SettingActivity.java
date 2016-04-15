package com.flowerfat.makepoint.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.activity.base.AnimActivity;
import com.flowerfat.makepoint.view.DotsView;

import butterknife.Bind;

public class SettingActivity extends AnimActivity{

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @Bind(R.id.setting_toolbar)
    Toolbar toolbar;

    @Bind(R.id.dotsView)
    DotsView dotsView;

    @Override
    public void animStart() {
        animIn();
        animDots();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void animIn() {
        toolbar.setScaleX(0);
        toolbar.setScaleY(0);
        toolbar.animate().scaleX(1).scaleY(1).setDuration(300)
                .setInterpolator(new OvershootInterpolator(1.f));
    }

    private void animDots() {
        ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(dotsView, DotsView.DOTS_PROGRESS, 0, 1f);
        dotsAnimator.setDuration(900);
        dotsAnimator.setStartDelay(50);
        dotsAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

}
