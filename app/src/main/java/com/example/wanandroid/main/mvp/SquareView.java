package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.SquareListBean;

/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 15:03
 */
public interface SquareView extends BaseView {

    /**
     * 广场列表数据
     * @param bean
     */
    void getSquareListSuc(SquareListBean bean);

    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
