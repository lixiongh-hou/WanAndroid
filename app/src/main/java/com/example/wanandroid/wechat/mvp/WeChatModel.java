package com.example.wanandroid.wechat.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.wechat.bean.WeChatListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 10:01
 */
public class WeChatModel extends BaseModel {

    /**
     * 公众号名称
     * @param rxObserver
     */
    public void getWeChatName(RxObserver<List<WeChatNameBean>> rxObserver){
        Api.getInstance()
                .mService
                .getWeChatName()
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }

    /**
     * 公众号名称对应数据
     * @param id
     * @param page
     * @param rxObserver
     */
    public void getWeChatList(String id, String page, RxObserver<WeChatListBean> rxObserver){
        Api.getInstance()
                .mService
                .getWeChatList(id, page)
                .compose(RxSchedulers.io_mains())
                .subscribe(rxObserver);
    }
}
