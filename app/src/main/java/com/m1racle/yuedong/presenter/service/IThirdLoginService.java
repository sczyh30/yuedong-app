package com.m1racle.yuedong.presenter.service;

import com.m1racle.yuedong.presenter.service.callback.LoginResultCallback;

/**
 * Yuedong app
 * Third social login service interface
 * @author sczyh30
 * @since 1.10
 */
public interface IThirdLoginService extends IBaseLoginService {

    void wxLogin(String username, String password, LoginResultCallback callback);

    void qqLogin(String username, String password, LoginResultCallback callback);

    void weiboLogin(String username, String password, LoginResultCallback callback);
}
