package com.example.wanandroid.ui.project.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.ui.project.bean.ProjectListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 13:56
 */
public class ProjectModel extends BaseModel {

    /**
     * 项目列表
     * @param rxObserver
     */
    public void getProjectTree(RxObserver<List<WeChatNameBean>> rxObserver){
        Api.getInstance()
                .mService
                .getProjectTree()
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

    /**
     * 项目列表数据
     * @param page
     * @param cid
     * @param rxObserver
     */
    public void getProjectList(String page, String cid, RxObserver<ProjectListBean> rxObserver){
        Api.getInstance()
                .mService
                .getProjectList(page, cid)
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
