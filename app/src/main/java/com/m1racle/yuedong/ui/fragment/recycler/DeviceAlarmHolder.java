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
        mTvTitle.setText(data.getAlarm_name().equals("") ? "无" : data.getAlarm_name());
        mSwitch.setChecked(data.isAlarm_enable());
    }

    public String convertTime(int rawTime) {
        String h = "";
        String m = "";
        if(rawTime < 1000 && rawTime >= 100) {
            h = "0" + Integer.toString(rawTime).substring(0, 1);
            m = Integer.toString(rawTime).substring(1, 3);
        } else if(rawTime >= 1000) {
            h = Integer.toString(rawTime).substring(0, 2);
            m = Integer.toString(rawTime).substring(2, 4);
        } else if(rawTime >= 10 && rawTime < 100) {
            h = "00";
            m = Integer.toString(rawTime);
        } else if(rawTime >= 0 && rawTime < 10) {
            h = "00";
            m = "0" + Integer.toString(rawTime);
        }

        return h + ":" + m;
    }

    public static int getHour(int rawTime) {
        String h = "0";
        if(rawTime < 1000 && rawTime >= 100) {
            h = Integer.toString(rawTime).substring(0, 1);
        } else if(rawTime >= 1000) {
            h = Integer.toString(rawTime).substring(0, 2);
        }
        return Integer.parseInt(h);
    }

    public static int getMinute(int rawTime) {
        String m = "0";
        if(rawTime < 1000 && rawTime >= 100) {
            m = Integer.toString(rawTime).substring(1, 3);
        } else if(rawTime >= 1000) {
            m = Integer.toString(rawTime).substring(2, 4);
        } else if(rawTime >= 0 && rawTime < 100) {
            m = Integer.toString(rawTime);
        }
        return Integer.parseInt(m);
    }

    public String convertType(int rawType) {
        switch (rawType) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 4:
                return "周三";
            case 8:
                return "周四";
            case 16:
                return "周五";
            case 32:
                return "周六";
            case 64:
                return "周日";
            case 31:
                return "周一至周五";
            default:
                return "多日";
        }
    }

}
