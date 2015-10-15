package com.m1racle.yuedong.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.util.LogUtil;

import org.apache.http.client.params.ClientPNames;

import java.util.Locale;

/**
 * Yuedong App Common Library
 * Samsara API HttpClient
 * @author sczyh30
 * @since 0.1
 * we are considering to deprecate this class soon
 */
public class ApiHttpClient {

    //public final static String HOST = "233.233.233.233";
    //private static String API_URL = "http://233.233.233.233/";
    public final static String HOST = "192.168.95.1";
    private static String API_URL = "http://192.168.95.1/sandbox/";
    private static String appCookie;

    public static AsyncHttpClient client;

    public ApiHttpClient() {}

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static void post(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), handler);
        LogUtil.log("POST " + partUrl);
    }

    public static void post(String partUrl, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), params, handler);
        LogUtil.log("POST " + partUrl + "&" + params);
    }

    public static void get(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), params, handler);
        LogUtil.log("GET " + partUrl + "&" + params);
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        LogUtil.log("GET " + partUrl);
        LogUtil.log("GET => " + getAbsoluteApiUrl(partUrl));
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = API_URL + partUrl;
        LogUtil.log("BASE_CLIENT", "request: " + url);
        return url;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void setHttpClient(AsyncHttpClient c) {
        client = c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
        client.addHeader("Host", HOST);
        client.addHeader("Connection", "Keep-Alive");
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        setUserAgent(getUserAgent(AppContext.getContext()));
    }

    public static void setUserAgent(String userAgent) {
        client.setUserAgent(userAgent);
    }

    public static String getUserAgent(AppContext appContext) {
        StringBuilder sb = new StringBuilder("Yuedong");
        sb.append('/' + appContext.getPackageInfo().versionName + '_'
                + appContext.getPackageInfo().versionCode);// app版本信息
        sb.append("/Android");// 手机系统平台
        sb.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
        sb.append("/" + android.os.Build.MODEL); // 手机型号
        sb.append("/" + appContext.getAppId());// 客户端唯一标识
        return sb.toString();
    }

    public static void setCookie(String cookie) {
        client.addHeader("Cookie", cookie);
    }

    public static void cleanCookie() {
        appCookie = "";
    }

    public static String getCookie(AppContext appContext) {
        if (appCookie == null || appCookie.equals("")) {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }
}
