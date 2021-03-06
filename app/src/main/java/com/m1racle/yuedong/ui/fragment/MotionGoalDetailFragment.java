package com.m1racle.yuedong.ui.fragment;


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

import com.huawei.huaweiwearable.callback.IResultReportCallback;
import com.huawei.huaweiwearable.data.DataHealthGoal;
import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.cache.XmlCacheManager;
import com.m1racle.yuedong.service.HWServiceConfig;
import com.m1racle.yuedong.ui.recycler.MotionGoalHolder;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong App
 * Motion Goal Detail Fragment
 */
public class MotionGoalDetailFragment extends BaseFragment {

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
        View view = inflater.inflate(R.layout.fragment_motion_goal_detail, container, false);
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

    private static class MyHandler extends Handler {
        private final WeakReference<MotionGoalDetailFragment> mFragment;

        public MyHandler(MotionGoalDetailFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object object = msg.obj;
            switch (msg.what) {
                case HWServiceConfig.GET_DEVICE_MOTION_GOAL:
                    mFragment.get().mList = (ArrayList<DataHealthGoal>)object;
                    mFragment.get().updateUI();
                    mFragment.get().saveCache();
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
                    mList.add(XmlCacheManager.readGoal(1));
                    updateUI();
                    ToastUtil.toast(R.string.no_b2_now_local);
                }
            });
        }
    }

    private void saveCache() {
        for(DataHealthGoal g : mList)
            XmlCacheManager.saveGoal(g.getMotion_type(), g);
    }

}
