package com.m1racle.yuedong.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.dao.WeightDaoImpl;
import com.m1racle.yuedong.entity.Weight;
import com.m1racle.yuedong.ui.activity.WeightGoalActivity;
import com.m1racle.yuedong.ui.activity.WeightRecordActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Yuedong app
 * Weight main fragment<br>
 * This fragment provides basic weight data to user.
 * @author sczyh30
 * @since v1.30
 */
public class WeightMainFragment extends BaseFragment {

    WeightDaoImpl weightDao = new WeightDaoImpl();

    private Weight present;

    @Bind(R.id.tv_wg_weight)
    TextView mTvWeight;
    @Bind(R.id.tv_wg_height)
    TextView mTvHeight;
    @Bind(R.id.tv_wg_index)
    TextView mTvBMI;
    @Bind(R.id.tv_wg_time)
    TextView mTvTime;

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
        present = weightDao.getLatest();

    }

    @Override
    public void initView(View view) {
        if(present != null) {
            mTvWeight.setText(String.format("%s 公斤", String.valueOf(present.getWeight())));
            mTvHeight.setText(String.format("%s 厘米", String.valueOf(present.getHeight())));
            mTvTime.setText(present.getwTime());
            mTvBMI.setText(String.valueOf(present.getIndex()));
        }
    }

    @Override
    @OnClick({R.id.btn_weight_record, R.id.btn_weight_sg})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weight_record:
                Intent i1 = new Intent(getActivity(), WeightRecordActivity.class);
                startActivity(i1);
                break;
            case R.id.btn_weight_sg:
                Intent i2 = new Intent(getActivity(), WeightGoalActivity.class);
                startActivity(i2);
                break;
            default:
                break;
        }
    }

}
