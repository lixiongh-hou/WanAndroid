package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 14:19
 */
public interface FootPrintView extends BaseView {
    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
