package com.m1racle.yuedong.ui.activity;

import android.view.View;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;

/**
 * Yuedong App
 * Weight data activity
 * @author sczyh30
 * @since v1.30
 */
public class WeightActivity extends BaseActivity {


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
    public void onClick(View v) {

    }
}
