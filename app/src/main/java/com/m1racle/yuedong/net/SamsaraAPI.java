package com.m1racle.yuedong.net;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.m1racle.yuedong.util.crypt.MD5Util;

/**
 * Yuedong App Common Library
 * Samsara WebService API for Yuedong App
 * @author sczyh30
 * @since 0.1
 * 注：由于主办方没有提供云服务器，因此测试只能在线下进行
 * 在进行重要操作（注册、登录）等等的时候，一定要合理对数据加密（这里默认设计为MD5加密）
 * 若服务器后端为PHP，则切记防止SQL注入及XSS攻击
 * 若服务器后端为Java，则要合理利用持久层框架（如Mybatis）以及合理的设计模式（AOP，IoC等等的）
 * 设置拦截器拦截恶意请求，并进行记录
 * 在进行并发操作时，应保证各线程之间数据的安全
 * 我们在这里设计了REST风格的接口，配合Spring MVC是极好的
 * 返回数据应为加密的JSON数据
 */
public class SamsaraAPI {

    /**
     * login action
     * use MD5 crypt by default
     * @param username Username
     * @param password Password
     * @param handler Http Handler
     */
    public static void login(String username, String password,
                             AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("pwd", MD5Util.MD5(password));
        params.put("keep_login", 1);
        //String login_url = "action/api/login";
        String login_url = "action/api/login.json";
        ApiHttpClient.post(login_url, params, handler);
    }


    /**
     * upload log action
     * @param data some entries
     * @param report report code
     * @param handler Http Handler
     */
    private static void uploadLog(String data, String report,
                                  AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("data",data);
        params.put("report",report);
        String log_url = "action/api/upload/log";
        ApiHttpClient.post(log_url, params, handler);

    }

    public static void uploadLog(String data, AsyncHttpResponseHandler handler) {
        uploadLog(data, "1", handler);
    }

    public static void getUserDetail(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", uid);
        //ApiHttpClient.get("action/api/get/user_detail", params, handler);
        ApiHttpClient.get("action/api/get/user_detail.json", handler);
    }

    public static void getUserInfo(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", uid);
        ApiHttpClient.get("action/api/get/user.json", handler);
    }

    /**
     * The action of getting the latest version of the app
     * @param handler Http Handler
     */
    public static void getUpdateInfo(AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("action/api/get/app_version_android.json",handler);
    }

    public static void getLatestMotionActivities(AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("action/api/get/latest_ma.json",handler);
    }

    /**
     * The register action
     * invocation of simple register api
     * this should be more complicated in some security detection
     * @param username username
     * @param password password crypted by MD5
     * @param handler Http Handler
     */
    public static void register(String username, String password,
                                String account, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("username", username);
        params.put("password", MD5Util.MD5(password));
        final String REG_URL = "action/api/register";
        ApiHttpClient.post(REG_URL, params, handler);
    }

}
