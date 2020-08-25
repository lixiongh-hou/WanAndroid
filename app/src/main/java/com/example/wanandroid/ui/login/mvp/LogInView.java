package com.example.wanandroid.ui.login.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.ui.login.bean.LogInBean;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 16:37
 */
public interface LogInView extends BaseView {

    /**
     * 登录
     * @param bean
     */
    void logInSuc(LogInBean bean);
}
