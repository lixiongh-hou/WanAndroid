package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.MyCollectionBean;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 15:57
 */
public interface MyCollectionView extends BaseView {

    /**
     * 我的收藏
     * @param bean
     */
    void getMyCollectionSuc(MyCollectionBean bean);

    /**
     * 取消收藏
     */
    void unMyCollectSuc();
}
