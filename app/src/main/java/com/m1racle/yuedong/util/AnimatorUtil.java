package com.m1racle.yuedong.util;

import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;

/**
 * Yuedong app
 * Animator Util Class
 */
public class AnimatorUtil {

    public static void doSimpleRefresh(Object view) {
        LinearInterpolator interpolator = new LinearInterpolator();
        ObjectAnimator refreshAnimator = ObjectAnimator.ofFloat(view, "rotation",
                0f, 360f);
        refreshAnimator.setInterpolator(interpolator);
        refreshAnimator.setDuration(300);
        refreshAnimator.setRepeatCount(3);
        refreshAnimator.start();
    }

    public static void doSimpleRefresh(Object view, int repeat) {
        LinearInterpolator interpolator = new LinearInterpolator();
        ObjectAnimator refreshAnimator = ObjectAnimator.ofFloat(view, "rotation",
                0f, 360f);
        refreshAnimator.setInterpolator(interpolator);
        refreshAnimator.setDuration(300);
        refreshAnimator.setRepeatCount(repeat);
        refreshAnimator.start();
    }
}
