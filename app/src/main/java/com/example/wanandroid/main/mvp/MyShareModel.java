package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.main.bean.MyShareBean;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/24
 * Time: 10:42
 */
public class MyShareModel extends BaseModel {

    /**
     * 我的分享
     * @param page
     * @param rxObserver
     */
    public void myShare(String page, RxObserver<MyShareBean> rxObserver){
        Api.getInstance()
                .mService
                .myShare(page)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

    /**
     * 删除自己分享
     * @param id
     * @param rxObserver
     */
    public void delMyShare(String id, RxObserver<JsonElement> rxObserver){
        Api.getInstance()
                .mService
                .delMyShare(id)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

}
