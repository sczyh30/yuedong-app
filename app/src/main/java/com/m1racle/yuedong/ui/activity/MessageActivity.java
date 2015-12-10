package com.m1racle.yuedong.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.adapter.BasicFragmentPagerAdapter;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.ui.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.m1racle.yuedong.base.Constants.*;

/**
 * Yuedong app
 * Social message activity
 * @author sczyh30
 * @since v1.36
 */
public class MessageActivity extends BaseActivity {

    @Bind(R.id.tab_my_message)
    TabLayout mTab;
    @Bind(R.id.vp_my_message)
    ViewPager mViewPager;
    @Bind(R.id.btnActionRefresh)
    FloatingActionButton btnRefresh;

    private BasicFragmentPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.title_activity_message;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // init fragment list
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MessageFragment(MESSAGE_DONGTAI));
        fragmentList.add(new MessageFragment(MESSAGE_NOTICE));
        // init title list
        List<String> titleList = new ArrayList<>();
        titleList.add("动态");
        titleList.add("信息");
        // init the adapter
        adapter = new BasicFragmentPagerAdapter(getFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        // init the tab
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    @OnClick(R.id.btnActionRefresh)
    public void onClick(View v) {

    }
}
