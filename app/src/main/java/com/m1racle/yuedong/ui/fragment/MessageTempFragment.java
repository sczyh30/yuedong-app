package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.entity.BaseMessage;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.ui.recycler.BaseMessageHolder;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong App
 * Message Fragment
 * @deprecated has been refactored
 */
@Deprecated
public class MessageTempFragment extends BaseFragment {

    private int uid = AppContext.getContext().getLoginUid();
    private ArrayList<BaseMessage> mList = new ArrayList<>();
    private MessageAdapter adapter = new MessageAdapter();

    PullToRefreshView mPullToRefreshView;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.error_layout)
    EmptyLayout emptyLayout;
    @Bind(R.id.content_layout)
    FrameLayout contentLayout;
    @Bind(R.id.tl_tab)
    TabLayout tlUserProfileTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_temp, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        mPullToRefreshView = (PullToRefreshView)view.findViewById(R.id.pull_to_refresh_b);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setText("动态"));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setText("信息"));
        getData();
    }

    private void getData() {
        updateUI();
        if (!DeviceUtil.hasInternet()) {
            mPullToRefreshView.setRefreshing(false);
            ToastUtil.toast(R.string.error_view_network_error_click_to_refresh);
        }
        else
            YuedongAPI.getMessages(uid, listener0, errorListener);
    }

    private void updateUI() {
        if(mList != null && mList.size() > 0) {
            contentLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            contentLayout.setVisibility(View.GONE);
            emptyLayout.setErrorType(EmptyLayout.NODATA);
            emptyLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {

    }

    private class MessageAdapter extends RecyclerView.Adapter<BaseMessageHolder> {

        @Override
        public BaseMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_message, parent, false);
            return new BaseMessageHolder(view);
        }

        @Override
        public void onBindViewHolder(final BaseMessageHolder holder, int position) {
            final BaseMessage data = mList.get(position);
            final int uid = data.getUid();
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private Response.Listener<String> listener0 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            mList = JsonUtil.resolveMessages(response);
            if(mList.size() > 0)
                updateUI();
            if(mPullToRefreshView != null)
                mPullToRefreshView.setRefreshing(false);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(mPullToRefreshView != null)
                mPullToRefreshView.setRefreshing(false);
            ToastUtil.toast(R.string.no_device_data);
        }
    };
}
