package com.m1racle.yuedong.ui.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.cache.CacheManager;
import com.m1racle.yuedong.cache.SaveCacheTask;
import com.m1racle.yuedong.entity.MotionActivitiesDetail;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Yuedong app
 * Activities Detail Fragment
 */
public class ActivitiesDetailFragment extends BaseFragment {

    private int maid = 0;
    private MotionActivitiesDetail mData;
    
    @Bind(R.id.tv_event_location)
    TextView mEtLocation;
    @Bind(R.id.tv_event_title)
    TextView mTvTitle;
    @Bind(R.id.tv_event_start_time)
    TextView mTvStartTime;
    @Bind(R.id.tv_event_end_time)
    TextView mTvEndTime;
    @Bind(R.id.rl_event_location)
    View mLocation;
    @Bind(R.id.tv_event_tip)
    TextView mEventTip;
    @Bind(R.id.tv_body)
    TextView mTvBody;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;
    @Bind(R.id.webview)
    WebView mWebView;

    private AsyncTask<String, Void, MotionActivitiesDetail> mCacheTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null)
            maid = getArguments().getInt("ma_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities_detail, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDetail();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                break;
        }
        return true;
    }

    private void updateUI() {
        mTvTitle.setText(mData.getTitle());
        mTvStartTime.setText(mData.getStartTime());
        mTvEndTime.setText(mData.getEndTime());
        mEtLocation.setText(mData.getLocation());
        mTvBody.setText(mData.getBody());
    }

    @Override
    @OnClick({R.id.bt_event_apply, R.id.rl_event_location})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_event_location:
                break;
            case R.id.bt_event_apply:
                if(!AppContext.getContext().isLogin()) {
                    ToastUtil.toast(R.string.unlogin);
                    UIUtil.showLoginActivity(getActivity());
                    return;
                }
                showApply();
                break;
        }
    }

    private void showApply() {

    }

    private Response.Listener<MotionActivitiesDetail> listener = new Response.Listener<MotionActivitiesDetail>() {
        @Override
        public void onResponse(MotionActivitiesDetail response) {
            mErrorLayout.setVisibility(View.GONE);
            if(response != null)
                mData = response;
            saveCache(mData);
            updateUI();
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            mErrorLayout.setVisibility(View.VISIBLE);
            ToastUtil.toast("服务器解析错误，请重试。");
        }
    };

    private void getDetail() {
        if(!DeviceUtil.hasInternet())
            readCacheData(getCacheKey());
        else {
            YuedongAPI.getActivityDetail(maid, listener, errorListener);
        }
    }


    //cache
    private <T extends Serializable> void saveCache(T detail) {
        new SaveCacheTask(getActivity(), detail, getCacheKey()).execute();
    }

    private void readCacheData(String cacheKey) {
        cancelReadCache();
        mCacheTask = new CacheTask(getActivity()).execute(cacheKey);
    }

    private class CacheTask extends AsyncTask<String, Void, MotionActivitiesDetail> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        protected MotionActivitiesDetail doInBackground(String... params) {
            Serializable object = CacheManager.readObject(mContext.get(), params[0]);
            if (object == null) {
                return null;
            } else {
                return (MotionActivitiesDetail) object;
            }
        }

        @Override
        protected void onPostExecute(MotionActivitiesDetail info) {
            super.onPostExecute(info);
            if (info != null) {
                mData = info;
                updateUI();
            }
        }
    }

    private void cancelReadCache() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    private String getCacheKey() {
        return "act_detail_" + maid;
    }

}
