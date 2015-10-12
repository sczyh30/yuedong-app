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

import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.UIUtil;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;


/**
 * Yuedong App
 * Device Alarm Fragment
 */
public class DeviceAlarmFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_alarm, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                LogUtil.toast("添加闹钟");
                UIUtil.showDeviceAlarmSet(getActivity(), null);
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        HWManager = getHuaweiManager();
    }

    @Override
    public void onClick(View v) {

    }

    private class MyHandler extends Handler {
        private final WeakReference<DeviceAlarmFragment> mFragment;

        public MyHandler(DeviceAlarmFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {

            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

}
