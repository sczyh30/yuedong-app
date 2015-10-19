package com.m1racle.yuedong.ui.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.huaweiwearable.data.DataHealthGoal;
import com.m1racle.yuedong.R;

/**
 * Yuedong app
 * RecyclerView Holder for MotionGoalFragment
 * extends MotionHolder
 * @see com.m1racle.yuedong.ui.fragment.MotionGoalFragment
 * @see MotionHolder
 */
public class MotionGoalHolder extends MotionHolder {

    private DataHealthGoal mData;

    private TextView mTvType;
    private TextView mTvStep;
    private TextView mTvCalorie;
    private TextView mTvDistance;
    private TextView mTvDurType;
    private ImageView mIvMotion;

    public MotionGoalHolder(View itemView) {
        super(itemView);
        mTvType = (TextView)itemView.findViewById(R.id.tv_mg_type);
        mTvStep = (TextView)itemView.findViewById(R.id.tv_mg_step);
        mTvCalorie = (TextView)itemView.findViewById(R.id.tv_mg_calorie);
        mTvDistance = (TextView)itemView.findViewById(R.id.tv_mg_distance);
        mTvDurType = (TextView)itemView.findViewById(R.id.tv_mg_dur_type);
        mIvMotion = (ImageView)itemView.findViewById(R.id.image_motion_icon_mg);
    }

    public void bindData(DataHealthGoal data) {
        mTvType.setText(getMotionType(data.getMotion_type()));
        mTvStep.setText(String.format("%s 步", getString(data.getStep_goal())));
        mTvCalorie.setText(String.format("%s 大卡", getString(data.getEnergy_burn_goal())));
        mTvDistance.setText(String.format("%s m", getString(data.getSport_distance_goal())));
        mTvDurType.setText(getDurType(data.getGoal_type()));
        mIvMotion.setImageResource(getPicture(data.getMotion_type()));
    }

    private String getDurType(int i) {
        switch (i) {
            case 1:
                return "日目标";
            case 2:
                return "周目标";
            default:
                return "";
        }
    }
}
