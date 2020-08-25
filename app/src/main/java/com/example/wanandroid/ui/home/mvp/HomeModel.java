package com.example.wanandroid.ui.home.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.example.wanandroid.ui.home.bean.BannerBase;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 13:39
 */
public class HomeModel extends BaseModel {

    /**
     * 获取Banner数据
     * @param rxObserver
     */
    public void getBanner(RxObserver<List<BannerBase>> rxObserver){
        Api.getInstance()
                .mService
                .getBanner()
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }

    /**
     * 获取首页文章数据
     * @param page
     * @param rxObserver
     */
    public void getArticle(String page, RxObserver<ArticleBean> rxObserver){
        Api.getInstance()
                .mService
                .getArticle(page)
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }

    /**
     * 获取首页文章置顶
     * @param rxObserver
     */
    public void getArticleTop(RxObserver<List<DatasBean>> rxObserver){
        Api.getInstance()
                .mService
                .getArticleTop()
                .compose(RxSchedulers.io_main())
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
