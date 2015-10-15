package com.m1racle.yuedong.net;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.m1racle.yuedong.R;
import com.m1racle.yuedong.cache.BitmapCache;
import com.m1racle.yuedong.util.LogUtil;

/**
 * Yuedong App Common Library
 * Samsara API Bitmap Request Client
 * uses the Volley library
 * @author sczyh30
 * @since 0.6.32
 */
public class BitmapRequestClient {

    private static final String PIC_SERVER_URL = "http://192.168.95.1/sandbox/res/img/";

    public static ImageLoader imageLoader = new ImageLoader(
            ApiRequestClient.getQueue(), new BitmapCache());

    public static void send(ImageView iv, String resId) {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv,
                R.mipmap.pic_load_wait, R.mipmap.pic_load_failed);
        imageLoader.get(getPictureUrl(resId), imageListener);
    }

    public static void send(ImageView iv, String resId, int maxWidth, int maxHeight) {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv,
                R.mipmap.pic_load_wait, R.mipmap.pic_load_failed);
        String url = getPictureUrl(resId);
        imageLoader.get(url, imageListener, maxWidth, maxHeight);
        LogUtil.log("GET PIC => " + url);
    }

    public static String getPictureUrl(String resId) {
        return PIC_SERVER_URL + resId + ".png";
    }
}
