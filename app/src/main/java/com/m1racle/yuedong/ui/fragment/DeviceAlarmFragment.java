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
import android.widget.LinearLayout;

import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataAlarm;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.cache.SaveCacheTask;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.ui.fragment.recycler.DeviceAlarmHolder;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong App
 * Device Alarm Fragment
 */
public class DeviceAlarmFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private int error_code = 0;
    private List<DataAlarm> mList = new ArrayList<>();
    private AlarmAdapter adapter = new AlarmAdapter();

    @Bind(R.id.recycler_view_alarm)
    RecyclerView mRecyclerView;
    @Bind(R.id.my_warning_layout)
    LinearLayout mWarningLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_alarm, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAlarms();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(HWManager != null)
            getAlarms();
    }

    @Override
    public void initView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, DataAlarm data) {
                UIUtil.showDeviceAlarmSet(getActivity(), data);
            }

            @Override
            public void onItemLongClick(View view, DataAlarm data) {

            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_send:
                UIUtil.showDeviceAlarmSet(getActivity(), mList.size());
                break;
        }
        return true;
    }

    @Override
    public void initData() {
        HWManager = getHuaweiManager();
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnItemClickListener {

        void onItemClick(View view, DataAlarm data);
        void onItemLongClick(View view , DataAlarm data);

    }

    private class AlarmAdapter extends RecyclerView.Adapter<DeviceAlarmHolder> {

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
        {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public DeviceAlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_alarm, parent, false);
            return new DeviceAlarmHolder(view);
        }

        @Override
        public void onBindViewHolder(final DeviceAlarmHolder holder, final int position) {
                final DataAlarm data = mList.get(position);
                holder.bindData(data);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView, data);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onItemLongClick(holder.itemView, data);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<DeviceAlarmFragment> mFragment;

        public MyHandler(DeviceAlarmFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_ALARM:
                    mFragment.get().mList = (ArrayList<DataAlarm>)object;
                    mFragment.get().updateUI();
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private void ensureView() {
        if (mList.size() == 0 || mList == null) {
            mWarningLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mWarningLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void updateUI() {
        ensureView();
        adapter.notifyDataSetChanged();
    }

    private void getAlarms() {
        if(HWManager != null) {
            HWManager.getAlarmList(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {
                @Override
                public void onSuccess(Object object) {
                    Message message = Message.obtain();
                    message.what = HWServiceConfig.GET_DEVICE_ALARM;
                    message.obj = object;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFailure(int err_code, String err_msg) {
                    ToastUtil.toast("获取手环闹钟时出现错误");
                    updateUI();
                    error_code = err_code;
                }
            });
        }
    }

}
