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
 * Yuedong app
 * Motion Goal Set Fragment
 */
public class MotionGoalSetFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_goal_set, container, false);
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
        if(HWManager != null) {
            HWManager.registerConnectStateCallback(stateCallBack);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private IDeviceConnectStatusCallback stateCallBack = new IDeviceConnectStatusCallback() {
        @Override
        public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
            Message message = Message.obtain();
            message.what = HWServiceConfig.CONNECT_DEVICE;
            message.obj = status;
            message.arg1 = err_code;
            mHandler.sendMessage(message);
        }
    };

    private class MyHandler extends Handler {
        private final WeakReference<MotionGoalSetFragment> mFragment;

        public MyHandler(MotionGoalSetFragment fragment) {
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
