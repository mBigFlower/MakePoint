package com.flowerfat.makepoint.entity.db;

import com.flowerfat.makepoint.utils.DateUtil;

/**
 * Created by 明明大美女 on 2016/4/8.
 */
public class OneDayPoints {

    private int id;
    private String date;
    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;

    public OneDayPoints() {
        date = DateUtil.getDate(DateUtil.YMD);
        this.point1 = new Point(1, date);
        this.point2 = new Point(2, date);
        this.point3 = new Point(3, date);
        this.point4 = new Point(4, date);
    }

    public OneDayPoints(Point point1, Point point2, Point point3, Point point4) {
        this.date = DateUtil.getDate(DateUtil.YMD);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    public Point getPoint4() {
        return point4;
    }

    public void setPoint4(Point point4) {
        this.point4 = point4;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void onDestroy() {
        point1 = point2 = point3 = point4 = null;
    }
}
