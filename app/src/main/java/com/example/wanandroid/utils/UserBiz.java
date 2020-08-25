package com.example.wanandroid.utils;

import android.content.Context;
import android.content.Intent;

import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.system.SPUtil;
import com.example.wanandroid.ui.login.activity.LogInActivity;
import com.example.wanandroid.ui.login.bean.LogInBean;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 14:37
 */
public class UserBiz {

    public static LogInBean getUser(){
        return SPUtil.getByClass(ConstantUtil.USER, LogInBean.class);
    }

    public static boolean loginStatus(){
        return getUser() != null;
    }
    /**
     * @return true:已登录 false:未登录,并跳登录页面
     */
    public static boolean hasLogin(Context context) {
        boolean status = UserBiz.loginStatus();
        if (!status) {
            Intent intent = new Intent();
            // 登录
            intent.setClass(context, LogInActivity.class);
            context.startActivity(intent);
        }
        return status;
    }
}
