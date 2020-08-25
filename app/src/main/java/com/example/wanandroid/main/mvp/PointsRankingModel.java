package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.main.bean.PointsRankingBean;
import com.example.wanandroid.main.bean.UserPointsBean;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 13:57
 */
public class PointsRankingModel extends BaseModel {

    /**
     * 获取排行榜
     * @param page
     * @param rxObserver
     */
    public void getPointsRanking(String page, RxObserver<PointsRankingBean> rxObserver){
        Api.getInstance()
                .mService
                .getPointsRanking(page)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

    /**
     * 获取用户积分
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
