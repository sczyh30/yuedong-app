package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;

import butterknife.ButterKnife;


/**
 * Yuedong App
 * My Social Util Fragment
 */
public class MySocialUtilFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_social_util, container, false);
        initData();
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {

    }
}
