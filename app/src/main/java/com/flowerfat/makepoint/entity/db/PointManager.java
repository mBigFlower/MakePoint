package com.flowerfat.makepoint.entity.db;

import com.flowerfat.makepoint.utils.DateUtil;
import com.flowerfat.makepoint.utils.FileUtil;
import com.flowerfat.makepoint.utils.NotificationUtil;
import com.github.mikephil.charting.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 若File是我们的数据库
 * 则此类，负责与File交互，例如从File中取数据，把新数据保存到File等
 *
 * Created by 明明大美女 on 2016/3/23.
 */
public class PointManager {

    // 所有数据
    private List<OneDayPoints> mPoints = new ArrayList<>();
    // 当前页面要显示的数据
    private OneDayPoints mOneDayPoints;

    public static PointManager mInstance;

    public static PointManager get() {
        if (mInstance == null) {
            mInstance = new PointManager();
        }
        return mInstance;
    }

    /**
     * 重新加载数据
     */
    public void onResume() {
        initData();
        if (DateUtil.isNewDay()) {
            createOneDayPoints();
            NotificationUtil.refresh();
        }
        else {
            initOneDayPoints();
        }
    }

    private void initData(){
        String data = FileUtil.readFile(FileUtil.FILE_NAME);
        if(data == null || data.isEmpty()){
            mPoints = new ArrayList<>();
        } else {
            mPoints = new Gson().fromJson(data, new TypeToken<List<OneDayPoints>>() {}.getType());
        }
    }


    private void initOneDayPoints(){
        String date = DateUtil.getDate(DateUtil.YMD);
        mOneDayPoints = findOneDayPoints(date);
        if(mOneDayPoints == null){
            createOneDayPoints();
        }
    }

    /**
     * 找某一天的数据
     * @return
     */
    private OneDayPoints findOneDayPoints(String date){
        OneDayPoints oneDayPoints = null;
        for (OneDayPoints item : getAllPoints()) {
            if(date.equals(item.getDate())){
                oneDayPoints = item;
                break;
            }
        }
        return oneDayPoints;
    }


    private void createOneDayPoints() {
        mOneDayPoints = new OneDayPoints();
        mPoints.add(mOneDayPoints);
        save();
    }

    public void save(){
        String data = new Gson().toJson(mPoints);
        FileUtil.writeFile(FileUtil.FILE_NAME, data);
    }

    public void onDestroy() {
        mPoints = null;
        mOneDayPoints.onDestroy();
        mInstance = null;
    }

    public OneDayPoints getOneDayPoints(){
        if(mOneDayPoints == null){
            initOneDayPoints();
        }
        return mOneDayPoints;
    }

    public Point getPoint1() {
        return getOneDayPoints().getPoint1();
    }

    public Point getPoint2() {
        return getOneDayPoints().getPoint2();
    }

    public Point getPoint3() {
        return getOneDayPoints().getPoint3();
    }

    public Point getPoint4() {
        return getOneDayPoints().getPoint4();
    }

    public List<OneDayPoints> getAllPoints() {
        if(mPoints == null){
            initData();
        }
        return mPoints;
    }
}
