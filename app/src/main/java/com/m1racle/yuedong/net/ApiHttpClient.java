package com.m1racle.yuedong.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.m1racle.yuedong.util.LogUtil;

/**
 * Yuedong App Common Library
 * Samsara API HttpClient
 * @author sczyh30
 * @since 0.1
 */
public class ApiHttpClient {

    public final static String HOST = "";
    private static String API_URL = "";
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
        LogUtil.log("GET" + partUrl);
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = String.format(API_URL, partUrl);
        LogUtil.log("BASE_CLIENT", "request:" + url);
        return url;
    }

    public static String getApiUrl() {
        return API_URL;
    }


}
