package com.m1racle.yuedong.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.net.request.SamsaraStringRequest;
import com.m1racle.yuedong.util.LogUtil;
import com.m1racle.yuedong.util.crypt.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Yuedong App Common Library
 * Samsara WebService API for Yuedong App
 * updated since 0.6
 * support Volley
 * @author sczyh30
 * @since 0.6
 * 注：由于主办方没有提供云服务器，因此测试只能在线下进行
 * 在进行重要操作（注册、登录）等等的时候，一定要合理对数据加密（这里默认设计为MD5加密）
 * 切记防止SQL注入及XSS攻击
 * 若服务器后端为Java，则要合理利用持久层框架（如Mybatis）以及合理的设计模式（AOP，IoC等等的）
 * 设置拦截器拦截恶意请求，并进行记录
 * 在进行并发操作时，应保证各线程之间数据的安全
 * 我们在这里设计了REST风格的接口，配合Spring MVC是极好的，Flask也不错
 * 返回数据应为加密的JSON数据
 */
public class YuedongAPI {

    private static String appCookie;

    /**
     * Login API method (POST)
     * @param username Username
     * @param password Password (not hashed)
     * @param listener Response.Listener
     * @param errorListener Response.ErrorListener
     */
    public static void login(final String username, final String password, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        String login_url = ApiRequestClient.getAbsoluteApiUrl("action/api/login.json");
        SamsaraStringRequest request = new SamsaraStringRequest(Request.Method.POST, login_url,
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", MD5Util.MD5(password));
                return params;
            }
        };
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("POST => " + login_url);
    }

    public static void getLatestMotionActivities (Response.Listener<String> listener,
                                                 Response.ErrorListener errorListener) {
        String url = ApiRequestClient.getAbsoluteApiUrl("action/api/get/latest_ma.json");
        SamsaraStringRequest request = new SamsaraStringRequest(url, listener, errorListener);
        request.setDefaultHeaders(appCookie);
        ApiRequestClient.send(request);
        LogUtil.log("GET => " + url);
    }


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
