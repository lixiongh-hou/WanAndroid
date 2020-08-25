package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.UserPointsBean;
import com.example.wanandroid.main.bean.UserPointsListBean;

/**
 * @author: 雄厚
 * Date: 2020/8/20
 * Time: 9:30
 */
public interface UserPointsView extends BaseView {

    /**
     * 用户获取积分列表
     * @param bean
     */
    void getUserPointsListSuc(UserPointsListBean bean);

    /**
     * 获取用户积分
     * @param bean
     */
    void getUserPoints(UserPointsBean bean);
}
