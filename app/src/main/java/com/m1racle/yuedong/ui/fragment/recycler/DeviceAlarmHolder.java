package com.m1racle.yuedong.ui.fragment.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.huawei.huaweiwearable.data.DataAlarm;
import com.m1racle.yuedong.R;

/**
 * Yuedong app
 * RecyclerView Holder for DeviceAlarmFragment
 */
public class DeviceAlarmHolder extends RecyclerView.ViewHolder {

    private DataAlarm mData;

    TextView mTvTime;
    TextView mTvTitle;
    TextView mTvWeek;
    Switch mSwitch;

    public DeviceAlarmHolder(View itemView) {
        super(itemView);
        mTvTime = (TextView) itemView.findViewById(R.id.tv_alarm_list_time);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_alarm_list_title);
        mTvWeek = (TextView) itemView.findViewById(R.id.tv_alarm_list_week);
        mSwitch = (Switch) itemView.findViewById(R.id.switch_alarm);
    }

    public void bindData(DataAlarm data) {
        mTvTime.setText(convertTime(data.getAlarm_time()));
        mTvWeek.setText(convertType(data.getAlarm_cycle()));
        mTvTitle.setText(data.getAlarm_name());
        mSwitch.setChecked(data.isAlarm_enable());
    }

    public String convertTime(int rawTime) {
        return "20:00";
    }

    public String convertType(int rawType) {
        return "周五";
    }

}
