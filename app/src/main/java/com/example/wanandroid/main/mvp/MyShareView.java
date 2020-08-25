package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.MyShareBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/24
 * Time: 10:41
 */
public interface MyShareView extends BaseView {

    /**
     * 我的分享
     * @param beans
     */
    void myShareSuc(MyShareBean beans);

    /**
     * 删除自己分享
     */
    void delMyShareSuc();
}
