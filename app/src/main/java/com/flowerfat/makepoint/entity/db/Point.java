package com.flowerfat.makepoint.entity.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 明明大美女 on 2016/3/20.
 */
@ModelContainer
@Table(database = MPDatabase.class)
public class Point extends BaseModel {

    @PrimaryKey(autoincrement = true)
    int id;
    @Column
    String title;
    @Column
    String doneTime;
    @Column
    String date;
    @Column
    String imgPath;

    public Point(){
        title = "";
        this.save();
    }

    public Point(String date) {
        this.date = date;
        title = "";
        this.save();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isDone=" + doneTime +
                ", date=" + date +
                ", imgPath=" + imgPath +
                '}';
    }
}
