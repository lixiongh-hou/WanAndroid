package com.example.mvpbase.annotation;

import androidx.fragment.app.Fragment;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 12:07
 * BindLayoutRes 注解工具类
 */
public final class BindLayoutResUtil {

    private BindLayoutResUtil() {
    }

    /**
     * 获取布局ID
     *
     * @param obj Activity or Fragment 对象
     * @return 布局ID
     */
    public static int getLayoutId(Object obj) {
        if (null != obj) {

            if (obj instanceof android.app.Activity || obj instanceof Fragment) {

                Class<?> cls = obj.getClass();
                if (cls.isAnnotationPresent(BindLayoutRes.class)) {
                    return cls.getAnnotation(BindLayoutRes.class).value();
                }
                throw new RuntimeException("请在 Activity 或 Fragment 加上 @BindLayoutRes(R.layout.xxx) 注解");
            }
            throw new RuntimeException("BindLayoutResUtil.getLayoutId(Object obj)方法中，obj 必须是 Activity 或 Fragment");
        }
        throw new RuntimeException("BindLayoutResUtil.getLayoutId(Object obj)方法中，obj 不能为 null");
    }

}
