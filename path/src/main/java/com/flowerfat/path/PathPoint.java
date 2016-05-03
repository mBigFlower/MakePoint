package com.flowerfat.path;

/**
 * Created by 明明大美女 on 2016/4/30.
 */

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

    @Override
    public String toString() {
        return "PathPoint{" +
                "x=" + x +
                ", y=" + y +
                ", isDown=" + isDown +
                '}';
    }
}
