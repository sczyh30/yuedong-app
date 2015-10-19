package com.m1racle.yuedong.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huawei.huaweiwearable.data.DataRawSleepData;
import com.m1racle.yuedong.R;

import java.text.SimpleDateFormat;

/**
 * Sleep Data Holder
 */
public class SleepDataHolder extends RecyclerView.ViewHolder {

    private TextView mTvType;
    private TextView mTvTime;
    private TextView mTvJTime;

    public SleepDataHolder(View itemView) {
        super(itemView);
        mTvType = (TextView)itemView.findViewById(R.id.tv_dsl_type);
        mTvTime = (TextView)itemView.findViewById(R.id.tv_dsl_time);
        mTvJTime = (TextView)itemView.findViewById(R.id.tv_dsl_jlsj);
    }

    public void bindData(DataRawSleepData data) {
        mTvType.setText(getMotionType(data.getCurrentStatus()));
        mTvTime.setText(String.format("%s 分钟", getString(data.getTotalSleepTime())));
        mTvJTime.setText(getDate(data.getStartTime()));
    }

    private String getString(int i) {
        return Integer.toString(i);
    }

    private String getMotionType(int i) {
        switch (i) {
            case 10:
                return "浅睡";
            case 11:
                return "深睡";
            default:
                return "未知";
        }
    }

    private String getDate(int rawTime) {
        try {
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return fm.format(rawTime);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
