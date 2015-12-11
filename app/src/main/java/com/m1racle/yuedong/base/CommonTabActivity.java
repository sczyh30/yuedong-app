package com.m1racle.yuedong.base;

import android.os.Bundle;
import android.app.Activity;

import com.m1racle.yuedong.R;

public class CommonTabActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tab);
    }

}
