package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.util.LogUtil;

import java.lang.ref.WeakReference;

/**
 * Base Fragment with Huawei API Service Impl
 * @author sczyh30
 * @since 0.2
 */
@Deprecated
public abstract class BaseFragmentHWS extends BaseFragment {

    protected HuaweiWearableManager HWmanager = null;

    protected static class MyHandler extends Handler {
        private final WeakReference<DeviceBasicInfoFragment> mFragment;
        private Bundle bundle = null;

        public MyHandler(DeviceBasicInfoFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_BATTERY:
                    if(object.equals("获取数据失败"))
                        mFragment.get().mEtBatteryStatus.setText(R.string.b2_not_get_data);
                    else
                        mFragment.get().mEtBatteryStatus.setText("" + object + "%");
                    break;
                case HWServiceConfig.CONNECT_DEVICE:
                    int state = (Integer)object;
                    switch (state) {
                        case 0:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_not_connected_st);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.red));
                            mFragment.get().mEtStatus.setText(R.string.b2_status_0);
                            break;
                        case 1:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_connecting);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.lightblue));
                            mFragment.get().mEtStatus.setText(R.string.b2_status_1);
                            break;
                        case 2:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_connected_ok_st);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.darker_blue));
                            mFragment.get().mEtStatus.setText(R.string.b2_status_2);
                            break;
                        case 3:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_not_connected_st);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.red));
                            mFragment.get().mEtStatus.setText(R.string.b2_status_3);
                            break;
                        default:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_not_connected_st);
                            mFragment.get().mEtStatus.setText(R.string.b2_status_default);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.red));
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

    }

    protected MyHandler mHandler;

    public void setHandler(MyHandler mHandler) {
        this.mHandler = mHandler;
    }

    protected IDeviceConnectStatusCallback stateCallBack = new IDeviceConnectStatusCallback() {

        @Override
        public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
            Message message = Message.obtain();
            message.what = HWServiceConfig.CONNECT_DEVICE;
            message.obj = status;
            message.arg1 = err_code;
            mHandler.sendMessage(message);
            getBlueToothBattery();
        }
    };

    protected void getConnectState() {
        int connectState = 0;
        if(null != HWmanager){
            HWmanager.getConnect(HWServiceConfig.HUAWEI_TALKBAND_B2);
            connectState = HWmanager.getConnectStatus(HWServiceConfig.HUAWEI_TALKBAND_B2);
        }
        LogUtil.log("getConnectState() => connectState = " + connectState);
        Message message = Message.obtain();
        message.what = HWServiceConfig.GET_DEVICE_CONNECT_STATUS;
        message.obj = connectState;
        message.arg1 = HWServiceConfig.HUAWEI_TALKBAND_B2;
        mHandler.sendMessage(message);
    }

    protected void getBlueToothBattery(){
        HWmanager.getBatteryLevel(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {

            @Override
            public void onSuccess(Object arg0) {
                Message message = Message.obtain();
                message.what = HWServiceConfig.GET_DEVICE_BATTERY;
                message.obj = arg0;
                message.arg1 = HWServiceConfig.HUAWEI_TALKBAND_B2;
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                LogUtil.log("getBlueToothBattery => err_code = " + arg0 + " arg1 = " + arg1);
                Message message = new Message();
                message.what = 1;
                message.obj = "获取数据失败";
                mHandler.sendMessage(message);
            }
        });
    }
}
