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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.cache.CacheManager;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.ui.dialog.MyQRCodeDialog;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.ui.widget.AvatarView;
import com.m1racle.yuedong.ui.widget.BadgeView;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;

import org.kymjs.kjframe.utils.StringUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Yuedong App
 * fucking My Information Fragment
 */
public class MyInformationFragment extends BaseFragment {

    @Bind(R.id.iv_avatar)
    AvatarView mIvAvatar;
    @Bind(R.id.iv_gender)
    ImageView mIvGender;
    @Bind(R.id.tv_name)
    TextView username;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.tv_motion_activities)
    TextView mTvFavorite;
    @Bind(R.id.tv_following)
    TextView mTvFollowing;
    @Bind(R.id.tv_follower)
    TextView mTvFans;
    @Bind(R.id.tv_mes)
    View mMesView;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;
    @Bind(R.id.iv_qr_code)
    ImageView mQrCode;
    @Bind(R.id.ll_user_container)
    View mUserContainer;
    @Bind(R.id.rl_user_unlogin)
    View mUserUnLogin;
    @Bind(R.id.rootview)
    LinearLayout rootView;

    private static BadgeView mMesCount;

    private boolean mIsWaitingLogin;

    private User mInfo;
    private AsyncTask<String, Void, User> mCacheTask;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constants.INTENT_ACTION_LOGOUT:
                    if (mErrorLayout != null) {
                        mIsWaitingLogin = true;
                        setupUser();
                        mMesCount.hide();
                    }
                    break;
                case Constants.INTENT_ACTION_USER_CHANGE:
                    requestData(true);
                    break;
                case Constants.INTENT_ACTION_NOTICE:
                    break;
            }
        }
    };

    private final AsyncHttpResponseHandler msHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                //mInfo = XmlUtils.toBean(MyInformation.class,
                 //       new ByteArrayInputStream(arg2)).getUser();
                if (mInfo != null) {
                    fillUI();
                    //AppContext.getContext().updateUserInfo(mInfo);
                    //new SaveCacheTask(getActivity(), mInfo, getCacheKey())
                     //       .execute();
                } else {
                    onFailure(arg0, arg1, arg2, new Throwable());
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(arg0, arg1, arg2, e);
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                Throwable arg3) {}
    };

    private final BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

        }

        @Override
        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            return null;
        }
    };

    private void setupUser() {
        if (mIsWaitingLogin) {
            mUserContainer.setVisibility(View.GONE);
            mUserUnLogin.setVisibility(View.VISIBLE);
        } else {
            mUserContainer.setVisibility(View.VISIBLE);
            mUserUnLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_LOGOUT);
        filter.addAction(Constants.INTENT_ACTION_USER_CHANGE);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information,
                container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData(true);
        mInfo = AppContext.getContext().getLoginUser();
        fillUI();
    }

    @Override
    public void initView(View view) {
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        mIvAvatar.setOnClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getContext().isLogin()) {
                    requestData(true);
                } else {
                    UIUtil.showLoginActivity(getActivity());
                }
            }
        });
        view.findViewById(R.id.ly_motion_activities).setOnClickListener(this);
        view.findViewById(R.id.ly_following).setOnClickListener(this);
        view.findViewById(R.id.ly_follower).setOnClickListener(this);
        view.findViewById(R.id.rl_message).setOnClickListener(this);
        view.findViewById(R.id.rl_health).setOnClickListener(this);
        view.findViewById(R.id.rl_activities).setOnClickListener(this);
        mQrCode.setOnClickListener(this);
        /*view.findViewById(R.id.rl_note).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtil.showActivity(getActivity(),
                                SimpleBackPage.NOTE);
                    }
                });*/
        mUserUnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showLoginActivity(getActivity());
            }
        });

        mMesCount = new BadgeView(getActivity(), mMesView);
        mMesCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mMesCount.setBadgePosition(BadgeView.POSITION_CENTER);
        mMesCount.setGravity(Gravity.CENTER);
        mMesCount.setBackgroundResource(R.mipmap.notification_bg);
        mQrCode.setOnClickListener(this);

    }

    private void fillUI() {
        if (mInfo == null)
            return;
        mIvAvatar.setAvatarUrl(mInfo.getPortrait());
        username.setText(mInfo.getUsername());
        mIvGender
                .setImageResource(StringUtils.toInt(mInfo.getGender()) != 2 ? R.mipmap.userinfo_icon_male
                        : R.mipmap.userinfo_icon_female);
        mTvScore.setText(String.valueOf(mInfo.getScore()));
        //mTvFavorite.setText(String.valueOf(mInfo.));
        mTvFollowing.setText(String.valueOf(mInfo.getFollowers()));
        mTvFans.setText(String.valueOf(mInfo.getFans()));
    }

    private void requestData(boolean refresh) {
        if (AppContext.getContext().isLogin()) {
            mIsWaitingLogin = false;
            String key = getCacheKey();
            if (refresh || DeviceUtil.hasInternet()
                    && (!CacheManager.isExistDataCache(getActivity(), key))) {
                sendRequestData();
            } else {
                readCacheData(key);
            }
        } else {
            mIsWaitingLogin = true;
        }
        setupUser();
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

    private void sendRequestData() {
        int uid = AppContext.getContext().getLoginUid();
        //OSChinaApi.getMyInformation(uid, mHandler);
    }

    private String getCacheKey() {
        return "my_information" + AppContext.getContext().getLoginUid();
    }

    private class CacheTask extends AsyncTask<String, Void, User> {
        private final WeakReference<Context> mContext;

        private CacheTask(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        protected User doInBackground(String... params) {
            Serializable seri = CacheManager.readObject(mContext.get(),
                    params[0]);
            if (seri == null) {
                return null;
            } else {
                return (User) seri;
            }
        }

        @Override
        protected void onPostExecute(User info) {
            super.onPostExecute(info);
            if (info != null) {
                mInfo = info;
                // mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                // } else {
                // mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                fillUI();
            }
        }
    }

    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Context> mContext;
        private final Serializable seri;
        private final String key;

        private SaveCacheTask(Context context, Serializable seri, String key) {
            mContext = new WeakReference<Context>(context);
            this.seri = seri;
            this.key = key;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CacheManager.saveObject(mContext.get(), seri, key);
            return null;
        }
    }

    @OnClick({R.id.iv_qr_code})
    @Override
    public void onClick(View v) {
        if (mIsWaitingLogin) {
            ToastUtil.showToast(R.string.unlogin);
            UIUtil.showLoginActivity(getActivity());
            return;
        }
        final int id = v.getId();
        switch (id) {
        case R.id.iv_avatar:
            //UIUtil.showActivity(getActivity(),
            //        SimpleBackPage.MY_INFORMATION_DETAIL);
            break;
        case R.id.iv_qr_code:
            showMyQrCode();
            break;
        /*case R.id.ly_following:
            UIUtil.showFriends(getActivity(), AppContext.getContext()
                    .getLoginUid(), 0);
            break;
        case R.id.ly_follower:
            UIUtil.showFriends(getActivity(), AppContext.getContext()
                    .getLoginUid(), 1);
            break;
        case R.id.ly_favorite:
            UIUtil.showUserFavorite(getActivity(), AppContext.getContext()
                    .getLoginUid());
            break;
        case R.id.rl_message:
            UIUtil.showMyMes(getActivity());
            setNoticeRead();
            break;
        case R.id.rl_blog:
            UIUtil.showUserBlog(getActivity(), AppContext.getContext()
                    .getLoginUid());
            break;*/
        case R.id.rl_user_center:
            UIUtil.showUserCenter(getActivity(), AppContext.getContext()
                    .getLoginUid(), AppContext.getContext().getLoginUser()
                    .getUsername());
            break;
        default:
            break;
        }
    }

    private void showMyQrCode() {
        MyQRCodeDialog dialog = new MyQRCodeDialog(getActivity());
        dialog.show();
    }

    @Override
    public void initData() {}

    private void setNoticeRead() {
        mMesCount.setText("");
        mMesCount.hide();
    }

}
