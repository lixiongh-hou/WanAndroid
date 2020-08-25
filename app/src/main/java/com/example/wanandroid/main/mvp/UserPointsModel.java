package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.main.bean.UserPointsBean;
import com.example.wanandroid.main.bean.UserPointsListBean;

/**
 * @author: 雄厚
 * Date: 2020/8/20
 * Time: 9:31
 */
public class UserPointsModel extends BaseModel {

    /**
     * 用户获取积分列表
     * @param page
     * @param rxObserver
     */
    public void getUserPointsList(String page, RxObserver<UserPointsListBean> rxObserver){
        Api.getInstance()
                .mService
                .getUserPointsList(page)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);

    }

    /**
     * 用户积分
     * @param rxObserver
     */
    public void getUserPoints(RxObserver<UserPointsBean> rxObserver){
        Api.getInstance()
                .mService
                .getUserPoints()
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }
}
