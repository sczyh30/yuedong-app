package com.m1racle.yuedong.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.ui.widget.CircleImageView;
import com.m1racle.yuedong.util.DeviceUtil;

import org.kymjs.kjframe.utils.StringUtils;

/**
 * Yuedong App
 * Base Friend ViewHolder
 */
public class BaseFriendHolder extends RecyclerView.ViewHolder {

    private TextView mTvUsername;
    private TextView mTvTips;
    private ImageView mIvGender;
    private CircleImageView mCircle;

    public BaseFriendHolder(View itemView) {
        super(itemView);
        mCircle = (CircleImageView)itemView.findViewById(R.id.iv_avatar);
        mTvUsername = (TextView)itemView.findViewById(R.id.tv_username);
        mTvTips = (TextView)itemView.findViewById(R.id.tv_tip);
        mIvGender = (ImageView)itemView.findViewById(R.id.iv_gender);
    }

    public void bindData(User data) {
        mIvGender.setImageResource(StringUtils.toInt(data.getGender()) == 1 ?
                R.mipmap.userinfo_icon_male : R.mipmap.userinfo_icon_female);
        mTvTips.setText(data.getTips());
        mTvUsername.setText(data.getUsername());
        if(DeviceUtil.hasInternet()) {
            if(data.getPortrait() != null)
                BitmapRequestClient.send(mCircle, "portrait/" + data.getPortrait(), 50, 50);
            else
                BitmapRequestClient.send(mCircle, "image_no");
        }
    }
}
