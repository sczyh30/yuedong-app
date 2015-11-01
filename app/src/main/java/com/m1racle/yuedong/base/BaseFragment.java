package com.m1racle.yuedong.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.huawei.huaweiwearableApi.HuaweiWearableManager;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;

/**
 * Base Fragment
 * @author sczyh30
 * @since 0.1
 */
public abstract class BaseFragment extends Fragment implements
        android.view.View.OnClickListener {

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;
    public static int mState = STATE_NONE;

    // the flag of whether the fragment is on the top
    protected int top_flag = 0;

    protected LayoutInflater mInflater;

    public Context getContext() {
        return AppContext.getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        top_flag = 1;
    }

    @Override
    public void onPause() {
        super.onPause();
        top_flag = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        top_flag = 0;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return this.mInflater.inflate(resId, null);
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void hideWaitDialog() {
        Activity activity = getActivity();
        if (activity instanceof BaseDialogControl) {
            ((BaseDialogControl) activity).hideWaitDialog();
        }
    }

    protected ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    protected ProgressDialog showWaitDialog(int resId) {
        Activity activity = getActivity();
        if (activity instanceof BaseDialogControl) {
            return ((BaseDialogControl) activity).showWaitDialog(resId);
        }
        return null;
    }

    protected ProgressDialog showWaitDialog(String str) {
        Activity activity = getActivity();
        if (activity instanceof BaseDialogControl) {
            return ((BaseDialogControl) activity).showWaitDialog(str);
        }
        return null;
    }

    public void initView(View view) {

    }

    public void initData() {

    }

    protected HuaweiWearableManager getHuaweiManager() {
        return AppContext.getContext().getHWManager();
    }

    protected void hideKeyboard() {
        InputMethodManager imm =  (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
