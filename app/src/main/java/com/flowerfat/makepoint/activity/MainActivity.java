package com.flowerfat.makepoint.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.flowerfat.makepoint.Constants.PointColor;
import com.flowerfat.makepoint.Constants.PointManager;
import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.utils.Utils;
import com.flowerfat.makepoint.view.ExitView;
import com.flowerfat.makepoint.view.QuarterBlock;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * TODO
 * 1. 这个界面完全可以用来界面显示，不保留数据
 */
public class MainActivity extends AppCompatActivity {

    private static final int ANIM_DURATION_BLOCK = 400;
    private static final int ANIM_DURATION_LINE = 1000;

    private final int TITLE_PADDINGTOP = Utils.dp2px(15);

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

    @Bind(R.id.main_exitView)
    ExitView exitView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        // 没有下面这句话，则会不显示onCreateOptionsMenu
        setSupportActionBar(new Toolbar(this));

        initListener();
        initLongListener();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Timber.e(Utils.getStateHeight(this) + " " + Utils.getScreenSize(this)[1]);
        }
    }

    /**
     * 将title添加一个可拖拽的效果
     */
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
        TaskActivity.startFromLocation(this, startingLocation, getColorFromClick(v));
        overridePendingTransition(0, 0);
    }

    public void listOnclick(View v) {
        HistoryActivity.launchFromLocation(this, HistoryActivity.class,
                new int[]{v.getLeft(), titleFL.getHeight()});
    }

    private void initLongListener() {
        qbTopLeft.setOnLongClickListener(longClick);
        qbBottomLeft.setOnLongClickListener(longClick);
        qbBottomRight.setOnLongClickListener(longClick);
        qbTopRight.setOnLongClickListener(longClick);
    }

    View.OnLongClickListener longClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ((QuarterBlock) v).toggle();
            return true;
        }
    };

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
        animBlockInit();
        animBlock();
        exitView.animLineIn();
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
        PointManager.get().onResume();
        showOldBoards();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PointManager.get().onDestroy();
    }

    /**
     * 展示所有的board内容, 文字和
     */
    private void showOldBoards() {
        qbTopLeft.onResume(PointManager.get().point1);
        qbTopRight.onResume(PointManager.get().point2);
        qbBottomLeft.onResume(PointManager.get().point3);
        qbBottomRight.onResume(PointManager.get().point4);
    }

    private void goSetting() {
        int[] startingLocation = new int[2];
        titleFL.getLocationOnScreen(startingLocation);
        startingLocation[0] += titleFL.getWidth() / 2;
        SettingActivity.launchFromLocation(this, SettingActivity.class, startingLocation);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exitView.animLineOut(new ExitView.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    finish();
                }
            });
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
