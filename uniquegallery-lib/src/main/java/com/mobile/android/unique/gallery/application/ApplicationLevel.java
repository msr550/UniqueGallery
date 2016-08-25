package com.mobile.android.unique.gallery.application;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by MSR on 8/12/2016.
 */
public class ApplicationLevel {

    public static Context applicationContext = null;
    public static volatile Handler applicationHandler = null;
    public static Point displaySize = new Point();
    public static float density = 1;

    /**
     * Used to convert DP value
     *
     * @param value actual value
     * @return return result after conversion
     */
    public static int dp(float value) {
        return (int) Math.ceil(density * value);
    }

    /**
     * Used get the display size
     */
    public static void checkDisplaySize() {
        try {
            WindowManager manager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    if (android.os.Build.VERSION.SDK_INT < 13) {
                        displaySize.set(display.getWidth(), display.getHeight());
                    } else {
                        display.getSize(displaySize);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It used to instial the display sizes
     *
     * @param context
     */
    public void onCreate(Context context) {
        applicationContext = context;
        applicationHandler = new Handler(applicationContext.getMainLooper());
        checkDisplaySize();
        density = applicationContext.getResources().getDisplayMetrics().density;
    }
}
