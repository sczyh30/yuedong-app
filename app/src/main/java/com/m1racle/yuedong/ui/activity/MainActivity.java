package com.m1racle.yuedong.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.ui.fragment.adapter.UsualViewPagerAdapter;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.entity.Notice;
import com.m1racle.yuedong.service.HWService;
import com.m1racle.yuedong.service.receiver.NetworkChangeReceiver;
import com.m1racle.yuedong.ui.fragment.DeviceBasicInfoFragment;
import com.m1racle.yuedong.ui.fragment.MainSelectFragment;
import com.m1racle.yuedong.ui.fragment.MySocialInfoFragment;
import com.m1racle.yuedong.ui.fragment.YdActiListFragment;
import com.m1racle.yuedong.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements MainActivityListener {
    @Bind(R.id.pager_tab)
    PagerTabStrip tabStrip;

    @Bind(R.id.viewpager_pk)
    ViewPager viewPager;

    private static final int TAB_COUNT = 4;
    private UsualViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    public static final int MENU_TEST = R.menu.global;
    public static Notice mNotice;

    private IntentFilter netFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initNetStatus();
        //startDeviceService();
    }

    private void initNetStatus() {
        netFilter = new IntentFilter();
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, netFilter);
    }

    private void initView() {
        tabStrip.setBackgroundColor(getResources().getColor(R.color.main_green));
        fragmentList = new ArrayList<>();
        fragmentList.add(new DeviceBasicInfoFragment());
        fragmentList.add(new MainSelectFragment());
        fragmentList.add(new YdActiListFragment());
        fragmentList.add(new MySocialInfoFragment());
        adapter = new UsualViewPagerAdapter(getFragmentManager(), fragmentList);
        adapter.setOnReloadListener(new UsualViewPagerAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragmentList = null;
                List<Fragment> list = new ArrayList<>();
                list.add(new DeviceBasicInfoFragment());
                list.add(new MainSelectFragment());
                list.add(new YdActiListFragment());
                list.add(new MySocialInfoFragment());
                adapter.setPagerItems(list);
            }
        });
        if (adapter != null)
            viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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
            UIUtil.showSetting(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        stopDeviceService();
    }

    private void startDeviceService() {
        Intent intent = new Intent(this, HWService.class);
        startService(intent);
    }

    private void stopDeviceService() {
        Intent intent = new Intent(this, HWService.class);
        stopService(intent);
    }
}