package com.example.mvpbase.utils;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.mvpbase.R;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.system.SPUtil;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 10:53
 */
public class ThemeColorUtil {

    /**
     * 获取主题色
     * @param context
     * @return
     */
    public static int getThemeColor(Context context){
        int defaultColor = ContextCompat.getColor(context, R.color.theme_color);
        int colorTheme = (int) SPUtil.get(ConstantUtil.COLOR, defaultColor);
        if (colorTheme != 0 && Color.alpha(colorTheme) != 255) {
            return defaultColor;
        } else {
            return colorTheme;
        }
    }

    /**
     * 设置主题色
     * @param color 颜色值
     */
    public static void setThemeColor(int color){
        SPUtil.put(ConstantUtil.COLOR, color);
    }

    public static int getTitleColor(Context context){
        int defaultColor = ContextCompat.getColor(context, R.color.theme_color);
        int colorTheme = (int) SPUtil.get(ConstantUtil.COLOR_TITLE, defaultColor);
        if (colorTheme != 0 && Color.alpha(colorTheme) != 255) {
            return defaultColor;
        } else {
            return colorTheme;
        }
    }
    public static void setTitleColor(int color){
        SPUtil.put(ConstantUtil.COLOR_TITLE, color);
    }
    /**
     * 获取主题色下标
     * @return
     */
    public static int getThemeColorIndex(){
        return (int) SPUtil.get(ConstantUtil.COLOR_INDEX, 0);
    }
    public static void setThemeColorIndex(int index){
        SPUtil.put(ConstantUtil.COLOR_INDEX, index);
    }
}
