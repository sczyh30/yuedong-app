package com.m1racle.yuedong.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.entity.Update;
import com.m1racle.yuedong.net.SamsaraAPI;
import com.m1racle.yuedong.ui.DialogUtil;

import cz.msebera.android.httpclient.Header;

/**
 * Yuedong App
 * Update Manager Class
 */
public class UpdateManager {

    private Context mContext;
    private ProgressDialog waitDialog;
    private boolean isShow = false;
    private Update mUpdateInfo;

    public UpdateManager(Context context, boolean isShow) {
        this.mContext = context;
        this.isShow = isShow;
    }

    private BaseJsonHttpResponseHandler mHandler = new BaseJsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
            hideCheckDialog();
            mUpdateInfo = (Update)response;
            onFinishCheck();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
            hideCheckDialog();
            if (isShow) {
                showFailureDialog();
            }
        }

        @Override
        protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            return JsonUtil.resolveUpdate(rawJsonData);
        }
    };

    public void checkUpdate() {
        if(!DeviceUtil.hasInternet()) {
            ToastUtil.toast(R.string.error_view_network_error_click_to_refresh);
            return;
        }
        if (isShow) {
            showCheckDialog();
        }
        SamsaraAPI.getUpdateInfo(mHandler);
    }

    private void showCheckDialog() {

        if (waitDialog == null) {
            waitDialog = DialogUtil.getWaitDialog(mContext, "正在获取新版本信息...");
        }
        //TODO:此处有影响用户体验，需改进
        waitDialog.setCancelable(false);
        waitDialog.show();
    }

    private void hideCheckDialog() {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }

    private void showUpdateInfo() {
        if (mUpdateInfo == null) {
            return;
        }
        AlertDialog.Builder dialog = DialogUtil.getConfirmDialog(mContext, mUpdateInfo.getUpdateLog(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //DownloadDispatcher.startDownloadService(mContext, mUpdateInfo.getDownloadUrl(), mUpdateInfo.getFileHash());
                LogUtil.toast("暂时不提供下载服务");
            }
        });
        dialog.setTitle("发现新版本");
        dialog.show();
    }
    
    public boolean isUpdateAvailable() {
        if(this.mUpdateInfo == null) {
            return false;
        }
        int curVersionCode = DeviceUtil.getVersionCode(AppContext
                .getContext().getPackageName());
        return curVersionCode < mUpdateInfo.getVersionCode();
    }

    private void showLatestDialog() {
        DialogUtil.getMessageDialog(mContext, "已经是最新版本了").show();
    }

    private void showFailureDialog() {
        DialogUtil.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
    }

    private void onFinishCheck() {
        if (isUpdateAvailable()) {
            showUpdateInfo();
        } else {
            if (isShow)
                showLatestDialog();
        }
    }
}
