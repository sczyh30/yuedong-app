package com.m1racle.yuedong.presenter.service.impl;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.m1racle.yuedong.entity.LoginResult;
import com.m1racle.yuedong.net.SamsaraAPI;
import com.m1racle.yuedong.presenter.service.IThirdLoginService;
import com.m1racle.yuedong.presenter.service.callback.LoginResultCallback;
import com.m1racle.yuedong.util.JsonUtil;
import com.m1racle.yuedong.util.LogUtil;

import cz.msebera.android.httpclient.Header;

/**
 * Yuedong app
 * Login service interface impl
 * @author sczyh30
 * @since 1.10
 */
public class LoginServiceImpl implements IThirdLoginService {

    @Override
    public void login(final String username, final String password, final LoginResultCallback callback) {

        final BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(response != null) {
                    callback.onLoginSuccess((LoginResult)response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                callback.onFailure(statusCode);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return JsonUtil.resolveLoginResult(rawJsonData);
            }
        };

        new Thread() {
            @Override
            public void run() {
                SamsaraAPI.login(username, password, mHandler);
            }
        }.start();
    }

    //TODO:需要的时候添加第三方登录接口支持
    @Override
    public void wxLogin(String username, String password, LoginResultCallback callback) {
        LogUtil.toast("微信API 接口未开放");
    }

    @Override
    public void qqLogin(String username, String password, LoginResultCallback callback) {
        LogUtil.toast("QQ API 接口未开放");
    }

    @Override
    public void weiboLogin(String username, String password, LoginResultCallback callback) {
        LogUtil.toast("微博API 接口未开放");
    }
}
