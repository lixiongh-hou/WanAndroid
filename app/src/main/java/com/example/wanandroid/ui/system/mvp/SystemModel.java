package com.example.wanandroid.ui.system.mvp;

import com.example.mvpbase.mvp.BaseModel;
import com.example.mvpbase.rx.RxObserver;
import com.example.mvpbase.rx.RxSchedulers;
import com.example.wanandroid.api.Api;
import com.example.wanandroid.ui.system.bean.SystemBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 9:27
 */
public class SystemModel extends BaseModel {

    /**
     * 获取体系数据
     * @param rxObserver
     */
    public void getSystem(RxObserver<List<SystemBean>> rxObserver){
        Api.getInstance()
                .mService
                .getSystem()
                .compose(RxSchedulers.io_main())
                .subscribe(rxObserver);
    }
}
