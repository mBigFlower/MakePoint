package com.flowerfat.makepoint.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flowerfat.makepoint.PointColor;
import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.Utils.FileUtil;
import com.flowerfat.makepoint.Utils.SpInstance;
import com.flowerfat.makepoint.Utils.Utils;
import com.flowerfat.makepoint.view.QuarterBlock;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int ANIM_DURATION_BLOCK = 400;
    private static final int ANIM_DURATION_LINE = 1000;

    private final int TITLE_PADDINGTOP = Utils.dp2px(15);

    private boolean isFirstLoad;

    private float downX, downY = 0;

    @Bind(R.id.main_framLayout)
    FrameLayout titleFL;
    @Bind(R.id.top_left)
    QuarterBlock qbTopLeft;
    @Bind(R.id.top_right)
    QuarterBlock qbTopRight;
    @Bind(R.id.bottom_left)
    QuarterBlock qbBottomLeft;
    @Bind(R.id.bottom_right)
    QuarterBlock qbBottomRight;
    @Bind(R.id.view_hline)
    View vHline;
    @Bind(R.id.view_vline)
    View vVline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        // 没有下面这句话，则会不显示onCreateOptionsMenu
        setSupportActionBar(new Toolbar(this));
        animBlock();
        initListener();
        if (savedInstanceState == null) {
            isFirstLoad = true;
        }
    }

    private void initListener() {
        titleFL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = event.getX();
                    downY = event.getY();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    titleFL.setPadding((int) (event.getX() - downX),
                            TITLE_PADDINGTOP + (int) (event.getY() - downY), 0, 0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    titleFL.setPadding(0, TITLE_PADDINGTOP, 0, 0);
                    if (event.getY() < Utils.dp2px(56))
                        goSetting();
                    else {
                        animTitle();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 块的点击监听
     *
     * @param v
     */
    public void blockOnclick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        TaskActivity.startUserProfileFromLocation(startingLocation, getColorFromClick(v), this);
        overridePendingTransition(0, 0);
    }

    public void listOnclick(View v) {
        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
    }

    /**
     * 根据不同的点击，返回对应颜色
     *
     * @param v
     * @return
     */
    public int getColorFromClick(View v) {
        if (v.getId() == R.id.top_left) {
            return PointColor.COLOR_1;
        } else if (v.getId() == R.id.top_right) {
            return PointColor.COLOR_2;
        } else if (v.getId() == R.id.bottom_left) {
            return PointColor.COLOR_3;
        } else {
            return PointColor.COLOR_4;
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    // 下面都是动画
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * 这里仅仅是执行动画用
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        if (isFirstLoad) {
            isFirstLoad = false;
            animBlockInit();
            animBlock();
            animLine();
        }
        return true;
    }

    private void animBlockInit() {
        qbTopLeft.setAlpha(0);
        qbTopRight.setAlpha(0);
        qbBottomLeft.setAlpha(0);
        qbBottomRight.setAlpha(0);
    }

    private void animBlock() {
        qbTopLeft.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK);
        qbTopRight.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK).setStartDelay(200);
        qbBottomLeft.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK).setStartDelay(400);
        qbBottomRight.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK).setStartDelay(600);
    }

    private void animLine() {
        vVline.setTranslationY(-1500);
        vHline.setTranslationX(-1500);
        vVline.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(900)
                .setDuration(ANIM_DURATION_LINE)
                .start();
        vHline.animate()
                .translationX(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(900)
                .setDuration(ANIM_DURATION_LINE)
                .start();
    }

    private void animTitle() {
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_title);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        operatingAnim.setFillAfter(true);
        titleFL.startAnimation(operatingAnim);
    }
    /////////////////////////////////////////////////////////////////////////////////
    // 上面都是动画
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("MainActivity", "onResume");
        // TODO
        dateCheck();
    }

    private void dateCheck() {
        if (Utils.ifStepDay(this)) {
            // reset the point's text
            SpInstance.get().initOneDayPoint();
            // delete all the board pic
            try {
                FileUtil.del(new File(Environment.getExternalStorageDirectory(), "/boards/"));
            } catch (Exception e) {
                Toast.makeText(this, "错误信息：" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        showOldBoards();
    }

    /**
     * 展示所有的board内容, 文字和
     */
    private void showOldBoards() {
        qbTopLeft.onResume(PointColor.COLOR_1);
        qbTopRight.onResume(PointColor.COLOR_2);
        qbBottomLeft.onResume(PointColor.COLOR_3);
        qbBottomRight.onResume(PointColor.COLOR_4);
    }

    private void goSetting() {
        int[] startingLocation = new int[2];
        titleFL.getLocationOnScreen(startingLocation);
        startingLocation[0] += titleFL.getWidth() / 2;
        SettingActivity.startSettingFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }


    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3000) {
                showSnake("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected void showSnake(String text) {
        if (!TextUtils.isEmpty(text)) {
            Snackbar.make(getWindow().getDecorView(), text, Snackbar.LENGTH_LONG)
                    .setAction("OK", null).setActionTextColor(Color.WHITE)
                    .show();
        }
    }

}
