package com.m1racle.yuedong.net;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.m1racle.yuedong.AppContext;
import com.m1racle.yuedong.util.LogUtil;

/**
 * Yuedong App Common Library
 * Samsara API HttpClient
 * uses the Volley library
 * the old client will be replaced by this
 * @author sczyh30
 * @since 0.6
 * @see YuedongAPI api implements
 */
public class ApiRequestClient {

    //public final static String HOST = "192.168.95.1";
    //private final static String API_URL = "http://192.168.95.1/sandbox/";

    public final static String HOST = "www.sczyh30.com";
    private final static String API_URL = "http://www.sczyh30.com/sandbox/";

    //volley net object
    public static RequestQueue mQueue = Volley.newRequestQueue(AppContext.getContext());

    public ApiRequestClient() {}

    public static RequestQueue getQueue() {
        return mQueue;
    }

    public static void setQueue(RequestQueue queue) {
        mQueue = queue;
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = API_URL + partUrl;
        LogUtil.log("BASE_CLIENT", "request: " + url);
        return url;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void send(Request<?> request) {
        mQueue.add(request);
    }

    public static void cancel(String tag) {
        mQueue.cancelAll(tag);
    }

}
