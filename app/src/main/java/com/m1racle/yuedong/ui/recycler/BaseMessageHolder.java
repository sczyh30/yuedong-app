package com.m1racle.yuedong.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.entity.BaseMessage;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.ui.widget.CircleImageView;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.UIUtil;

/**
 * Yuedong app
 */
public class BaseMessageHolder extends RecyclerView.ViewHolder {

    private CircleImageView mCircle;
    private TextView mTvUsername;
    private TextView mTvTime;
    private TextView mTvMessage;

    public BaseMessageHolder(View itemView) {
        super(itemView);
        mCircle = (CircleImageView)itemView.findViewById(R.id.iv_avatar);
        mTvUsername = (TextView)itemView.findViewById(R.id.tv_username);
        mTvTime = (TextView)itemView.findViewById(R.id.tv_time);
        mTvMessage = (TextView)itemView.findViewById(R.id.tv_message);
    }

    public void bindData(final BaseMessage data) {
        mTvMessage.setText(data.getMessage());
        mTvTime.setText(data.getTime());
        mTvUsername.setText(data.getUsername());
        if(DeviceUtil.hasInternet()) {
            if(data.getPortrait() != null)
                BitmapRequestClient.send(mCircle, "portrait/" + data.getPortrait(), 100, 100);
            else
                mCircle.setImageResource(R.mipmap.widget_dface);
        }
        mCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getUid() != 0)
                    UIUtil.showUserProfile(AppContext.getContext(), data.getUid());
            }
        });
    }
}
