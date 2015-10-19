package com.m1racle.yuedong.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.entity.LoginResult;
import com.m1racle.yuedong.entity.MotionActivitiesDetail;
import com.m1racle.yuedong.net.request.SamsaraGsonRequest;
import com.m1racle.yuedong.net.request.SamsaraStringRequest;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.crypt.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Yuedong App Common Library
 * Samsara WebService API for Yuedong App
 * updated since 0.6.2
 * support Volley
 * @author sczyh30
 * @since 0.6
 */
public class YuedongAPI {

    private static String appCookie;

    public static final String URL_REGISTER = "action/api/register";
    public static final String URL_LOGIN = "action/api/login.json";
    public static final String URL_GET_LATEST_ACTIVITIES = "action/api/get/latest_ma.json";
    public static final String URL_GET_USER = "action/api/get/user.json";
    public static final String URL_GET_USER_DETAIL= "action/api/get/user_detail.json";
    public static final String URL_UPLOAD_LOG = "action/api/upload/log";
    public static final String URL_GET_ANDROID_UPDATE = "action/api/get/app_version_android.json";
    public static final String URL_GET_ACTIVITY_DETAIL = "action/api/get/act_detail";

    public static final String URL_GET_FRIEND = "action/api/get/friend";
    public static final String URL_GET_FANS = "action/api/get/fans";
    public static final String URL_GET_FOLLOWING = "action/api/get/following";

    /**
     * Login API method (POST)
     * @param username Username
     * @param password Password (not hashed)
     * @param listener Response.Listener
     * @param errorListener Response.ErrorListener
     */
    public static void login(final String username, final String password, Response.Listener<LoginResult> listener,
                             Response.ErrorListener errorListener) {
        String login_url = ApiRequestClient.getAbsoluteApiUrl(URL_LOGIN);
        SamsaraGsonRequest<LoginResult> request = new SamsaraGsonRequest<LoginResult>(Request.Method.POST, login_url,
                LoginResult.class, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", MD5Util.MD5(password));
                params.put("keep_login", "1");
                return params;
            }
        };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("POST => " + login_url);
    }

    public static void getActivityDetail(final int maid, Response.Listener<MotionActivitiesDetail> listener,
                                         Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl(URL_GET_ACTIVITY_DETAIL + "/" + maid + ".json");
        SamsaraGsonRequest<MotionActivitiesDetail> request = new
                SamsaraGsonRequest<MotionActivitiesDetail>(url, MotionActivitiesDetail.class,
                listener, errorListener) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("maid", Integer.toString(maid));
                        return params;
                    }
                };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }

    public static void getLatestMotionActivities(Response.Listener<String> listener,
                                                 Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl(URL_GET_LATEST_ACTIVITIES);
        SamsaraStringRequest request = new SamsaraStringRequest(url, listener, errorListener);
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }

    public static void getUpdateInfo(Response.Listener<String> listener,
                                     Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl(URL_GET_ANDROID_UPDATE);
        SamsaraStringRequest request = new SamsaraStringRequest(url, listener, errorListener);
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }

    public static void getUserInfo(final int uid, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl(URL_GET_USER);
        SamsaraStringRequest request = new SamsaraStringRequest(url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", Integer.toString(uid));
                return params;
            }
        };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }

    public static void getFriendList(final int uid, final int type, Response.Listener<String> listener,
                                     Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl(getRelationRequestURL(uid, type));
        SamsaraStringRequest request = new SamsaraStringRequest(url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", Integer.toString(uid));
                return params;
            }
        };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }

    public static String getRelationRequestURL(final int uid, final int type) {
        switch (type) {
            case 1:
                return URL_GET_FRIEND + "/" + uid + ".json";
            case 2:
                return URL_GET_FANS + "/" + uid + ".json";
            case 3:
                return URL_GET_FOLLOWING + "/" + uid + ".json";
            default:
                return "";
        }
    }

    public static void getUserDetail(final int uid, Response.Listener<String> listener,
                                     Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl(URL_GET_USER_DETAIL);
        SamsaraStringRequest request = new SamsaraStringRequest(url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", Integer.toString(uid));
                return params;
            }
        };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }

    public static void uploadLog(String data, Response.Listener<String> listener,
                                 Response.ErrorListener errorListener) {
        uploadLog(data, "1", listener, errorListener);
    }

    private static void uploadLog(final String data, final String report, Response.Listener<String> listener,
                                  Response.ErrorListener errorListener) {
        String login_url = ApiRequestClient.getAbsoluteApiUrl(URL_UPLOAD_LOG);
        SamsaraStringRequest request = new SamsaraStringRequest(Request.Method.POST, login_url,
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("data", data);
                params.put("report", report);
                return params;
            }
        };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("POST => " + login_url);
    }

    // common functions

    public static void setCookie(String cookie) {
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
