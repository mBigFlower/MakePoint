package com.flowerfat.makepoint;

import android.app.Application;

import com.flowerfat.makepoint.Utils.GreenDaoUtil;

/**
 * Created by 明明大美女 on 2015/9/22.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this; // 单例
        // 数据库初始化
        GreenDaoUtil.getInstance().setupDatabase(this, "db-points");
    }

    public static MyApplication getInstance() {
        return myApplication;
    }
}
