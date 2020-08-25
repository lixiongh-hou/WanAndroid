package com.example.wanandroid.ui.system.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.google.gson.JsonElement;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 17:03
 */
public class SystemDetailedModel extends BaseModel {

    /**
     * 体系下的数据
     * @param page
     * @param cid
     * @param rxObserver
     */
    public void getSystemDetailed(String page, String cid, RxObserver<ArticleBean> rxObserver){
        Api.getInstance()
                .mService
                .getSystemDetailed(page, cid)
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
