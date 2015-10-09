package com.m1racle.yuedong.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseApplication;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.UtilActivityPage;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.ui.dialog.MyQRCodeDialog;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.ui.widget.AvatarView;
import com.m1racle.yuedong.ui.widget.BadgeView;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.UIUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MySocialInfoFragment extends BaseFragment {


    public static final int sChildView = 9; // 在没有加入TeamList控件时rootview有多少子布局

    @Bind(R.id.iv_avatar)
    AvatarView mIvAvatar;
    @Bind(R.id.iv_gender)
    ImageView mIvGender;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.tv_favorite)
    TextView mTvFavorite;
    @Bind(R.id.tv_following)
    TextView mTvFollowing;
    @Bind(R.id.tv_follower)
    TextView mTvFans;
    @Bind(R.id.tv_mes)
    View mMesView;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;
    //@Bind(R.id.iv_qr_code)
    //ImageView mQrCode;
    @Bind(R.id.ll_user_container)
    View mUserContainer;
    @Bind(R.id.rl_user_unlogin)
    View mUserUnLogin;
    @Bind(R.id.rootview)
    LinearLayout rootView;

    private OnFragmentInteractionListener mListener;

    private User user;
    private static BadgeView mMesCount;

    private boolean mIsWatingLogin;

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void initView(View view) {
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        mIvAvatar.setOnClickListener(this);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.getContext().isLogin()) {
                    //requestData(true);
                } else {
                    UIUtil.showLoginActivity(getActivity());
                }
            }
        });
        view.findViewById(R.id.ly_favorite).setOnClickListener(this);
        view.findViewById(R.id.ly_following).setOnClickListener(this);
        view.findViewById(R.id.ly_follower).setOnClickListener(this);
        view.findViewById(R.id.rl_message).setOnClickListener(this);
        view.findViewById(R.id.rl_team).setOnClickListener(this);
        view.findViewById(R.id.rl_blog).setOnClickListener(this);
        view.findViewById(R.id.rl_note).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //UIUtil.showActivity(getActivity(),
                         //       SimpleBackPage.NOTE);
                    }
                });
        mUserUnLogin.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    @OnClick({R.id.iv_qr_code})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_avatar:
                UIUtil.showSocialDetail(getActivity());
                break;
            case R.id.iv_qr_code:
                showMyQrCode();
                break;
            default:
                break;
        }
    }

    private void showMyQrCode() {
        MyQRCodeDialog dialog = new MyQRCodeDialog(getActivity());
        dialog.show();
        LogUtil.toast("QR Code click");
    }
}
