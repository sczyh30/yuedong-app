package com.m1racle.yuedong.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.huaweiwearable.data.DataTotalMotion;
import com.m1racle.yuedong.R;

/**
 * Yuedong app
 * RecyclerView Holder for EverydayMotionFragment
 * @see com.m1racle.yuedong.ui.fragment.EverydayMotionFragment
 */
public class EverydayMotionHolder extends RecyclerView.ViewHolder {

    private TextView mTvType;
    private TextView mTvStep;
    private TextView mTvCalorie;
    private TextView mTvDistance;
    private TextView mTvTime;
    private ImageView mIvMotion;

    public EverydayMotionHolder(View itemView) {
        super(itemView);
        mTvType = (TextView)itemView.findViewById(R.id.tv_evm_type);
        mTvStep = (TextView)itemView.findViewById(R.id.tv_evm_step);
        mTvCalorie = (TextView)itemView.findViewById(R.id.tv_evm_calorie);
        mTvDistance = (TextView)itemView.findViewById(R.id.tv_evm_distance);
        mTvTime = (TextView)itemView.findViewById(R.id.tv_evm_time);
        mIvMotion = (ImageView)itemView.findViewById(R.id.image_motion_icon);
    }

    public void bindData(DataTotalMotion data) {
        mTvType.setText(getMotionType(data.getMotion_type()));
        mTvStep.setText(String.format("%s 步", getString(data.getStep())));
        mTvCalorie.setText(String.format("%s 大卡", getString(data.getCalorie())));
        mTvDistance.setText(String.format("%s m", getString(data.getDistance())));
        mTvTime.setText(String.format("%s 分钟", getString(data.getSleep_time())));
        mIvMotion.setImageResource(getPicture(data.getMotion_type()));
    }

    private String getString(int i) {
        return Integer.toString(i);
    }

    public String getMotionType(int i) {
        switch (i) {
            case 1:
                return "走路";
            case 2:
                return "跑步";
            case 3:
                return "爬山";
            case 4:
                return "骑车";
            case 5:
                return "站立";
            case 6:
                return "浅睡";
            case 7:
                return "深睡";
            default:
                return "";
        }
    }

    private int getPicture(int i) {
        switch (i) {
            case 1:
                return R.mipmap.sport_health_exercise_icon_walking;
            case 2:
                return R.mipmap.s_health_exercise_icon_run;
            case 3:
                return R.mipmap.s_health_exercise_icon_rock_climbing;
            case 4:
                return R.mipmap.s_health_exercise_icon_bicycling;
            case 5:
                return R.mipmap.sport_health_exercise_icon_walking;
            case 6:
                return R.mipmap.s_health_drawer_sleep;
            default:
                return R.mipmap.sport_health_exercise_icon_walking;
        }
    }

}
