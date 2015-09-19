package com.flowerfat.makepoint.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.view.RevealBackgroundView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static int fillColor;

    @Bind(R.id.animBack)
    RevealBackgroundView vRevealBackground;

    @Bind(R.id.content)
    TextView tvContent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        initToolBar();
        setupRevealBackground(savedInstanceState);
    }

    private void initToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.task_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void startUserProfileFromLocation(int[] startingLocation, int color, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TaskActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);

        fillColor = color;
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setFillPaintColor(fillColor);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            for (int i = 0; i < startingLocation.length; i++) {
                Log.i("TAG", "content：" + startingLocation[i]);
            }
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            tvContent.setVisibility(View.VISIBLE);
        } else {
            tvContent.setVisibility(View.INVISIBLE);
        }
    }
}
