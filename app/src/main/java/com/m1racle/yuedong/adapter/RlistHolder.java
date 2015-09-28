package com.m1racle.yuedong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.m1racle.yuedong.util.LogUtil;

import java.util.Map;

/**
 * RecyclerView ViewHolder Common Abstract
 * @author sczyh30
 * @since 0.2
 */
public abstract class RlistHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener{

    private RecyclerItemClickListener mListener;
    private RecyclerItemLongClickListener mLongClickListener;

    public RlistHolder(View itemView) {
        super(itemView);
    }

    public void bindData(Map<String, Object> data) {

    }

    @Override
    public void onClick(View view) {
        if(mListener != null) {
            mListener.onItemClick(view, getPosition());
        }
        else {
            LogUtil.log("RecyclerItemClickListener not set");
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(mLongClickListener != null) {
            mLongClickListener.onItemLongClick(view, getPosition());
        }
        else {
            LogUtil.log("RecyclerItemLongClickListener not set");
        }
        return true;
    }
}
