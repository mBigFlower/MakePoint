package com.flowerfat.makepoint.utils;

import android.content.Context;
import android.util.Log;

import com.flowerfat.makepoint.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 明明大美女 on 2015/10/10.
 */
public class Utils {



    public static boolean isEmpty(String text) {
        if (text == null || text.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static Date yesteday(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 自动判断是否是新的一天。使用了我默认的sp存时间
     *
     * @param context
     * @return 是否跨天
     */
    public static boolean ifStepDay(Context context) {        Log.d("datecheck", "ifStepDay");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(new Date());
        String lastDay = SpInstance.get().gString("lastDay") ;
        // 如果之前没存lastDay ，或者today与lastDay相同，则没有跨天
        if (lastDay == null) {
            SpInstance.get().pString("lastDay", today);
            return false;
        } else if (today.equals(lastDay)) {
            return false;
        } else {
            SpInstance.get().pString("lastDay", today);
            return true;
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

}
