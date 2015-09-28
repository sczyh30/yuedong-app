package com.m1racle.yuedong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.entity.MotionActivities;

import java.util.List;

/**
 * Yuedong Motion Activities RecyclerView Adapter
 * @author sczyh30
 * @since 0.2
 */
public class YdActiRecyclerAdapter extends RecyclerView.Adapter<RlistHolder> {

    private List<MotionActivities> mList;
    private RecyclerItemClickListener mListener;

    public YdActiRecyclerAdapter(List<MotionActivities> mList) {
        this.mList = mList;
    }

    @Override
    public RlistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yd_acti_list_item, parent, false);
        return new RlistHolder(view);
    }

    @Override
    public void onBindViewHolder(RlistHolder holder, int position) {
        MotionActivities bean = mList.get(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.mListener = listener;
    }
}
