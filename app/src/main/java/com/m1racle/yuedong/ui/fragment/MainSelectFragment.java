package com.m1racle.yuedong.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.ui.activity.MotionDataActivity;
import com.m1racle.yuedong.ui.activity.MotionGoalActivity;
import com.m1racle.yuedong.ui.activity.WeightActivity;
import com.m1racle.yuedong.util.UIUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Yuedong App
 * Device, Motion, Health Basic Functions Intro Fragment
 * @author sczyh30
 * @since 0.2.28
 */
public class MainSelectFragment extends BaseFragment {

    public MainSelectFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_function_select, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.fn_button_advice, R.id.fn_button_alarm, R.id.fn_button_mt_data, R.id.fn_button_mt_everyday,
        R.id.fn_button_mt_goal, R.id.fn_button_sleep, R.id.fn_button_temperature, R.id.fn_button_userinfo, R.id.fn_button_weight})
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fn_button_userinfo:
                UIUtil.showDeviceUserInfo(getActivity());
                break;
            case R.id.fn_button_mt_everyday:
                UIUtil.showEverydayMotion(getActivity());
                break;
            case R.id.fn_button_mt_data:
                //UIUtil.showDeviceMotionData(getActivity());
                Intent imd = new Intent(getActivity(), MotionDataActivity.class);
                startActivity(imd);
                break;
            case R.id.fn_button_mt_goal:
                //UIUtil.showMotionGoal(getActivity());
                Intent isg = new Intent(getActivity(), MotionGoalActivity.class);
                startActivity(isg);
                break;
            case R.id.fn_button_sleep:
                UIUtil.showSleepObserver(getActivity());
                break;
            case R.id.fn_button_alarm:
                UIUtil.showDeviceAlarm(getActivity());
                break;
            case R.id.fn_button_advice:
                UIUtil.showHealthAdvice(getActivity());
                break;
            case R.id.fn_button_temperature:
                UIUtil.showTemperature(getActivity());
                break;
            case R.id.fn_button_weight:
                Intent iw = new Intent(getActivity(), WeightActivity.class);
                startActivity(iw);
                break;
            default:
                break;
        }
    }
}
