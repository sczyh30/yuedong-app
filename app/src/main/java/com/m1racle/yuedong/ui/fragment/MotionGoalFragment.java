package com.m1racle.yuedong.ui.fragment;


import android.content.Context;
import android.os.AsyncTask;
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

import com.huawei.huaweiwearable.callback.IDeviceConnectStatusCallback;
import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataHealthGoal;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.cache.CacheManager;
import com.m1racle.yuedong.cache.SaveCacheTask;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.ui.fragment.recycler.MotionGoalHolder;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong App
 * Motion Goal Fragment
 */
public class MotionGoalFragment extends BaseFragment {

    private HuaweiWearableManager HWManager;
    private ArrayList<DataHealthGoal> mList = new ArrayList<>();
    private int error_code;

    @Bind(R.id.recycler_view_mg)
    RecyclerView mRecyclerView;
    @Bind(R.id.my_warning_layout)
    LinearLayout mWarningLayout;
    private MotionGoalAdapter adapter = new MotionGoalAdapter();
    private AsyncTask<String, Void, ArrayList<DataHealthGoal>> mCacheTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_goal, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getGoal();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh_change, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                getGoal();
                break;
            case R.id.public_menu_send:
                UIUtil.showMotionGoalSet(getActivity());
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

    private class MotionGoalAdapter extends RecyclerView.Adapter<MotionGoalHolder> {
        @Override
        public MotionGoalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_motion_goal, parent, false);
            return new MotionGoalHolder(view);
        }

        @Override
        public void onBindViewHolder(MotionGoalHolder holder, int position) {
            DataHealthGoal data = mList.get(position);
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
            Message message = Message.obtain();
            message.what = HWServiceConfig.CONNECT_DEVICE;
            message.obj = status;
            message.arg1 = err_code;
            mHandler.sendMessage(message);
        }
    };

    private static class MyHandler extends Handler {
        private final WeakReference<MotionGoalFragment> mFragment;

        public MyHandler(MotionGoalFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_MOTION_GOAL:
                    mFragment.get().mList = (ArrayList<DataHealthGoal>)object;
                    new SaveCacheTask(mFragment.get().getActivity(),
                            mFragment.get().mList, mFragment.get().getCacheKey()).execute();
                    mFragment.get().updateUI();
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private void ensureView() {
        if (mList.size() == 0) {
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

    private void getGoal() {
        if(HWManager != null) {
            HWManager.getSportGoal(HWServiceConfig.HUAWEI_TALKBAND_B2, new IResultReportCallback() {
                @Override
                public void onSuccess(Object object) {
                    Message message = Message.obtain();
                    message.what = HWServiceConfig.GET_DEVICE_MOTION_GOAL;
                    message.obj = object;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFailure(int err_code, String err_msg) {
                    error_code = err_code;
                    readCacheData(getCacheKey());
                    ensureView();
                    ToastUtil.toast("获取运动目标时出现了点问题，请稍后重试");
                }
            });
        }
    }

    private class CacheTask extends AsyncTask<String, Void, ArrayList<DataHealthGoal>> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<DataHealthGoal> doInBackground(String... params) {
            Serializable object = CacheManager.readObject(mContext.get(), params[0]);
            if (object == null) {
                return null;
            } else {
                return (ArrayList<DataHealthGoal>) object;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<DataHealthGoal> info) {
            super.onPostExecute(info);
            if (info != null) {
                mList = info;
                updateUI();
            }
        }
    }

    private void readCacheData(String key) {
        cancelReadCacheTask();
        mCacheTask = new CacheTask(getActivity()).execute(key);
    }

    private void cancelReadCacheTask() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    private String getCacheKey() {
        return "m_motion_goal";
    }
}
