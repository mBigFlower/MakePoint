package com.flowerfat.makepoint.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.utils.GreenDaoUtil;
import com.flowerfat.makepoint.utils.ScreenUtil;
import com.flowerfat.makepoint.utils.SpInstance;
import com.flowerfat.makepoint.utils.Utils;
import com.flowerfat.makepoint.sqlite.Point;
import com.flowerfat.makepoint.view.DrawBoardView;
import com.flowerfat.makepoint.view.RevealBackgroundView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    EditText contentEt;

    @Bind(R.id.task_toolbar)
    Toolbar toolbar;

    @Bind(R.id.task_save)
    TextView saveTV;

    @Bind(R.id.task_hline)
    View viewHline;

    @Bind(R.id.task_board)
    DrawBoardView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        initToolBar();

        initContent();

        setupRevealBackground(savedInstanceState);

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
        contentEt.setText(SpInstance.get().gString("pColor" + fillColor));
        contentEt.setSelection(contentEt.getText().length());
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utils.isEnglish(s.toString())) {
                    Log.w("onTextChanged", "包含了英文");
                } else {
                    Log.i("onTextChanged", "不包含英文");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.task_save)
    void save() {
        Utils.closeSoftInput(this, contentEt);
        final String editContent = contentEt.getText().toString().trim();
        String title = "保存　";
        String posName;
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        if (mBoardView.isDrawed()) {
            posName = "仅文字";
            title = title + "<画了个画>";
            build.setNeutralButton("图文", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mBoardView.save();
                    savePoint(editContent);
                    finish();
                }
            });
        } else {
            posName = "确定";
        }
        build.setTitle(title).setMessage("Mark内容：" + editContent);
        build.setPositiveButton(posName,
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
            contentEt.setVisibility(View.VISIBLE);
            viewHline.setVisibility(View.VISIBLE);
            mBoardView.setVisibility(View.VISIBLE);
        } else {
            contentEt.setVisibility(View.INVISIBLE);
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

    /**
     * 这个地方的代码太笨了。自己都看不下去了！！！！ 要改 TODO
     *
     * @param text
     */
    private void savePoint(String text) {
        Point point = GreenDaoUtil.getInstance().getBottomPoint();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        if (point != null && point.getDate().equals(format.format(new Date()))) {
            Log.i("savePoint", "old date:" + point.getDate().toString());
            // 更新数据库
            point.setPoint(fillColor, text);
            GreenDaoUtil.getInstance().replacePoint(point);
        } else {
            Point newPoint = new Point(fillColor, text, format.format(new Date()));
            Log.i("savePoint2", newPoint.getDate() + " new date:" + format.format(new Date()));
            GreenDaoUtil.getInstance().insertPoint(newPoint);
        }
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
