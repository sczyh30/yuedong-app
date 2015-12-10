package com.m1racle.yuedong.presenter.service.callback;

import com.m1racle.yuedong.entity.LoginResult;

/**
 * Yuedong app
 * Login result callback interface
 * @author sczyh30
 * @since 1.10
 */
public interface LoginResultCallback {

    void onLoginSuccess(LoginResult result);

    void onFailure(int error_code);
}
