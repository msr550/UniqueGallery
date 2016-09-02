package com.mobile.android.unique.gallery.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobile.android.unique.gallery.views.Header;

/**
 * Created by MSR on 8/9/2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected Header header = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
