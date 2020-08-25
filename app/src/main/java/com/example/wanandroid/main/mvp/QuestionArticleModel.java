package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.main.bean.QuestionArticleBean;
import com.google.gson.JsonElement;

/**
 * @author: 雄厚
 * Date: 2020/8/25
 * Time: 14:31
 */
public class QuestionArticleModel extends BaseModel {

    /**
     * ，每日一问
     * @param page
     * @param rxObserver
     */
    public void getQuestionArticle(String page, RxObserver<QuestionArticleBean> rxObserver){
        Api.getInstance()
                .mService
                .getQuestionArticle(page)
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
