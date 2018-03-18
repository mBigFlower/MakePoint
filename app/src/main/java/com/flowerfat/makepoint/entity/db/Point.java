package com.flowerfat.makepoint.entity.db;

/**
 * Created by 明明大美女 on 2016/3/20.
 */
public class Point {

    int id;
    int level;
    String title;
    String doneTime;
    String date;
    String imgPath;

    public Point(){
        title = "";
    }

    public Point(int level, String date) {
        setLevel(level);
        setDate(date);
        title = "";
    }
    public int getLevel(){return level;}
    public void setLevel(int level){this.level = level;}

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
