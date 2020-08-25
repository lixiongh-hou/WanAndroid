package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.google.gson.JsonElement;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 14:19
 */
public class FootPrintModel extends BaseModel {
    /**
     * 收藏
     * @param id
     * @param rxObserver
     */
    public void collect(String id, RxObserver<JsonElement> rxObserver){
        Api.getInstance()
                .mService
                .collect(id)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }

    /**
     * 取消收藏
     * @param id
     * @param rxObserver
     */
    public void unCollect(String id, RxObserver<JsonElement> rxObserver){
        Api.getInstance()
                .mService
                .unCollect(id)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }
}
