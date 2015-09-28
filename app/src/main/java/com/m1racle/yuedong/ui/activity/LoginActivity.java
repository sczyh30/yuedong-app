package com.m1racle.yuedong.ui.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Login Activity
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_username)
    EditText etUserName;

    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.login;
    }

    @Override
    @OnClick({R.id.btn_login, R.id.iv_qq_login, R.id.iv_wx_login, R.id.iv_sina_login})
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                handleLogin();
                break;
            case R.id.iv_qq_login:
                qqLogin();
                break;
            case R.id.iv_wx_login:
                wxLogin();
                break;
            case R.id.iv_sina_login:
                sinaLogin();
                break;
            default:
                break;
        }
    }

    private boolean okForLogin() {
        if (!DeviceUtil.hasInternet()) {
            ToastUtil.showToastShort(R.string.tip_no_internet);
            return false;
        }
        if (etUserName.length() == 0) {
            etUserName.setError("请输入邮箱/用户名");
            etUserName.requestFocus();
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("请输入密码");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void handleLogin() {
        if(!okForLogin())
            return;

        LogUtil.toast("服务器维护中，请稍后再试");
    }

    private void qqLogin() {
        LogUtil.toast("QQ API invocation");
    }

    private void wxLogin() {
        LogUtil.toast("WeiXin API invocation");
    }

    private void sinaLogin() {

    }
}
