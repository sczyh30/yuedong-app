package com.m1racle.yuedong.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Yuedong app
 * Bitmap cache class (support Volley)
 * uses LRU Cache
 * @author sczyh30
 */
public class BitmapCache implements ImageLoader.ImageCache {

    //max : 2048 kb
    private static final int MAX_SIZE = 1024 * 1024 * 2;

    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        mCache = new LruCache<String, Bitmap>(MAX_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
