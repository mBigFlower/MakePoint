package com.flowerfat.makepoint.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by 明明大美女 on 2015/9/19.
 */
public class ScreenUtil {
    /**
     * 获取屏幕宽高
     *
     * @return
     */
    public static int[] getScreenSize(Context context) {
        int[] screens;
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        screens = new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }





}
