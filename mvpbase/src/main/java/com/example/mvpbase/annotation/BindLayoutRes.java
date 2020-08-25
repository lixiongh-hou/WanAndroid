package com.example.mvpbase.annotation;

import androidx.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 12:04
 * 绑定布局资源
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindLayoutRes {
    @LayoutRes int value();
}
