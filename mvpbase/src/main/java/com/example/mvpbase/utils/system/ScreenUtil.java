package com.example.mvpbase.utils.system;

import android.content.res.Resources;
import android.os.Build;
import android.view.View;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 9:48
 * 屏幕工具类
 */
public class ScreenUtil {

    private ScreenUtil() {
        // 这个类不能实例化
    }

    /**
     * 屏幕宽度
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取状态栏高度
     */
    private static int getStatusBarHeight() {
        int barHeight;
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            barHeight = Resources.getSystem().getDimensionPixelSize(resId);
        } else {
            barHeight = DensityUtil.dp2px(24);
        }
        return barHeight;
    }

    /**
     * 获取状态栏高度(实际使用)
     */
    public static int getRealStatusBarHeight() {
        int barHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            barHeight = ScreenUtil.getStatusBarHeight();
        }
        return barHeight;
    }

    public static void fitsSystemWindow(View view) {
        if (null != view) {

            int barHeight = ScreenUtil.getRealStatusBarHeight();

            int left = view.getPaddingLeft();
            int top = view.getPaddingTop() + barHeight;
            int right = view.getPaddingRight();
            int bottom = view.getPaddingBottom();
            view.setPadding(left, top, right, bottom);
        }
    }

}
