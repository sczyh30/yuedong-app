package com.m1racle.yuedong.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Yuedong app
 * Achievement Fragment
 * @author sczyh30
 * @since v1.40
 */
public class AchievementFragment extends BaseFragment {

    private int type = 1;

    @Bind(R.id.recycler_view_ach)
    RecyclerView recyclerView;

    public AchievementFragment() {}

    public AchievementFragment(int type) {
        super();
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_share:
                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
