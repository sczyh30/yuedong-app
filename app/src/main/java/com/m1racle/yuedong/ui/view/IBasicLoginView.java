package com.m1racle.yuedong.ui.view;

/**
 * Yuedong app
 * Basic login view layer interface
 * @author sczyh30
 * @since 1.10
 */
public interface IBasicLoginView {

    String getUsername();

    String getPassword();

    void handleLoginSuccess();

    void showWaiting();

    void hideWaiting();

    boolean okForLogin();

}
