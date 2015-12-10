package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.dao.WeightDaoImpl;

import butterknife.ButterKnife;

/**
 * Yuedong app
 * Weight main fragment<br>
 * This fragment provides basic weight data to user.
 * @author sczyh30
 * @since v1.30
 */
public class WeightMainFragment extends BaseFragment {

    WeightDaoImpl weightDao = new WeightDaoImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_main, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void onClick(View v) {

    }

}
