package com.m1racle.yuedong.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.adapter.BasicFragmentPagerAdapter;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.ui.fragment.AchievementFragment;
import com.m1racle.yuedong.ui.fragment.DeviceMotionDataFragment;
import com.m1racle.yuedong.ui.fragment.MotionChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Yuedong app
 * Motion Data Activity
 * @author sczyh30
 * @since v1.40
 * This is a big refactor.
 */
public class MotionDataActivity extends BaseActivity {

    private static final int ACH_TYPE_MTBDATA = 1;

    @Bind(R.id.tab_motion_data)
    TabLayout mTab;
    @Bind(R.id.vp_motion_data)
    ViewPager mViewPager;

    private BasicFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_motion_data;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_activity_motion_data;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // init fragment list
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DeviceMotionDataFragment());
        fragmentList.add(new MotionChartFragment());
        fragmentList.add(new AchievementFragment(ACH_TYPE_MTBDATA));
        // init title list
        List<String> titleList = new ArrayList<>();
        titleList.add("运动总览");
        titleList.add("运动跟踪");
        titleList.add("运动成就");
        // init the adapter
        adapter = new BasicFragmentPagerAdapter(getFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        // init the tab
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}
