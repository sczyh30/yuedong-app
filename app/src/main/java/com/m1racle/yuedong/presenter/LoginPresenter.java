package com.m1racle.yuedong.presenter;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.m1racle.yuedong.AppConfig;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.entity.LoginResult;
import com.m1racle.yuedong.entity.User;
import com.m1racle.yuedong.net.ApiHttpClient;
import com.m1racle.yuedong.presenter.service.IThirdLoginService;
import com.m1racle.yuedong.presenter.service.callback.LoginResultCallback;
import com.m1racle.yuedong.presenter.service.impl.LoginServiceImpl;
import com.m1racle.yuedong.ui.view.IBasicLoginView;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.ToastUtil;

import org.apache.http.client.protocol.ClientContext;
import org.kymjs.kjframe.http.HttpConfig;

import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Yuedong app
 * Basic login presenter
 * @author sczyh30
 * @since 1.10
 */
public class LoginPresenter {

    private IThirdLoginService loginService;
    private IBasicLoginView loginView;
    private Handler mHandler = new Handler();

    public LoginPresenter(IBasicLoginView loginView) {
        this.loginView = loginView;
        this.loginService = new LoginServiceImpl();
    }

    public void login() {
        if(!loginView.okForLogin())
            return;
        loginView.showWaiting();
        loginService.login(loginView.getUsername(), loginView.getPassword(),
                new LoginResultCallback() {
            @Override
            public void onLoginSuccess(final LoginResult result) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handleLoginResult(result);
                        loginView.hideWaiting();
                        loginView.handleLoginSuccess();
                    }
                });
            }

            @Override
            public void onFailure(int error_code) {
                ToastUtil.toast("网络出现错误，请重试。(" + error_code + ")");
            }
        });
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
            User user = result.getUser();
            result.getUser().setAccount(user.getUsername());
            result.getUser().setPassword(user.getPassword());
            result.getUser().setRememberMe(true);
            AppContext.getContext().saveUserInfo(user);
        }
        else {
            AppContext.getContext().cleanLoginInfo();
            ToastUtil.showToast("错误：" + result.getDataResult().getErrorMsg());
        }
    }

    public void qqLogin() {
        loginService.qqLogin("", "", null);
    }

    public void wxLogin() {
        loginService.wxLogin("", "", null);
    }

    public void sinaLogin() {
        loginService.weiboLogin("", "", null);
    }

}
