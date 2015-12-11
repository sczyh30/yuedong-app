package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;
import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong App
 * Device Motion Data View Fragment
 * @author sczyh30
 * @since v1.39
 */
public class DeviceMotionDataFragment extends BaseFragment {

    @Bind(R.id.cp_goal_circle)
    DashedCircularProgress progressGoal;

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
        progressGoal.setMax(10000);
        progressGoal.setValue(8866);
    }


    @Override
    public void onClick(View v) {

    }
}
