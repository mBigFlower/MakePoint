package com.flowerfat.makepoint.Utils;


import com.flowerfat.makepoint.entity.Points;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 明明大美女 on 2015/9/22.
 *
 * 我特么的，思维好乱。先不管List了，假设一共只有4个Point！
 *
 */
public class FilePlusUtil extends FileUtil {

    public final static String FILEPATH_POINTS = "PointsList";
    private final Gson mGson = new Gson();

    private static FilePlusUtil mInstance;

    public static synchronized FilePlusUtil getInstance() {
        if (mInstance == null)
            mInstance = new FilePlusUtil();
        if(!FileUtil.isExistByName(FilePlusUtil.FILEPATH_POINTS)){
            FileUtil.write("", FilePlusUtil.FILEPATH_POINTS);
        }
        return mInstance;
    }

    public Points getPoints(){
        Points points =  mGson.fromJson(FileUtil.read(FILEPATH_POINTS), Points.class);
        if(points == null)
            points = new Points();
        return points;
    }

    public void setPointsList(Points points){

    }

    public List<Points> getPointsList(){

        return null ;
    }

//    public List<Points> getPointsList() {
//        String data = read(FILEPATH_POINTS);
//        return mGson.fromJson(data, new TypeToken<List<Points>>() {
//        }.getType());
//    }

}
