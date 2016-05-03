package com.flowerfat.makepoint.Constants;

import com.flowerfat.makepoint.entity.db.Point;
import com.flowerfat.makepoint.entity.db.Point_Table;
import com.flowerfat.makepoint.entity.db.Points;
import com.flowerfat.makepoint.utils.DateUtil;
import com.flowerfat.makepoint.utils.NotificationUtil;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by 明明大美女 on 2016/3/23.
 */
public class PointManager {

    public Points points;
    public Point point1, point2, point3, point4;

    public static PointManager mInstance;

    public static PointManager get() {
        if (mInstance == null) {
            mInstance = new PointManager();
        }
        return mInstance;
    }

    /**
     * 这里有两种方式，不晓得用哪种了。。。
     */
    public void onResume() {
        if (DateUtil.isNewDay()) {
            String date = DateUtil.getDate(DateUtil.YMD);
            point1 = new Point(date);
            point2 = new Point(date);
            point3 = new Point(date);
            point4 = new Point(date);
            points = new Points(point1, point2, point3, point4);
            points.save();
            NotificationUtil.refresh();
        } else if (point1 == null || point2 == null || point3 == null || point4 == null) {
            // TODO 数据库取出来
            String date = DateUtil.getDate(DateUtil.YMD);
            List<Point> pointList = new Select().from(Point.class)
                    .where(Point_Table.date.eq(date))
                    .queryList();
            point1 = pointList.get(0);
            point2 = pointList.get(1);
            point3 = pointList.get(2);
            point4 = pointList.get(3);
        }
    }

    public void onDestroy() {
        points = null;
        point1 = null;
        point2 = null;
        point3 = null;
        point4 = null;
    }

    public Point getPoint1() {
        if (point1 == null) {
            if (DateUtil.isNewDay()) {
                point1 = new Point();
            } else {
                // TODO 数据库取出来
            }
        }
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public Point getPoint4() {
        return point4;
    }
}
