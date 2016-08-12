package com.gallery.mobile.uniquegallery;

import android.app.Application;

import com.mobile.android.unique.gallery.application.ApplicationLevel;

/**
 * Created by user on 8/12/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationLevel applicationLevel = new ApplicationLevel();
        applicationLevel.onCreate(this);
    }
}
