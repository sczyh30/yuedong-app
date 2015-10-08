package com.m1racle.yuedong.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Yuedong Motion Basic InfoFragment
 */
public class MotionBasicInfoFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    public MotionBasicInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_basic_info, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void initData() {
        super.initData();
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

    @OnClick({R.id.fn_button_advice, R.id.fn_button_alarm, R.id.fn_button_mt_data, R.id.fn_button_mt_everyday,
        R.id.fn_button_mt_goal, R.id.fn_button_sleep, R.id.fn_button_temperature, R.id.fn_button_userinfo, R.id.fn_button_weight})
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fn_button_mt_data:
                break;
            case R.id.fn_button_temperature:
                UIUtil.showTemperature(getActivity());
                break;
            default:
                break;
        }
    }
}
