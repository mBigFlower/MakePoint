package com.flowerfat.makepoint.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.view.DotsView;
import com.flowerfat.makepoint.view.RevealBackgroundView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @Bind(R.id.setting_animBack)
    RevealBackgroundView vRevealBackground;

    @Bind(R.id.setting_toolbar)
    Toolbar toolbar;

    @Bind(R.id.dotsView)
    DotsView dotsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initToolBar();

        setupRevealBackground(savedInstanceState);
    }

    private void initToolBar() {
//        toolbar.setTitle("Setting");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * @param savedInstanceState
     */
    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setFillPaintColor(Color.BLACK);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    // toolbar anim
                    animIn();

                    animDots();
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
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

    /**
     * @param startingLocation
     * @param startingActivity
     */
    public static void startSettingFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, SettingActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {

        } else {

        }
    }
}
