package com.example.wanandroid.ui.home.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.example.wanandroid.ui.home.bean.BannerBase;
import com.example.wanandroid.ui.home.bean.DatasBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 13:38
 */
public interface HomeView extends BaseView {

    /**
     * 获取Banner数据
     * @param base
     */
    void getBannerSuc(List<BannerBase> base);

    /**
     * 首页文章数据
     * @param bean
     */
    void getArticleSuc(ArticleBean bean);
    /**
     * 首页文章置顶
     * @param bean
     */
    void getArticleTopSuc(List<DatasBean> bean);

    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
