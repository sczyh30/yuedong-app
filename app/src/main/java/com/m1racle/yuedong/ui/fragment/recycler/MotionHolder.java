package com.m1racle.yuedong.ui.fragment.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.m1racle.yuedong.R;

/**
 * Yuedong app
 * Basic Motion Holder
 */
public abstract class MotionHolder extends RecyclerView.ViewHolder {

    public MotionHolder(View view) {
        super(view);
    }

    protected String getString(int i) {
        return Integer.toString(i);
    }

    protected String getMotionType(int i) {
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
                return "睡眠";
            default:
                return "";
        }
    }

    protected int getPicture(int i) {
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
