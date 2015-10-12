package com.m1racle.yuedong.net.request;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Samsara Basic Request Object
 * @author sczyh30
 * @since 0.6.3
 */
public abstract class SamsaraRequest<T> extends Request<T> {

    protected Map<String, String> mHeaders = new HashMap<>();
    private String encoding = "UTF-8";

    public SamsaraRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public SamsaraRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    protected void setCookie(String cookie) {
        mHeaders.put("Cookie", cookie);
    }

    protected void setHost(String host) {
        mHeaders.put("Host", host);
    }

    protected void setKeepAlive(boolean b) {
        if(b)
            mHeaders.put("Connection", "Keep-Alive");
    }

    protected void setAcceptLanguage() {
        mHeaders.put("Accept-Language", Locale.getDefault().toString());
    }

    protected void setHeader(String a, String b) {
        mHeaders.put(a, b);
    }
}
