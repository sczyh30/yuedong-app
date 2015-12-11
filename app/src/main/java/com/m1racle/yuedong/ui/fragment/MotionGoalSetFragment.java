package com.m1racle.yuedong.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.util.ToastUtil;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong app
 * Motion Goal Set Fragment
 */
public class MotionGoalSetFragment extends BaseFragment {

    // TODO: UNFINISHED

    private HuaweiWearableManager HWManager;
    private int error_code = 0;

    @Bind(R.id.et_goal_step)
    EditText mEtStep;
    @Bind(R.id.et_goal_distance)
    EditText mEtDistance;
    @Bind(R.id.et_goal_time)
    EditText mEtTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_goal_set, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        float a = XmlCacheManager.readMutiGoal(Constants.GOAL_STEP);
        float b = XmlCacheManager.readMutiGoal(Constants.GOAL_DISTANCE);
        float c = XmlCacheManager.readMutiGoal(Constants.GOAL_TIME);
        if(a >= 0)
            mEtStep.setText(String.valueOf(a));
        if(b >= 0)
            mEtDistance.setText(String.valueOf(b));
        if(c >= 0)
            mEtTime.setText(String.valueOf(c));
    }

    private void save() {
        if(mEtStep.length() == 0)
            XmlCacheManager.saveMutiGoal(0.0f, Constants.GOAL_STEP);
        else
            XmlCacheManager.saveMutiGoal(Float.parseFloat(mEtStep.getText().toString()), Constants.GOAL_STEP);

        if(mEtDistance.length() == 0)
            XmlCacheManager.saveMutiGoal(0.0f, Constants.GOAL_DISTANCE);
        else
            XmlCacheManager.saveMutiGoal(Float.parseFloat(mEtDistance.getText().toString()), Constants.GOAL_DISTANCE);

        if(mEtTime.length() == 0)
            XmlCacheManager.saveMutiGoal(0.0f, Constants.GOAL_TIME);
        else
            XmlCacheManager.saveMutiGoal(Float.parseFloat(mEtTime.getText().toString()), Constants.GOAL_TIME);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finish, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                save();
                ToastUtil.toast(R.string.weight_sync_goal_ok);
                Intent intent = new Intent(Constants.INTENT_ACTION_ON_GOAL_CHANGE);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                getActivity().finish();
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        initHWManager();
    }

    private void initHWManager() {
        HWManager = getHuaweiManager();
        if(HWManager != null) {
            HWManager.registerConnectStateCallback(stateCallBack);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private IDeviceConnectStatusCallback stateCallBack = new IDeviceConnectStatusCallback() {
        @Override
        public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
            Message message = Message.obtain();
            message.what = HWServiceConfig.CONNECT_DEVICE;
            message.obj = status;
            message.arg1 = err_code;
            mHandler.sendMessage(message);
        }
    };

    private class MyHandler extends Handler {
        private final WeakReference<MotionGoalSetFragment> mFragment;

        public MyHandler(MotionGoalSetFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {

            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);


}
