package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.entity.UserDetail;
import com.m1racle.yuedong.net.BitmapRequestClient;
import com.m1racle.yuedong.net.SamsaraAPI;
import com.m1racle.yuedong.ui.empty.EmptyLayout;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.ToastUtil;

import org.kymjs.kjframe.KJBitmap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * My Social Information Detail Fragment
 */
public class MySocialDetailFragment extends BaseFragment {

    private int uid = AppContext.getContext().getLoginUid();

    @Bind(R.id.iv_avatar)
    ImageView mUserFace;
    @Bind(R.id.tv_name)
    TextView mName;
    @Bind(R.id.tv_join_time)
    TextView mJoinTime;
    @Bind(R.id.tv_location)
    TextView mLocation;
    @Bind(R.id.tv_love_sport)
    TextView mLoveSport;
    @Bind(R.id.tv_my_tip)
    TextView mMyTip;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private UserDetail mUserDetail;
    private User mUser;

    private final static String FACE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Yuedong/portrait/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information_detail,
                container, false);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestData();
            }
        });
    }

    @Override
    public void initData() {
        if (!DeviceUtil.hasInternet()) {
            ToastUtil.toast(R.string.tip_no_internet);
            return;
        }
        sendRequestData();
    }

    private final BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            if(response != null) {
                handleResult((UserDetail)response);
            } else {
                onFailure(statusCode, headers, rawJsonResponse, null);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
            ToastUtil.toast("网络出现错误，请重试。(" + statusCode + ")");
        }

        @Override
        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            return JsonUtil.resolveUserDetail(rawJsonData);
        }
    };

    public void sendRequestData() {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        SamsaraAPI.getUserDetail(uid, mHandler);
    }

    private void handleResult(UserDetail detail) {
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        mUserDetail = detail;
        mUser = AppContext.getContext().getLoginUser();
        updateUI();
    }

    private void updateUI() {
        if(mUser != null && mUserDetail != null) {
            BitmapRequestClient.send(mUserFace, "portrait/" + mUser.getPortrait(), 50, 50);
            //new KJBitmap().displayWithLoadBitmap(mUserFace, mUser.getPortrait(),
            //        R.mipmap.widget_dface);
            mName.setText(mUser.getUsername());
            mJoinTime.setText(mUserDetail.getRegTime());
            mLocation.setText(mUserDetail.getLocation());
            mLoveSport.setText(mUserDetail.getSports());
            mMyTip.setText(mUserDetail.getMyTip());
        }
    }

    @Override
    @OnClick({R.id.btn_logout})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_logout:
                AppContext.getContext().logout();
                getActivity().finish();
                ToastUtil.toast("注销成功");
                break;
            default:
                break;
        }
    }
}
