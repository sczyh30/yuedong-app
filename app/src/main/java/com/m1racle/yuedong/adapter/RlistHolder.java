package com.m1racle.yuedong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.m1racle.yuedong.entity.Entity;
import com.m1racle.yuedong.util.LogUtil;

import java.util.Map;

/**
 * RecyclerView ViewHolder Common Abstract
 * every subclass of this class should rewrite the bindData method
 * @author sczyh30
 * @since 0.2
 */
public abstract class RlistHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener{

    protected RecyclerItemClickListener mListener;
    protected RecyclerItemLongClickListener mLongClickListener;

    public RlistHolder(View itemView) {
        super(itemView);
    }

    public RlistHolder(View view, RecyclerItemClickListener mListener,
                       RecyclerItemLongClickListener mLongClickListener) {
        super(view);
        this.mListener = mListener;
        this.mLongClickListener = mLongClickListener;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }
    /**
     * A big issue on this method
     * owing to the Java Generic
     * this might not be inherited by the subclasses
     * then we should rewrite this method
     */
    @Deprecated
    public <T extends Entity> void bindData(T data) {
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
