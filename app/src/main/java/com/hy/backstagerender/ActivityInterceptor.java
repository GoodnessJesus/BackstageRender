package com.hy.backstagerender;

import android.app.Activity;
import android.os.IBinder;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActivityInterceptor {

    private static Class<?> sWindowManagerGlobal;
    private static Object sManager;

    static {
        try {
            sWindowManagerGlobal = Class.forName("android.view.WindowManagerGlobal");
            Method getInstance = sWindowManagerGlobal.getDeclaredMethod("getInstance");
            sManager = getInstance.invoke(null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void onStop(@NonNull Activity activity) {
        try {
            Field mToken = Activity.class.getDeclaredField("mToken");
            mToken.setAccessible(true);
            Object rawToken = mToken.get(activity);
            setStoppedState(((IBinder) rawToken), false);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset stopped state to false in order to perform traversals even current window is invisible.
     */
    public static void setStoppedState(IBinder token, boolean stopped) {
        try {
            Method setStoppedState = sWindowManagerGlobal.getDeclaredMethod("setStoppedState", IBinder.class, boolean.class);
            setStoppedState.invoke(sManager, token, stopped);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
