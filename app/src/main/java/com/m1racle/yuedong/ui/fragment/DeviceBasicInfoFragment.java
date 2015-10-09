package com.m1racle.yuedong.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Yuedong App
 * Device Basic Info Fragment
 * @author sczyh30
 * extends BaseFragment
 * @see com.m1racle.yuedong.base.BaseFragment
 */
public class DeviceBasicInfoFragment extends BaseFragment {

    protected WeakReference<View> mRootView;

    public static final String STATUS_CHANGE_ACTION = "com.m1racle.yuedong.action.DEVICE_STATUS_CHANGED";
    // bind the view components
    @Bind(R.id.b2_status_text)
    TextView mEtStatus;
    @Bind(R.id.b2_status_instruct_text)
    TextView mEtStatusInst;
    @Bind(R.id.b2_battery_status_text)
    TextView mEtBatteryStatus;
    @Bind(R.id.b2_battery_status_layout)
    LinearLayout batteryLayout;

    private HuaweiWearableManager HWmanager = null;
    private OnFragmentInteractionListener mListener;

    public DeviceBasicInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initData() {
        super.initData();
    }

    public void initHWManager() {
        HWmanager = HuaweiWearableManager.getInstance(getContext());
        if(HWmanager != null) {
            HWmanager.registerConnectStateCallback(stateCallBack);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.fragment_device_basic_info, container, false);
            mRootView = new WeakReference<>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        View view = mRootView.get();
        initData();
        ButterKnife.bind(this, view);
        initView(view);
        initHWManager();
        if(HWmanager != null) {
            getConnectState();
            getBlueToothBattery();
        }
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
}

    @Override
    @OnClick({R.id.btn_update_dvstatus})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_update_dvstatus:
                if(HWmanager != null) {
                    getConnectState();
                    getBlueToothBattery();
                }
                break;
            default:
                break;
        }
    }

    /**
     * In case of causing memory leak
     * use the nested class instead
     * the WeakReference could solve this issue
     */
    private static class MyHandler extends Handler {
        private final WeakReference<DeviceBasicInfoFragment> mFragment;

        public MyHandler(DeviceBasicInfoFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.JAR_GET_DEVICE_BATTERY:
                    if(object.equals("获取数据失败"))
                        mFragment.get().mEtBatteryStatus.setText(R.string.b2_not_get_data);
                    else
                        mFragment.get().mEtBatteryStatus.setText("" + object + "%");
                    break;
                case HWServiceConfig.JAR_CONNECT_DEVICE:
                    int state = (Integer)object;
                    switch (state) {
                        /*case 0:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_not_connected_st);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.red));
                            mFragment.get().mEtStatus.setText(R.string.b2_status_0);
                            break;*/
                        case 1:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_connecting);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.darker_blue));
                            setStatus(R.string.b2_status_1);
                            break;
                        case 2:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_connected_ok_st);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.lightblue));
                            setStatus(R.string.b2_status_2);
                            break;
                        /*case 3:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_not_connected_st);
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.red));
                            if(msg.arg1 == )
                            mFragment.get().mEtStatus.setText(R.string.b2_status_3);
                            break;*/
                        default:
                            mFragment.get().mEtStatusInst.setText(R.string.b2_not_connected_st);
                            //mFragment.get().mEtStatus.setText(R.string.b2_status_default);
                            switch (msg.arg1) {
                                case 100100:
                                    setStatus(R.string.b2_status_err_100100);
                                    break;
                                case 100101:
                                    setStatus(R.string.b2_status_err_100101);
                                    break;
                                case 100104:
                                    setStatus(R.string.b2_status_err_100104);
                                    break;
                                case 100105:
                                    setStatus(R.string.b2_status_err_100105);
                                    break;
                                default:
                                    setStatus(R.string.b2_status_err_100106);
                                    break;
                            }
                            mFragment.get().mEtStatus.setTextColor(AppContext.getContext().getResources().getColor(R.color.red));
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

        private void setStatus(int resId) {
            mFragment.get().mEtStatus.setText(resId);
        }

    }

    private final MyHandler mHandler = new MyHandler(this);

    private IDeviceConnectStatusCallback stateCallBack = new IDeviceConnectStatusCallback() {

        @Override
        public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
            Message message = Message.obtain();
            message.what = HWServiceConfig.JAR_CONNECT_DEVICE;
            message.obj = status;
            message.arg1 = err_code;
            mHandler.sendMessage(message);
            getBlueToothBattery();
        }
    };

    private void getConnectState() {
        int connectState = 0;
        if(null != HWmanager){
            HWmanager.getConnect(HWServiceConfig.HUAWEI_TALKBAND_B2);
            connectState = HWmanager.getConnectStatus(HWServiceConfig.HUAWEI_TALKBAND_B2);
        }
        LogUtil.log("getConnectState() => connectState = " + connectState);
        Message message = Message.obtain();
        message.what = HWServiceConfig.JAR_GET_DEVICE_CONNECT_STATUS;
        message.obj = connectState;
        message.arg1 = HWServiceConfig.HUAWEI_TALKBAND_B2;
        mHandler.sendMessage(message);
    }

    private void getBlueToothBattery(){
        HWmanager.getBatteryLevel(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {

            @Override
            public void onSuccess(Object arg0) {
                Message message = Message.obtain();
                message.what = HWServiceConfig.JAR_GET_DEVICE_BATTERY;
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

    @Deprecated
    private final class DeviceStatusInnerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(STATUS_CHANGE_ACTION.equals(action)) {
                Bundle bundle = intent.getExtras();
                int state = bundle.getInt("conn_state");

            }
        }

        private void changeState(int state) {

        }
    }

}
