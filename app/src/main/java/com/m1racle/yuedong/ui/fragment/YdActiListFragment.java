package com.m1racle.yuedong.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseRefreshFragment;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * Yuedong common motion activities(in life) Fragment
 */
public class YdActiListFragment extends BaseRefreshFragment {

    private OnFragmentInteractionListener mListener;
    private PullToRefreshView mPullToRefreshView;

    public YdActiListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_yd_acti_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RlistAdapter());
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
        View view = inflater.inflate(R.layout.fragment_yd_acti_list, container, false);
        initData();
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class RlistAdapter extends RecyclerView.Adapter<RlistHolder> {

        @Override
        public RlistHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.yd_acti_list_item, viewGroup, false);
            return new RlistHolder(view);
        }

        @Override
        public void onBindViewHolder(RlistHolder holder, int pos) {
            Map<String, Integer> data = MotionActiList.get(pos);
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return MotionActiList.size();
        }
    }

    private class RlistHolder extends RecyclerView.ViewHolder {

        private View mRootView;
        private ImageView mImageViewIcon;

        private Map<String, Integer> mData;

        public RlistHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
            mImageViewIcon = (ImageView) itemView.findViewById(R.id.image_view_icon);
        }
        public void bindData(Map<String, Integer> data) {
            mData = data;

            mRootView.setBackgroundResource(mData.get(KEY_COLOR));
            mImageViewIcon.setImageResource(mData.get(KEY_ICON));
        }
    }
    @Override
    public void onClick(View view) {

    }
}
