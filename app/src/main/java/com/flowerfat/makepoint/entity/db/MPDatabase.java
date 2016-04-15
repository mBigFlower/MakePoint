package com.flowerfat.makepoint.entity.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by 明明大美女 on 2016/4/9.
 */
@Database(name = MPDatabase.NAME, version = MPDatabase.VERSION)
public class MPDatabase {
    //数据库名称
    public static final String NAME = "MPDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}
