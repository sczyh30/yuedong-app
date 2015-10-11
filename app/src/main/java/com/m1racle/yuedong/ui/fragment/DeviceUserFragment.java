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
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataUserInfo;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;

import org.kymjs.kjframe.utils.StringUtils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Yuedong App
 * Device User Fragment
 * @author sczyh30
 */
public class DeviceUserFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private DataUserInfo mInfo;
    private int error_code = 0;

    @Bind(R.id.tv_du_age)
    TextView mTvAge;
    @Bind(R.id.tv_du_birthday)
    TextView mTvBirthday;
    @Bind(R.id.tv_du_gender)
    TextView mTvGender;
    @Bind(R.id.tv_du_walk_step)
    TextView mTvWalk;
    @Bind(R.id.tv_du_run_step)
    TextView mTvRun;
    @Bind(R.id.tv_du_weight)
    TextView mTvWeight;
    @Bind(R.id.tv_du_height)
    TextView mTvHeight;
    @Bind(R.id.iv_du_gender)
    ImageView mIvGender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_user, container, false);
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
        inflater.inflate(R.menu.menu_change, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                UIUtil.showDeviceUserSet(getActivity());
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        super.initData();
        initHWManager();
    }

    private void initHWManager() {
        HWManager = getHuaweiManager();
        if(HWManager != null) {
            HWManager.registerConnectStateCallback(stateCallBack);
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    @OnClick({R.id.btn_start_set_du_info})
    public void onClick(View v) {
        if(error_code != 0) {
            ToastUtil.toast(R.string.tip_device_no_conn_warning);
            return;
        }
        int id = v.getId();
        switch (id) {
            case R.id.btn_start_set_du_info:
                UIUtil.showDeviceUserSet(getActivity());
                break;
            default:
                break;
        }
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

    private static class MyHandler extends Handler {
        private final WeakReference<DeviceUserFragment> mFragment;

        public MyHandler(DeviceUserFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                /*case HWServiceConfig.CONNECT_DEVICE:
                    int state = (Integer)object;
                    switch (state) {
                        default:
                            break;
                    }
                    break;*/
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
            mFragment.get().mTvAge.setText(getInfoStr(mFragment.get().mInfo.getAge()));
            mFragment.get().mTvBirthday.setText(getInfoStr(mFragment.get().mInfo.getBirthday()));
            mFragment.get().mTvGender.setText(mFragment.get().mInfo.getGender() == 1 ? "男" : "女");
            mFragment.get().mTvHeight.setText(getInfoStr(mFragment.get().mInfo.getHeight()) + " cm");
            mFragment.get().mTvWeight.setText(getInfoStr(mFragment.get().mInfo.getWeight()) + " kg");
            mFragment.get().mTvWalk.setText(Integer.toString(mFragment.get().mInfo.getWalk_step_length()) + " cm");
            mFragment.get().mTvRun.setText(Integer.toString(mFragment.get().mInfo.getRun_step_length()) + " cm");
            mFragment.get().mIvGender.setImageResource(StringUtils.toInt(mFragment.get().mInfo.getGender()) == 1 ? R.mipmap.userinfo_icon_male
                    : R.mipmap.userinfo_icon_female);
        }

        private String getInfoStr(int id) {
            if(id == 0)
                return "未知";
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
                    error_code = err_code;
                }
            });
        }
    }
}
