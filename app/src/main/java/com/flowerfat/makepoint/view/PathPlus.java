package com.flowerfat.makepoint.view;

import android.graphics.Path;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 明明大美女 on 2016/4/19.
 * 因为Path不能序列化，所以只能记录所有的点，方便下次恢复
 */
public class PathPlus extends Path {

    List<PathPoint> pathPoints = new ArrayList<>();

    public PathPlus() {
    }

    public PathPlus(String pathStr) {
        pathPoints2Path(str2PathPoints(pathStr));
    }

    @Override
    public void reset() {
        super.reset();
        pathPoints.clear();
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        pathPoints.add(new PathPoint(x, y, true));
    }

    @Override
    public void lineTo(float x, float y) {
        super.lineTo(x, y);
        pathPoints.add(new PathPoint(x, y, false));
    }

    public String pathPoints2Str() {
        return new Gson().toJson(pathPoints);
    }

    public List<PathPoint> str2PathPoints(String pathStr) {
        try {
            List<PathPoint> pathPoints = new Gson().fromJson(pathStr, new TypeToken<List<PathPoint>>() {
            }.getType());
            return pathPoints;
        } catch (Exception e) {
            e.printStackTrace();
            return pathPoints;
        }
    }

    public void pathPoints2Path(List<PathPoint> pathPoints) {
        // TODO 这里要做数量的判断哈！！！
        if (pathPoints == null) {
            return;
        }
        if (pathPoints.size() < 3) {
            return;
        }
        for (PathPoint ppt : pathPoints) {
            if(ppt.isDown) {
                moveTo(ppt.x, ppt.y);
            } else {
                lineTo(ppt.x, ppt.y);
            }
        }
    }

    public class PathPoint {
        public float x;
        public float y;
        // 判断这个点，是按下的还是move的
        public boolean isDown;

        public PathPoint(float x, float y, boolean isDown) {
            this.x = x;
            this.y = y;
            this.isDown = isDown;
        }
    }


}
