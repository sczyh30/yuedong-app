package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;


/**
 * Yuedong App
 * Device Motion Data View Fragment
 */
public class DeviceMotionDataFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_motion_data, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initHWManager();
    }

    private void initHWManager() {
        HWManager = getHuaweiManager();
    }

    @Override
    public void onClick(View v) {

    }

    private class MyHandler extends Handler {
        private final WeakReference<DeviceMotionDataFragment> mFragment;

        public MyHandler(DeviceMotionDataFragment fragment) {
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
