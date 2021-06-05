package com.hy.backstagerender;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import java.lang.reflect.Field;

import me.ele.lancet.base.Origin;
import me.ele.lancet.base.This;
import me.ele.lancet.base.annotations.Insert;
import me.ele.lancet.base.annotations.TargetClass;

public class FragmentController {

    /**
     * Stop synchronizing paused status to all fragments.
     */
    @TargetClass("android.support.v4.app.FragmentController")
    @Insert("dispatchPause")
    public void dispatchPause() {
    }

    /**
     * Stop synchronizing stopped status to all fragments.
     */
    @TargetClass("android.support.v4.app.FragmentController")
    @Insert("dispatchStop")
    public void dispatchStop() {
    }

    /**
     * Swallow MSG_REALLY_STOPPED message.
     */
    @TargetClass("android.support.v4.app.FragmentActivity")
    @Insert("onStop")
    protected void onStop() {
        Origin.callVoid();
        try {
            Handler handler = ((Handler) This.getField("mHandler"));
            Field stopped = FragmentActivity.class.getDeclaredField("MSG_REALLY_STOPPED");
            stopped.setAccessible(true);
            Object o = stopped.get(null);
            handler.removeMessages((Integer) o);
        } catch (Throwable ignore) {
        }
    }

}
