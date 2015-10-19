package com.m1racle.yuedong.net.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.m1racle.yuedong.net.ApiRequestClient;
import com.m1racle.yuedong.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Samsara Json Request Object
 * support http headers
 * use gson library to resolve json data
 * @author sczyh30
 * @since 0.6.3
 */
public class SamsaraGsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;
    private Map<String, String> mHeaders = new HashMap<>();
    private String encoding = "UTF-8";
    private Gson mGson;

    private Class<T> mClass;

    public SamsaraGsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
    }

    public SamsaraGsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, encoding);
            return Response.success(mGson.fromJson(parsed, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
            LogUtil.log("SamsaraGsonRequest resolve error => " + parsed);
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    public void setCookie(String cookie) {
        mHeaders.put("Cookie", cookie);
    }

    public void setHost(String host) {
        mHeaders.put("Host", host);
    }

    public void setKeepAlive(boolean b) {
        if(b)
            mHeaders.put("Connection", "Keep-Alive");
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }

    public void setAcceptLanguage() {
        mHeaders.put("Accept-Language", Locale.getDefault().toString());
    }

    public void setHeader(String a, String b) {
        mHeaders.put(a, b);
    }

    public void setDefaultHeaders(String cookie) {
        mHeaders.put("Cookie", cookie);
        mHeaders.put("Host", ApiRequestClient.HOST);
        mHeaders.put("Connection", "Keep-Alive");
        mHeaders.put("Accept-Language", Locale.getDefault().toString());
    }
}
