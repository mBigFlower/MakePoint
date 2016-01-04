package com.flowerfat.makepoint.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.flowerfat.makepoint.sqlite.DaoMaster;
import com.flowerfat.makepoint.sqlite.DaoSession;
import com.flowerfat.makepoint.sqlite.Point;
import com.flowerfat.makepoint.sqlite.PointDao;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by 明明大美女 on 2015/10/12.
 * 最近尝试使用了 数据库操作库 GreenDao
 * 这里可以算是封装，也可以算是一个使用方法的 mark 吧
 *
 * 这样弄只能是一个 数据库 啊！看来弄成Util不太靠谱
 */
public class GreenDaoUtil {

    private static GreenDaoUtil mGreenDaoUtil;

    private SQLiteDatabase mSQLiteDb;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private String currentDbName ;

    public static GreenDaoUtil getInstance() {
        if (mGreenDaoUtil == null) {
            synchronized (GreenDaoUtil.class) {
                if (mGreenDaoUtil == null) {
                    mGreenDaoUtil = new GreenDaoUtil();
                }
            }
        }
        return mGreenDaoUtil;
    }

    public GreenDaoUtil() {
    }

    /**
     * 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
     * 也就是说我们一辈子只用创建一个 dbName ？
     *
     * @param context Application层？
     * @param dbName  数据库名称
     */
    public void setupDatabase(Context context, String dbName) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);
        mSQLiteDb = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(mSQLiteDb);
        mDaoSession = mDaoMaster.newSession();
    }


    // 上：基础
    ////////////////////////////////////////////////////////////////
    // 下：外部扩展


    public PointDao getPointDao() {
        return mDaoSession.getPointDao();
    }

    public void insertPoint(Point entity) {
        getPointDao().insert(entity);
    }

    public void replacePoint(Point entity){
        getPointDao().insertOrReplace(entity);
    }

    /**
     * 返回所有数据
     * @return
     */
    public List<Point> searchAllPoint() {
        return getPointDao().loadAll();
    }

    public Point getTopPoint(){
        if(getPointDao().loadAll().size() > 0)
            return getPointDao().loadAll().get(0);
        else {
            return null ;
        }
    }
    public Point getBottomPoint(){
        int size = getPointDao().loadAll().size();
        if(size > 0)
            return getPointDao().loadAll().get(size-1);
        else {
            return null ;
        }
    }

    /**
     * 数据的数量
     * @return
     */
    public int getListSize(){
        return getPointDao().loadAll().size();
    }
    /**
     * 根据某个条件查找
     * @param searchText
     * @return
     */
    public List<Point> searchPoint(String searchText) {
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        if (Utils.isEmpty(searchText)) {
            return null;
        } else {
            // Query 类代表了一个可以被重复执行的查询
            Query query = getPointDao().queryBuilder()
                    .where(PointDao.Properties.Point1.eq(searchText))
                    .orderAsc(PointDao.Properties.Date)
                    .build();
            // 查询结果以 List 返回
            return query.list();
        }
    }

    public void deletePoint(long id) {
        getPointDao().deleteByKey(id);
//        getNoteDao().deleteAll();
    }
}
