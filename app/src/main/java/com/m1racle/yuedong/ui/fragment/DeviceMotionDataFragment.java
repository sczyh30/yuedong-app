package com.m1racle.yuedong.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;
import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.dao.EverydayMotionDao;
import com.m1racle.yuedong.entity.StepDayData;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.util.DateUtil;

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
    @Bind(R.id.tv_emd_consume)
    TextView mTvConsume;
    @Bind(R.id.tv_emd_distance)
    TextView mTvDistance;
    @Bind(R.id.tv_emd_goal_step)
    TextView mTvGoal;
    @Bind(R.id.tv_emd_present_step)
    TextView mTvPresent;

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
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter goalIntentFilter = new IntentFilter(Constants.INTENT_ACTION_ON_GOAL_CHANGE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateDataUI();
            }
        };
        broadcastManager.registerReceiver(receiver, goalIntentFilter);
    }

    @Override
    public void initView(View view) {
        updateDataUI();
    }

    private void updateDataUI() {
        EverydayMotionDao dao = new EverydayMotionDao();
        StepDayData data = dao.getByDate(DateUtil.getToday());
        if(data != null) {
            mTvPresent.setText(String.valueOf(data.getStep()));
            mTvConsume.setText(String.valueOf(data.getCalorie()));
            mTvDistance.setText(String.valueOf(data.getDistance()));
            progressGoal.setValue(data.getStep());
        } else {
            mTvPresent.setText("0");
            mTvConsume.setText("0");
            mTvDistance.setText("0.00");
            progressGoal.setValue(0);
        }
        int goal = (int) XmlCacheManager.readMutiGoal(Constants.GOAL_STEP);
        if(goal == 0) {
            mTvGoal.setText(String.valueOf(goal));
            progressGoal.setMax(10000);
        }
        else {
            mTvGoal.setText("10000");
            progressGoal.setMax(goal);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
