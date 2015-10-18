package com.flowerfat.makepoint.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.Utils.GreenDaoUtil;
import com.flowerfat.makepoint.adapter.PointsAdapter;
import com.flowerfat.makepoint.sqlite.Point;
import com.flowerfat.makepoint.view.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PointsHistoryActivity extends AppCompatActivity {

    private PointsAdapter mAdapter;

    @Bind(R.id.points_recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_history);
        ButterKnife.bind(this);
        GreenDaoUtil.getInstance().setupDatabase(this, "db-points");

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new PointsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));

        List<Point> pointsLists = GreenDaoUtil.getInstance().searchAllPoint();
        Log.i("PointsHistoryActivity", pointsLists.size() +" length");
        mAdapter.addItems(pointsLists);
    }


}
