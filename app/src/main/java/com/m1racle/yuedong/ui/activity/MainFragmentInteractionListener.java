package com.m1racle.yuedong.ui.activity;

import com.m1racle.yuedong.ui.fragment.DeviceBasicInfoFragment;
import com.m1racle.yuedong.ui.fragment.MotionBasicInfoFragment;
import com.m1racle.yuedong.ui.fragment.MySocialInfoFragment;
import com.m1racle.yuedong.ui.fragment.YdActiListFragment;

/**
 * Main FragmentInteractionListener (for main activity)
 */
public interface MainFragmentInteractionListener
    extends MotionBasicInfoFragment.OnFragmentInteractionListener,
        DeviceBasicInfoFragment.OnFragmentInteractionListener,
        MySocialInfoFragment.OnFragmentInteractionListener,
        YdActiListFragment.OnFragmentInteractionListener{

}
