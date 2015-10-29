package com.m1racle.yuedong.presenter.service;

import com.m1racle.yuedong.presenter.service.callback.LoginResultCallback;

/**
 * Yuedong app
 * Base login service interface
 * @author sczyh30
 * @since 1.10
 */
public interface IBaseLoginService {

    /**
     * the abstract method to login
     * @param username username
     * @param password password
     * @param callback login callback interface
     */
    void login(String username, String password, LoginResultCallback callback);

}
