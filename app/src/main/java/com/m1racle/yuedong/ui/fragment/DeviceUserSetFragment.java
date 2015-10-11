package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataUserInfo;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import org.kymjs.kjframe.utils.StringUtils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong app
 * Device User Set Fragment
 */
public class DeviceUserSetFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private DataUserInfo mInfo;
    private int error_code = 0;
    private int cb_status = 0;

    @Bind(R.id.ed_dus_age)
    EditText mEtAge;
    @Bind(R.id.ed_dus_birthday)
    EditText mEtBirthday;
    @Bind(R.id.ed_dus_gender)
    EditText mEtGender;
    @Bind(R.id.ed_dus_height)
    EditText mEtHeight;
    @Bind(R.id.ed_dus_weight)
    EditText mEtWeight;
    @Bind(R.id.ed_dus_walk_step)
    EditText mEtWalkStep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_user_set, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDeviceUserInfo();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.submit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                sendInfo();
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        super.initData();
        initHWManager();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
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
            error_code = err_code;
        }
    };

    private boolean okForSubmit() {
        if(HWManager == null && error_code != 0) {
            ToastUtil.toast(R.string.tip_device_no_conn_warning);
            return false;
        }
        if(mEtAge.length() == 0) {
            mEtAge.setError("请输入年龄");
            mEtAge.requestFocus();
            return false;
        }
        return true;
    }

    private void sendInfo() {
        hideKeyboard();
        if(okForSubmit()) {
            showWaitDialog(R.string.saving_data).show();
            setInfoByView();
            setUserInfo();
            onHandleCallback();
        }
    }

    private void setInfoByView() {
        if(mInfo == null)
            mInfo = new DataUserInfo();
        mInfo.setAge(getIntForData(mEtAge));
        mInfo.setBirthday(getIntForData(mEtBirthday));
        mInfo.setGender(getString(mEtGender).equals("男") ? 1 : 2);
        mInfo.setHeight(getIntForData(mEtHeight));
        mInfo.setHeight(getIntForData(mEtHeight));
        mInfo.setWalk_step_length(getIntForData(mEtWalkStep));
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }

    private int getIntForData(EditText editText) {
        String temp = editText.getText().toString();
        return temp.equals("") ? 0 : StringUtils.toInt(temp);
    }

    private void setUserInfo() {
        if(error_code != 0) {
            ToastUtil.toast(R.string.tip_device_no_conn_warning);
            return;
        }
        if(mInfo == null) {
            ToastUtil.toast("手环用户信息出现异常");
            return;
        }
        HWManager.setUserInfo(HWServiceConfig.HUAWEI_TALKBAND_B2, mInfo, new IResultReportCallback() {
            @Override
            public void onSuccess(Object object) {
                cb_status = 6666;
            }

            @Override
            public void onFailure(int err_code, String err_msg) {
                cb_status = err_code;
            }
        });
    }

    private void onHandleCallback() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(cb_status == 6666) {
                    ToastUtil.toast("手环用户信息更改成功！");
                    getActivity().finish();
                } else {
                    ToastUtil.toast("更改手环信息状态异常" + "(" + cb_status + ")");
                }
                hideWaitDialog();
            }
        }, 1000);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<DeviceUserSetFragment> mFragment;

        public MyHandler(DeviceUserSetFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_USER_INFO:
                    mFragment.get().mInfo = (DataUserInfo)object;
                    updateUI();
                    break;
                default:
                    break;
            }
        }

        private void updateUI() {
            LogUtil.log("GET => Log :" + mFragment.get().mInfo.toString());
            mFragment.get().mEtAge.setText(getInfoStr(mFragment.get().mInfo.getAge()));
            mFragment.get().mEtBirthday.setText(getInfoStr(mFragment.get().mInfo.getBirthday()));
            mFragment.get().mEtGender.setText(mFragment.get().mInfo.getGender() == 1 ? "男" : "女");
            mFragment.get().mEtHeight.setText(getInfoStr(mFragment.get().mInfo.getHeight()));
            mFragment.get().mEtWeight.setText(getInfoStr(mFragment.get().mInfo.getWeight()));
            mFragment.get().mEtWalkStep.setText(Integer.toString(mFragment.get().mInfo.getWalk_step_length()));
        }

        private String getInfoStr(int id) {
            if(id == 0)
                return "";
            else
                return Integer.toString(id);
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    private void getDeviceUserInfo() {
        if(HWManager != null) {
            HWManager.getUserInfo(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {
                @Override
                public void onSuccess(Object object) {
                    Message message = Message.obtain();
                    message.what = HWServiceConfig.GET_DEVICE_USER_INFO;
                    message.obj = object;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFailure(int err_code, String err_msg) {

                }
            });
        }
    }
}
