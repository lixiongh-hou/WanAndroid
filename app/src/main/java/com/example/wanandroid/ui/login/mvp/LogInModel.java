package com.example.wanandroid.ui.login.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.ui.login.bean.LogInBean;

import java.util.Map;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 16:38
 */
public class LogInModel extends BaseModel {

    /**
     * 登录
     * @param map
     * @param rxObserver
     */
    public void logIn(Map<String, Object> map, RxObserver<LogInBean> rxObserver){
        Api.getInstance()
                .mService
                .logIn(map)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }
}
