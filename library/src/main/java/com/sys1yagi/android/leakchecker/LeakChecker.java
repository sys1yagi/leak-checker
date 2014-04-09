package com.sys1yagi.android.leakchecker;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LeakChecker {

    private final static String TAG = LeakChecker.class.getSimpleName();

    private static ILogger sLogger = new DefaultLogger();

    private static List<WeakReference<Object>> sLeakHolder
            = new ArrayList<WeakReference<Object>>();

    public static void setLogger(ILogger logger) {
        sLogger = logger;
    }

    private static void print(String message) {
        if (sLogger != null) {
            sLogger.print(TAG, message);
        }
    }

    public static void addLeakChecker(Object object) {
        print("add checker:" + object);
        sLeakHolder.add(new WeakReference<Object>(object));
    }

    public static synchronized void sweep() {
        List<WeakReference<Object>> notYetReleased
                = new ArrayList<WeakReference<Object>>();
        int releasedCount = 0;
        for (WeakReference<Object> holder : sLeakHolder) {
            if (holder.get() != null) {
                notYetReleased.add(holder);
            } else {
                releasedCount++;
            }
        }
        print("sweep:" + releasedCount);
        sLeakHolder = notYetReleased;
    }

    public static void dump() {
        System.gc();
        sweep();

        for (WeakReference<Object> holder : sLeakHolder) {
            if (holder.get() != null) {
                print("not yet released:" + holder.get());
            }
        }
    }
}
