package com.example.wanandroid.ui.navigation.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.ui.navigation.bean.NavigationBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/9/2
 * Time: 10:27
 */
public interface NavigationView extends BaseView {

    /**
     * 导航数据
     * @param beans
     */
    void getNavigationSuc(List<NavigationBean> beans);
}
