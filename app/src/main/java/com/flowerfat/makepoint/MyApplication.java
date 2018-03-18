package com.flowerfat.makepoint;

import android.app.Application;

import com.flowerfat.makepoint.entity.db.PointManager;

import timber.log.Timber;

/**
 * Created by 明明大美女 on 2015/9/22.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this; // 单例

        PointManager.get();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
//            Timber.plant(new CrashReportingTree());
        }
    }

    public static MyApplication getInstance() {
        return myApplication;
    }
}
