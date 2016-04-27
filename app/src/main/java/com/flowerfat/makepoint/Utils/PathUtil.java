package com.flowerfat.makepoint.utils;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import com.flowerfat.makepoint.MyApplication;

/**
 * Created by 明明大美女 on 2016/4/15.
 */
public class PathUtil {

    /**
     * 缩放
     *
     * @param path        待处理path
     * @param scaleFactor 这个值有限定
     * @return
     */
    public static Path scale(Path path, float scaleFactor) {
        if (scaleFactor <= 0) {
            return path;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleFactor, scaleFactor);
        path.transform(matrix);
        return path;
    }

    /**
     * 位移
     *
     * @param path   待处理path
     * @param xTrans 负值向左
     * @param yTrans 负值向上
     * @return
     */
    public static Path trans(Path path, float xTrans, float yTrans) {
        Matrix matrixTrans = new Matrix();
        matrixTrans.setTranslate(xTrans, yTrans);
        path.transform(matrixTrans);
        return path;
    }

    /**
     * path去适配View
     *  如果view的高宽获取不到，则默认来一个
     *
     * @param view
     * @param path
     * @param viewPadding
     * @return
     */
    public static Path suitView(View view, Path path, float viewPadding) {
        if (view.getWidth() == 0) {
            int[] screenSize = Utils.getScreenSize(MyApplication.getInstance());
            float viewW = screenSize[0] / 2;
            float viewH = (screenSize[1] - Utils.dp2px(76)) / 2;
            return suitWH(path, viewW, viewH, viewPadding);
        } else {
            float viewW = view.getWidth();
            float viewH = view.getHeight();
            return suitWH(path, viewW, viewH, viewPadding);
        }
    }

    public static Path suitWH(Path path, float width, float height, float viewPadding) {
        RectF bounds = new RectF();
        path.computeBounds(bounds, false);

        float pathW = bounds.width();
        float pathH = bounds.height();
        float scaleW = (width-viewPadding*2) / pathW;
        float scaleH = (height-viewPadding*2) / pathH;

        Log.i("Path ", "宽度：" + pathW + " 高度：" + pathH);
        Log.i("View ", "宽度：" + width + " 高度：" + height);
        float scale = Math.min(scaleH, scaleW);
        pathW = pathW * scale;
        pathH = pathH * scale;
        Log.i("Path 调整 ", "宽度：" + pathW + " 高度：" + pathH);
        Log.i("Path 边距 ", "左：" + bounds.left + " 上：" + bounds.top);

        trans(path, -bounds.left, -bounds.top);
        scale(path, scale);
        path.computeBounds(bounds, false);

        float transLeft = (width - pathW) / 2;
        float transTop = (height - pathH) / 2;
        trans(path, transLeft, transTop);
        Log.i("Path 边距调整 ", "左：" + bounds.left + " 上：" + bounds.top);
        Log.i("Path 大小调整 ", "宽度：" + bounds.width() + " 上：" + bounds.height());
        return path;
    }
}
