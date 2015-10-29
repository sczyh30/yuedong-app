package com.m1racle.yuedong.ui.activity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.presenter.LoginPresenter;
import com.m1racle.yuedong.ui.view.IBasicLoginView;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Yuedong app
 * Login Activity (refactored)
 * @since 1.10 refactor version
 */
public class LoginActivity extends BaseActivity implements IBasicLoginView {

    private final int requestCode = 0;
    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";

    @Bind(R.id.et_username)
    EditText etUserName;
    @Bind(R.id.et_password)
    EditText etPassword;

    private LoginPresenter presenter = new LoginPresenter(this);

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
                presenter.login();
                break;
            case R.id.iv_qq_login:
                presenter.qqLogin();
                break;
            case R.id.iv_wx_login:
                presenter.wxLogin();
                break;
            case R.id.iv_sina_login:
                presenter.sinaLogin();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean okForLogin() {
        if (!DeviceUtil.hasInternet()) {
            ToastUtil.toast(R.string.tip_no_internet);
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

    @Override
    public String getUsername() {
        return etUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void showWaiting() {
        hideKeyboard();
        showWaitDialog(R.string.progress_login).show();
    }

    @Override
    public void hideWaiting() {
        hideWaitDialog();
    }

    @Override
    public void handleLoginSuccess() {
        Intent data = new Intent();
        data.putExtra(BUNDLE_KEY_REQUEST_CODE, requestCode);
        setResult(RESULT_OK, data);
        this.sendBroadcast(new Intent(Constants.INTENT_ACTION_USER_CHANGE));
        ToastUtil.toast("登录成功");
        finish();
    }

    // TODO: 2015/10/14 TEST VOLLEY START
   /* private Response.Listener<LoginResult> listener = new Response.Listener<LoginResult>() {
        @Override
        public void onResponse(LoginResult response) {
            if(response != null) {
                handleLoginResult(response);
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            ToastUtil.toast("网络出现错误，请重试");
        }
    };*/
    // TODO: 2015/10/14 TEST VOLLEY END

}
