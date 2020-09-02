package com.example.wanandroid.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author: 雄厚
 * Date: 2020/8/27
 * Time: 10:01
 */
public class DataCleanManager {

    /**
     * 获取缓存大小
     */
    public static String getTotalCacheSize(Context context){
        long cacheSize = 0;
        try {
            cacheSize = getFolderSize(context.getCacheDir());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清楚缓存
     */
    public static void clearAllCache(Context context){
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 删除文件
     */
    private static boolean deleteDir(File dir){
        if (dir != null && dir.isDirectory()){
            String[] children = dir.list();
            if (children != null) {
                for (String string : children) {
                    boolean success = deleteDir(new File(dir, string));
                    if (!success){
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取文件大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    private static long getFolderSize(File file){
        long size = 0;
        File[] fileList = file.listFiles();
        try {
            if (fileList != null) {
                for (File field : fileList) {
                    // 如果下面还有文件
                    if (field.isDirectory()){
                        size = size + getFolderSize(field);
                    }else {
                        size =  size + field.length();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     */
    private static String getFormatSize(double size){
        double kiloByte = size / 1024;
        if (kiloByte < 1){
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1){
            BigDecimal result1 = new BigDecimal(String.valueOf(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1){
            BigDecimal result2 = new BigDecimal(String.valueOf(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1){
            BigDecimal result3 = new BigDecimal(String.valueOf(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(String.valueOf(teraBytes));
        return (result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB");
    }
}
