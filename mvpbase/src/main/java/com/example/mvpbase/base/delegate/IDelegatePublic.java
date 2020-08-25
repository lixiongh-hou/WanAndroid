package com.example.mvpbase.base.delegate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 13:53
 * 给外部调用的方法
 */
public interface IDelegatePublic {
    /**
     * 获取Handler
     *
     * @param callback 回调
     * @return Handler
     */
    Handler getHandler(Handler.Callback callback);

    /**
     * 清除Handler
     */
    void clearHandler();

    /**
     * 显示加载弹出框
     * @param context
     * @param msg
     */
    void showLoadingDialog(Context context, String msg);

    /**
     * 显示加载弹出框
     * @param context
     */
    void showLoadingDialog(Context context);

    /**
     * 关闭加载弹出框
     */
    void dismissLoadingDialog();

    /**
     * 跳Activity
     * @param activity
     * @param cls
     * @param mBundle
     * @param requestCode
     */
    void gotoActivity(Activity activity, Class<? extends Activity> cls, @Nullable Bundle mBundle, int requestCode);

    /**
     * 跳Activity
     * @param activity
     * @param cls
     * @param mBundle
     */
    void gotoActivity(Activity activity, Class<? extends Activity> cls, @Nullable Bundle mBundle);

    /**
     * 跳Activity
     * @param activity
     * @param cls
     * @param requestCode
     */
    void gotoActivity(Activity activity, Class<? extends Activity> cls, int requestCode);

    /**
     * 跳Activity
     * @param activity
     * @param cls
     */
    void gotoActivity(Activity activity, Class<? extends Activity> cls);

    /**
     * 显示Toast
     *
     * @param message
     */
    void showToast(@NonNull String message);
}
