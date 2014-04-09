package com.sys1yagi.android.leakchecker.sample.views;

import com.sys1yagi.android.leakchecker.ILogger;

import android.widget.TextView;

public class TextViewMonitorLogger implements ILogger {

    private TextView target;

    public TextViewMonitorLogger(TextView target) {
        this.target = target;
    }

    @Override
    public void print(String tag, String message) {
        target.setText(message);
    }
}
