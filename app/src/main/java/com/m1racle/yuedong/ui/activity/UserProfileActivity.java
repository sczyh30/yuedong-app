package com.m1racle.yuedong.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.adapter.BasicFragmentPagerAdapter;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.ui.fragment.MessageFragment;
import com.m1racle.yuedong.ui.widget.CircleImageView;
import com.m1racle.yuedong.ui.widget.RevealBackgroundView;
import com.m1racle.yuedong.util.AnimatorUtil;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.ToastUtil;

import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.m1racle.yuedong.base.Constants.MESSAGE_DONGTAI;
import static com.m1racle.yuedong.base.Constants.MESSAGE_NOTICE;

/**
 * Yuedong app
 * User profile activity
 * @author sczyh30
 * @since v1.0
 */
public class UserProfileActivity extends BaseActivity
        implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;

    private BasicFragmentPagerAdapter adapter;

    @Bind(R.id.tv_username)
    TextView mTvUsername;
    @Bind(R.id.tv_tip)
    TextView mTvTips;
    @Bind(R.id.iv_avatar)
    CircleImageView mAvatar;
    @Bind(R.id.tv_act_num)
    TextView mTvActNum;
    @Bind(R.id.tv_followers)
    TextView mTvFollowers;
    @Bind(R.id.tv_following)
    TextView mTvFollowing;
    @Bind(R.id.iv_gender)
    ImageView mIvGender;

    @Bind(R.id.vp_profile)
    ViewPager vpProfile;

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @Bind(R.id.profile_message_tab)
    TabLayout messageTab;
    @Bind(R.id.vUserDetails)
    View vUserDetails;
    @Bind(R.id.vUserStats)
    View vUserStats;
    @Bind(R.id.vUserProfileRoot)
    View vUserProfileRoot;
    @Bind(R.id.btnCreate)
    FloatingActionButton btnRefresh;

    private int uid;
    private User mUser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        uid = getIntent().getIntExtra("uid", 0);
        getData();
        setupTabs();
        //setupUserProfileGrid();
        //setupRevealBackground(savedInstanceState);
    }

    @Override
    @OnClick(R.id.btnCreate)
    public void onClick(View v) {
        AnimatorUtil.doSimpleRefresh(btnRefresh, 2);
        getData();
    }

    private Response.Listener<User> listener = new Response.Listener<User>() {
        @Override
        public void onResponse(User response) {
            if(response != null) {
                mUser = response;
                updateUI();
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            ToastUtil.toast(R.string.no_device_data);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateUI() {
        //此逻辑还有些问题，可能会NullPointer
        //fixed in 1.12
        if(mUser != null && top_flag > 0) {
            mTvUsername.setText(mUser.getUsername());
            mTvTips.setText(mUser.getTips());
            mTvActNum.setText(Integer.toString(mUser.getActivitiesNumber()));
            mTvFollowers.setText(Integer.toString(mUser.getFans()));
            mTvFollowing.setText(Integer.toString(mUser.getFollowers()));
            mIvGender.setImageResource(StringUtils.toInt(mUser.getGender()) == 1 ?
                    R.mipmap.userinfo_icon_male : R.mipmap.userinfo_icon_female);
            getImages();
        }
    }

    private void getImages() {
        if(mUser.getBackground() != null)
            vUserProfileRoot.setBackgroundResource(R.mipmap.profile_bg3);
        else
            vUserProfileRoot.setBackgroundResource(R.mipmap.profile_bg2);
        if(mUser.getPortrait() != null)
            BitmapRequestClient.send(mAvatar, "portrait/" + mUser.getPortrait(), 100, 100);
        else
            mAvatar.setImageResource(R.mipmap.widget_dface);
    }

    private void getData() {
        if(!DeviceUtil.hasInternet()) {
            ToastUtil.toast(R.string.error_view_network_error_click_to_refresh);
        } else {
            YuedongAPI.getUser(uid, listener, errorListener);
        }
    }

    private void setupTabs() {
        // init fragment list
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MessageFragment(MESSAGE_DONGTAI));
        fragmentList.add(new MessageFragment(MESSAGE_NOTICE));
        // init title list
        List<String> titleList = new ArrayList<>();
        titleList.add("动态");
        titleList.add("交流");
        // init the adapter
        adapter = new BasicFragmentPagerAdapter(getFragmentManager(), fragmentList, titleList);
        vpProfile.setAdapter(adapter);
        vpProfile.setCurrentItem(0);
        // init the tab
        messageTab.setTabMode(TabLayout.MODE_FIXED);
        messageTab.setupWithViewPager(vpProfile);
        //messageTab.addTab(messageTab.newTab().setText("交流"));
        //messageTab.addTab(messageTab.newTab().setIcon(R.drawable.ic_place_white));
    }

    /*private void setupUserProfileGrid() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                userPhotosAdapter.setLockedAnimations(true);
            }
        });
    }*/

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra("reveal_start_location");
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
            //userPhotosAdapter.setLockedAnimations(true);
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            //mRecyclerView.setVisibility(View.VISIBLE);
            messageTab.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            //userPhotosAdapter = new UserProfileAdapter(this);
            //mRecyclerView.setAdapter(userPhotosAdapter);
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            messageTab.setVisibility(View.INVISIBLE);
            //mRecyclerView.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void animateUserProfileOptions() {
        messageTab.setTranslationY(-messageTab.getHeight());
        messageTab.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
        vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
        mAvatar.setTranslationY(-mAvatar.getHeight());
        vUserDetails.setTranslationY(-vUserDetails.getHeight());
        vUserStats.setAlpha(0);

        vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
        mAvatar.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
        vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
        vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }
}
