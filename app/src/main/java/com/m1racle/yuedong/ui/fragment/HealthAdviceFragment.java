package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.dao.EverydayMotionDao;
import com.m1racle.yuedong.util.DateUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong App
 * Health Advice Fragment
 */
public class HealthAdviceFragment extends BaseFragment {

    @Bind(R.id.tv_goal)
    TextView mTvGoal;
    @Bind(R.id.tv_motion_advice)
    TextView mTvAdvice;
    @Bind(R.id.iv_advice_level)
    ImageView mIvLevel;

    private int goal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_advice, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        goal = XmlCacheManager.readGoal(1).getStep_goal();
        updateUI();
    }

    @Override
    public void onClick(View v) {

    }

    private int getAdviceByGoal() {
        float goal = XmlCacheManager.readMutiGoal("step_goal");
        EverydayMotionDao dao = new EverydayMotionDao();
        int present = dao.getByDate(DateUtil.getToday()).getStep();
        float ratio = goal / present;
        if(ratio == 0)
            ratio = 0.6f;

        if(goal >= 4000 && goal <= 16000 && ratio >= 0.5)
            return R.string.advice_level_1;
        else if((goal > 16000 && goal <= 22000) || (goal >= 1800 && goal < 4000) && ratio >= 0.5)
            return R.string.advice_level_2;
        else if(goal > 22000 && ratio >= 0.5)
            return R.string.advice_level_f;
        else if(goal > 22000 && ratio < 0.5)
            return R.string.advice_level_fjc;
        else
            return R.string.advice_level_3;
    }

    private int getLevelByGoal() {
        if(goal >= 4000 && goal <= 16000)
            return R.mipmap.s_health_ani_icon_comfort_b08;
        else if((goal > 16000 && goal <= 22000) || (goal >= 1800 && goal < 4000))
            return R.mipmap.s_health_ani_icon_comfort_b07;
        else if(goal > 22000)
            return R.mipmap.s_health_ani_icon_comfort_b06;
        else
            return R.mipmap.s_health_ani_icon_comfort_b05;
    }

    private void updateUI() {
        mTvAdvice.setText(getAdviceByGoal());
        mIvLevel.setImageResource(getLevelByGoal());
    }
}
