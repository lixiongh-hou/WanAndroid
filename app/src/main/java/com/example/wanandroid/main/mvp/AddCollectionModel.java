package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 13:37
 */
public class AddCollectionModel extends BaseModel {

    /**
     * 添加文章
     * @param map
     * @param rxObserver
     */
    public void addCollection(Map<String, Object> map, RxObserver<JsonElement> rxObserver){
        Api.getInstance()
                .mService
                .addCollection(map)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

    /**
     * 分享文章
     * @param map
     * @param rxObserver
     */
    public void shareArticles(Map<String, Object> map, RxObserver<JsonElement> rxObserver){
        Api.getInstance()
                .mService
                .shareArticles(map)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }
}
