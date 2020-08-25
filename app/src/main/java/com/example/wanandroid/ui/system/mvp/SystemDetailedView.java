package com.example.wanandroid.ui.system.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.ui.home.bean.ArticleBean;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 17:02
 */
public interface SystemDetailedView extends BaseView {

    /**
     * 体系下的数据
     * @param bean
     */
    void getSystemDetailedSuc(ArticleBean bean);
    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
