package com.flowerfat.makepoint.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.activity.base.AnimActivity;
import com.flowerfat.makepoint.entity.db.Point;
import com.flowerfat.makepoint.utils.NotificationUtil;
import com.flowerfat.makepoint.utils.ScreenUtil;
import com.flowerfat.makepoint.utils.Utils;
import com.flowerfat.makepoint.view.DrawBoardView;
import com.flowerfat.makepoint.view.RevealBackgroundView;

import butterknife.Bind;
import butterknife.OnClick;

public class TaskActivity extends AnimActivity {

    public static final int ANIM_DELAY_TOOLBAR = 400;

    public static final String KEY_FILL_COLOR = "fillColor";

    private int fillColor;

    @Bind(R.id.task_edit)
    EditText contentEt;
    @Bind(R.id.task_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_save)
    TextView saveTV;
    @Bind(R.id.task_hline)
    View viewHline;
    @Bind(R.id.task_board)
    DrawBoardView mBoardView;

    /**
     * 其他activity打开这个activity所调用的方法
     *
     * @param startingLocation
     * @param color
     * @param startingActivity
     */
    public static void startFromLocation(Activity startingActivity, int[] startingLocation, int color) {
        Intent intent = new Intent(startingActivity, TaskActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        intent.putExtra(KEY_FILL_COLOR, color);
        startingActivity.startActivity(intent);
        startingActivity.overridePendingTransition(0, 0);
    }

    @Override
    public void animStart() {
        // toolbar动画
        animIn();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_task;
    }


    @Override
    public int getFillColor() {
        fillColor = getIntent().getIntExtra(KEY_FILL_COLOR, Color.BLACK);
        return fillColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolBar();
        initContent();
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化内容
     */
    private void initContent() {
        Point point = Utils.color2Point(fillColor);
        contentEt.setText(point.getTitle());
        contentEt.setSelection(contentEt.getText().length());

        mBoardView.setPathStr(point.getImgPath());
    }

    @OnClick(R.id.task_save)
    void save() {
        Utils.closeSoftInput(this, contentEt);
        final String editContent = contentEt.getText().toString().trim();
        if (mBoardView.isDrawed()) {
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("<画了个画>").setMessage("Mark内容：" + editContent);
            build.setPositiveButton("仅文字",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            savePoint(editContent);
                            finish();
                        }
                    });
            build.setNegativeButton("图文",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mBoardView.save();
                            savePoint(editContent);
                            finish();
                        }
                    }).show();
        } else {
            savePoint(editContent); // TODO 这里为什么有它？
            finish();
        }

    }

    /**
     * 入场动画
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
    private void animExit() {
        toolbar.animate().translationX(toolbar.getWidth() / 4).alpha(0).setDuration(ANIM_DELAY_TOOLBAR);
        saveTV.animate().scaleX(0).scaleY(0).setDuration(ANIM_DELAY_TOOLBAR);
    }

    /**
     * 根据动画圆的状态来判断：主页面内容的显示与否
     *
     * @param state
     */
    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            contentEt.setVisibility(View.VISIBLE);
            viewHline.setVisibility(View.VISIBLE);
            mBoardView.setVisibility(View.VISIBLE);
        } else if (RevealBackgroundView.STATE_FINISHED > state) {
            contentEt.setVisibility(View.INVISIBLE);
            viewHline.setVisibility(View.INVISIBLE);
            mBoardView.setVisibility(View.INVISIBLE);
        }
        super.onStateChange(state);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animExit();
    }

    /**
     *
     * @param text
     */
    private void savePoint(String text) {
        Point point = Utils.color2Point(fillColor);
        point.setTitle(text);
        point.update();
        NotificationUtil.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBoardView.release();
    }
}
