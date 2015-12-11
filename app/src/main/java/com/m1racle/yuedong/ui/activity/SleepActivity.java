package com.m1racle.yuedong.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.adapter.BasicFragmentPagerAdapter;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.ui.fragment.SleepObserverFragment;
import com.m1racle.yuedong.ui.fragment.SleepOverviewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SleepActivity extends BaseActivity {

    @Bind(R.id.tab_sleep)
    TabLayout mTab;
    @Bind(R.id.vp_sleep)
    ViewPager mViewPager;

    private BasicFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sleep;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_activity_sleep;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // init fragment list
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SleepOverviewFragment());
        fragmentList.add(new SleepObserverFragment());
        // init title list
        List<String> titleList = new ArrayList<>();
        titleList.add("睡眠状况总览");
        titleList.add("睡眠详细信息");
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
