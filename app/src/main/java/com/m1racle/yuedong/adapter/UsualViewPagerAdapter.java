package com.m1racle.yuedong.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPagerAdapter of the Fragment
 */
public class UsualViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private FragmentManager mFragmentManager;
    private ArrayList<String> titleList = new ArrayList<String>();

    public interface OnReloadListener
    {
        void onReload();
    }
    /**
     * 当数据发生改变时的回调接口
     */
    private OnReloadListener mListener;

    public UsualViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        titleList.add("设备状态");
        titleList.add("运动数据");
        titleList.add("约动起来");
        titleList.add("我的悦动");
        this.fragmentList = fragmentList;
        mFragmentManager = fragmentManager;
    }

    public void setOnReloadListener(OnReloadListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("Adapter Activity(s10)", "Adapter items get...");
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setPagerItems(List<Fragment> list) {
        if(list != null) {
            for(Fragment fragment : list) {
                mFragmentManager.beginTransaction().remove(fragment).commit();
            }
            this.fragmentList = list;
        }
    }

    public void reload() {
        if(mListener != null) {
            mListener.onReload();
        }
        this.notifyDataSetChanged();
    }


}