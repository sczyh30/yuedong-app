package com.m1racle.yuedong.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.dao.WeightDaoImpl;
import com.m1racle.yuedong.entity.Weight;
import com.m1racle.yuedong.ui.widget.SlideButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong app
 * Weight chart fragment<br>
 * This fragment provides vivid and explicit weight data charts to user.
 * @author sczyh30
 * @since v1.30
 */
public class WeightChartFragment extends BaseFragment {

    //TODO:代码多量重复，下一版本需进行进一步抽象实现代码复用(2015-12-10)

    WeightDaoImpl weightDao = new WeightDaoImpl();

    @Bind(R.id.chart_weight)
    LineChart mChart;
    @Bind(R.id.btn_update_wg_status)
    SlideButton btnRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_chart, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        // init the broadcast receiver
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter goalIntentFilter = new IntentFilter("com.m1racle.yuedong.action.ON_WEIGHT_GOAL_CHANGE");
        IntentFilter dataIntentFilter = new IntentFilter("com.m1racle.yuedong.action.ON_WEIGHT_PRESENT_CHANGE");
        BroadcastReceiver goalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //refreshGoal();
            }
        };
        BroadcastReceiver dataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshData();
            }
        };
        broadcastManager.registerReceiver(goalReceiver, goalIntentFilter);
        broadcastManager.registerReceiver(dataReceiver, dataIntentFilter);
    }

    @Override
    public void initView(View view) {
        initChart();
        btnRefresh.setBeforeText("刷新数据");
        btnRefresh.setOnSendClickListener(new SlideButton.OnSendClickListener() {
            @Override
            public void onSendClickListener(View v) {
                refreshData();
                btnRefresh.setCurrentState(SlideButton.STATE_DONE);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void refreshData() {
        setData(weightDao.getAll());
    }

    private void initChart() {
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
                    mChart.highlightValues(null);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart.setDrawGridBackground(false);

        mChart.setDescription("");
        mChart.setNoDataTextDescription("没有记录过数据");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        refreshData();
    }

    private void setData(List<Weight> list) {


        int count = list.size();
        if(count <= 0) {
            return;
        }
        // set x bar
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xVals.add(list.get(i).getwTime());
        }
        // set y bar (weight)
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals.add(new Entry(list.get(i).getWeight(), i));
        }

        /*ArrayList<Entry> BMIVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals.add(new Entry(list.get(i).getIndex(), i));
        }
        LineDataSet BMISet = new LineDataSet(BMIVals, "BMI");
        setDataSetStyle(BMISet);
        dataSets.add(BMISet);*/

        // create a dataset and give it a type
        LineDataSet weightSet = new LineDataSet(yVals, "体重");
        // set style
        setDataSetStyle(weightSet);
        // add the datasets
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(weightSet);
        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        // set data
        mChart.setData(data);
    }

    private void setDataSetStyle(LineDataSet dataSet) {
        int color = Color.rgb(147, 196, 125);

        dataSet.enableDashedLine(10f, 5f, 0f);
        dataSet.enableDashedHighlightLine(10f, 5f, 0f);
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setLineWidth(1f);
        dataSet.setCircleSize(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(color);
    }
}
