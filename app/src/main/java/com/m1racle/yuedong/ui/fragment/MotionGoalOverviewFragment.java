package com.m1racle.yuedong.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.util.UIUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Yuedong app
 * Motion Goal Overview Fragment
 * @author sczyh30
 * @since v1.40
 */
public class MotionGoalOverviewFragment extends BaseFragment {

    @Bind(R.id.tv_mg_step)
    TextView mTvStep;
    @Bind(R.id.tv_mg_distance)
    TextView mTvDistance;
    @Bind(R.id.tv_mg_time)
    TextView mTvTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_goal_overview, container, false);
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
                refreshUI();
            }
        };
        broadcastManager.registerReceiver(receiver, goalIntentFilter);
    }

    @Override
    public void initView(View view) {
        refreshUI();
    }

    private void refreshUI() {
        float a = XmlCacheManager.readMutiGoal(Constants.GOAL_STEP);
        float b = XmlCacheManager.readMutiGoal(Constants.GOAL_DISTANCE);
        float c = XmlCacheManager.readMutiGoal(Constants.GOAL_TIME);
        if(a >= 0)
            mTvStep.setText(String.format("%s 步", String.valueOf(a)));
        if(b >= 0)
            mTvDistance.setText(String.format("%s公里", String.valueOf(b)));
        if(c >= 0)
            mTvTime.setText(String.format("%s 分钟", String.valueOf(c)));
    }

    @Override
    @OnClick({R.id.btn_change_goal})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_goal:
                UIUtil.showMotionGoalSet(getActivity());
                break;
        }
    }
}
