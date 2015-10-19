package com.m1racle.yuedong.net.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.m1racle.yuedong.net.ApiRequestClient;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Samsara Basic String Request Object
 * support http headers
 * @author sczyh30
 * @since 0.6.3
 */
public class SamsaraStringRequest extends Request<String> {

    private Response.Listener<String> mListener;

    private Map<String, String> mHeaders = new HashMap<>();
    private String encoding = "UTF-8";
    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public SamsaraStringRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new request with the given method.
     * with http headers
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     * @param headers Custom http headers
     */
    public SamsaraStringRequest(int method, String url, Response.Listener<String> listener,
                                Response.ErrorListener errorListener ,Map<String, String> headers) {
        super(method, url, errorListener);
        mHeaders = headers;
        mListener = listener;
    }
    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public SamsaraStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, encoding);
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
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
