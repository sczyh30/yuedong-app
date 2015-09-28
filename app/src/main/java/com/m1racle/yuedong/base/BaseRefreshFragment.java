package com.m1racle.yuedong.base;

import android.os.Bundle;

import com.m1racle.yuedong.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base Refresh Fragment
 * for ListView/RecyclerView
 */
public abstract class BaseRefreshFragment extends BaseFragment {

    public static final int REFRESH_DELAY = 2000;

    public static final String KEY_ICON = "icon";
    public static final String KEY_COLOR = "color";

    protected List<Map<String, Integer>> MotionActiList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Integer> map;
        MotionActiList = new ArrayList<>();

        int[] icons = {
                R.mipmap.sunny,
                R.mipmap.sweat_smile,
                R.mipmap.sunglasses};

        int[] colors = {
                R.color.saffron,
                R.color.eggplant,
                R.color.sienna};

        for (int i = 0; i < icons.length; i++) {
            map = new HashMap<>();
            map.put(KEY_ICON, icons[i]);
            map.put(KEY_COLOR, colors[i]);
            MotionActiList.add(map);
        }
    }

}
