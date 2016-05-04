package com.flowerfat.makepoint.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.activity.base.AnimActivity;
import com.flowerfat.makepoint.utils.NotificationUtil;
import com.flowerfat.makepoint.utils.SpInstance;
import com.flowerfat.makepoint.view.DotsView;
import com.flowerfat.makepoint.view.MaterialCheckBox;

import butterknife.Bind;

public class SettingActivity extends AnimActivity{

    @Bind(R.id.setting_toolbar)
    Toolbar toolbar;

    @Bind(R.id.dotsView)
    DotsView dotsView;

    @Bind(R.id.notification_cb)
    MaterialCheckBox notificationCb;
    @Bind(R.id.welcome_anim_cb)
    MaterialCheckBox welcomeAnimCb;

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
        initListener();
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
        dotsAnimator.start();
    }

    private void initListener(){
        notificationCb.setChecked(SpInstance.get().isNotification());
        notificationCb.setOnCheckedChangedListener(new MaterialCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                NotificationUtil.setSwitchState(isChecked);
            }
        });
        welcomeAnimCb.setChecked(SpInstance.get().isWelcomeAnim());
        welcomeAnimCb.setOnCheckedChangedListener(new MaterialCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean isChecked) {
                SpInstance.get().setWelcomeAnim(isChecked);
            }
        });
    }
}
