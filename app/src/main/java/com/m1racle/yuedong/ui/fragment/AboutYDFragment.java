package com.m1racle.yuedong.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.base.UtilActivityPage;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.UIUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * About Yuedong Fragment
 */
public class AboutYDFragment extends BaseFragment {


    @Bind(R.id.ab_version)
    TextView mTvVersionStatus;

    @Bind(R.id.ab_version_name)
    TextView mTvVersionName;

    private OnFragmentInteractionListener mListener;

    public static AboutYDFragment newInstance() {
        AboutYDFragment fragment = new AboutYDFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutYDFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            LogUtil.log("About Fragment => Created");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.log("About Fragment => OnCreateView");
        View view = inflater.inflate(R.layout.fragment_about_yd, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.rl_check_update).setOnClickListener(this);
        view.findViewById(R.id.rl_feedback).setOnClickListener(this);
        view.findViewById(R.id.rl_grade).setOnClickListener(this);
        view.findViewById(R.id.rl_gitapp).setOnClickListener(this);
        view.findViewById(R.id.ab_gosite).setOnClickListener(this);
        view.findViewById(R.id.tv_knowmore).setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTvVersionName.setText("悦动 V " + DeviceUtil.getVersionName());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        LogUtil.log("About Fragment => onDetach");
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.rl_check_update:
                //onClickUpdate();
                break;
            case R.id.rl_feedback:
                //showFeedBack();
                break;
            case R.id.rl_grade:
                DeviceUtil.openAppInMarket(getActivity());
                break;
            case R.id.rl_gitapp:
                boolean res = DeviceUtil.openAppActivity(getActivity(),
                        "com.m1racle.yuedong", "com.m1racle.yuedong");

                if (!res) {
                    if (!DeviceUtil.isHaveMarket(getActivity())) {
                        UIUtil.openSysBrowser(getActivity(),
                                "http://www.sczyh30.com");
                    } else {
                        DeviceUtil.gotoMarket(getActivity(), "com.m1racle.yuedong");
                    }
                }
                break;
            case R.id.ab_gosite:
                onClickTestDB();
                break;
            case R.id.tv_knowmore:

                break;
            default:
                break;
        }
    }

    public void onClickTestDB() {
        UIUtil.showActivity(getActivity(), UtilActivityPage.DB_TEST);
    }
}
