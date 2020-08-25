package com.example.mvpbase.utils;

import android.view.ViewGroup;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 13:59
 */
public class LayoutParamsUtil {

    public static ViewGroup.LayoutParams getLayoutParams(ViewGroup.LayoutParams params , int width, int height){
        params.width = width;
        params.height = height;
        return params;
    }
}
