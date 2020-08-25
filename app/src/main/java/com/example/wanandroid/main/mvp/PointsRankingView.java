package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.PointsRankingBean;
import com.example.wanandroid.main.bean.UserPointsBean;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 13:56
 */
public interface PointsRankingView extends BaseView {

    /**
     * 获取排行榜
     * @param bean
     */
    void getPointsRankingSuc(PointsRankingBean bean);

    /**
     * 获取用户积分
     * @param bean
     */
    void getUserPointsSuc(UserPointsBean bean);
}
