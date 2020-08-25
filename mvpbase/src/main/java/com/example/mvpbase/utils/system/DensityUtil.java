package com.example.mvpbase.utils.system;

import android.content.res.Resources;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 9:49
 * dip,px,sp相互转化
 */
public class DensityUtil {
    private DensityUtil() {
        // 这个类不能实例化
    }

    /**
     * 获取密度
     */
    public static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * dip --> px
     */
    public static int dp2px(float dp) {
        return (int) (dp * DensityUtil.getDensity() + 0.5F);
    }

    /**
     * px --> dp
     */
    public static int px2dp(float px) {
        return (int) (px / DensityUtil.getDensity() + 0.5F);
    }


    /**
     * sp --> px
     */
    public static int sp2px(float sp) {
        float sd = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (sp * sd + 0.5F);
    }

    /**
     * px --> sp
     */
    public static int px2sp(float px) {
        float sd = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (px / sd + 0.5F);
    }
}
