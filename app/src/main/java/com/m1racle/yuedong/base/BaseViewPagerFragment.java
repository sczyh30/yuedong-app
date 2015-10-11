package com.m1racle.yuedong.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.adapter.ViewPageFragmentAdapter;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.ui.widget.PagerSlidingTabStrip;

/**
 * Base Fragment with ViewPager
 */
public abstract class BaseViewPagerFragment extends BaseFragment {

    @Override
    public void onClick(View view) {

    }

    protected PagerSlidingTabStrip mTabStrip;
    protected ViewPager mViewPager;
    protected ViewPageFragmentAdapter mTabsAdapter;
    protected EmptyLayout mErrorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_viewpage_fragment, null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTabStrip = (PagerSlidingTabStrip) view
                .findViewById(R.id.pager_tabstrip);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);

        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
    }

    protected void setScreenPageLimit() {
    }

    protected abstract void onSetupTabAdapter(ViewPageFragmentAdapter adapter);
}
