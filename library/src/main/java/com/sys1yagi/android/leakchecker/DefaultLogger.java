package com.sys1yagi.android.leakchecker;

import android.util.Log;

public class DefaultLogger implements ILogger {

    @Override
    public void print(String tag, String message) {
        Log.d(tag, message);
    }
}
