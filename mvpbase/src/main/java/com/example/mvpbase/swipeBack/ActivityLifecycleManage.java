package com.example.mvpbase.swipeBack;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.LinkedList;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:17
 */
public class ActivityLifecycleManage implements Application.ActivityLifecycleCallbacks  {
    /**锁*/
    private static final Object lockObj = new Object();
    /**管理对象单例*/
    private volatile static ActivityLifecycleManage mLifecycleManage;

    /**
     * 获取单例
     */
    public static ActivityLifecycleManage getInstance() {
        if (null == mLifecycleManage) {
            synchronized (lockObj) {
                if (null == mLifecycleManage) {
                    mLifecycleManage = new ActivityLifecycleManage();
                }
            }
        }
        return mLifecycleManage;
    }

    /**保存Activity*/
    private LinkedList<Activity> actList;

    /**
     * 构造方法
     */
    private ActivityLifecycleManage() {
        actList = new LinkedList<>();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        // 添加Activity到集合
        if (null == actList) {
            actList = new LinkedList<>();
        }
        actList.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        actList.remove(activity);
        if (actList.size() == 0) {
            actList = null;
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 获取当前Activity
     */
    public Activity getLatestActivity() {
        if (null != actList && actList.size() > 0) {
            return actList.getLast();
        }
        return null;
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity getPreviousActivity() {
        if (null != actList && actList.size() >= 2) {
            return actList.get(actList.size() - 2);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------
    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity) {
            actList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> clazz) {
        if (null != clazz) {
            try {
                for (Activity activity : actList) {
                    if (clazz.equals(activity.getClass())) {
                        // 结束指定的Activity
                        finishActivity(activity);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, count = actList.size(); i < count; i++) {
            if (null != actList.get(i)) {
                actList.get(i).finish();
            }
        }
        actList.clear();
    }

    /**
     * 返回到指定的Activity
     */
    public void returnToActivity(Class<?> clazz) {
        while (actList.size() != 0) {
            Activity activity = actList.getLast();
            if (clazz.equals(activity.getClass())) {
                break;
            } else {
                // 结束指定的Activity
                finishActivity(activity);
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context mContext) {
        try {
            // 结束所有Activity
            finishAllActivity();
            ActivityManager mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            mActivityManager.restartPackage(mContext.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startHome(Context context){
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }
}
