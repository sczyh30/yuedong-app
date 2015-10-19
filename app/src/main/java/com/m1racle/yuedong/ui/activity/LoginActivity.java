package com.m1racle.yuedong.ui.activity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.m1racle.yuedong.AppConfig;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.base.BaseActivity;
import com.m1racle.yuedong.base.Constants;
import com.m1racle.yuedong.entity.LoginResult;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.ApiHttpClient;
import com.m1racle.yuedong.net.SamsaraAPI;
import com.m1racle.yuedong.util.DeviceUtil;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import org.apache.http.client.protocol.ClientContext;
import org.kymjs.kjframe.http.HttpConfig;

import butterknife.Bind;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Login Activity
 */
public class LoginActivity extends BaseActivity {

    private final int requestCode = 0;
    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";

    @Bind(R.id.et_username)
    EditText etUserName;

    @Bind(R.id.et_password)
    EditText etPassword;

    private String mUserName = "";
    private String mPassword = "";

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

    // TODO: 2015/10/14 TEST VOLLEY START
    private Response.Listener<LoginResult> listener = new Response.Listener<LoginResult>() {
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
    };
    // TODO: 2015/10/14 TEST VOLLEY END

    private final BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            if(response != null) {
                handleLoginResult((LoginResult)response);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
            ToastUtil.toast("网络出现错误，请重试。(" + statusCode + ")");
        }

        @Override
        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            return JsonUtil.resolveLoginResult(rawJsonData);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideWaitDialog();
        }
    };

    private void handleLogin() {
        if(!okForLogin())
            return;

        mUserName = etUserName.getText().toString();
        mPassword = etPassword.getText().toString();

        hideKeyboard();
        showWaitDialog(R.string.progress_login).show();
        SamsaraAPI.login(mUserName, mPassword, mHandler);
    }


    private void handleLoginResult(LoginResult result) {
        if(result.getDataResult().isOK()) {
            AsyncHttpClient client = ApiHttpClient.getHttpClient();
            HttpContext httpContext = client.getHttpContext();
            CookieStore cookies = (CookieStore)httpContext
                    .getAttribute(ClientContext.COOKIE_STORE);
            if(cookies != null) {
                String tmp_cookies = "";
                for(Cookie c : cookies.getCookies()) {
                    LogUtil.log("cookie:" + c.getName() + " " + c.getValue());
                    tmp_cookies += (c.getName() + "=" + c.getValue()) + ";";
                    AppContext.getContext().setProperty(AppConfig.CONF_COOKIE,
                            tmp_cookies);
                    ApiHttpClient.setCookie(ApiHttpClient.getCookie(AppContext
                            .getContext()));
                    HttpConfig.sCookie = tmp_cookies;
                }
            }
            result.getUser().setAccount(mUserName);
            result.getUser().setPassword(mPassword);
            result.getUser().setRememberMe(true);
            AppContext.getContext().saveUserInfo(result.getUser());
            hideWaitDialog();
            handleLoginSuccess();
        }
        else {
            AppContext.getContext().cleanLoginInfo();
            ToastUtil.showToast("错误：" + result.getDataResult().getErrorMsg());
        }
    }

    private void handleLoginSuccess() {
        Intent data = new Intent();
        data.putExtra(BUNDLE_KEY_REQUEST_CODE, requestCode);
        setResult(RESULT_OK, data);
        this.sendBroadcast(new Intent(Constants.INTENT_ACTION_USER_CHANGE));
        ToastUtil.toast("登录成功");
        finish();
    }

    //TODO:需要的时候添加第三方登录接口支持
    private void qqLogin() {
        LogUtil.toast("QQ API 接口未开放");
    }

    private void wxLogin() {
        LogUtil.toast("微信API 接口未开放");
    }

    private void sinaLogin() {
        LogUtil.toast("微博API 接口未开放");
    }
}
