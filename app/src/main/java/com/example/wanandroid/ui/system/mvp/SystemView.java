package com.example.wanandroid.ui.system.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.ui.system.bean.SystemBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 9:26
 */
public interface SystemView extends BaseView {

    /**
     * 获取体系数据
     * @param beans
     */
    void getSystemSuc(List<SystemBean> beans);
}
