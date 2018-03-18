package com.flowerfat.makepoint.utils;

import android.view.View;

/**
 * Created by Administrator on 2015/9/19.
 *
 * 这个类，其实也没什么简化的，全当作笔记了吧，以后万一忘了呢？。。。
 * 突然发现这个好鸡肋啊。写了一些了又不想删，回头整理到博客吧
 */
public class AnimUtil {

    /////////////////////////////////////////////////
    // 透明度
    /////////////////////////////////////////////////
    /**
     * 透明度初始化
     * @param view
     * @param state
     */
    public static void alphaInit(View view, int state){
        view.setAlpha(state);
    }

    /**
     * 透明度动画
     * @param view
     * @param alpha
     * @param duration
     * @param startDelay
     */
    public static void alpha(View view, int alpha, int duration, int startDelay){
        view.animate().alpha(alpha).setDuration(duration).setStartDelay(startDelay);
    }

    /////////////////////////////////////////////////
    // 位置 这个是通用接口
    /////////////////////////////////////////////////

    /**
     * 位移初始化
     * @param view
     * @param transX
     * @param transY
     */
    public static void transInit(View view, int transX, int transY){
        view.animate().translationX(transX).translationY(transY);
    }

    /**
     * 位移  动画
     * @param view
     * @param transX
     * @param transY
     * @param duration
     * @param startDelay
     */
    public static void translate(View view, int transX, int transY, int duration, int startDelay){
        view.animate().translationX(transX).translationY(transY).setDuration(duration).setStartDelay(startDelay);
    }

    /////////////////////////////////////////////////
    // 位置 分别X,Y
    /////////////////////////////////////////////////
    public static void translateX(View view, int transX, int duration, int startDelay){
        view.animate().translationX(transX).setDuration(duration).setStartDelay(startDelay);
    }
    public static void translateY(View view, int transY, int duration, int startDelay){
        view.animate().translationY(transY).setDuration(duration).setStartDelay(startDelay);
    }

}
