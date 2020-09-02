package com.example.wanandroid.ui.navigation.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.ui.navigation.bean.NavigationBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/9/2
 * Time: 10:28
 */
public class NavigationModel extends BaseModel {

    /**
     * 导航数据
     * @param rxObserver
     */
    public void getNavigation(RxObserver<List<NavigationBean>> rxObserver){
        Api.getInstance()
                .mService
                .getNavigation()
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

}
