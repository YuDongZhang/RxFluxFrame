package com.pateo.yudo.radio.util;

public class Log {

    public static final boolean DEBUG = true;

    public static final String TAG = "RadioSample";

    public static void i(String className, String message) {
        android.util.Log.i(TAG, ": [" + className + "]:" + message);
    }

    public static void d(String className, String message) {
        if (DEBUG)
            android.util.Log.d(TAG, ": [" + className + "]:" + message);
    }

    public static void e(String className, String message) {
        android.util.Log.e(TAG, ": [" + className + "]:" + message);
    }

    public static void e(String className, String message, Throwable exception) {
        android.util.Log.e(TAG, ": [" + className + "]:" + message, exception);
    }
}
