package com.flowerfat.makepoint.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.flowerfat.makepoint.Constants.PointColor;
import com.flowerfat.makepoint.entity.db.PointManager;
import com.flowerfat.makepoint.MyApplication;
import com.flowerfat.makepoint.entity.db.Point;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 明明大美女 on 2015/10/10.
 */
public class Utils {

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        int[] screens;
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        screens = new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    public static int getStateHeight(Activity activity){
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

    public static Date yesteday(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Point color2Point(int color){
        if(color == PointColor.COLOR_1) {
            return PointManager.get().getPoint1();
        } else if(color == PointColor.COLOR_2) {
            return PointManager.get().getPoint2();
        } else if(color == PointColor.COLOR_3) {
            return PointManager.get().getPoint3();
        } else{
            return PointManager.get().getPoint4();
        }
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dp2px(float dipValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static boolean isEnglish(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isEnglish = pattern.matcher(str);
        if( !isEnglish.matches() ){
            return false;
        }
        return true;
    }

    public static void closeSoftInput(Context context, View focusingView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusingView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

}
