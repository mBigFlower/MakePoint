package com.flowerfat.makepoint.Utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public static boolean ifStepDay(Context context) {
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


}
