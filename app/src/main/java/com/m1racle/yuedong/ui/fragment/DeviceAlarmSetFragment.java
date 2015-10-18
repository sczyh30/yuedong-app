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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataAlarm;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.ui.fragment.recycler.DeviceAlarmHolder;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong app
 * Device Alarm Set Fragment
 */
public class DeviceAlarmSetFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code = 0;
    private int cb_status = 0;
    private int index = 0;
    private ArrayList<DataAlarm> mList = new ArrayList<>();
    private DataAlarm mAlarm;

    @Bind(R.id.alarm_timePicker)
    TimePicker timePicker;
    @Bind(R.id.et_alarm_set_title)
    EditText mEtName;

    @Bind(R.id.cb_week_1)
    CheckBox mCbWeek1;
    @Bind(R.id.cb_week_2)
    CheckBox mCbWeek2;
    @Bind(R.id.cb_week_3)
    CheckBox mCbWeek3;
    @Bind(R.id.cb_week_4)
    CheckBox mCbWeek4;
    @Bind(R.id.cb_week_5)
    CheckBox mCbWeek5;
    @Bind(R.id.cb_week_6)
    CheckBox mCbWeek6;
    @Bind(R.id.cb_week_7)
    CheckBox mCbWeek7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null) {
            Bundle args = getArguments();
            mAlarm = new DataAlarm();
            mAlarm.setAlarm_enable(args.getBoolean("enable"));
            index = args.getInt("index");
            mAlarm.setAlarm_index(index);
            mAlarm.setAlarm_cycle(args.getInt("cycle"));
            mAlarm.setAlarm_name(args.getString("name"));
            mAlarm.setAlarm_time(args.getInt("time"));
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
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
                handleChange();
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        HWManager = getHuaweiManager();
    }

    @Override
    public void initView(View view) {
        timePicker.setIs24HourView(true);
    }

    @Override
    public void onClick(View v) {

    }

    private void updateUI() {
        if(mAlarm != null) {
            int rawTime = mAlarm.getAlarm_time();
            mEtName.setText(mAlarm.getAlarm_name());
            timePicker.setCurrentHour(DeviceAlarmHolder.getHour(rawTime));
            timePicker.setCurrentMinute(DeviceAlarmHolder.getMinute(rawTime));
            updateUIByCycle(mAlarm.getAlarm_cycle());
        }
    }

    private void handleChange() {
        showWaitDialog(R.string.sync_data).show();
        if(HWManager != null && mAlarm != null) {
            summaryData();
            mList.remove(mAlarm.getAlarm_index() - 1);
            mList.add(mAlarm.getAlarm_index() - 1, mAlarm);
            HWManager.setAlarmList(HWServiceConfig.HUAWEI_TALKBAND_B2, mList, new IResultReportCallback() {
                @Override
                public void onSuccess(Object object) {
                    cb_status = 6666;
                }

                @Override
                public void onFailure(int err_code, String err_msg) {
                    cb_status = err_code;
                }
            });
        }
        onHandleCallback();
    }

    private void summaryData() {
        mAlarm.setAlarm_name(mEtName.getText().toString());
        mAlarm.setAlarm_enable(true);
        mAlarm.setAlarm_cycle(getCycleToSet());
        mAlarm.setAlarm_time(getTime());
        mAlarm.setAlarm_index(index);
        mAlarm.setAvailable(true);
    }

    private int getCycleToSet() {
        int cycle = 0;
        if(mCbWeek1.isChecked())
            cycle += 1;
        if(mCbWeek2.isChecked())
            cycle += 2;
        if(mCbWeek3.isChecked())
            cycle += 4;
        if(mCbWeek4.isChecked())
            cycle += 8;
        if(mCbWeek5.isChecked())
            cycle += 16;
        if(mCbWeek6.isChecked())
            cycle += 32;
        if(mCbWeek7.isChecked())
            cycle += 64;
        return cycle;
    }

    private void updateUIByCycle(int cycle) {
        String s = Integer.toBinaryString(cycle);
    }

    private int getTime() {
        return timePicker.getCurrentHour() * 100 + timePicker.getCurrentMinute();
    }

    private void onHandleCallback() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(cb_status == 6666) {
                    ToastUtil.toast("手环信息同步成功！");
                    getActivity().finish();
                } else {
                    ToastUtil.toast("手环信息同步异常" + "(" + cb_status + ")");
                }
                hideWaitDialog();
            }
        }, 1000);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<DeviceAlarmSetFragment> mFragment;

        public MyHandler(DeviceAlarmSetFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_ALARM:
                    mFragment.get().mList = (ArrayList<DataAlarm>)object;
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private void getAlarms() {
        if(HWManager != null) {
            HWManager.getAlarmList(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {
                @Override
                public void onSuccess(Object object) {
                    Message message = Message.obtain();
                    message.what = HWServiceConfig.GET_DEVICE_ALARM;
                    message.obj = object;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFailure(int err_code, String err_msg) {
                    LogUtil.log("fucking error: " + err_msg);
                }
            });
        }
    }
}
