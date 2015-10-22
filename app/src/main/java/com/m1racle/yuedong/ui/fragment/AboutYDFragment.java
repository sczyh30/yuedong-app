package com.m1racle.yuedong.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseFragment;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;
import com.m1racle.yuedong.util.UIUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Yuedong App
 * About Fragment
 */
public class AboutYDFragment extends BaseFragment {

    @Bind(R.id.ab_version_name)
    TextView mTvVersionName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        view.findViewById(R.id.rl_app_feature).setOnClickListener(this);
        view.findViewById(R.id.rl_feedback).setOnClickListener(this);
        view.findViewById(R.id.rl_grade).setOnClickListener(this);
        view.findViewById(R.id.bt_hwclient).setOnClickListener(this);
        view.findViewById(R.id.ab_gosite).setOnClickListener(this);
        view.findViewById(R.id.tv_knowmore).setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTvVersionName.setText(String.format("悦动 v %s", DeviceUtil.getVersionName()));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.rl_app_feature:
                showFeatures();
                break;
            case R.id.rl_feedback:
                showFeedback();
                break;
            case R.id.rl_grade:
                DeviceUtil.openAppInMarket(getActivity());
                break;
            case R.id.bt_hwclient:
                ToastUtil.toast("打开华为智能设备客户端");
                break;
            case R.id.ab_gosite:
                UIUtil.openSysBrowser(getActivity(),
                        "http://app.yuedong.com");
                break;
            case R.id.tv_knowmore:
                UIUtil.openSysBrowser(getActivity(),
                        "http://www.sczyh30.com");
                break;
            default:
                break;
        }
    }

    private void showFeatures() {
        UIUtil.openSysBrowser(getActivity(),
                "http://app.yuedong.com/features");
    }

    private void showFeedback() {

    }
}
