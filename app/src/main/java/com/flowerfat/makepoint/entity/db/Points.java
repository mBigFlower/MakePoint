package com.flowerfat.makepoint.entity.db;

import com.flowerfat.makepoint.utils.DateUtil;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 明明大美女 on 2016/4/8.
 */
@ModelContainer
@Table(database = MPDatabase.class)
public class Points extends BaseModel {

    public Points() {
        date = DateUtil.getDate(DateUtil.YMD);
    }

    public Points(Point point1, Point point2, Point point3, Point point4) {
        this.date = DateUtil.getDate(DateUtil.YMD);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }

    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String date;
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    private Point point1;
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    private Point point2;
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    private Point point3;
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    private Point point4;

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
}
