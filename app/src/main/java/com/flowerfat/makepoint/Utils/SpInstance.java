package com.flowerfat.makepoint.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.flowerfat.makepoint.MyApplication;
import com.flowerfat.makepoint.PointColor;

/**
 * Created by 明明大美女 on 2015/10/10.
 *
 * 一直觉得每次使用sp都挺麻烦，一直想弄一个单例。
 * 不知道这样做会对性能有什么影响，反正我是弄了。
 * 先爽着
 *
 */
public class SpInstance {

    private static SpInstance spInstance;
    private SharedPreferences sp;
    private Editor editor;

    public SpInstance() {
        sp = MyApplication.getInstance().getSharedPreferences("makePoint", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SpInstance get() {
        if (spInstance == null) {
            spInstance = new SpInstance();
        }
        return spInstance;
    }

    /////////////////////////////////////////////////////////////////

    public void pString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public String gString(String key) {
        return sp.getString(key, null);
    }

    /////////////////////////////////////////////////////////////////

    public void initOneDayPoint(){
        editor.putString("pColor" + PointColor.COLOR_1, "level top");
        editor.putString("pColor" + PointColor.COLOR_2, "蓝色");
        editor.putString("pColor" + PointColor.COLOR_3, "level three");
        editor.putString("pColor" + PointColor.COLOR_4, "level bottom");
        editor.commit();
    }

}
