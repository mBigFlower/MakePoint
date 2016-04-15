package com.flowerfat.makepoint.utils;

import com.flowerfat.makepoint.Constants.SPConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 明明大美女 on 2016/4/13.
 */
public class DateUtil {

    public static final String YMD = "yyyyMMdd";

    public static String getDate(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 自动判断是否是新的一天。使用了我默认的sp存时间
     *
     * @return 是否跨天
     */
    public static boolean isNewDay() {
        String today = getDate(YMD);
        String lastDay = SpInstance.get().gString(SPConstant.KEY_IS_NEW_DAY) ;
        // 如果之前没存lastDay ，或者today与lastDay相同，则没有跨天
        boolean isNotNewDay = today.equals(lastDay);
        if(isNotNewDay) {
            return false ;
        } else {
            SpInstance.get().pString(SPConstant.KEY_IS_NEW_DAY, today) ;
            return true ;
        }
    }
}
