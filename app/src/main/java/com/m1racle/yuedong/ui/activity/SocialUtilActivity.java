package com.m1racle.yuedong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.ui.recycler.BaseFriendHolder;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;
import com.yalantis.phoenix.PullToRefreshView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class SocialUtilActivity extends BaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.error_layout)
    EmptyLayout emptyLayout;
    @Bind(R.id.content_layout)
    FrameLayout contentLayout;

    PullToRefreshView mPullToRefreshView;
    private int type;
    private ArrayList<User> mList = new ArrayList<>();
    private SocialAdapter adapter = new SocialAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_social_util;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        mActionBar.setTitle(getActionbarTitle());
        initView();
        getData();
    }

    private String getActionbarTitle() {
        switch (type) {
            case 1:
                return "我的好友";
            case 2:
                return "我的粉丝";
            case 3:
                return "我的关注";
            case 15:
                return "今日运动排行榜";
            default:
                return "我的悦动";
        }
    }

    private void initView() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int uid) {
                //ToastUtil.toast(Integer.toString(uid));
                UIUtil.showUserProfile(SocialUtilActivity.this, uid);
            }

            @Override
            public void onItemLongClick(View view, int uid) {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mPullToRefreshView = (PullToRefreshView)findViewById(R.id.pull_to_refresh_a);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getData() {
        updateUI();
        if (!DeviceUtil.hasInternet()) {
            mPullToRefreshView.setRefreshing(false);
            ToastUtil.toast(R.string.error_view_load_error_click_to_refresh);
        }
        else
            //YuedongAPI.getFriendList(AppContext.getContext().getLoginUid(), type, listener, errorListener);
            YuedongAPI.getFriendList(6666, type, listener, errorListener);
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
    @OnClick(R.id.btnCreate)
    public void onClick(View v) {
        getData();
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int uid);
        void onItemLongClick(View view , int uid);

    }

    private class SocialAdapter extends RecyclerView.Adapter<BaseFriendHolder> {

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
        {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public BaseFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_social_util, parent, false);
            return new BaseFriendHolder(view);
        }

        @Override
        public void onBindViewHolder(final BaseFriendHolder holder, int position) {
            final User user = mList.get(position);
            final int uid = user.getId();
            holder.bindData(user);

            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView, uid);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onItemLongClick(holder.itemView, uid);
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

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            mList = JsonUtil.resolveUsers(response);
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
            ToastUtil.toast("服务器解析错误，请重试。");
        }
    };


}
