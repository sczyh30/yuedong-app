package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;

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
    public void initView(View view) {

    }

    @Override
    public void onClick(View v) {

    }
}