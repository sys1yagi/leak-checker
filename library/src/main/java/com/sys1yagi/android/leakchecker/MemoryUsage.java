package com.sys1yagi.android.leakchecker;

import android.os.Handler;


public class MemoryUsage {

    private final static String TAG = MemoryUsage.class.getSimpleName();

    private final static int KB = 1024;

    private static boolean isStarted = false;

    private static ILogger sLogger = null;

    public static void setLogger(ILogger logger) {
        MemoryUsage.sLogger = logger;
    }

    private static void print(String message) {
        if (sLogger != null) {
            sLogger.print(TAG, message);
        }
    }

    public static void startPrintMemoryUsage() {
        startPrintMemoryUsage(new DefaultLogger());
    }

    public static void startPrintMemoryUsage(ILogger logger) {
        isStarted = true;
        setLogger(logger);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isStarted) {
                    print();
                    handler.postDelayed(this, 3000);
                }
            }
        };
        runnable.run();
    }

    public static void stopPrintMemoryUsage() {
        isStarted = false;
    }

    public static void print() {
        print("total/max " + totalMemoryHuman() + "/" + maxMemoryHuman());
    }

    public static String maxMemoryHuman() {
        return maxMemory() / KB + "KB";
    }

    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static String totalMemoryHuman() {
        return totalMemory() / KB + "KB";
    }

    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
