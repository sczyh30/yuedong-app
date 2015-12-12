package com.m1racle.yuedong.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.m1racle.yuedong.dao.SleepDao;
import com.m1racle.yuedong.entity.SleepData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SleepOverviewFragment extends BaseFragment {

    SleepDao sleepDao = new SleepDao();

    List<SleepData> mList;

    @Bind(R.id.chart_sleep)
    LineChart mChart;
    @Bind(R.id.tv_sleep_quality)
    TextView mTvSleepQuality;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep_overview, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        initChart();
    }

    private void refreshData() {
        mList = sleepDao.getAllData();
        setData(mList);
        mTvSleepQuality.setText(calcQuality());
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

    private void setData(List<SleepData> list) {

        int count = list.size();
        if(count <= 0) {
            return;
        }
        // set x bar
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xVals.add(list.get(i).getDate());
        }
        // set y bar (weight)
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals.add(new Entry(list.get(i).getDeep() + list.get(i).getLight(), i));
        }

        /*ArrayList<Entry> BMIVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals.add(new Entry(list.get(i).getIndex(), i));
        }
        LineDataSet BMISet = new LineDataSet(BMIVals, "BMI");
        setDataSetStyle(BMISet);
        dataSets.add(BMISet);*/

        // create a dataset and give it a type
        LineDataSet weightSet = new LineDataSet(yVals, "睡眠时长（分钟）");
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
        int color = Color.rgb(61, 134, 198);

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

    private String calcQuality() {
        if(mList != null) {
            final int count = mList.size();
            final String status;
            //new Thread(new Runnable() {
             //   @Override
               // public void run() {
                    int total_d = 0;
                    int total_l = 0;
                    for(SleepData d : mList) {
                        total_d += d.getDeep();
                        total_l += d.getLight();
                    }
                    int avg_d = total_d / count;
                    int avg_l = total_l / count;
                    if(avg_d >= 400 && avg_l <= 90)
                        return "很好";
                    else if (avg_d >= 300 && avg_d < 360 && avg_l <= 100 && (avg_d >= 360 && avg_l > 90))
                        return "良好";
                    else
                        return "一般";
                }
            //});
        //}
        return "未知";
    }

    @Override
    public void onClick(View v) {

    }
}
