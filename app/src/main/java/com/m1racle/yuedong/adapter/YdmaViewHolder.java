package com.m1racle.yuedong.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

/**
 */
public class YdmaViewHolder extends RlistHolder {

    private View mRootView;
    private ImageView mImageViewIcon;
    private TextView mText;

    public YdmaViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Map<String, Object> data) {
        super.bindData(data);
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
