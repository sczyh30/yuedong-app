package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.entity.MotionActivities;
import com.m1racle.yuedong.net.SamsaraAPI;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.yalantis.phoenix.PullToRefreshView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Yuedong common motion activities(in life) Fragment
 */
public class YdActiListFragment extends BaseFragment {

    private PullToRefreshView mPullToRefreshView;
    RecyclerView mRecyclerView;
    RlistAdapter adapter = new RlistAdapter();

    protected List<MotionActivities> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_yd_acti_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //SamsaraAPI.getLatestMotionActivities(mHandler);
                YuedongAPI.getLatestMotionActivities(listener, errorListener);
            }
        });
        //View view = inflater.inflate(R.layout.fragment_yd_acti_list, container, false);
        initData();
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }


    @Override
    public void initView(View view) {
        super.initView(view);
        if(DeviceUtil.getNetworkType() == 1)
            YuedongAPI.getLatestMotionActivities(listener, errorListener);
            //SamsaraAPI.getLatestMotionActivities(mHandler);
    }

    @Override
    public void initData() {
        super.initData();
        List<MotionActivities> test = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            MotionActivities temp = new MotionActivities();
            temp.setMAid(i);
            temp.setTitle("运动信息 => " + i);
            test.add(temp);
        }
        dataList = test;
    }

    //TODO:TEST START

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            dataList = JsonUtil.resolveMAList(response);
            if(dataList != null) {
                adapter.notifyDataSetChanged();
            }
            mPullToRefreshView.setRefreshing(false);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mPullToRefreshView.setRefreshing(false);
            error.printStackTrace();
            ToastUtil.toast("服务器解析错误，请重试。");
        }
    };

    //TODO:TEST END

    private final BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            if(response != null) {
                dataList = (ArrayList<MotionActivities>)response;
                adapter.notifyDataSetChanged();
            }
            mPullToRefreshView.setRefreshing(false);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
            mPullToRefreshView.setRefreshing(false);
            switch (statusCode) {
                case 200:
                    ToastUtil.toast("服务器解析错误，请重试。");
                    break;
                default:
                    ToastUtil.toast("网络错误，请重试。(" + statusCode + ")");
                    break;
            }
        }

        @Override
        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            LogUtil.log(rawJsonData);
            return JsonUtil.resolveMAList(rawJsonData);
        }
    };



    private class RlistAdapter extends RecyclerView.Adapter<RlistHolder> {

        @Override
        public RlistHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_yd_acti_item, viewGroup, false);
            return new RlistHolder(view);
        }

        @Override
        public void onBindViewHolder(RlistHolder holder, int pos) {
            MotionActivities data = dataList.get(pos);
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private class RlistHolder extends RecyclerView.ViewHolder {

        private View mRootView;
        private ImageView mImageViewIcon;
        private TextView mText;

        private MotionActivities mData;

        public RlistHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
            mImageViewIcon = (ImageView) itemView.findViewById(R.id.image_view_icon);
            mText = (TextView) itemView.findViewById(R.id.textView_list);
        }

        public void bindData(MotionActivities data) {
            mData = data;

            //mRootView.setBackgroundResource(mData.get(KEY_COLOR));
            //mImageViewIcon.setImageResource(mData.getImgId());

            mText.setText(mData.getTitle());
        }
    }
    @Override
    public void onClick(View view) {

    }
}
