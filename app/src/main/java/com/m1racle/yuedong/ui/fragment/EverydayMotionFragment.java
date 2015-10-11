package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataTodayTotalMotion;
import com.huawei.huaweiwearable.data.DataTotalMotion;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.ui.fragment.recycler.EverydayMotionHolder;
import com.m1racle.yuedong.util.ToastUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong App
 * Everyday Motion Fragment
 */
public class EverydayMotionFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code;
    private DataTodayTotalMotion mToday;
    private List<DataTotalMotion> mList = new ArrayList<>();

    @Bind(R.id.recycler_view_evm)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_evm_total_carl)
    TextView mTvTC;

    private EverydayMotionAdapter adapter = new EverydayMotionAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_everyday_motion, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getEverydayMotion();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                getEverydayMotion();
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        initHWManager();
    }

    @Override
    public void initView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

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

    private void updateUI() {
        if(mToday != null)
            mTvTC.setText(String.format("%s 大卡", Integer.toString(mToday.getTotalCalorie())));
        adapter.notifyDataSetChanged();
    }

    private class EverydayMotionAdapter extends RecyclerView.Adapter<EverydayMotionHolder> {

        @Override
        public EverydayMotionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.everyday_motion_list, parent, false);
            return new EverydayMotionHolder(view);
        }

        @Override
        public void onBindViewHolder(EverydayMotionHolder holder, int position) {
            DataTotalMotion data = mList.get(position);
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private IDeviceConnectStatusCallback stateCallBack = new IDeviceConnectStatusCallback() {
        @Override
        public void onConnectStatusChange(int deviceType, String macAddress, int status, int err_code) {
            if(status > 2) {
                ToastUtil.toast("获取数据时出了点问题");
            }
        }
    };

    private class MyHandler extends Handler {
        private final WeakReference<EverydayMotionFragment> mFragment;

        public MyHandler(EverydayMotionFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_TODAY_MOTION_INFO :
                    mToday = (DataTodayTotalMotion)object;
                    if(mToday != null)
                        mList = mToday.getDataTotalMotions();
                    updateUI();
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private void getEverydayMotion() {
        if(HWManager != null) {
            HWManager.getHealthDataCurrentDay(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {
                @Override
                public void onSuccess(Object object) {
                    Message message = Message.obtain();
                    message.what = HWServiceConfig.GET_DEVICE_TODAY_MOTION_INFO;
                    message.obj = object;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFailure(int err_code, String err_msg) {
                    error_code = err_code;
                    ToastUtil.toast("获取数据时出了点问题");
                }
            });
        }
    }

}
