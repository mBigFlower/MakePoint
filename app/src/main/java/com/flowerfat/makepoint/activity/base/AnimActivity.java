package com.flowerfat.makepoint.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.view.RevealBackgroundView;

import butterknife.ButterKnife;

/**
 * 圆形背景扩展动画的baseActivity，继承这个，就有拉个效果了！
 *
 * 注意了啊，xml里千万不要白色的background
 */
public abstract class AnimActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    public RevealBackgroundView vRevealBackground ;
    /**
     * @param startingActivity
     * @param startingLocation 圆环开始的位置
     */
    public static void launchFromLocation(Activity startingActivity, Class aimActivity, int[] startingLocation) {
        Intent intent = new Intent(startingActivity, aimActivity);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
        startingActivity.overridePendingTransition(0, 0);
    }

    public abstract void animStart();
    public abstract int initLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        // 加载布局
        ViewStub vs = (ViewStub)findViewById(R.id.base_animLayoutVs);
        vs.setLayoutResource(initLayout());
        View v = vs.inflate();
        ButterKnife.bind(this, v);

        setupRevealBackground(savedInstanceState);
    }

    /**
     * 圆形扩展背景的动画
     * @param savedInstanceState
     */
    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground = (RevealBackgroundView)findViewById(R.id.base_animBack);
        vRevealBackground.setFillPaintColor(getFillColor());
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    // 动画开始
                    animStart();
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    public int getFillColor(){
        return Color.BLACK;
    }

    /**
     * 背景圆的状态
     *
     * @param state
     */
    @Override
    public void onStateChange(int state) {
        if(state == RevealBackgroundView.STATE_ACT_FINISH) {
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        vRevealBackground.finishFromLocation();
    }
}
