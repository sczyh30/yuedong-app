package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataHealthData;
import com.huawei.huaweiwearable.data.DataRawSleepData;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.ui.recycler.SleepDataHolder;
import com.m1racle.yuedong.util.ToastUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong App
 * Sleep Observer Fragment
 */
public class SleepObserverFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code;

    @Bind(R.id.recycler_view_sleep)
    RecyclerView mRecyclerView;
    @Bind(R.id.my_warning_layout)
    LinearLayout mWarningLayout;

    private List<DataRawSleepData> mList = new ArrayList<>();
    private SleepAdapter adapter = new SleepAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleep_observer, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    @Override
    public void initData() {
        HWManager = getHuaweiManager();
    }

    @Override
    public void initView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
    }

    @Override
    public void onClick(View v) {

    }

    private void updateUI() {
        ensureView();
        adapter.notifyDataSetChanged();
    }

    private void ensureView() {
        if (mList.size() == 0) {
            mWarningLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mWarningLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<SleepObserverFragment> mFragment;

        public MyHandler(SleepObserverFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_SLEEP:
                    DataHealthData d = (DataHealthData)object;
                    mFragment.get().mList = d.getDataRawSleepDatas();
                    mFragment.get().updateUI();
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private void getData() {
        if(HWManager != null) {
            HWManager.getHealthDataByTime(HWServiceConfig.HUAWEI_TALKBAND_B2,
                    Long.toString((System.currentTimeMillis() / 1000) - 24 * 60 * 60),
                    Long.toString(System.currentTimeMillis() / 1000), new IResultReportCallback() {
                        @Override
                        public void onSuccess(Object object) {
                            Message message = Message.obtain();
                            message.what = HWServiceConfig.GET_DEVICE_SLEEP;
                            message.obj = object;
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onFailure(int err_code, String err_msg) {
                            //ensureView();
                            DataRawSleepData dataRawSleepData = new DataRawSleepData();
                            dataRawSleepData.setStartTime((int)System.currentTimeMillis());
                            dataRawSleepData.setCurrentStatus(10);
                            dataRawSleepData.setTotalCalorie(1000);
                            dataRawSleepData.setTotalSleepTime(60);
                            mList.add(dataRawSleepData);
                            updateUI();
                            error_code = err_code;
                            ToastUtil.toast(R.string.no_b2_now_local);
                        }
                    });
        }
    }

    private class SleepAdapter extends RecyclerView.Adapter<SleepDataHolder> {

        @Override
        public SleepDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_device_sleep, parent, false);
            return new SleepDataHolder(view);
        }

        @Override
        public void onBindViewHolder(SleepDataHolder holder, int position) {
            DataRawSleepData data = mList.get(position);
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

}
