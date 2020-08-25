package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.SearchBean;
import com.example.wanandroid.main.bean.SearchHotKeyBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:07
 */
public interface SearchView extends BaseView {
    /**
     * 搜索热词
     * @param bean
     */
    void searchHotKeySuc(List<SearchHotKeyBean> bean);

    /**
     * 搜索关键词
     * @param bean
     */
    void searchSuc(SearchBean bean);
    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
