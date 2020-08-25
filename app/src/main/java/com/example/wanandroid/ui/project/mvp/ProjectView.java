package com.example.wanandroid.ui.project.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.ui.project.bean.ProjectListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 13:54
 */
public interface ProjectView extends BaseView {

    /**
     * 项目列表
     * @param bean
     */
    void getProjectTreeSuc(List<WeChatNameBean> bean);

    /**
     * 项目列表数据
     * @param bean
     */
    void getProjectListSuc(ProjectListBean bean);

    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
