package com.m1racle.yuedong.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.dao.WeightDaoImpl;
import com.m1racle.yuedong.entity.Weight;
import com.m1racle.yuedong.ui.activity.WeightGoalActivity;
import com.m1racle.yuedong.ui.activity.WeightRecordActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Yuedong app
 * Weight main fragment<br>
 * This fragment provides basic weight data to user.
 * @author sczyh30
 * @since v1.30
 */
public class WeightMainFragment extends BaseFragment {

    WeightDaoImpl weightDao = new WeightDaoImpl();

    float goal;

    @Bind(R.id.tv_wg_weight)
    TextView mTvWeight;
    @Bind(R.id.tv_wg_height)
    TextView mTvHeight;
    @Bind(R.id.tv_wg_index)
    TextView mTvBMI;
    @Bind(R.id.tv_wg_time)
    TextView mTvTime;
    @Bind(R.id.tv_wg_goal)
    TextView mTvGoal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_main, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        //present = weightDao.getLatest();
        // init the broadcast receiver
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter goalIntentFilter = new IntentFilter(Constants.INTENT_ON_WEIGHT_GOAL_CHANGE);
        IntentFilter dataIntentFilter = new IntentFilter(Constants.INTENT_ON_WEIGHT_PRESENT_CHANGE);
        BroadcastReceiver goalReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initGoal();
            }
        };
        BroadcastReceiver dataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initWData();
            }
        };
        broadcastManager.registerReceiver(goalReceiver, goalIntentFilter);
        broadcastManager.registerReceiver(dataReceiver, dataIntentFilter);
    }

    @Override
    public void initView(View view) {
        initWData();
        initGoal();
    }

    private void initWData() {
        Weight present = weightDao.getLatest();
        if(present != null) {
            mTvWeight.setText(String.format("%s 公斤", String.valueOf(present.getWeight())));
            mTvHeight.setText(String.format("%s 厘米", String.valueOf(present.getHeight())));
            mTvTime.setText(present.getwTime());
            mTvBMI.setText(String.valueOf(present.getIndex()));
        }
    }

    private void initGoal() {
        goal = XmlCacheManager.readWeightGoal();
        if(goal == 0.0f)
            mTvGoal.setText(R.string.weight_goal_not_set);
        else
            mTvGoal.setText(String.format("%s 公斤", String.valueOf(goal)));
    }

    @Override
    @OnClick({R.id.btn_weight_record, R.id.btn_weight_sg})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weight_record:
                Intent i1 = new Intent(getActivity(), WeightRecordActivity.class);
                startActivity(i1);
                break;
            case R.id.btn_weight_sg:
                Intent i2 = new Intent(getActivity(), WeightGoalActivity.class);
                startActivity(i2);
                break;
            default:
                break;
        }
    }

}
