package com.flowerfat.makepoint.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.activity.base.AnimActivity;
import com.flowerfat.makepoint.fragment.EmptyFragment;
import com.flowerfat.makepoint.fragment.HistoryChartFragment;
import com.flowerfat.makepoint.fragment.HistoryListFragment;

import butterknife.Bind;

public class HistoryActivity extends AnimActivity {

    private static final int ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @Bind(R.id.history_toolbar)
    Toolbar toolbar;
    @Bind(R.id.history_viewPager)
    ViewPager mViewPager;
    @Bind(R.id.history_tabs)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        initViewPager();
    }

    @Override
    public void animStart() {
        animTitleIn();
        animTabIn();
        animViewPagerIn();
    }


    @Override
    public int initLayout() {
        return R.layout.activity_history;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new HistoryListFragment();
                    case 1:
                        return new HistoryChartFragment();
                    default:
                        return new EmptyFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "List";
                    case 1:
                        return "Chart";
                    default:
                        return "Test";
                }
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    private void animTitleIn() {
        toolbar.setScaleX(0);
        toolbar.setScaleY(0);
        toolbar.animate().scaleX(1).scaleY(1).setDuration(300)
                .setStartDelay(ANIMATION_DELAY)
                .setInterpolator(new OvershootInterpolator(1.f));
    }

    private void animTabIn() {
        mTabLayout.setTranslationY(-mTabLayout.getHeight());
        mTabLayout.animate().translationY(0).setDuration(300)
                .setStartDelay(ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animViewPagerIn() {
        mViewPager.setAlpha(0);
        mViewPager.animate().alpha(1).setDuration(600)
                .setStartDelay(600).setInterpolator(INTERPOLATOR);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mViewPager.setAlpha(0);
    }
}
