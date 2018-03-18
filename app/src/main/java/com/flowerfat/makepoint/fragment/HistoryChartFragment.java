package com.flowerfat.makepoint.fragment;

import android.graphics.Color;

import com.flowerfat.makepoint.R;
import com.flowerfat.makepoint.entity.db.OneDayPoints;
import com.flowerfat.makepoint.entity.db.PointManager;
import com.flowerfat.makepoint.fragment.base.BaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 明明大美女 on 2016/8/10.
 */
public class HistoryChartFragment extends BaseFragment {

    @Bind(R.id.history_chart)
    LineChart mLineChart;
    @Bind(R.id.history_chart_pie)
    PieChart mPieChart;

    @Override
    public void main() {

        initChartData();
        test();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_history_chart;
    }

    private void initChartData() {
        // TODO 这里注释了
        // 获取数据
//        List<OneDayPoints> pointsLists = PointManager.get().getAllPoints();
//        // list反向
//        Collections.reverse(pointsLists);
//        List<Entry> entries = new ArrayList<Entry>();
////        for (OneDayPoints points : pointsLists) {
//            entries.add(new Entry(1f, 5f));
//            entries.add(new Entry(2f, 15f));
//            entries.add(new Entry(3f, 53f));
//            entries.add(new Entry(4f, 45f));
////        }
//
//        LineDataSet dataSet = new LineDataSet(entries, "1 2 3"); // add entries to dataset
//        dataSet.setColor(Color.WHITE);
//        dataSet.setValueTextColor(Color.WHITE);
//        dataSet.setLineWidth(6);
//        dataSet.setCircleRadius(6);
//        dataSet.setCircleColor(Color.WHITE);
//        dataSet.setValueTextColor(Color.TRANSPARENT);
//
//        LineData lineData = new LineData(dataSet);
//        mLineChart.setData(lineData);
//
//        setupChart(mLineChart, lineData, Color.rgb(137, 230, 81));
//        mLineChart.invalidate();
    }

    private void test(){

    }
    // 设置显示的样式
    void setupChart(LineChart chart, LineData data, int color) {
        // if enabled, the chart will always start at zero on the y-axis
//        chart.setStartAtZero(true);

        // disable the drawing of values into the chart
        chart.setVerticalFadingEdgeEnabled(false);

        chart.setDrawBorders(false);

        // no description text
        chart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid lines
//        chart.setDrawVerticalGrids(false); // 是否显示水平的表格
        chart.setDrawGridBackground(false); // 是否显示水平的表格
        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false); // 是否显示表格颜色
//        chart.setGridColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
//        chart.setGridWidth(1.25f);// 表格线的线宽
        // enable touch gestures
        chart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        chart.setDragEnabled(true);// 是否可以拖拽
        chart.setScaleEnabled(true);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);//

        chart.setBackgroundColor(color);// 设置背景

//        chart.setValueTypeface(mTf);// 设置字体

        // add data
        chart.setData(data); // 设置数据

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend(); // 设置标示，就是那个一组y的value的

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.CIRCLE);// 样式
        l.setFormSize(6f);// 字体
        l.setTextColor(Color.WHITE);// 颜色
//        l.setTypeface(mTf);// 字体

        YAxis y = chart.getAxisLeft(); // y轴的标示
        y.setTextColor(Color.WHITE);
        y.setDrawGridLines(false);
        y.setDrawLabels(false); // no axis labels
        y.setDrawAxisLine(false); // no axis line
        y.setDrawGridLines(false); // no grid lines
        y.setDrawZeroLine(true); // draw a zero line
        y.setLabelCount(2); // y轴上的标签的显示的个数
//        y.setTypeface(mTf);

        chart.getAxisRight().setEnabled(false);

        XAxis x = chart.getXAxis(); // x轴显示的标签
        x.setTextColor(Color.WHITE);
        x.setDrawGridLines(false);
//        x.setTypeface(mTf);

        // animate calls invalidate()...
        chart.animateX(2500); // 立即执行的动画,x轴
    }

    // 生成一个数据，
    LineData getData(int count, float range) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xVals.add(""+i % 12);
        }

        // y轴的数据
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f); // 线宽
        set1.setCircleSize(3f);// 显示的圆形大小
        set1.setColor(Color.WHITE);// 显示颜色
        set1.setCircleColor(Color.WHITE);// 圆形的颜色
        set1.setHighLightColor(Color.WHITE); // 高亮的线的颜色

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(set1);

        return data;
    }
}
