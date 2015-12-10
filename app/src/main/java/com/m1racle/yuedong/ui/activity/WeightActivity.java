package com.m1racle.yuedong.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.ui.fragment.WeightChartFragment;
import com.m1racle.yuedong.ui.fragment.WeightMainFragment;
import com.m1racle.yuedong.adapter.BasicFragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Yuedong App
 * Weight data activity
 * @author sczyh30
 * @since v1.30
 */
public class WeightActivity extends BaseActivity {

    public static final String WEIGHT_TRACE = "体重跟踪";
    public static final String WEIGHT_TREND = "体重趋势";

    @Bind(R.id.weight_tab)
    TabLayout mTab;
    @Bind(R.id.weight_vp)
    ViewPager mViewPager;

    private BasicFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weight;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.btn_fn_weight;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // init fragment list
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new WeightMainFragment());
        fragmentList.add(new WeightChartFragment());
        // init title list
        List<String> titleList = new ArrayList<>();
        titleList.add(WEIGHT_TRACE);
        titleList.add(WEIGHT_TREND);
        // init the adapter
        adapter = new BasicFragmentPagerAdapter(getFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        // init the tab
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }


}
