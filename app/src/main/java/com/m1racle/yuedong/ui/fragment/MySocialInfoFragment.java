package com.m1racle.yuedong.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.CacheManager;
import com.m1racle.yuedong.cache.SaveCacheTask;
import com.m1racle.yuedong.entity.Notice;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.net.SamsaraAPI;
import com.m1racle.yuedong.ui.activity.MainActivity;
import com.m1racle.yuedong.ui.dialog.MyQRCodeDialog;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.ui.widget.AvatarView;
import com.m1racle.yuedong.ui.widget.BadgeView;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;

import org.kymjs.kjframe.utils.StringUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Yuedong App
 * My Social Information Fragment
 */
public class MySocialInfoFragment extends BaseFragment {

    @Bind(R.id.iv_avatar)
    AvatarView mIvAvatar;
    @Bind(R.id.iv_gender)
    ImageView mIvGender;
    @Bind(R.id.tv_name)
    TextView mTvUsername;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.tv_motion_activities)
    TextView mTvMotionActivities;
    @Bind(R.id.tv_following)
    TextView mTvFollowing;
    @Bind(R.id.tv_follower)
    TextView mTvFans;
    @Bind(R.id.tv_mes)
    View mMesView;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;
    @Bind(R.id.ll_user_container)
    View mUserContainer;
    @Bind(R.id.rl_user_unlogin)
    View mUserUnLogin;
    @Bind(R.id.rootview)
    LinearLayout rootView;

    private User mInfo;
    private AsyncTask<String, Void, User> mCacheTask;
    private static BadgeView mMesCount;

    private boolean isWaitingLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_LOGOUT);
        filter.addAction(Constants.INTENT_ACTION_USER_CHANGE);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information,
                container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preRequest(true);
        mInfo = AppContext.getContext().getLoginUser();
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        setNotice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView(View view) {
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        mIvAvatar.setOnClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getContext().isLogin()) {
                    preRequest(true);
                } else {
                    UIUtil.showLoginActivity(getActivity());
                }
            }
        });

        mMesCount = new BadgeView(getActivity(), mMesView);
        mMesCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mMesCount.setBadgePosition(BadgeView.POSITION_CENTER);
        mMesCount.setGravity(Gravity.CENTER);
        mMesCount.setBackgroundResource(R.mipmap.notification_bg);
    }

    private final BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                mInfo = (User)response;
                if(mInfo != null) {
                    updateUI();
                    AppContext.getContext().updateUserInfo(mInfo);
                    new SaveCacheTask(getActivity(), mInfo, getCacheKey()).execute();
                } else
                    onFailure(statusCode, headers, rawJsonResponse, null);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
            LogUtil.toast("获取用户数据出现错误");
        }

        @Override
        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            return JsonUtil.resolveSingleUser(rawJsonData);
        }
    };

    private void preRequest(boolean refresh) {
        if (AppContext.getContext().isLogin()) {
            isWaitingLogin = false;
            String key = getCacheKey();
            if (refresh || DeviceUtil.hasInternet()
                    && (!CacheManager.isExistDataCache(getActivity(), key))) {
                sendRequestData();
            } else {
                readCacheData(key);
            }
        } else {
            isWaitingLogin = true;
        }
        setUserView();
    }

    public void sendRequestData() {
        int uid = AppContext.getContext().getLoginUid();
        SamsaraAPI.getUserInfo(uid, mHandler);
    }

    private void updateUI() {
        if (mInfo == null)
            return;
        mIvAvatar.setAvatarUrl(mInfo.getPortrait());
        mTvUsername.setText(mInfo.getAccount());
        mIvGender
                .setImageResource(StringUtils.toInt(mInfo.getGender()) == 1 ? R.mipmap.userinfo_icon_male
                        : R.mipmap.userinfo_icon_female);
        mTvScore.setText(String.valueOf(mInfo.getScore()));
        mTvMotionActivities.setText(String.valueOf(mInfo.getActivitiesNumber()));
        mTvFollowing.setText(String.valueOf(mInfo.getFollowers()));
        mTvFans.setText(String.valueOf(mInfo.getFans()));
        if(mInfo.getPortrait() != null) {
            BitmapRequestClient.send(mIvAvatar, "portrait/" + mInfo.getPortrait(), 60, 60);
        }
    }

    private void readCacheData(String key) {
        cancelReadCacheTask();
        mCacheTask = new CacheTask(getActivity()).execute(key);
    }

    private void cancelReadCacheTask() {
        if (mCacheTask != null) {
            mCacheTask.cancel(true);
            mCacheTask = null;
        }
    }

    private String getCacheKey() {
        return "m_info_" + AppContext.getContext().getLoginUid();
    }

    @Override
    @OnClick({R.id.ly_motion_activities, R.id.iv_qr_code, R.id.ly_following, R.id.ly_follower, R.id.rl_message,
            R.id.rl_health, R.id.rl_rank, R.id.rl_friend, R.id.rl_user_unlogin})
    public void onClick(View view) {
        if (isWaitingLogin) {
            ToastUtil.toast(R.string.unlogin);
            UIUtil.showLoginActivity(getActivity());
            return;
        }
        int id = view.getId();
        switch (id) {
            case R.id.rl_user_unlogin:
                ToastUtil.toast(R.string.unlogin);
                UIUtil.showLoginActivity(getActivity());
                break;
            case R.id.rl_friend:
                UIUtil.showRelationActivity(getActivity(), 1);
                break;
            case R.id.rl_rank:
                UIUtil.showRelationActivity(getActivity(), 15);
                break;
            case R.id.rl_message:
                UIUtil.showMessages(getActivity());
                break;
            case R.id.ly_follower:
                UIUtil.showRelationActivity(getActivity(), 2);
                break;
            case R.id.ly_following:
                UIUtil.showRelationActivity(getActivity(), 3);
                break;
            case R.id.rl_health:
                UIUtil.showHealthAdvice(getActivity());
                break;
            case R.id.iv_avatar:
                showMyDetail();
                break;
            case R.id.iv_qr_code:
                showMyQrCode();
                break;
            default:
                break;
        }
    }

    private void showMyDetail() {
        if(!AppContext.getContext().isLogin()) {
            ToastUtil.toast("未登录，请先登录");
            UIUtil.showLoginActivity(getActivity());
        } else {
            UIUtil.showSocialDetail(getActivity());
        }
    }

    private void showMyQrCode() {
        MyQRCodeDialog dialog = new MyQRCodeDialog(getActivity());
        dialog.show();
    }

    private void setUserView() {
        if (isWaitingLogin) {
            mUserContainer.setVisibility(View.GONE);
            mUserUnLogin.setVisibility(View.VISIBLE);
        } else {
            mUserContainer.setVisibility(View.VISIBLE);
            mUserUnLogin.setVisibility(View.GONE);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constants.INTENT_ACTION_LOGOUT:
                    if (mErrorLayout != null) {
                        isWaitingLogin = true;
                        setUserView();
                        mMesCount.hide();
                    }
                    break;
                case Constants.INTENT_ACTION_USER_CHANGE:
                    preRequest(true);
                    break;
                case Constants.INTENT_ACTION_NOTICE:
                    setNotice();
                    break;
            }
        }
    };

    private class CacheTask extends AsyncTask<String, Void, User> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        protected User doInBackground(String... params) {
            Serializable object = CacheManager.readObject(mContext.get(), params[0]);
            if (object == null) {
                return null;
            } else {
                return (User) object;
            }
        }

        @Override
        protected void onPostExecute(User info) {
            super.onPostExecute(info);
            if (info != null) {
                mInfo = info;
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                updateUI();
            } else {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                updateUI();
            }
        }
    }

    private void setNotice() {
        if(MainActivity.mNotice != null) {
            Notice notice = MainActivity.mNotice;
            int atMeCount = notice.getAtMeCount();// @我
            int messageCount = notice.getMessageCount();// 留言
            int commentCount = notice.getCommentCount();// 评论
            int newFansCount = notice.getNewFansCount();// 新粉丝
            int newZansCount = notice.getNewZansCount();// 获得点赞
            int finalCount = atMeCount + messageCount + commentCount + newFansCount + newZansCount;
            if (finalCount > 0) {
                mMesCount.setText(finalCount + "");
                mMesCount.show();
                return;
            }
        }
        mMesCount.hide();
    }

}
