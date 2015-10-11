package com.m1racle.yuedong.adapter;

import android.os.Bundle;

public final class ViewPageInfo {

	public final String tag;
    public final Class<?> cls;
    public final Bundle args;
    public final String title;

    public ViewPageInfo(String title, String tag, Class<?> cls, Bundle args) {
    	this.title = title;
        this.tag = tag;
        this.cls = cls;
        this.args = args;
    }
}