package com.m1racle.yuedong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.entity.MotionActivitiesPre;

import java.util.List;

/**
 * Yuedong Motion Activities RecyclerView Adapter
 * @author sczyh30
 * @since 0.2
 */
public class YdActiRecyclerAdapter extends RecyclerView.Adapter<YdmaViewHolder> {

    private List<MotionActivitiesPre> mList;
    private RecyclerItemClickListener mListener;
    private RecyclerItemLongClickListener mLongClickListener;

    public YdActiRecyclerAdapter(List<MotionActivitiesPre> mList) {
        this.mList = mList;
    }

    @Override
    public YdmaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_yd_acti_item, parent, false);
        return new YdmaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YdmaViewHolder holder, int position) {
        MotionActivitiesPre bean = mList.get(position);
        holder.bindData(bean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.mListener = listener;
    }
}
