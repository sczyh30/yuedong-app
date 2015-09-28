package com.m1racle.yuedong.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.m1racle.yuedong.entity.MotionActivities;

/**
 * Yuedong Motion Activity ViewHolder
 */
public class YdmaViewHolder extends RlistHolder {

    //private RecyclerItemClickListener mListener;
    //private RecyclerItemLongClickListener mLongClickListener;

    private View mRootView;
    private ImageView mImageViewIcon;
    private TextView mText;

    public YdmaViewHolder(View itemView) {
        super(itemView);
    }

    public YdmaViewHolder(View view, RecyclerItemClickListener mListener,
                          RecyclerItemLongClickListener mLongClickListener) {
        super(view, mListener, mLongClickListener);
    }

    public void bindData(MotionActivities data) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        return super.onLongClick(view);
    }
}
