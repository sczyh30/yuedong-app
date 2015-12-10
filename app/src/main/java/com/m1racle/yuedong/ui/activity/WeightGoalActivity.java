package com.m1racle.yuedong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class WeightGoalActivity extends BaseActivity {

    @Bind(R.id.et_weight_rec_wg)
    EditText mEtGoal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weight_goal;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mEtGoal.setText(String.valueOf(XmlCacheManager.readWeightGoal()));
    }

    private boolean validate() {
        if(mEtGoal.length() == 0) {
            mEtGoal.setError("请输入您的目标体重");
            mEtGoal.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    @OnClick({R.id.btn_weight_save_goal})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weight_save_goal:
                if(validate()) {
                    XmlCacheManager.saveWeightGoal(Float.parseFloat(mEtGoal.getText().toString()));
                    ToastUtil.toast(R.string.weight_sync_goal_ok);
                    Intent intent = new Intent("com.m1racle.yuedong.action.ON_WEIGHT_GOAL_CHANGE");
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
