package com.flowerfat.makepoint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.flowerfat.makepoint.PointColor;
import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.Utils.GreenDaoUtil;
import com.flowerfat.makepoint.Utils.SpInstance;
import com.flowerfat.makepoint.Utils.Utils;
import com.flowerfat.makepoint.sqlite.Point;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final int ANIM_DURATION_BLOCK = 400;
    private static final int ANIM_DURATION_LINE = 1000;

    // 这个只对手机自带返回按钮有效，而toolbar的系统返回无效。。。
    private boolean isFirstLoad = true;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.top_left)
    TextView tvTopLeft;
    @Bind(R.id.top_right)
    TextView tvTopRight;
    @Bind(R.id.bottom_left)
    TextView tvBottomLeft;
    @Bind(R.id.bottom_right)
    TextView tvBottomRight;
    @Bind(R.id.view_hline)
    View vHline;
    @Bind(R.id.view_vline)
    View vVline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        animBlock();
        initToolBar();

    }

    /**
     * toolbar初始化
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    /**
     * toolbar点击监听
     */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_search:
                    msg += "Click edit";
                    startActivity(new Intent(MainActivity.this, PointsHistoryActivity.class));
                    break;
            }
            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

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
        animLine();
        return true;
    }

    private void animBlockInit() {
        tvTopLeft.setAlpha(0);
        tvTopRight.setAlpha(0);
        tvBottomLeft.setAlpha(0);
        tvBottomRight.setAlpha(0);
    }

    private void animBlock() {
        tvTopLeft.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK);
        tvTopRight.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK).setStartDelay(200);
        tvBottomLeft.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK).setStartDelay(400);
        tvBottomRight.animate().alpha(1).setDuration(ANIM_DURATION_BLOCK).setStartDelay(600);
    }

    private void animLine() {
        if (isFirstLoad) {
            isFirstLoad = false;
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

    }


    @Override
    protected void onResume() {
        super.onResume();

        dateCheck();

    }

    /**
     * 每日检测，跨天与否执行的内容不同
     */
    private void dateCheck() {
        if (Utils.ifStepDay(this)) {
            // 把上次的值存到数据库
            storePointsSQLite();
            SpInstance.get().initOneDayPoint();
        } else {
            // 显示上次的值
            showOldBoards();
        }
    }

    private void storePointsSQLite() {
        GreenDaoUtil.getInstance().setupDatabase(getApplicationContext(), "db-points");
        Point point = new Point(null,
                SpInstance.get().gString("pColor" + PointColor.COLOR_1),
                SpInstance.get().gString("pColor" + PointColor.COLOR_2),
                SpInstance.get().gString("pColor" + PointColor.COLOR_3),
                SpInstance.get().gString("pColor" + PointColor.COLOR_4), Utils.yesteday());
        GreenDaoUtil.getInstance().insertPoint(point);
    }

    /**
     * 展示所有的board内容
     */
    private void showOldBoards() {
        tvTopLeft.setText(SpInstance.get().gString("pColor" + PointColor.COLOR_2));
        tvTopRight.setText(SpInstance.get().gString("pColor" + PointColor.COLOR_1));
        tvBottomLeft.setText(SpInstance.get().gString("pColor" + PointColor.COLOR_4));
        tvBottomRight.setText(SpInstance.get().gString("pColor" + PointColor.COLOR_3));
    }

}
