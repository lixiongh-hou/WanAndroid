package com.example.mvpbase.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author: 雄厚
 * Date: 2020/8/27
 * Time: 10:37
 */
public class VersionUtil {
    private VersionUtil() {
        // 这个类不能实例化
    }

    /**
     * 获取App版本名
     */
    public static String getVersionName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /**
     * 获取App版本号
     */
    public static int getVersionCode(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
