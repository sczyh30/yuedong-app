package com.m1racle.yuedong.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.m1racle.yuedong.util.ToastUtil;

/**
 * Network Status Change Broadcast Receiver
 * inspect the network status
 * @see com.m1racle.yuedong.ui.activity.MainActivity
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable()) {
            ToastUtil.toast("网络连接出现错误，请检查网络配置是否正确");
        }
    }
}
