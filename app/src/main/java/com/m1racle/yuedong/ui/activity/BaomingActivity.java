package com.m1racle.yuedong.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.entity.EventApply;
import com.m1racle.yuedong.net.YuedongAPI;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class BaomingActivity extends BaseActivity {

    private static final int uid = AppContext.getContext().getLoginUid();
    private int maid;

    @Bind(R.id.tv_apply_uid)
    TextView mTvUid;
    @Bind(R.id.tv_apply_event_name)
    TextView mTvEventName;
    @Bind(R.id.et_apply_name)
    EditText mEtName;
    @Bind(R.id.et_apply_mobile)
    EditText mEtMobile;
    @Bind(R.id.et_apply_address)
    EditText mEtAddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baoming;
    }

    private void initView() {
        //mTvEventName.setText(getIntent().getStringExtra("MANAME"));
        mTvUid.setText(AppContext.getContext().getUsername());
        maid = getIntent().getIntExtra("MAID", 0);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mActionBar.setTitle("活动报名");
        initView();
    }

    @Override
    @OnClick(R.id.btn_apply_send)
    public void onClick(View v) {
        sendApply();
    }

    private void sendApply() {
        if(!isDataPrepared())
            return;
        showWaiting();
        // generate apply entity
        EventApply data = new EventApply();
        data.setUid(uid);
        data.setAddress(mEtAddress.getText().toString());
        data.setUid(AppContext.getContext().getLoginUid());
        data.setMaid(maid);
        data.setGender(0);
        data.setName(mEtName.getText().toString());
        data.setMobile(mEtMobile.getText().toString());
        // handle the request
        YuedongAPI.postEventApply(data, listener, errorListener);
    }

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if(response != null) {
                switch (response) {
                    case "fuck":
                        ToastUtil.toast("未知的错误");
                        hideWaitDialog();
                        break;
                    case "okeoke":
                        onHandleSuccess();
                        break;
                    default:
                        onHandleFailure();
                        break;
                }
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            onHandleFailure();
        }
    };

    private void onHandleSuccess() {
        hideWaitDialog();
        finish();
    }

    private void onHandleFailure() {
        ToastUtil.toast(R.string.error_WP0014);
        hideWaitDialog();
    }

    private void showWaiting() {
        hideKeyboard();
        showWaitDialog(R.string.sending_reply_to_server).show();
    }

    private Boolean isDataPrepared() {

        if (!DeviceUtil.hasInternet()) {
            ToastUtil.toast(R.string.tip_no_internet);
            return false;
        }

        if(mEtName.length() <= 1 || mEtName.length() >= 30) {
            mEtName.setError("长度错误");
            mEtName.requestFocus();
            return false;
        }
        if(mEtMobile.length() <= 6 || mEtMobile.length() >= 20) {
            mEtMobile.setError("格式错误");
            mEtMobile.requestFocus();
            return false;
        }
        if(mEtAddress.length() > 100) {
            mEtAddress.setError("格式错误");
            mEtAddress.requestFocus();
            return false;
        }
        return true;
    }
}
