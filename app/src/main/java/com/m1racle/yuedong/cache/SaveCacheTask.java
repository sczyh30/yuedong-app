package com.m1racle.yuedong.cache;

import android.content.Context;
import android.os.AsyncTask;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Cache Save Task
 */
public class SaveCacheTask extends AsyncTask<Void, Void, Void> {
    private final WeakReference<Context> mContext;
    private final Serializable object;
    private final String key;

    public SaveCacheTask(Context context, Serializable object, String key) {
        mContext = new WeakReference<>(context);
        this.object = object;
        this.key = key;
    }

    @Override
    protected Void doInBackground(Void... params) {
        CacheManager.saveObject(mContext.get(), object, key);
        return null;
    }
}
