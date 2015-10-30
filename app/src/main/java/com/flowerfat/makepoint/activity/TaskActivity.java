package com.flowerfat.makepoint.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.Utils.ScreenUtil;
import com.flowerfat.makepoint.Utils.SpInstance;
import com.flowerfat.makepoint.view.DrawBoardView;
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
    EditText etContent;

    @Bind(R.id.task_toolbar)
    Toolbar toolbar;

    @Bind(R.id.task_save)
    TextView saveTV ;

    @Bind(R.id.task_hline)
    View viewHline;

    @Bind(R.id.task_board)
    DrawBoardView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        initState();

        initToolBar();

        initContent();

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

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("lalala", "onMenuItemClick");
                return false;
            }
        });
    }

    /**
     * 初始化内容
     */
    private void initContent() {
        etContent.setText(SpInstance.get().gString("pColor" + fillColor));
    }

    @OnClick(R.id.task_save)
    void save() {
        final String editContent = etContent.getText().toString().trim();
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("保存？").setMessage("Mark内容：" + editContent);
        build.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePoint(editContent);
                        finish();
                    }
                });
        build.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                }).show();
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
                    animIn();
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
    private void animIn() {
        toolbar.setAlpha(0);
        toolbar.setTranslationX(ScreenUtil.getScreenSize(this)[0] / 2);
        toolbar.animate().translationX(0).alpha(1).setDuration(ANIM_DELAY_TOOLBAR);

        saveTV.setScaleX(0);
        saveTV.setScaleY(0);
        saveTV.animate().scaleX(1).scaleY(1).setDuration(300).setStartDelay(400)
                .setInterpolator(new OvershootInterpolator(1.f));

        mBoardView.setBoardColor(fillColor);
        mBoardView.show();
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
            mBoardView.setVisibility(View.VISIBLE);
        } else {
            etContent.setVisibility(View.INVISIBLE);
            viewHline.setVisibility(View.INVISIBLE);
            mBoardView.setVisibility(View.INVISIBLE);
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

    private void savePoint(String text) {
        SpInstance.get().pString("pColor" + fillColor, text);
    }

//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.i("Task", "onKeyDown");
//        if (editContent == null || editContent.equals("")) {
//            finish();
//            return false;
//        }
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBoardView.release();
    }
}
