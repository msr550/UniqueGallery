package com.mobile.android.unique.gallery.utils;

import android.util.Log;

/**
 * Created by MSR on 8/9/2016.
 */
public class Logger {
    private static final String TAG = "==UniqueGallery==";

    /**
     * Used to display the logs which is used for debugging
     *
     * @param string log value
     */
    public static void getInfo(String string) {
        Log.i(TAG, "===" + string + "===");
    }
}
