package com.m1racle.yuedong.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.entity.BaseMessage;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.ui.recycler.BaseMessageHolder;
import com.m1racle.yuedong.ui.widget.CircleImageView;
import com.m1racle.yuedong.ui.widget.RevealBackgroundView;
import com.m1racle.yuedong.util.AnimatorUtil;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class UserProfileActivity extends BaseActivity
        implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;

    private int ac_flag = 1;

    private ArrayList<BaseMessage> mList = new ArrayList<>();
    private MessageAdapter adapter = new MessageAdapter();

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
    @Bind(R.id.rv_profile)
    RecyclerView mRecyclerView;

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @Bind(R.id.tlUserProfileTabs)
    TabLayout tlUserProfileTabs;
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
        setupUserProfileGrid();
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

    private Response.Listener<String> listener0 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            mList = JsonUtil.resolveMessages(response);
            if(mList.size() > 0)
                updateUI();
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
        ac_flag = 1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ac_flag = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ac_flag = 0;
    }

    private void updateUI() {
        //此逻辑还有些问题，可能会NullPointer
        //fixed in 1.12
        if(mUser != null && ac_flag > 0) {
            mTvUsername.setText(mUser.getUsername());
            mTvTips.setText(mUser.getTips());
            mTvActNum.setText(Integer.toString(mUser.getActivitiesNumber()));
            mTvFollowers.setText(Integer.toString(mUser.getFans()));
            mTvFollowing.setText(Integer.toString(mUser.getFollowers()));
            mIvGender.setImageResource(StringUtils.toInt(mUser.getGender()) == 1 ?
                    R.mipmap.userinfo_icon_male : R.mipmap.userinfo_icon_female);
            getImages();
        }
        if(mList.size() > 0)
            adapter.notifyDataSetChanged();
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
            YuedongAPI.getMessages(uid, listener0, errorListener);
        }
    }

    private class MessageAdapter extends RecyclerView.Adapter<BaseMessageHolder> {

        @Override
        public BaseMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_message, parent, false);
            return new BaseMessageHolder(view);
        }

        @Override
        public void onBindViewHolder(final BaseMessageHolder holder, int position) {
            final BaseMessage data = mList.get(position);
            final int uid = data.getUid();
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }



    private void setupTabs() {
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setText("动态"));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setText("交流"));
        //tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
        //tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
    }

    private void setupUserProfileGrid() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //userPhotosAdapter.setLockedAnimations(true);
            }
        });
    }

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
            mRecyclerView.setVisibility(View.VISIBLE);
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            //userPhotosAdapter = new UserProfileAdapter(this);
            //mRecyclerView.setAdapter(userPhotosAdapter);
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void animateUserProfileOptions() {
        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
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
