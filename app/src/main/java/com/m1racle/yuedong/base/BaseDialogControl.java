package com.m1racle.yuedong.base;

import android.app.ProgressDialog;

/**
 * Base Dialog Control Interface
 * @author sczyh30
 */
public interface BaseDialogControl {

    void hideWaitDialog();

    ProgressDialog showWaitDialog();

    ProgressDialog showWaitDialog(int resId);

    ProgressDialog showWaitDialog(String text);

}
