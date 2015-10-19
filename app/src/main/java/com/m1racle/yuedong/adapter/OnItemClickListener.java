package com.m1racle.yuedong.adapter;

import android.view.View;

/**
 * RecyclerView OnItemClickListener
 */
public interface OnItemClickListener {

    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);

}
