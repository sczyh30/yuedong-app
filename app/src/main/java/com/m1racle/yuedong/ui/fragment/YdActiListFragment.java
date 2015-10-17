package com.m1racle.yuedong.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
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
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.adapter.OnItemClickListener;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.cache.CacheManager;
import com.m1racle.yuedong.cache.SaveCacheTask;
import com.m1racle.yuedong.entity.MotionActivitiesPre;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;
import com.yalantis.phoenix.PullToRefreshView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Yuedong common motion activities(in life) Fragment
 */
public class YdActiListFragment extends BaseFragment {

    private PullToRefreshView mPullToRefreshView;
    RecyclerView mRecyclerView;
    RlistAdapter adapter = new RlistAdapter();

    protected ArrayList<MotionActivitiesPre> dataList = new ArrayList<>();

    private AsyncTask<String, Void, ArrayList<MotionActivitiesPre>> mCacheTask;

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
        adapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UIUtil.showActivitiesDetail(getActivity(), position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LogUtil.log("onItemLongClick => to detail");
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!DeviceUtil.hasInternet()) {
                    mPullToRefreshView.setRefreshing(false);
                    readCacheData(getCacheKey());
                }
                else
                    YuedongAPI.getLatestMotionActivities(listener, errorListener);
            }
        });
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
    }

    @Override
    public void initData() {
        super.initData();
        ArrayList<MotionActivitiesPre> test = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            MotionActivitiesPre temp = new MotionActivitiesPre();
            temp.setMAid(i);
            temp.setTitle("运动信息 => " + i);
            test.add(temp);
        }
        dataList = test;
        readCacheData(getCacheKey());
    }

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            dataList = JsonUtil.resolveMAList(response);
            new SaveCacheTask(getActivity(), dataList, getCacheKey()).execute();
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

     class RlistAdapter extends RecyclerView.Adapter<RlistHolder> {

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener)
        {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public RlistHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_yd_acti_item, viewGroup, false);
            return new RlistHolder(view);
        }

        @Override
        public void onBindViewHolder(final RlistHolder holder, final int pos) {
            final MotionActivitiesPre data = dataList.get(pos);
            holder.bindData(data);

            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int maid = data.getMAid();
                        mOnItemClickListener.onItemClick(holder.itemView, maid);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int maid = data.getMAid();
                        mOnItemClickListener.onItemLongClick(holder.itemView, maid);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private class RlistHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic;
        private TextView mTvTitle;
        private TextView mTvSummary;
        private TextView mTvCT;
        private TextView mTvAuthor;

        private MotionActivitiesPre mData;

        public RlistHolder(View itemView) {
            super(itemView);
            mIvPic = (ImageView) itemView.findViewById(R.id.image_view_icon);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_ma_list_title);
            mTvSummary = (TextView) itemView.findViewById(R.id.tv_ma_list_summary);
            mTvCT = (TextView) itemView.findViewById(R.id.tv_ma_ct);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tv_ma_author);
        }

        public void bindData(MotionActivitiesPre data) {
            mData = data;
            String img_id = mData.getImgId();
            String title = mData.getTitle();
            String summary = mData.getSummary();
            if(img_id != null) {
                BitmapRequestClient.send(mIvPic, img_id, 80, 80);
            } else {
                BitmapRequestClient.send(mIvPic, "image_no");
            }
            setTextOptional(title, mTvTitle);
            setTextOptional(summary, mTvSummary);
            mTvCT.setText(mData.getCreateTime());
            mTvAuthor.setText(mData.getAuthor());
        }

        public void setTextOptional(String s, TextView tv) {
            if(tv == null) {
                LogUtil.warn("Fucking null object on RecyclerView => TextView");
                return;
            }
            if(s != null)
                tv.setText(s);
            else
                tv.setText(R.string.error_view_no_data);
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
        return "yd_ma_list";
    }

    private class CacheTask extends AsyncTask<String, Void, ArrayList<MotionActivitiesPre>> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<MotionActivitiesPre> doInBackground(String... params) {
            Serializable object = CacheManager.readObject(mContext.get(), params[0]);
            if (object == null) {
                return null;
            } else {
                return (ArrayList<MotionActivitiesPre>) object;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MotionActivitiesPre> info) {
            super.onPostExecute(info);
            if (info != null) {
                dataList = info;
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
