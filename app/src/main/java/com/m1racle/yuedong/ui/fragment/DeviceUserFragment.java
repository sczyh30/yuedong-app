package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;

import butterknife.ButterKnife;


/**
 * Yuedong App
 * Device User Fragment
 * @author sczyh30
 */
public class DeviceUserFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_user, container, false);
        initData();
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
