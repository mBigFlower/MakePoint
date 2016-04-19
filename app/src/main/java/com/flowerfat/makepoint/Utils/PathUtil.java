package com.flowerfat.makepoint.utils;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

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
     *
     * @param view
     * @param path
     * @param viewPadding
     * @return
     */
    public static Path suitView(View view, Path path, float viewPadding) {
        float viewW = view.getWidth() - viewPadding * 2;
        float viewH = view.getHeight() - viewPadding * 2;

        return suitWH(path, viewW, viewH);
    }

    public static Path suitWH(Path path, float width, float height) {
        RectF bounds = new RectF();
        path.computeBounds(bounds, false);

        float pathW = bounds.right - bounds.left;
        float pathH = bounds.bottom - bounds.top;
        float scaleW = width / pathW;
        float scaleH = height / pathH;

        Log.i("suitWH", width + " " + height + " " + pathW + " " + pathH + " " + scaleW + " " + scaleH);
        if (scaleH > scaleW) {
            Path _path = scale(path, scaleW);
            pathH = pathH * scaleW;
            _path = trans(_path, 0, (height - pathH) / 2 - bounds.top);
            Log.e("scaleH>scaleW", pathH + " " + bounds.top + " " + ((height - pathH) / 2 - bounds.top));
            return _path;
        } else {
            Path _path = scale(path, scaleH);
            pathW = pathW * scaleH;
            _path = trans(_path, (width - pathW) / 2 - bounds.left, 0);
            return _path;
        }


    }
}
