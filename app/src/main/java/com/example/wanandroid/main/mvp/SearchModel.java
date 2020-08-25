package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.main.bean.SearchBean;
import com.example.wanandroid.main.bean.SearchHotKeyBean;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:08
 */
public class SearchModel extends BaseModel {

    /**
     * 搜索热词
     * @param rxObserver
     */
    public void searchHotKey(RxObserver<List<SearchHotKeyBean>> rxObserver){
        Api.getInstance()
                .mService
                .searchHotKey()
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

    /**
     * 搜索关键词
     * @param page
     * @param k
     * @param rxObserver
     */
    public void search(String page, String k, RxObserver<SearchBean> rxObserver){
        Api.getInstance()
                .mService
                .search(page, k)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

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
