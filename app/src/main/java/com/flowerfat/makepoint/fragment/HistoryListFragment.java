package com.flowerfat.makepoint.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.adapter.PointsAdapter;
import com.flowerfat.makepoint.entity.db.Points;
import com.flowerfat.makepoint.fragment.base.BaseFragment;
import com.flowerfat.makepoint.view.DividerItemDecoration;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 明明大美女 on 2016/8/10.
 */
public class HistoryListFragment extends BaseFragment {

    private PointsAdapter mAdapter;

    @Bind(R.id.history_list_recycler)
    RecyclerView mRecyclerView;

    @Override
    public void main() {
        initRecyclerView();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_history_list;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new PointsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getContext(), DividerItemDecoration.VERTICAL_LIST));


        // 获取数据
        List<Points> pointsLists = new Select().from(Points.class).queryList();
        // list反向
        Collections.reverse(pointsLists);
        mAdapter.addItems(pointsLists);
    }


}
