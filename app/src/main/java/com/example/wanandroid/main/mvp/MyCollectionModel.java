package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.main.bean.MyCollectionBean;
import com.google.gson.JsonElement;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 15:59
 */
public class MyCollectionModel extends BaseModel {

    /**
     * 我的收藏
     * @param page
     * @param rxObserver
     */
    public void getMyCollection(String page, RxObserver<MyCollectionBean> rxObserver){
        Api.getInstance()
                .mService
                .getMyCollect(page)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }

    /**
     * 取消我的收藏文章
     * @param id
     * @param originId
     * @param rxObserver
     */
    public void unMyCollect(String id, String originId, RxObserver<JsonElement> rxObserver){
        Api.getInstance()
                .mService
                .unMyCollect(id, originId)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }
}
