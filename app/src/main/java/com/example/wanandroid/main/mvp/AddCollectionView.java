package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;

/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 13:37
 */
public interface AddCollectionView extends BaseView {

    /**
     * 添加文章
     */
    void addCollectionSuc();

    /**
     * 分享文章
     */
    void shareArticlesSuc();
}
