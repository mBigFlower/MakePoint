package com.flowerfat.makepoint.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.Utils.ScreenUtil;
import com.flowerfat.makepoint.view.DrawBoard2;
import com.flowerfat.makepoint.view.RevealBackgroundView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final int ANIM_DELAY_TOOLBAR = 400;

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static int fillColor;

    @Bind(R.id.animBack)
    RevealBackgroundView vRevealBackground;

    @Bind(R.id.task_edit)
    TextView etContent;

    @Bind(R.id.task_toolbar)
    Toolbar toolbar;

    @Bind(R.id.task_hline)
    View viewHline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        initState();

        initToolBar();
        setupRevealBackground(savedInstanceState);
    }

    /**
     * 天真的以为这个是沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 变化的圆的动画控制
     *
     * @param savedInstanceState
     */
    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setFillPaintColor(fillColor);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    // toolbar动画
                    animToolbarIn();
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    /**
     * toolbar的入场动画，右侧进入
     */
    private void animToolbarIn() {
        toolbar.setAlpha(0);
        toolbar.setTranslationX(ScreenUtil.getScreenSize(this)[0] / 2);

        toolbar.animate().translationX(0).alpha(1).setDuration(ANIM_DELAY_TOOLBAR);
    }

    /**
     * toolbar 的退出动画
     */
    private void animToolbarExit() {
        toolbar.animate().translationX(ScreenUtil.getScreenSize(this)[0] / 2).setDuration(ANIM_DELAY_TOOLBAR);
    }

    /**
     * 根据动画圆的状态来判断：主页面内容的显示与否
     *
     * @param state
     */
    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            etContent.setVisibility(View.VISIBLE);
            viewHline.setVisibility(View.VISIBLE);
        } else {
            etContent.setVisibility(View.INVISIBLE);
            viewHline.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 其他activity打开这个activity所调用的方法
     *
     * @param startingLocation
     * @param color
     * @param startingActivity
     */
    public static void startUserProfileFromLocation(int[] startingLocation, int color, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TaskActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);

        fillColor = color;
    }

    @Bind(R.id.task_board)
    DrawBoard2 tesxt;

    @OnClick(R.id.task_edit)
    void tesxt() {
        tesxt.toLastPath();
    }
}
