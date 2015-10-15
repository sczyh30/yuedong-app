package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.data.DataAlarm;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong app
 * Device Alarm Set Fragment
 */
public class DeviceAlarmSetFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code = 0;
    private DataAlarm mAlarm;

    @Bind(R.id.alarm_timePicker)
    TimePicker timePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null) {
            Bundle args = getArguments();
            mAlarm = new DataAlarm();
            //mAlarm.setAlarm_enable(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_alarm_set, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finish, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                hideKeyboard();
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        initHWManager();
    }

    @Override
    public void initView(View view) {
        timePicker.setIs24HourView(true);
    }

    private void initHWManager() {
        HWManager = getHuaweiManager();
    }

    @Override
    public void onClick(View v) {

    }

    private class MyHandler extends Handler {
        private final WeakReference<DeviceAlarmSetFragment> mFragment;

        public MyHandler(DeviceAlarmSetFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.SET_DEVICE_ALARM:
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);
}
