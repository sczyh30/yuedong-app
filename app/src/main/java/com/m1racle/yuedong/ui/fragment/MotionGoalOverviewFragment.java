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

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong app
 * Motion Goal Overview Fragment
 * @author sczyh30
 * @since v1.40
 */
public class MotionGoalOverviewFragment extends BaseFragment {

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
        //broadcastManager.registerReceiver(receiver, goalIntentFilter);
    }

    @Override
    public void initView(View view) {

    }

    private void refreshUI() {

    }

    @Override
    public void onClick(View v) {

    }
}
